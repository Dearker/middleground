package com.hanyi.daily.common.atomic;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @PackAge: middleground com.hanyi.daily.common.atomic
 * @Author: weiwenchang
 * @Description: atomic原子类
 * @CreateDate: 2020-02-22 12:00
 * @Version: 1.0
 */
public class AtomicIntegerCount implements Runnable {

    /**
     * getAndAdd(int)    //增加指定的数据，返回变化前的数据
     * getAndDecrement() //减少1，返回减少前的数据
     * getAndIncrement() //增加1，返回增加前的数据
     * getAndSet(int)    //设置指定的数据，返回设置前的数据
     * addAndGet(int)    //增加指定的数据后返回增加后的数据
     * decrementAndGet() //减少1，返回减少后的值
     * incrementAndGet() //增加1，返回增加后的值
     * lazySet(int)      //仅仅当get时才会set
     * compareAndSet(int, int)//尝试新增后对比，若增加成功则返回true否则返回 false
     */
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
        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(2, 2);
        threadPoolExecutor.submit(atomicIntegerCount);
        threadPoolExecutor.submit(atomicIntegerCount);
        threadPoolExecutor.shutdown();
    }

}
