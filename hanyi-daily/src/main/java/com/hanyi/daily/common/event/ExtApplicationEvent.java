package com.hanyi.daily.common.event;

import com.hanyi.daily.pojo.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 事件处理器，默认按文件排序的顺序执行，可使用@Order注解来控制执行的顺序
 * </p>
 *
 * @author wenchangwei
 * @since 10:54 上午 2021/4/4
 */
@Slf4j
@Order(1)
@Component
public class ExtApplicationEvent implements ApplicationListener<MessageEvent> {

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(MessageEvent event) {
        log.info("ExtApplicationEvent 事件触发：{},当前线程名称：{}", event.getName(), Thread.currentThread().getName());
    }
}
