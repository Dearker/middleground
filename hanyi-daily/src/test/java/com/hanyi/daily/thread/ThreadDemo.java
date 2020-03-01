package com.hanyi.daily.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.daily.thread.pojo.Athlete;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @PackAge: middleground com.hanyi.daily.thread
 * @Author: weiwenchang
 * @Description: 线程测试类
 * @CreateDate: 2020-02-09 16:47
 * @Version: 1.0
 */
public class ThreadDemo {

    /**
     * 初始化线程池
     */
    private static final ThreadPoolExecutor EXECUTOR = ThreadUtil.newExecutor(8, 16);

    /**
     * join 子线程先执行，执行完成后再执行主线程
     * @throws InterruptedException
     */
    @Test
    public void joinTest() throws InterruptedException {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                System.out.println("子线程: " + i);
            }
        });
        thread.start();
        thread.join();

        for (int i = 0; i < 30; i++) {
            System.out.println("主线程: " + i);
        }
    }

    /**
     * 多个线程异步执行，在将各线程执行的结果汇总
     */
    @Test
    public void multithreadedAsyncCallback() throws Exception {

        TimeInterval timer = DateUtil.timer();
        int length = 8;

        List<Integer> integerList = new ArrayList<>(length);

        List<Athlete> personList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            personList.add(new Athlete(i));

        }
        List<Future<Integer>> futureList = EXECUTOR.invokeAll(personList);

        for (Future<Integer> future : futureList) {
            integerList.add(future.get());
        }

        System.out.println("执行耗时--》 "+ timer.intervalRestart());
        System.out.println("获取的数组为："+ integerList);
        int sum = integerList.stream().mapToInt(Integer::intValue).sum();
        System.out.println("总数为："+sum);

    }



}
