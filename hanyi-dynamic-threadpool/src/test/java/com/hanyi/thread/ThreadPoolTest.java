package com.hanyi.thread;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import com.hanyi.thread.common.handler.LogRejectedExecutionHandler;
import com.hanyi.thread.domain.TimingThreadPoolExecutor;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 线程池测试
 *
 * @author wcwei@iflytek.com
 * @since 2021-08-04 10:07
 */
public class ThreadPoolTest {

    @Test
    public void traceTest() {
        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(4, 4);

        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 5, 10, TimeUnit.MINUTES, new LinkedBlockingDeque<>(), r -> {
            Thread thread = new Thread(r);
            //设置为守护线程
            thread.setDaemon(true);
            thread.setName("scheduled_" + atomicInteger.incrementAndGet());
            return thread;
        });

        threadPoolExecutor.prestartAllCoreThreads();
        poolExecutor.prestartAllCoreThreads();

        ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMBean.dumpAllThreads(true, true);
        Stream.of(threadInfos).forEach(s -> {
            System.out.println("dump all thread: " + s.getThreadName());
            Stream.of(s.getStackTrace()).forEach(a -> System.out.println("       " + a));
        });

        System.out.println("----------------------------------------");
        long[] allThreadIds = threadMBean.getAllThreadIds();
        ThreadInfo[] threadInfo = threadMBean.getThreadInfo(allThreadIds);

        //Stream.of(threadInfo).forEach(s -> System.out.println("thread info: " + s.getStackTrace()));
        ThreadUtil.sleep(1, TimeUnit.MINUTES);
    }

    /**
     * interrupt方法会等到当前运行的逻辑结束后再检查是否中断，
     * stop会强行终止线程，该操作不安全，可能会导致数据不一致问题
     */
    @Test
    public void stopThreadTest() {
        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(4, 4);
        List<Long> threadIdList = new ArrayList<>();

        threadPoolExecutor.execute(() -> {
            DateTime date = DateUtil.date();
            threadIdList.add(Thread.currentThread().getId());
            System.out.println("当前线程名称：" + Thread.currentThread().getId());
            System.out.println("当前时间：" + date);

            ThreadUtil.sleep(5, TimeUnit.SECONDS);
            System.out.println("当前时间2：" + DateUtil.date());
        });

        threadPoolExecutor.execute(() -> {
            threadIdList.add(Thread.currentThread().getId());
            System.out.println("当前线程名称：" + Thread.currentThread().getId());

            System.out.println("11111111111");
            ThreadUtil.sleep(5, TimeUnit.SECONDS);
            System.out.println("222222222222");
        });

        ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
        Thread[] threads = ThreadUtil.getThreads();
        //线程集合
        Map<Long, Thread> longThreadMap = Stream.of(threads).collect(Collectors.toMap(Thread::getId, Function.identity()));

        List<Long> collect = Stream.of(threads).map(Thread::getId).sorted().collect(Collectors.toList());
        //System.out.println("线程id集合：" + collect);
        long[] allThreadIds = threadMBean.getAllThreadIds();
        Arrays.sort(allThreadIds);
        //System.out.println("通过threadMBean获取线程id集合：" + Arrays.toString(allThreadIds));

        System.out.println("启动的线程集合：" + threadIdList);
        Long firstThreadId = threadIdList.get(0);
        //停止线程
        longThreadMap.get(firstThreadId).interrupt();
    }

    /**
     * 自定义拒绝策略测试
     */
    @Test
    public void threadPoolExceptionTest() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1,
                TimeUnit.MINUTES, new LinkedBlockingDeque<>(1), new LogRejectedExecutionHandler());

        for (int i = 0; i < 2; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                ThreadUtil.sleep(1, TimeUnit.HOURS);
            });
        }
        ThreadUtil.sleep(1, TimeUnit.HOURS);
    }

    @Test
    public void threadPoolUseInfoTest() {
        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(5, 10);
        threadPoolExecutor.prestartAllCoreThreads();

        double div = NumberUtil.div(threadPoolExecutor.getActiveCount(), threadPoolExecutor.getMaximumPoolSize(), 2);
        System.out.println("线程池活跃度：" + div);

        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        int size = queue.size();
        System.out.println("当前排队线程数：" + size);

        int remainingCapacity = queue.remainingCapacity();
        System.out.println("队列剩余大小：" + remainingCapacity);
        int queueTotalSize = size + remainingCapacity;
        System.out.println("队列大小：" + queueTotalSize);

        double div1 = NumberUtil.div(size, queueTotalSize, 2);
        System.out.println("队列使用率：" + div1);
    }

    /**
     * 通过自定义线程池统计线程执行时间
     */
    @Test
    public void threadUseTimeTest() {
        ThreadPoolExecutor threadPoolExecutor = new TimingThreadPoolExecutor(1, 2, 1,
                TimeUnit.MINUTES, new LinkedBlockingDeque<>(1), new NamedThreadFactory("test-", true),
                new LogRejectedExecutionHandler());

        for (int i = 0; i < 3; i++) {
            threadPoolExecutor.execute(() -> {
                String name = Thread.currentThread().getName();
                System.out.println(name);
                ThreadUtil.sleep(5, TimeUnit.SECONDS);
            });
        }

        ThreadUtil.sleep(11, TimeUnit.SECONDS);
    }

}
