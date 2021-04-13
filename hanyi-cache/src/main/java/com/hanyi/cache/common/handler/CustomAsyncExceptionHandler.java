package com.hanyi.cache.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * <p>
 * 自定义线程异常处理器
 * </p>
 *
 * @author wenchangwei
 * @since 9:44 下午 2021/4/13
 */
@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    /**
     * 异常处理方法
     */
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.info("{} ,{} ,{}", throwable, method, objects);
    }
}
