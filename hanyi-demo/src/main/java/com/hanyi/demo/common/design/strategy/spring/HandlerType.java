package com.hanyi.demo.common.design.strategy.spring;

import java.lang.annotation.*;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy.spring HandlerType
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-22 15:26
 * @Version: 1.0
 */
@Target(ElementType.TYPE)  //作用在类上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandlerType {

    String value() default "1";

}
