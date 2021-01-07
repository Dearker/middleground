package com.hanyi.demo.common.design.pipeline;

import com.hanyi.demo.common.design.pipeline.impl.FirstValve;
import com.hanyi.demo.common.design.pipeline.impl.SecondValve;
import com.hanyi.demo.common.design.pipeline.impl.StandardPipeline;
import com.hanyi.demo.common.design.pipeline.impl.TailValve;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 9:24 下午 2021/1/6
 */
public class ClientDemo {

    public static void main(String[] args) {
        String s = "11,22,33";
        System.out.println("Input : " + s);
        StandardPipeline pipeline = new StandardPipeline();
        TailValve tail = new TailValve();
        FirstValve first = new FirstValve();
        SecondValve second = new SecondValve();
        pipeline.setTail(tail);
        pipeline.addValve(first);
        pipeline.addValve(second);
        pipeline.getHead().invoke(s);
    }

}
