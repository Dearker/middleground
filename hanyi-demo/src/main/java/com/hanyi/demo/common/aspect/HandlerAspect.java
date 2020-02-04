package com.hanyi.demo.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @ClassName: middleground com.hanyi.demo.common.aspect HandlerAspect
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-04 15:13
 * @Version: 1.0
 */
@Aspect
@Component
public class HandlerAspect {

    @Around("execution(* com.hanyi.demo.controller.*.*(..))")
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
