package com.hanyi.demo.common.design.pipeline.impl;

/**
 * <p>
 * 普通阀门二
 * </p>
 *
 * @author wenchangwei
 * @since 9:20 下午 2021/1/6
 */
public class SecondValve extends ValveBase{

    @Override
    public void invoke(String s) {
        s = s.replace("22","second");
        System.out.println("after second Valve handled: s = " + s);
        super.getNext().invoke(s);
    }
}
