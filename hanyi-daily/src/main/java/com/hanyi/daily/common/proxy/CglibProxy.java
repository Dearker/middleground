package com.hanyi.daily.common.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName: middleground com.hanyi.daily.common.proxy CglibProxy
 * @Author: weiwenchang
 * @Description: 使用cglib动态代理,JDK中的动态代理使用时，必须有业务接口，而cglib是针对类的
 * @CreateDate: 2020-02-04 20:49
 * @Version: 1.0
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    /**
     * 创建代理对象
     *
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        System.out.println("[cglib]切面之前执行");

        result = methodProxy.invokeSuper(proxy, args);

        System.out.println("[cglib]切面之后执行");

        return result;
    }

}
