package com.hanyi.thread.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 调度器任务信息
 *
 * @author wcwei@iflytek.com
 * @since 2021-08-03 14:18
 */
@Data
public class SchedulerTaskInfo implements Serializable {

    /**
     * 池大小
     */
    private int poolSize;

    /**
     * 活跃的数
     */
    private int activeCount;

    /**
     * 线程优先级
     */
    private int threadPriority;

    /**
     * 线程名称前缀
     */
    private String threadNamePrefix;

}
