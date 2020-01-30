package com.hanyi.demo.common.thread;

/**
 * @ClassName: middleground com.hanyi.demo.common.thread EventLoggingTask
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 21:18
 * @Version: 1.0
 */
public class EventLoggingTask implements Runnable{

    @Override
    public void run() {
        System.out.println("this is Runnable接口实现");
    }

}
