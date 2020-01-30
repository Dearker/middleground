package com.hanyi.demo.common.thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * @ClassName: middleground com.hanyi.demo.common.thread ThreadDemo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 21:20
 * @Version: 1.0
 */
public class ThreadDemo {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Test
    public void RunnableTest() throws Exception {

        Future future = executorService.submit(new EventLoggingTask());
        executorService.shutdown();
        System.out.println("获取的数据-->" + future.get());

    }

    @Test
    public void CallableTest() throws Exception {

        CallableTask task = new CallableTask();
        Future<Integer> future = executorService.submit(task);

        assertEquals(4950, future.get().intValue());
    }

}
