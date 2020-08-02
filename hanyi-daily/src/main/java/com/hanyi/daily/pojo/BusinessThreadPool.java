package com.hanyi.daily.pojo;

import lombok.Data;

/**
 * <p>
 * 线程池实体类
 * </p>
 *
 * @author wenchangwei
 * @since 8:47 下午 2020/7/31
 */
@Data
public class BusinessThreadPool {

    /**
     * 表示线程池核心线程，正常情况下开启的线程数量
     */
    private int corePoolSize = 5;

    /**
     * 如果queueCapacity存满了，还有任务就会启动更多的线程，
     * 直到线程数达到maxPoolSize。如果还有任务，则根据拒绝策略进行处理
     */
    private int maxPoolSize = 10;

    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = "thread-pool-business";

}
