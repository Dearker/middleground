package com.hanyi.daily.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @ClassName: middleground com.hanyi.daily.common.aspect HandlerAspect
 * @Author: weiwenchang
 * @Description: 调用顺序Filter->Interceptor->Aspect->Controller,异常则调用顺序由内到外
 * @CreateDate: 2020-02-04 15:32
 * @Version: 1.0
 */
@Aspect
@Component
public class HandlerAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut("execution(* com.hanyi.daily.controller.*Controller.*(..))")
    public void methodPointCut() {
    }

    @Around("methodPointCut() && requestMapping()")
    public Object handlerControllerMethod(ProceedingJoinPoint point) throws Throwable {
        System.out.println("time aspect start");
        long start = System.currentTimeMillis();
        Object[] args = point.getArgs();
        for (Object obj : args) {
            System.out.println("arg is:"+obj);
        }
        //具体方法的返回值
        Object obj = point.proceed();
        System.out.println("aspect 耗时："+(System.currentTimeMillis()-start));
        System.out.println("time aspect end");
        return obj;
    }

}
