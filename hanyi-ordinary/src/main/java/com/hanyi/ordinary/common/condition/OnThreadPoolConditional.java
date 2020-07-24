package com.hanyi.ordinary.common.condition;


import cn.hutool.core.collection.CollUtil;
import com.hanyi.ordinary.common.annotation.ConditionalOnThreadPool;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

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

        List<String> candidateList = this.getCandidates(metadata, ConditionalOnThreadPool.class);

        if(CollUtil.isNotEmpty(candidateList)){
            return true;
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
        if (itemsToAdd != null) {
            for (Object item : itemsToAdd) {
                Collections.addAll(list, (String[]) item);
            }
        }
    }


}
