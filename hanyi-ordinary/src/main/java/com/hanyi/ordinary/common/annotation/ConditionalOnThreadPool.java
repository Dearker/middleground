package com.hanyi.ordinary.common.annotation;

import com.hanyi.ordinary.common.condition.OnThreadPoolConditional;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>
 * 线程池条件注解
 * </p>
 *
 * @author wenchangwei
 * @since 11:42 下午 2020/7/24
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnThreadPoolConditional.class})
public @interface ConditionalOnThreadPool {

    Class<?>[] value() default {};

}
