package com.hanyi.demo.common.design.pipeline.impl;

/**
 * <p>
 * 尾阀门
 * </p>
 *
 * @author wenchangwei
 * @since 9:21 下午 2021/1/6
 */
public class TailValve extends ValveBase{

    @Override
    public void invoke(String s) {
        s = s.replace("33", "third");
        System.out.println("after tail Valve handled: s = " + s);
    }

}
