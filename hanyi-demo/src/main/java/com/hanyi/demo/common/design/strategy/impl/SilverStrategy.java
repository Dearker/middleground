package com.hanyi.demo.common.design.strategy.impl;

import com.hanyi.demo.common.design.strategy.Strategy;
import com.hanyi.demo.common.design.strategy.UserType;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy SilverStrategy
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-21 09:40
 * @Version: 1.0
 */
public class SilverStrategy implements Strategy {

    @Override
    public double compute(long money) {
        System.out.println("白银会员 优惠50元");
        return money - 50;
    }

    @Override
    public int getType() {
        return UserType.SILVER_VIP.getType();
    }
}
