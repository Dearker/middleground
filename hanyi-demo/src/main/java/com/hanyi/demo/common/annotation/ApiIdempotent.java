package com.hanyi.demo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: middleground com.hanyi.demo.common.annotation ApiIdempotent
 * @Author: weiwenchang
 * @Description: 在需要保证 接口幂等性 的Controller的方法上使用此注解
 * @CreateDate: 2020-01-06 23:04
 * @Version: 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {
}
