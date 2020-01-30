package com.hanyi.demo.common.design.strategy.spring;

import com.hanyi.demo.common.design.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy.spring HandlerContext
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-22 15:32
 * @Version: 1.0
 */
@Component
public class HandlerContext {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 存放所有策略类Bean的map
     */
    public static Map<String, Class<Strategy>> orderStrategyBeanMap= new HashMap<>();

    public Strategy getStrategy(String type){
        Class<Strategy> strategyClass = orderStrategyBeanMap.get(type);
        if(strategyClass==null) {
            throw new IllegalArgumentException("没有对应的订单类型");
        }
        //从容器中获取对应的策略Bean
        return applicationContext.getBean(strategyClass);
    }

}
