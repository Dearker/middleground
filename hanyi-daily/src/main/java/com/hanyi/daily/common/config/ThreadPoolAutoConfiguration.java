package com.hanyi.daily.common.config;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import com.hanyi.daily.common.annotation.EnableThreadPool;
import com.hanyi.daily.common.property.ThreadPoolProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
 * @author wenchangwei@wistronits.com
 * @since 11:21 2020/7/24
 */
@Configuration
@ConditionalOnClass(EnableThreadPool.class)
@ConditionalOnBean(name = {"threadPoolConfigurationImportSelector"})
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ExecutorService getInspectThreadPoolExecutor(ThreadPoolProperties threadPoolProperties) {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(threadPoolProperties.getThreadNamePrefix(), false);
        System.out.println("线程池加载完成。。。");
        return ExecutorBuilder.create().setThreadFactory(namedThreadFactory)
                .setCorePoolSize(threadPoolProperties.getCorePoolSize())
                .setMaxPoolSize(threadPoolProperties.getMaxPoolSize()).build();
    }

}
