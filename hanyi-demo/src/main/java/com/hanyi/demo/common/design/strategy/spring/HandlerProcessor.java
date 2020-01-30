package com.hanyi.demo.common.design.strategy.spring;

import com.hanyi.demo.common.design.strategy.Strategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy.spring HandlerProcessor
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-22 15:29
 * @Version: 1.0
 */
@Component
public class HandlerProcessor implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        //获取所有策略注解的Bean
        Map<String, Object> orderStrategyMap = applicationContext.getBeansWithAnnotation(HandlerType.class);
        orderStrategyMap.forEach((k,v)->{
            Class<Strategy> orderStrategyClass = (Class<Strategy>) v.getClass();
            String type = orderStrategyClass.getAnnotation(HandlerType.class).value();
            //将class加入map中,type作为key
            HandlerContext.orderStrategyBeanMap.put(type,orderStrategyClass);
        });

    }
}
