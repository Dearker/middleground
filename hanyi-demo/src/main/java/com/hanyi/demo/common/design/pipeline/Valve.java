package com.hanyi.demo.common.design.pipeline;

/**
 * <p>
 * 阀门接口
 * </p>
 *
 * @author wenchangwei
 * @since 9:12 下午 2021/1/6
 */
public interface Valve {

    Valve getNext();

    void setNext(Valve v);

    void invoke(String s);
}
