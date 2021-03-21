package com.hanyi.daily.thread.pojo;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author weiwenchang
 * @since 2020-03-01
 */
@RequiredArgsConstructor
public class Athlete implements Callable<Integer> {

    private final Integer name;

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "柯基：" + name + " 号" + "就位");
        TimeUnit.SECONDS.sleep(1);
        return name;
    }
}
