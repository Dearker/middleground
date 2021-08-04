package com.hanyi.thread.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
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
    @Getter
    @Setter
    private ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        if (Objects.nonNull(this.future)) {
            this.future.cancel(true);
        }
    }

}
