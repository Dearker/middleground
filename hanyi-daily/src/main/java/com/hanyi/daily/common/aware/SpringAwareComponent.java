package com.hanyi.daily.common.aware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.util.Map;

/**
 * @PackAge: middleground com.hanyi.daily.common.aware
 * @Author: weiwenchang
 * @Description: 获取spring底层的bean
 * @CreateDate: 2020-03-03 22:42
 * @Version: 1.0
 */
@Slf4j
@Component
public class SpringAwareComponent implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    private StringValueResolver stringValueResolver;

    /**
     * 获取当前bean的名称
     *
     * @param s
     */
    @Override
    public void setBeanName(String s) {
        log.info("获取当前组件的名称: {}", s);
    }

    /**
     * 获取spring的ioc容器
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 值解析器，用于解析字符串中各种表达式的内容
     *
     * @param stringValueResolver
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
        String resolveStringValue = stringValueResolver.resolveStringValue("你好 ${os.name} 我是 #{20*18}");
        log.info("解析的字符串：" + resolveStringValue);
    }

    /**
     * 获取指定接口的所有实现类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Map<String, T> getTargetInterfaceAllImplementor(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 发布事件
     *
     * @param event
     */
    public void applicationPublishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    /**
     * 事件的监听
     *
     * @param event
     */
    @EventListener(classes = {ApplicationEvent.class})
    public void listener(ApplicationEvent event) {
        log.info("SpringAwareComponent。。监听到的事件：{}", event);
    }

}
