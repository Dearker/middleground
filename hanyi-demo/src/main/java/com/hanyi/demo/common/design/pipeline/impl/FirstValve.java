package com.hanyi.demo.common.design.pipeline.impl;

/**
 * <p>
 * 普通阀门一
 * </p>
 *
 * @author wenchangwei
 * @since 9:19 下午 2021/1/6
 */
public class FirstValve extends ValveBase{

    @Override
    public void invoke(String s) {
        s = s.replace("11","first");
        System.out.println("after first Valve handled: s = " + s);
        super.getNext().invoke(s);
    }
}
