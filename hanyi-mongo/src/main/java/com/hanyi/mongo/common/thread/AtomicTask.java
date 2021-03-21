package com.hanyi.mongo.common.thread;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:45 下午 2020/6/7
 */
@RequiredArgsConstructor
public class AtomicTask implements Callable<Integer> {

    private final AtomicInteger atomicInteger;

    @Override
    public Integer call() throws Exception {
        atomicInteger.getAndIncrement();
        TimeUnit.SECONDS.sleep(1);
        return null;
    }
}
