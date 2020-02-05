package com.hanyi.daily.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import java.lang.reflect.Method;

/**
 * @ClassName: middleground com.hanyi.daily.common.proxy DynamicProxy
 * @Author: weiwenchang
 * @Description: 动态代理类
 * @CreateDate: 2020-02-04 20:42
 * @Version: 1.0
 */
public class DynamicProxy implements InvocationHandler {

    /**
     * 需要代理的目标类
     */
    private Object target;

    /**
     * 写法固定，aop专用:绑定委托对象并返回一个代理类
     *
     * @param target
     * @return
     */
    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * 调用 InvocationHandler接口定义方法
     *
     * @param proxy  指被代理的对象。
     * @param method 要调用的方法
     * @param args   方法调用时所需要的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        // 切面之前执行
        System.out.println("[动态代理]切面之前执行");

        // 执行业务
        result = method.invoke(target, args);

        // 切面之后执行
        System.out.println("[动态代理]切面之后执行");

        return result;
    }
}
