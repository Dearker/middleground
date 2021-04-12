package com.hanyi.web.common.condition;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.web.common.annotation.ConditionOnProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 属性条件匹配类
 * </p>
 *
 * @author wenchangwei
 * @since 10:40 下午 2021/4/12
 */
@Slf4j
public class OnPropertyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> multiValueMap = metadata.getAllAnnotationAttributes(ConditionOnProperty.class.getName());
        log.info("获取 ConditionOnProperty 注解所有的属性值：{}", multiValueMap);

        if (CollUtil.isNotEmpty(multiValueMap)) {
            String name = Optional.ofNullable(multiValueMap.getFirst("name")).map(Object::toString).orElse(StrUtil.EMPTY);
            String value = Optional.ofNullable(multiValueMap.getFirst("value")).map(Object::toString).orElse(StrUtil.EMPTY);

            if (Objects.equals(Locale.getDefault().getLanguage(), value)) {
                log.info("当前注解属性名称为：{} ,属性值为：{}", name, value);
                return true;
            }
        }
        return false;
    }
}
