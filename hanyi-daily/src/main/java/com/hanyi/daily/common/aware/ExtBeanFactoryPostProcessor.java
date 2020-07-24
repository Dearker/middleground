package com.hanyi.daily.common.aware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @PackAge: middleground com.hanyi.daily.common.aware
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-08 10:33
 * @Version: 1.0
 */
@Slf4j
@Component
public class ExtBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    /**
     * 在bean对象创建之前获取到所有bean的定义信息(BeanDefinition)
     *
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        int count = configurableListableBeanFactory.getBeanDefinitionCount();
        String[] names = configurableListableBeanFactory.getBeanDefinitionNames();
        log.info("当前BeanFactory中有" + count + " 个Bean");
        log.info(Arrays.asList(names).toString());
    }
}
