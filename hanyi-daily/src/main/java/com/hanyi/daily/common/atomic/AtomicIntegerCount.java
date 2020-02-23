package com.hanyi.daily.common.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @PackAge: middleground com.hanyi.daily.common.atomic
 * @Author: weiwenchang
 * @Description: atomic原子类
 * @CreateDate: 2020-02-22 12:00
 * @Version: 1.0
 */
public class AtomicIntegerCount implements Runnable {

    private static Integer count = 1;
    private static AtomicInteger atomic = new AtomicInteger();

    @Override
    public void run() {
        while (true) {
            int count = getCountAtomic();
            System.out.println(count);
            if (count >= 150) {
                break;
            }
        }
    }

    public synchronized Integer getCount() {
        try {
            Thread.sleep(50);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return count++;
    }

    private Integer getCountAtomic() {
        try {
            Thread.sleep(50);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return atomic.incrementAndGet();
    }

    public static void main(String[] args) {
        AtomicIntegerCount atomicIntegerCount = new AtomicIntegerCount();
        Thread t1 = new Thread(atomicIntegerCount);
        Thread t2 = new Thread(atomicIntegerCount);
        t1.start();
        t2.start();
    }

}
