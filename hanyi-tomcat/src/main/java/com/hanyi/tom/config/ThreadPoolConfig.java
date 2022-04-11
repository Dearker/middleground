package com.hanyi.tom.config;

import cn.hutool.core.thread.ThreadUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 线程池配置类
 * </p>
 *
 * @author wenchangwei
 * @since 2022/4/11 8:29 下午
 */
@Configuration
public class ThreadPoolConfig implements WebMvcConfigurer {

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(PROCESSORS, PROCESSORS * 2);
        threadPoolExecutor.setThreadFactory(ThreadUtil.newNamedThreadFactory("pool-task-", true));
        return threadPoolExecutor;
    }

    /**
     * 该线程池在Controller中返回值为Callable时会使用
     *
     * @return 线程池对象
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(PROCESSORS);
        threadPoolTaskExecutor.setMaxPoolSize(PROCESSORS * 2 + 1);
        threadPoolTaskExecutor.setQueueCapacity(25);
        threadPoolTaskExecutor.setKeepAliveSeconds(200);
        threadPoolTaskExecutor.setDaemon(true);
        threadPoolTaskExecutor.setThreadNamePrefix("thread-pool-");
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 处理callable超时
        configurer.setDefaultTimeout(60 * 1000);
        configurer.setTaskExecutor(this.threadPoolTaskExecutor());
        configurer.registerCallableInterceptors(timeoutCallableProcessingInterceptor());
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutCallableProcessingInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

}
