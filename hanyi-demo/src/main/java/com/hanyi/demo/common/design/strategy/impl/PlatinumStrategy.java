package com.hanyi.demo.common.design.strategy.impl;

import com.hanyi.demo.common.design.strategy.Strategy;
import com.hanyi.demo.common.design.strategy.UserType;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy PlatinumStrategy
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-21 09:43
 * @Version: 1.0
 */
public class PlatinumStrategy implements Strategy {

    @Override
    public double compute(long money) {
        System.out.println("白金会员 优惠50元，再打7折");
        return (money - 50) * 0.7;
    }

    @Override
    public int getType() {
        return UserType.PLATINUM_VIP.getType();
    }
}
