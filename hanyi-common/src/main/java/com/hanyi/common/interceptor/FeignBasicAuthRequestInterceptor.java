package com.hanyi.common.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * feign拦截器
 *
 * @author wcwei@iflytek.com
 * @since 2020-11-27 15:56
 */
@Slf4j
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        if(Objects.nonNull(attributes)){
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (CollUtil.isNotEmpty(headerNames)) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }
            Enumeration<String> bodyNames = request.getParameterNames();
            StringBuilder body =new StringBuilder();
            if (CollUtil.isNotEmpty(bodyNames)) {
                while (bodyNames.hasMoreElements()) {
                    String name = bodyNames.nextElement();
                    String values = request.getParameter(name);
                    body.append(name).append("=").append(values).append("&");
                }
            }
            if(StrUtil.isNotBlank(body)) {
                body.deleteCharAt(body.length()-1);
                //requestTemplate.body(body.toString());
                log.info("feign interceptor body:{}",body.toString());
            }
        }
    }
}
