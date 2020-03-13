package com.hanyi.daily.common.aware;

import com.hanyi.daily.pojo.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @PackAge: middleground com.hanyi.daily.common.aware
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-08 11:35
 * @Version: 1.0
 */
@Component
public class ExtBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * 在BeanFactoryPostProcessor之前执行，可向容器中注入bean
     *
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry...bean的数量：" + beanDefinitionRegistry.getBeanDefinitionCount());
        //向容器中注入bean对象
        //RootBeanDefinition beanDefinition = new RootBeanDefinition(User.class);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(User.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("hello", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory...bean的数量" + configurableListableBeanFactory.getBeanDefinitionCount());
    }
}
