package com.hanyi.common.interceptor;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * <p>
 * feign配置类
 * </p>
 *
 * @author wenchangwei
 * @since 9:06 下午 2020/11/15
 */
@Configuration
public class FeignConfig {

    /**
     * 解决feign请求头丢失的问题
     * <p>
     * 解决feign异步调用丢失请求头的问题，需要在方法调用之前获取到requestAttributes对象，
     * 在对应的线程中重新设置该对象即可解决该问题
     *
     * @return 返回feign拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(requestAttributes)) {
                HttpServletRequest request = requestAttributes.getRequest();
                //取出当前请求的header，找到jwt令牌
                Enumeration<String> headerNames = request.getHeaderNames();
                if (Objects.nonNull(headerNames)) {
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
                        String headerValue = request.getHeader(headerName);
                        // 将header向下传递
                        requestTemplate.header(headerName, headerValue);
                    }
                }
            }
        };
    }

}
