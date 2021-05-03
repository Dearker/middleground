package com.hanyi.web.common.component;

import com.hanyi.web.bo.EmailEvent;
import com.hanyi.web.bo.WeCatEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 事件监听组件
 * </p>
 *
 * @author wenchangwei
 * @since 2021/4/30 10:21 下午
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventListenerComponent {

    /**
     * 去除重复的事件
     */
    private final Set<ApplicationEvent> applicationEventSet = new HashSet<>(Integer.BYTES);

    /**
     * 应用上下文
     */
    private final ApplicationContext applicationContext;

    /**
     * 监听消息事件
     *
     * @param weCatEvent 消息事件
     */
    @Order(1)
    @EventListener
    public void messageEvent(WeCatEvent weCatEvent) {
        //判断当前上下文是否一致，可能会存在父子上下文的情况，getSource()用于获取事件来源
        boolean safeEquals = ObjectUtils.nullSafeEquals(applicationContext, weCatEvent.getSource());

        applicationEventSet.add(weCatEvent);
        if (!safeEquals) {
            if (log.isDebugEnabled()) {
                log.debug("The source of event[" + weCatEvent.getSource() + "] is not original!");
            }
        }

        log.info(weCatEvent.getContext());
    }

    /**
     * 监听邮箱事件
     *
     * @param emailEvent 邮箱事件
     */
    @Order(2)
    @EventListener
    public void emailEvent(EmailEvent emailEvent) {
        applicationEventSet.add(emailEvent);
        log.info(emailEvent.getContext());
    }

    /**
     * 监听应用事件
     *
     * @param applicationEvent 应用事件
     */
    @Order(3)
    @EventListener
    public void applicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEventSet.add(applicationEvent)) {
            log.info("触发 EventListenerComponent 的 applicationEvent事件：" + applicationEvent);
        }
        applicationEventSet.clear();
    }

}
