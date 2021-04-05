package com.hanyi.daily.common.event;

import com.hanyi.daily.pojo.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 其他事件
 * </p>
 *
 * @author wenchangwei
 * @since 11:53 上午 2021/4/4
 */
@Slf4j
@Order(2)
@Component
public class ExtOtherEvent implements ApplicationListener<MessageEvent> {
    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(MessageEvent event) {
        log.info("ExtOtherEvent 获取到事件：{},当前线程名称：{}", event.getName(), Thread.currentThread().getName());
    }
}
