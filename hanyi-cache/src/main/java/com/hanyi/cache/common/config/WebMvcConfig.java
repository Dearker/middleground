package com.hanyi.cache.common.config;

import com.hanyi.cache.common.interceptor.ApiIdempotentInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * mvc配置类
 * </p>
 *
 * @author wenchangwei
 * @since 4:57 下午 2020/11/14
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截使用请求
        registry.addInterceptor(apiIdempotentInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public ApiIdempotentInterceptor apiIdempotentInterceptor() {
        return new ApiIdempotentInterceptor();
    }

}
