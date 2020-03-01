package com.hanyi.daily.thread.pojo;

import java.util.concurrent.Callable;

/**
 * @author weiwenchang
 * @since 2020-03-01
 */
public class Athlete implements Callable<Integer> {

    private Integer name;

    public Athlete( Integer name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"柯基："+name+" 号" + "就位");
        Thread.sleep(1000);
        return name;
    }
}
