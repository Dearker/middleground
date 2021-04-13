package com.hanyi.cache.common.component;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 定时组件
 * </p>
 *
 * @author wenchangwei
 * @since 9:34 下午 2021/4/13
 */
@Slf4j
@Component
public class ScheduleComponent {

    /**
     * 0/1 * * * * ? 每秒执行一次
     *
     * 凌晨1点执行
     */
    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduledTask1() {
        log.info(Thread.currentThread().getName() + "---scheduledTask1 " + DateUtil.date());
    }

    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduledTask2() {
        log.info(Thread.currentThread().getName() + "---scheduledTask2 " + DateUtil.date());
    }

}
