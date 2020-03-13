package com.hanyi.daily.common.annotation;

import com.hanyi.daily.common.aware.ExtImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @PackAge: middleground com.hanyi.daily.common.annotation
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-10 16:59
 * @Version: 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ExtImportBeanDefinitionRegistrar.class})
public @interface EnableExtConfigPackage {
}
