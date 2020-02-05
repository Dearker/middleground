package com.hanyi.daily.common.config;
import	java.security.KeyStore.Entry.Attribute;

import com.hanyi.daily.common.filter.HandlerFilter;
import com.hanyi.daily.common.interceptor.WebInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: middleground com.hanyi.daily.common.config WebConfig
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-04 15:40
 * @Aersion: 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private HandlerFilter handlerFilter;

    @Autowired
    private WebInterceptor webInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor);
    }

    /**
     * 注册第三方过滤器
     * 功能与spring mvc中通过配置web.xml相同
     * @return
     */
    @Bean
    public FilterRegistrationBean thirdFilter(){

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean() ;
        filterRegistrationBean.setFilter(handlerFilter);
        List<String > urls = new ArrayList<>(2 << 1);
        // 匹配所有请求路径
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);

        return filterRegistrationBean;
    }

}
