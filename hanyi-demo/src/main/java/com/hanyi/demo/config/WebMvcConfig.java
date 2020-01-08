package com.hanyi.demo.config;

import com.hanyi.demo.common.Interceptor.ApiIdempotentInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: middleground com.hanyi.demo.config WebMvcConfig
 * @Author: weiwenchang
 * @Description: webMvc的拦截器
 * @CreateDate: 2020-01-08 21:36
 * @Version: 1.0
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
