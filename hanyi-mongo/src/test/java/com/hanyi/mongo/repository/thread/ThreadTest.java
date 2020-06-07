package com.hanyi.mongo.repository.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.mongo.common.thread.AtomicTask;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:48 下午 2020/6/7
 */
public class ThreadTest {

    private static final ThreadPoolExecutor THREADPOOLEXECUTOR = ThreadUtil.newExecutor(10, 10);

    @Test
    public void atomicTest() throws InterruptedException {

        TimeInterval timer = DateUtil.timer();
        AtomicInteger atomicInteger = new AtomicInteger(0);

        List<AtomicTask> atomicTaskList = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            atomicTaskList.add(new AtomicTask(atomicInteger));
        }

        THREADPOOLEXECUTOR.invokeAll(atomicTaskList);

        System.out.println("获取的总数：" + atomicInteger.get());
        System.out.println("总共耗时：" + timer.intervalRestart());

    }

}
