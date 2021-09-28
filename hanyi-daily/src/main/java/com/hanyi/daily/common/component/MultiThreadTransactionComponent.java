package com.hanyi.daily.common.component;

import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.daily.pojo.TransactionCallable;
import com.hanyi.daily.pojo.TransactionRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 多线程事务主键
 *
 * @author wcwei@iflytek.com
 * @since 2021-09-27 18:06
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MultiThreadTransactionComponent {

    /**
     * 线程池的大小
     */
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池执行人
     */
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = ThreadUtil.newExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE);

    /**
     * 供应商列表
     */
    private static final List<Callable<Object>> SUPPLIER_LIST = new ArrayList<>();

    /**
     * 平台事务管理程序
     */
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 事务模板
     */
    private final TransactionTemplate transactionTemplate;

    /**
     * 添加要异步执行的方法程序
     *
     * @param supplier 线程函数
     */
    public void addFunction(Callable<Object> supplier) {
        SUPPLIER_LIST.add(new TransactionCallable(platformTransactionManager, supplier));
    }

    /**
     * 执行事务方法
     *
     * @return 返回执行结果集合
     */
    public List<?> execute() {
        List<Future<Object>> futures = null;
        try {
            futures = THREAD_POOL_EXECUTOR.invokeAll(SUPPLIER_LIST);
        } catch (InterruptedException e) {
            log.error(" mutilate transaction execute fail：", e);
        }
        return futures;
    }

    /**
     * 执行事务方法
     *
     * @param task 任务
     */
    public void execute(Runnable task) {
        THREAD_POOL_EXECUTOR.execute(new TransactionRunnable(transactionTemplate, task));
    }

}
