package com.hanyi.thread.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * 自定义耗时统计线程池
 * </p>
 *
 * @author wenchangwei
 * @since 2021/8/5 10:02 下午
 */
@Slf4j
public class TimingThreadPoolExecutor extends ThreadPoolExecutor {

    /**
     * 开始时间
     */
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 任务总数
     */
    private final AtomicLong numTasks = new AtomicLong();

    /**
     * 总时间
     */
    private final AtomicLong totalTime = new AtomicLong();


    public TimingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                    BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 在给定的线程中执行给定的Runnable之前调用方法
     *
     * @param t 线程对象
     * @param r 线程任务
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info(String.format("Thread %s: start %s", t, r));
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 完成指定Runnable的执行后调用方法
     *
     * @param r 线程任务
     * @param t 异常
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.currentTimeMillis();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            String threadName = Thread.currentThread().getName();
            log.info(String.format("Thread %s: end %s, time=%dms", threadName, r, taskTime));
            if (Objects.nonNull(t)) {
                log.error(String.format("thread %s have the Throwable %s", threadName, t));
            }
        } finally {
            super.afterExecute(r, t);
        }
    }

    /**
     * 执行程序已终止时调用方法
     */
    @Override
    protected void terminated() {
        try {
            log.info(String.format("Terminated: avg time=%dns", totalTime.get() / numTasks.get()));
        } finally {
            super.terminated();
            startTime.remove();
        }
    }
}
