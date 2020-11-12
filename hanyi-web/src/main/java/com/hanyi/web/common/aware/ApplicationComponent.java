package com.hanyi.web.common.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:53 下午 2020/7/24
 */
@Component
public class ApplicationComponent implements ApplicationContextAware {

    private ApplicationContext applicationContext;

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
     * 获取指定接口的所有实现类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Map<String, T> getTargetInterfaceAllImplementor(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }


}
