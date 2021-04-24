package com.hanyi.web.common.configuration;

import cn.hutool.core.util.RandomUtil;
import com.hanyi.web.bo.Student;
import com.hanyi.web.bo.User;
import com.hanyi.web.common.annotation.AutowiredExt;
import com.hanyi.web.common.scope.ThreadLocalScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 * bean对象的全局配置
 * </p>
 *
 * @author wenchangwei
 * @since 7:20 下午 2021/4/17
 */
@Slf4j
@Configuration
public class BeanGlobalConfig {

    /**
     * 通过指定内置的处理类名称来覆盖springboot中默认的注解处理类
     * 使用该方式需要在配置文件中添加该配置(默认为false)： spring.main.allow-bean-definition-overriding: true
     * 该属性再spring中默认为true
     *
     * @return 返回自动注入注解处理对象
     */
    //@Bean(name = AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    public AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(4);

        autowiredAnnotationTypes.add(Autowired.class);
        autowiredAnnotationTypes.add(Value.class);
        try {
            autowiredAnnotationTypes.add((Class<? extends Annotation>) ClassUtils.forName("javax.inject.Inject",
                    AutowiredAnnotationBeanPostProcessor.class.getClassLoader()));
            log.trace("JSR-330 'javax.inject.Inject' annotation found and supported for autowiring");
        } catch (ClassNotFoundException ex) {
            // JSR-330 API not available - simply skip.
        }
        autowiredAnnotationTypes.add(AutowiredExt.class);
        beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
        return beanPostProcessor;
    }

    /**
     * 将自定义的依赖注入注解注入到容器中进行处理
     *
     * @return 返回自动注入注解处理对象
     */
    @Bean
    public AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(AutowiredExt.class);
        return beanPostProcessor;
    }

    /**
     * 添加自定义作用域注解处理类
     *
     * @return 返回自定义作用域配置类
     */
    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.addScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope());
        return customScopeConfigurer;
    }

    /**
     * 创建根据线程作用域的对象
     *
     * @return 返回创建的对象
     */
    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    public Student student() {
        Student student = new Student();
        student.setNumber(RandomUtil.randomInt());
        return student;
    }

    @Bean
    public User user(){
        User user = new User();
        user.setName("柯基");
        return user;
    }

}
