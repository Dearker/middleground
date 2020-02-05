package com.hanyi.daily.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class HandlerFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(HandlerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("初始化过滤器：{}", filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("start to doFilter");
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        LOG.info("the request of {} consumes {}ms.", getUrlFrom(request), (endTime - startTime));
        LOG.info("end to doFilter");
    }

    @Override
    public void destroy() {
        LOG.info("销毁过滤器");
    }

    private String getUrlFrom(ServletRequest servletRequest){
        if (servletRequest instanceof HttpServletRequest){
            return ((HttpServletRequest) servletRequest).getRequestURL().toString();
        }
        return null;
    }

}
