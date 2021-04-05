package com.hanyi.daily.common.event;

import com.hanyi.daily.pojo.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 监听事件组件
 * </p>
 *
 * @author wenchangwei
 * @since 9:51 下午 2021/4/5
 */
@Slf4j
@Component
public class ExtListenerEvent {

    @EventListener(classes = {MessageEvent.class})
    public void applicationEvent(MessageEvent messageEvent) {
        log.info("ExtListenerEvent 的 applicationEvent 获取到事件：{}", messageEvent.getName());
    }

    @EventListener(classes = {MessageEvent.class})
    public void otherEvent(MessageEvent messageEvent) {
        log.info("ExtListenerEvent 的 otherEvent 获取到事件：{}", messageEvent.getName());
    }

}
