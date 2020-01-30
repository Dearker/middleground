package com.hanyi.demo.common.design.strategy.impl;

import com.hanyi.demo.common.design.strategy.Strategy;
import com.hanyi.demo.common.design.strategy.UserType;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy OrdinaryStrategy
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-21 09:14
 * @Version: 1.0
 */
public class OrdinaryStrategy implements Strategy {

    @Override
    public double compute(long money) {
        System.out.println("普通会员 不打折");
        return money;
    }

    @Override
    public int getType() {
        return UserType.ORDINARY_VIP.getType();
    }
}
