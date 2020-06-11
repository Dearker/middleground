package com.hanyi.daily.thread.pojo;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author weiwenchang
 * @since 2020-03-01
 */
public class Athlete implements Callable<Integer> {

    private final Integer name;

    public Athlete(Integer name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "柯基：" + name + " 号" + "就位");
        TimeUnit.SECONDS.sleep(1);
        return name;
    }
}
