package com.hanyi.web.common.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 自定义依赖注入注解
 * </p>
 *
 * @author wenchangwei
 * @since 7:18 下午 2021/4/17
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutowiredExt {

    /**
     * @return 是否必须
     */
    boolean required() default true;

}
