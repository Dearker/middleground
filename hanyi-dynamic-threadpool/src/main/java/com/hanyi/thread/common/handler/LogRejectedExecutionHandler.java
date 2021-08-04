package com.hanyi.thread.common.handler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

/**
 * <p>
 * 日志拒绝执行处理程序
 * </p>
 *
 * @author wenchangwei
 * @since 2021/8/4 8:39 下午
 */
public class LogRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        Thread thread = Thread.currentThread();
        String name = thread.getName();
        System.out.println("当前异常线程名称：" + name);

        Stream.of(thread.getStackTrace()).forEach(s -> System.out.println("     " + s));
        throw new RuntimeException("Task " + r.toString() +
                " rejected from " +
                executor.toString());
    }
}
