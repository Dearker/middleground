package com.hanyi.daily.common.aware;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.StrUtil;
import com.hanyi.daily.common.property.BusinessThreadPoolProperties;
import com.hanyi.daily.pojo.BusinessThreadPool;
import com.hanyi.daily.pojo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 通过读取配置文件中的内容，向容器中注入bean
 * </p>
 *
 * @author wenchangwei
 * @since 11:20 下午 2020/8/1
 */
@Slf4j
@Component
public class ThreadPoolPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private BusinessThreadPoolProperties businessThreadPoolProperties;

    /**
     * 向容器中注入bean对象
     *
     * @param registry bean注册器
     * @throws BeansException 异常
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addConstructorArgValue(12345)
                .addConstructorArgValue("哈士奇")
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
            ThreadPoolExecutor threadPoolExecutor = newThreadPoolExecutor(v.getThreadNamePrefix(), v.getCorePoolSize(), v.getMaxPoolSize());
            threadPoolExecutorMap.put(k, threadPoolExecutor);
        });

        //向容器中注入bean对象
        threadPoolExecutorMap.forEach((k, v) -> registry.registerBeanDefinition(k, this.buildBeanDefinition(v)));
    }

    /**
     * 后置处理器
     *
     * @param beanFactory bean工厂
     * @throws BeansException 异常
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("postProcessBeanFactory the beanFactory: {}", beanFactory);
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

    /**
     * 通过线程池对象构建线程池beanDefinition对象
     *
     * @param threadPoolExecutor 线程池对象
     * @return 返回beanDefinition对象
     */
    private AbstractBeanDefinition buildBeanDefinition(ThreadPoolExecutor threadPoolExecutor) {

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(ThreadPoolExecutor.class);

        //组装线程池构造函数的参数集合
        List<Object> objectList = this.buildConstructorArgValue(threadPoolExecutor);
        objectList.forEach(beanDefinitionBuilder::addConstructorArgValue);

        return beanDefinitionBuilder.getBeanDefinition();
    }

    /**
     * 获取创建线程池的各个参数
     *
     * @param threadPoolExecutor 线程池对象
     * @return 返回线程池参数集合
     */
    private List<Object> buildConstructorArgValue(ThreadPoolExecutor threadPoolExecutor) {

        List<Object> objectList = new ArrayList<>(Byte.SIZE);

        objectList.add(threadPoolExecutor.getCorePoolSize());
        objectList.add(threadPoolExecutor.getMaximumPoolSize());
        objectList.add(threadPoolExecutor.getKeepAliveTime(TimeUnit.NANOSECONDS));
        objectList.add(TimeUnit.NANOSECONDS);
        objectList.add(threadPoolExecutor.getQueue());
        objectList.add(threadPoolExecutor.getThreadFactory());
        objectList.add(threadPoolExecutor.getRejectedExecutionHandler());

        return objectList;
    }

    /**
     * 构建线程池对象
     *
     * @param namePrefix 线程池名称
     * @param coreSize   核心线程数
     * @param maxSize    最大线程数
     * @return 返回线程池对象
     */
    private static ThreadPoolExecutor newThreadPoolExecutor(String namePrefix, int coreSize, int maxSize) {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(namePrefix, false);
        return ExecutorBuilder.create()
                .setThreadFactory(namedThreadFactory)
                .setCorePoolSize(coreSize)
                .setMaxPoolSize(maxSize)
                .build();
    }

}
