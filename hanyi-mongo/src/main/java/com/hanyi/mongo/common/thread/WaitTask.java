package com.hanyi.mongo.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @PackAge: middleground com.hanyi.mongo.common.thread
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-11 21:46
 * @Version: 1.0
 */
@Slf4j
public class WaitTask implements Runnable{

    private AtomicInteger atomicInteger;

    public WaitTask(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
            atomicInteger.incrementAndGet();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
