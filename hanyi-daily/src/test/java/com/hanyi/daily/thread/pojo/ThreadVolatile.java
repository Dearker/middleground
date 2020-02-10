package com.hanyi.daily.thread.pojo;

/**
 * @PackAge: middleground com.hanyi.daily.thread
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-09 21:50
 * @Version: 1.0
 */
public class ThreadVolatile extends Thread {

    public volatile boolean flag = true;

    @Override
    public void run() {
        System.out.println("开始执行子线程....");
        while (flag) {
        }
        System.out.println("线程停止");
    }

    public void setRuning(boolean flag) {
        this.flag = flag;
    }

}
