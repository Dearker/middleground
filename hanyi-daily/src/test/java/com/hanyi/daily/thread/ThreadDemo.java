package com.hanyi.daily.thread;

import org.junit.Test;

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


}
