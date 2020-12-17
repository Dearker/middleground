package com.hanyi.daily.thread.pojo;

import lombok.Getter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * 并行流使用实体类
 * </p>
 *
 * @author wenchangwei
 * @since 9:44 下午 2020/12/17
 */
public class Accumulator {

    private int number;

    @Getter
    private final AtomicLong total = new AtomicLong(0);

    public Accumulator() {
    }

    public Accumulator(int number) {
        this.number = number;
    }

    public void add(long value) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        total.addAndGet(value);
    }

    public void clear() {
        total.set(0);
    }

    public int getNumber() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number;
    }

}
