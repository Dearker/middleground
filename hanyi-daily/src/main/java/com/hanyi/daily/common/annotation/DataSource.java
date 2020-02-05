package com.hanyi.daily.common.annotation;

import com.hanyi.daily.common.enums.DataType;

import java.lang.annotation.*;

/**
 * 切换数据注解 可以用于类或者方法级别 方法级别优先级 > 类级别
 * @author weiwen
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 该值即key值，默认使用默认数据库
     * @return
     */
    DataType value() default DataType.PRIMARY;

}
