package com.hanyi.thread.domain;

import java.util.concurrent.ScheduledFuture;

/**
 * 计划任务
 *
 * @author wcwei@iflytek.com
 * @since 2021-08-03 10:23
 */
public final class ScheduledTask {

    /**
     * 调度结果
     */
    public volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }

}
