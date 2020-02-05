package com.hanyi.daily.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: middleground com.hanyi.daily.common.interceptor WebInterceptor
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-04 15:45
 * @Version: 1.0
 */
@Component
public class WebInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(WebInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LOG.info("在请求处理之前进行调用（Controller方法调用之前）");
        request.setAttribute("startTime", System.currentTimeMillis());
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LOG.info("controller object is {}", handlerMethod.getBean().getClass().getName());
        LOG.info("controller method is {}", handlerMethod.getMethod());

        //需要返回true，否则请求不会被控制器处理
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.info("请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后），如果异常发生，则该方法不会被调用");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOG.info("在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
        long startTime = (long) request.getAttribute("startTime");
        LOG.info("time consume is {}", System.currentTimeMillis() - startTime);
    }
}
