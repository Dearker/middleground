package com.hanyi.daily.common.aware;

import cn.hutool.core.util.StrUtil;
import com.hanyi.daily.common.property.BusinessThreadPoolProperties;
import com.hanyi.daily.common.util.BeanDefinitionUtil;
import com.hanyi.daily.pojo.BusinessThreadPool;
import com.hanyi.daily.pojo.Person;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 线程池配置注册器
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 11:25 2020/7/24
 */
public class ThreadPoolConfigurationImportSelector implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private BusinessThreadPoolProperties businessThreadPoolProperties;

    /**
     * 向容器中注入bean对象
     *
     * @param annotationMetadata 元数据
     * @param registry           bean注册器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("id", 123)
                .addPropertyValue("name", "哈士奇")
                .getBeanDefinition();

        registry.registerBeanDefinition("hanyi123", beanDefinition);

        //如果该属性为空，则使用默认值
        if (Objects.isNull(businessThreadPoolProperties)) {
            String simpleName = ThreadPoolExecutor.class.getSimpleName();
            String lowerFirst = StrUtil.lowerFirst(simpleName);
            businessThreadPoolProperties = new BusinessThreadPoolProperties(Collections.singletonMap(lowerFirst, new BusinessThreadPool()));
        }

        //组装线程池map对象，key为bean名称，value为线程池对象
        Map<String, BusinessThreadPool> threadPools = businessThreadPoolProperties.getThreadPools();
        Map<String, ThreadPoolExecutor> threadPoolExecutorMap = new HashMap<>(threadPools.size());

        threadPools.forEach((k, v) -> {
            ThreadPoolExecutor threadPoolExecutor = BeanDefinitionUtil.newThreadPoolExecutor(v.getThreadNamePrefix(), v.getCorePoolSize(), v.getMaxPoolSize());
            threadPoolExecutorMap.put(k, threadPoolExecutor);
        });

        //向容器中注入bean对象
        threadPoolExecutorMap.forEach((k, v) -> registry.registerBeanDefinition(k, BeanDefinitionUtil.buildBeanDefinitionForThreadPool(v)));
    }

    /**
     * 获取系统环境
     *
     * @param environment 系统环境对象
     */
    @Override
    public void setEnvironment(Environment environment) {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
        Binder binder = new Binder(sources);
        this.businessThreadPoolProperties = binder.bind("business.thread-pool-executor", BusinessThreadPoolProperties.class).orElse(null);
    }


}
