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
 *
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

    @Bean
    @ConditionalOnMissingBean
    public ExecutorService executorService(ThreadPoolProperties threadPoolProperties) {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(threadPoolProperties.getThreadNamePrefix(), false);
        return ExecutorBuilder.create().setThreadFactory(namedThreadFactory)
                .setCorePoolSize(threadPoolProperties.getCorePoolSize())
                .setMaxPoolSize(threadPoolProperties.getMaxPoolSize()).build();
    }

}
