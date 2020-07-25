package com.hanyi.ordinary.common.annotation;

import com.hanyi.ordinary.common.selector.ThreadPoolConfigurationImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 线程池开关注解
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 11:13 2020/7/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ThreadPoolConfigurationImportSelector.class)
public @interface EnableThreadPool {

}
