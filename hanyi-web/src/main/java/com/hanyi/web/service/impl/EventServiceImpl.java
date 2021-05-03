package com.hanyi.web.service.impl;

import com.hanyi.web.bo.EmailEvent;
import com.hanyi.web.bo.WeCatEvent;
import com.hanyi.web.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/1 6:38 下午
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    /**
     * 应用上下文
     */
    private final ApplicationContext applicationContext;

    /**
     * 发布事件
     *
     * @return 返回发布结果
     */
    @Override
    public String publish() {
        applicationContext.publishEvent(new WeCatEvent(applicationContext,"柯基"));
        applicationContext.publishEvent(new EmailEvent(applicationContext,"哈士奇"));
        applicationContext.publishEvent(new ApplicationEvent("柴犬一号"){
            private static final long serialVersionUID = -8790439947553341449L;
        });
        return "发布成功";
    }
}
