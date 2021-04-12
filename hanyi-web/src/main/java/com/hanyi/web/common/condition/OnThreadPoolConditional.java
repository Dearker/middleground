package com.hanyi.web.common.condition;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.web.common.annotation.EnableThreadPool;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 线程池条件过滤类，判断是否注入线程池
 * </p>
 *
 * @author wenchangwei
 * @since 11:37 下午 2020/7/24
 */
@Order
public class OnThreadPoolConditional implements Condition {

    /**
     * 线程池是否注入匹配方式
     *
     * @param context  上下文对象
     * @param metadata 元数据
     * @return 返回匹配结果
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            if (!StrUtil.contains(beanDefinitionName, StrUtil.DOT)) {
                //从容器中获取启动类的全路径
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
                String beanClassName = beanDefinition.getBeanClassName();

                //判断该class上是否包含EnableThreadPool注解
                Class<?> clazz = ClassLoaderUtil.loadClass(beanClassName);
                EnableThreadPool annotation = AnnotationUtils.findAnnotation(clazz, EnableThreadPool.class);
                if (Objects.nonNull(annotation)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 从元数据中获取指定的注解class的字符串集合
     *
     * @param metadata       元数据
     * @param annotationType 指定注解的class
     * @return 返回class的字符串集合
     */
    private List<String> getCandidates(AnnotatedTypeMetadata metadata, Class<?> annotationType) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(annotationType.getName(), true);
        if (Objects.isNull(attributes)) {
            return Collections.emptyList();
        }
        List<String> candidates = new ArrayList<>();
        //获取注解中的value属性集合
        this.addAll(candidates, attributes.get("value"));
        return candidates;
    }

    /**
     * 合并字符串集合
     *
     * @param list       需要合并到的集合
     * @param itemsToAdd 被合并的集合
     */
    private void addAll(List<String> list, List<Object> itemsToAdd) {
        if (CollUtil.isNotEmpty(itemsToAdd)) {
            itemsToAdd.forEach(item -> Collections.addAll(list, (String[]) item));
        }
    }

}
