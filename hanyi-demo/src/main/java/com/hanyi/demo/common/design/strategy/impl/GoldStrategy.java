package com.hanyi.demo.common.design.strategy.impl;

import com.hanyi.demo.common.design.strategy.Strategy;
import com.hanyi.demo.common.design.strategy.UserType;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy GoldStrategy
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-21 09:42
 * @Version: 1.0
 */
public class GoldStrategy implements Strategy {

    @Override
    public double compute(long money) {
        System.out.println("黄金会员 8折");
        return money * 0.8;
    }

    @Override
    public int getType() {
        return UserType.GOLD_VIP.getType();
    }
}
