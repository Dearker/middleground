package com.hanyi.daily.common.aware;

import com.hanyi.daily.common.property.BusinessThreadPoolProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:20 下午 2020/8/1
 */
@Component
public class ThreadPoolPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Autowired
    private BusinessThreadPoolProperties businessThreadPoolProperties;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
       /* businessThreadPoolProperties.getThreadPools().forEach((k, v) -> {
            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(ThreadPoolExecutor.class)
                    .addPropertyValue("threadNamePrefix", v.getThreadNamePrefix())
                    .addPropertyValue("corePoolSize", v.getCorePoolSize())
                    .addPropertyValue("maxPoolSize", v.getMaxPoolSize()).getBeanDefinition();
            registry.registerBeanDefinition(k, beanDefinition);
        });*/
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
