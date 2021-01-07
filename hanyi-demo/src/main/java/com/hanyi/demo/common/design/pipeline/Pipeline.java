package com.hanyi.demo.common.design.pipeline;

/**
 * <p>
 * 管道接口
 * </p>
 *
 * @author wenchangwei
 * @since 9:11 下午 2021/1/6
 */
public interface Pipeline {

    Valve getHead();

    Valve getTail();

    void setTail(Valve v);

    void addValve(Valve v);
}
