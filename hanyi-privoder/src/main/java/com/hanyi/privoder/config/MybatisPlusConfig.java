package com.hanyi.privoder.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @PackAge: middleground com.hanyi.privoder.config
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-14 15:33
 * @Version: 1.0
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 性能分析拦截器，不建议生产使用
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
