package com.hanyi.demo.common.design.strategy;

import org.junit.Test;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy Demo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-21 09:56
 * @Version: 1.0
 */
public class Demo {

    private static final long COUNT = 1000L;

    @Test
    public void strategyTest() {

        double result = Demo.getResult(1000L, 3);
        System.out.println("支付的金额-->" + result);
    }

    private static double getResult(long money, int type) {

        if (money < COUNT) {
            return money;
        }

        Strategy strategy = StrategyFactory.getInstance().get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("please input right type");
        }

        return strategy.compute(money);
    }

}
