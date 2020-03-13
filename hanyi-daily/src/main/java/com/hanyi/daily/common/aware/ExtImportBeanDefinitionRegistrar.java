package com.hanyi.daily.common.aware;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * @PackAge: middleground com.hanyi.daily.common.aware
 * @Author: weiwenchang
 * @Description: 动态获取需要扫描的包名
 * @CreateDate: 2020-03-10 16:22
 * @Version: 1.0
 */
public class ExtImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 获取需要扫描的包名，并进行注册
     *
     * @param metadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        String packageName = ClassUtils.getPackageName(metadata.getClassName());
        AutoConfigurationPackages.register(registry, packageName);
    }
}
