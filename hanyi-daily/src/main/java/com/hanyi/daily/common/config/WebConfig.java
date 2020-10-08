package com.hanyi.daily.common.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.hanyi.daily.common.filter.HandlerFilter;
import com.hanyi.daily.common.interceptor.WebInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
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

    @Resource
    private HandlerFilter handlerFilter;

    @Resource
    private WebInterceptor webInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor);
    }

    /**
     * 注册第三方过滤器
     * 功能与spring mvc中通过配置web.xml相同
     *
     * @return 返回过滤注册器对象
     */
    @Bean
    public FilterRegistrationBean<HandlerFilter> thirdFilter() {

        FilterRegistrationBean<HandlerFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(handlerFilter);
        List<String> urls = new ArrayList<>(Byte.BYTES);
        // 匹配所有请求路径
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);

        return filterRegistrationBean;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 雪花算法对象
     *
     * @return 返回对象
     */
    @Bean
    public Snowflake snowflake(){
        return IdUtil.createSnowflake(1, 1);
    }

}
