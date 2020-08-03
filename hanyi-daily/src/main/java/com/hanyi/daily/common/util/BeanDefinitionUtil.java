package com.hanyi.daily.common.util;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * bean定义工具类
 * </p>
 *
 * @author wenchangwei
 * @since 10:12 下午 2020/8/3
 */
public class BeanDefinitionUtil {

    /**
     * 通过线程池对象构建线程池beanDefinition对象
     *
     * @param threadPoolExecutor 线程池对象
     * @return 返回beanDefinition对象
     */
    public static AbstractBeanDefinition buildBeanDefinitionForThreadPool(ThreadPoolExecutor threadPoolExecutor) {

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(ThreadPoolExecutor.class);

        //组装线程池构造函数的参数集合
        List<Object> objectList = buildConstructorArgValue(threadPoolExecutor);
        objectList.forEach(beanDefinitionBuilder::addConstructorArgValue);

        return beanDefinitionBuilder.getBeanDefinition();
    }

    /**
     * 获取创建线程池的各个参数
     *
     * @param threadPoolExecutor 线程池对象
     * @return 返回线程池参数集合
     */
    private static List<Object> buildConstructorArgValue(ThreadPoolExecutor threadPoolExecutor) {

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
    public static ThreadPoolExecutor newThreadPoolExecutor(String namePrefix, int coreSize, int maxSize) {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(namePrefix, false);
        return ExecutorBuilder.create()
                .setThreadFactory(namedThreadFactory)
                .setCorePoolSize(coreSize)
                .setMaxPoolSize(maxSize)
                .build();
    }

}
