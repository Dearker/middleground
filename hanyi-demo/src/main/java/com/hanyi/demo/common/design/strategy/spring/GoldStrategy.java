package com.hanyi.demo.common.design.strategy.spring;

import com.hanyi.demo.common.design.strategy.Strategy;
import com.hanyi.demo.common.design.strategy.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy GoldStrategy
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-21 09:42
 * @Version: 1.0
 */
@Component
@HandlerType("1")
public class GoldStrategy implements Strategy {

    @Autowired
    private HandlerContext handlerContext;

    @Override
    public double compute(long money) {
        System.out.println("黄金会员 8折");
        Strategy strategy = handlerContext.getStrategy("1");
        return strategy.compute(money);
    }

    @Override
    public int getType() {
        return UserType.GOLD_VIP.getType();
    }
}
