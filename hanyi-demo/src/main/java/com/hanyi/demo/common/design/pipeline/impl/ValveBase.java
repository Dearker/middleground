package com.hanyi.demo.common.design.pipeline.impl;

import com.hanyi.demo.common.design.pipeline.Valve;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 9:15 下午 2021/1/6
 */
public abstract class ValveBase implements Valve {

    protected Valve next;


    @Override
    public Valve getNext() {
        return next;
    }

    @Override
    public void setNext(Valve v) {
        next = v;
    }

}
