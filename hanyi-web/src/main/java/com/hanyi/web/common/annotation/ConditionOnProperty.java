package com.hanyi.web.common.annotation;

import com.hanyi.web.common.condition.OnPropertyCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>
 * 属性条件注解
 * </p>
 *
 * @author wenchangwei
 * @since 10:38 下午 2021/4/12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnPropertyCondition.class})
public @interface ConditionOnProperty {

    /**
     * @return 属性名称
     */
    String name();

    /**
     * @return 属性值
     */
    String value();

}
