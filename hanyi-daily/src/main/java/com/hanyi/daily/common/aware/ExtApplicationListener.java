package com.hanyi.daily.common.aware;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @PackAge: middleground com.hanyi.daily.common.aware
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-08 14:30
 * @Version: 1.0
 */
@Component
public class ExtApplicationListener implements ApplicationListener<ApplicationEvent> {

    /**
     * 当容器中发布此事件以后，方法触发
     *
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("收到事件：" + applicationEvent);
    }

}
