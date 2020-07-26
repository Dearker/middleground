package com.hanyi.ordinary.common.configuration;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import com.hanyi.ordinary.common.annotation.ConditionalOnThreadPool;
import com.hanyi.ordinary.common.annotation.EnableThreadPool;
import com.hanyi.ordinary.common.property.ThreadPoolProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

/**
 * <p>
 * 线程池自动配置类
 * </p>
 *
 * @author wenchangwei
 * @since 10:04 下午 2020/7/24
 */
@Configuration
@ConditionalOnClass(EnableThreadPool.class)
@ConditionalOnThreadPool(EnableThreadPool.class)
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolAutoConfiguration {

    /**
     * 线程池bean对象
     *
     * @param threadPoolProperties 线程池属性
     * @return 返回线程池对象
     */
    @Bean
    @ConditionalOnMissingBean
    public ExecutorService executorService(ThreadPoolProperties threadPoolProperties) {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(threadPoolProperties.getThreadNamePrefix(), false);
        return ExecutorBuilder.create().setThreadFactory(namedThreadFactory)
                .setCorePoolSize(threadPoolProperties.getCorePoolSize())
                .setMaxPoolSize(threadPoolProperties.getMaxPoolSize()).build();
    }

}
