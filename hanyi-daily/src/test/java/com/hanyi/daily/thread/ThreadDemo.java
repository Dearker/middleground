package com.hanyi.daily.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.daily.thread.pojo.Athlete;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @PackAge: middleground com.hanyi.daily.thread
 * @Author: weiwenchang
 * @Description: 线程测试类
 * @CreateDate: 2020-02-09 16:47
 * @Version: 1.0
 */
public class ThreadDemo {

    /**
     * join 子线程先执行，执行完成后再执行主线程
     *
     * @throws InterruptedException
     */
    @Test
    public void joinTest() throws InterruptedException {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                System.out.println("子线程: " + i);
            }
        });
        thread.start();
        thread.join();

        for (int i = 0; i < 30; i++) {
            System.out.println("主线程: " + i);
        }
    }

    /**
     * 初始化线程池的核心线程数最好为需要执行的任务数量，在最开始的时候初始化完成，
     * 后续无需再进行创建线程，效率最高，不过还需要考虑服务器是IO密集型还是CPU密集型
     * <p>
     * 频繁的在同一时间创建线程池，如果创建的线程数相同，则会使用当前存活的线程；
     * 如果创建的线程数每次都不一样，实际上是用当前存活的线程数进行累加
     * <p>
     * 多个线程异步执行，在将各线程执行的结果汇总
     */
    @Test
    public void multithreadedAsyncCallback() throws Exception {

        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(32, 32);

        System.out.println("当前线程总数：" + threadPoolExecutor.getMaximumPoolSize());
        TimeInterval timer = DateUtil.timer();
        int length = 32;

        List<Integer> integerList = new ArrayList<>(length);

        List<Athlete> personList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            personList.add(new Athlete(i));
        }
        List<Future<Integer>> futureList = threadPoolExecutor.invokeAll(personList, 5, TimeUnit.MINUTES);

        for (Future<Integer> future : futureList) {
            integerList.add(future.get());
        }

        System.out.println("执行耗时--》 " + timer.intervalRestart());
        System.out.println("获取的数组为：" + integerList);
        int sum = integerList.stream().mapToInt(Integer::intValue).sum();
        System.out.println("总数为：" + sum);

    }

    /**
     * 定时线程池,设置守护线程，当JVM的用户线程退出时，守护线程会舍弃掉任务直接退出
     */
    @Test
    public void scheduledExecutorServiceTest() {
        TimeInterval timer = DateUtil.timer();

        AtomicInteger atomicInteger = new AtomicInteger(0);

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2, r -> {
            Thread thread = new Thread(r);
            //设置为守护线程
            thread.setDaemon(true);
            thread.setName("scheduled_" + atomicInteger.incrementAndGet());
            return thread;
        });

        executorService.schedule(() -> System.out.println(Thread.currentThread().getName()), 2, TimeUnit.MILLISECONDS);
        System.out.println("消耗时间：" + timer.intervalMs());
    }

}
