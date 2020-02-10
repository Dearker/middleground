package com.hanyi.daily.thread;

import com.hanyi.daily.thread.pojo.ThreadVolatile;

/**
 * @PackAge: middleground com.hanyi.daily.thread
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-09 21:55
 * @Version: 1.0
 */
public class VolatileTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadVolatile threadVolatile = new ThreadVolatile();
        threadVolatile.start();
        Thread.sleep(3000);
        threadVolatile.setRuning(false);
        System.out.println("flag 已经设置成false");
        Thread.sleep(1000);
        System.out.println(threadVolatile.flag);
    }

}
