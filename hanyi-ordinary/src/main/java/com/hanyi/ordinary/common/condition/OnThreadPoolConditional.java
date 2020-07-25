package com.hanyi.ordinary.common.condition;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.ordinary.common.annotation.EnableThreadPool;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:37 下午 2020/7/24
 */
@Order
public class OnThreadPoolConditional implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            if (!StrUtil.contains(beanDefinitionName, StrUtil.DOT)) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
                String beanClassName = beanDefinition.getBeanClassName();

                Class<?> clazz = ClassLoaderUtil.loadClass(beanClassName);

                EnableThreadPool annotation = AnnotationUtils.findAnnotation(clazz, EnableThreadPool.class);
                if (Objects.nonNull(annotation)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean includeAnnotation(Class<?> clazz, Annotation annotation) {
        for (Annotation a : clazz.getAnnotations()) {
            if (a.annotationType().equals(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

    private List<String> getCandidates(AnnotatedTypeMetadata metadata, Class<?> annotationType) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(annotationType.getName(), true);
        if (Objects.isNull(attributes)) {
            return Collections.emptyList();
        }
        List<String> candidates = new ArrayList<>();
        this.addAll(candidates, attributes.get("value"));
        return candidates;
    }

    private void addAll(List<String> list, List<Object> itemsToAdd) {
        if (CollUtil.isNotEmpty(itemsToAdd)) {
            for (Object item : itemsToAdd) {
                Collections.addAll(list, (String[]) item);
            }
        }
    }

}
