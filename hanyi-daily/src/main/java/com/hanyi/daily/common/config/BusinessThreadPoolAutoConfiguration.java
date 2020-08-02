package com.hanyi.daily.common.config;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import com.hanyi.daily.common.property.BusinessThreadPoolProperties;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 业务线程池自动配置类
 * </p>
 *
 * @author wenchangwei
 * @since 8:54 下午 2020/7/31
 */
@Configuration
//@EnableConfigurationProperties(BusinessThreadPoolProperties.class)
public class BusinessThreadPoolAutoConfiguration {

    /**
     * 构建线程池
     *
     * @param beanFactory ioc容器
     * @param properties  线程池配置类
     *//*
    public BusinessThreadPoolAutoConfiguration(ConfigurableListableBeanFactory beanFactory, BusinessThreadPoolProperties properties) {
        this.initThreadPool(beanFactory, properties);
    }*/

    /**
     * 初始化线程池
     *
     * @param beanFactory ioc容器
     * @param properties  线程池配置类
     */
    private void initThreadPool(ConfigurableListableBeanFactory beanFactory, BusinessThreadPoolProperties properties) {
        properties.getThreadPools().forEach((k, v) -> {
            ThreadPoolExecutor threadPoolExecutor = newThreadPoolExecutor(v.getThreadNamePrefix(), v.getCorePoolSize(), v.getMaxPoolSize());
            beanFactory.registerSingleton(k, threadPoolExecutor);
        });
    }

    /**
     * 创建线程池
     *
     * @param namePrefix 线程名称前缀
     * @param coreSize   核心数
     * @param maxSize    最大线程数
     * @return ThreadPoolExecutor
     */
    private static ThreadPoolExecutor newThreadPoolExecutor(String namePrefix, int coreSize, int maxSize) {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(namePrefix, false);
        return ExecutorBuilder.create()
                .setThreadFactory(namedThreadFactory)
                .setCorePoolSize(coreSize)
                .setMaxPoolSize(maxSize)
                .build();
    }

}
