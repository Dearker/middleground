package com.hanyi.ordinary.common.processor;

import com.hanyi.ordinary.common.selector.ThreadPoolConfigurationImportSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 16:19 2020/7/25
 */
@Slf4j
@Component
public class ThreadPoolPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

        Class<ThreadPoolConfigurationImportSelector> clazz = ThreadPoolConfigurationImportSelector.class;

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(clazz).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("hello", beanDefinition);

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
