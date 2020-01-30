package com.hanyi.demo.common.thread;

import java.util.concurrent.Callable;

/**
 * @ClassName: middleground com.hanyi.demo.common.thread CallableTask
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 21:25
 * @Version: 1.0
 */
public class CallableTask implements Callable<Integer> {

    private static final Integer TOTAL = 100;

    @Override
    public Integer call() {

        int count = 0;

        for (int i = 0; i < TOTAL; i++) {
            count += i;
        }

        return count;
    }
}
