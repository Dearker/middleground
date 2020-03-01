package com.hanyi.daily.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName: middleground com.hanyi.daily.common.filter HandlerFilter
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-04 15:35
 * @Version: 1.0
 */
@Component
@Slf4j
public class HandlerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化过滤器：{}", filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("start to doFilter");
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        log.info("the request of {} consumes {}ms.", getUrlFrom(request), (endTime - startTime));
        log.info("end to doFilter");
    }

    @Override
    public void destroy() {
        log.info("销毁过滤器");
    }

    private String getUrlFrom(ServletRequest servletRequest){
        if (servletRequest instanceof HttpServletRequest){
            return ((HttpServletRequest) servletRequest).getRequestURL().toString();
        }
        return null;
    }

}
