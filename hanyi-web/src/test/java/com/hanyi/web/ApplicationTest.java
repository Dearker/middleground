package com.hanyi.web;

import cn.hutool.core.date.DateUtil;
import com.hanyi.web.bo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * <p>
 * 应用测试类
 * </p>
 *
 * @author wenchangwei
 * @since 8:50 下午 2021/4/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    /**
     * applicationContext和beanFactory都是通过组合模式将defaultListableBeanFactory对象作为具体操作IOC的方式
     * <p>
     * beanFactory的具体实现为: DefaultListableBeanFactory
     * applicationContext的具体实现为: GenericWebApplicationContext
     * defaultListableBeanFactory为具体操作IOC容器的对象
     */
    @Test
    public void contextTest() {
        System.out.println(beanFactory);
        System.out.println(applicationContext);
        //DefaultListableBeanFactory
        System.out.println(applicationContext.getAutowireCapableBeanFactory());
        System.out.println(defaultListableBeanFactory);
    }

    /**
     * 向IOC容器中注入bean对象
     * <p>
     * BeanFactoryUtils用于从指定的IOC容器中查找对象
     * beanOfType()：从容器中获取对象后，容器中会移除该对象
     * beansOfTypeIncludingAncestors(): 通过递归的方式从父子容器中查找指定的对象
     */
    @Test
    public void registerTest() {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("name", "柯基")
                .addPropertyValue("age", "12")
                .addPropertyValue("birthDay", DateUtil.date())
                .getBeanDefinition();
        //通过命名的方式向IOC容器中注入bean对象
        defaultListableBeanFactory.registerBeanDefinition("user", beanDefinition);

        //通过非命名的方式向IOC容器中注入bean对象, bean的名称如下：com.hanyi.web.bo.User#0
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, defaultListableBeanFactory);

        Map<String, User> beansOfType = defaultListableBeanFactory.getBeansOfType(User.class);
        beansOfType.forEach((k, v) -> System.out.println("bean的名称：" + k + "，bean对象：" + v));
    }

    /**
     * applicationContext的getBean(): 直接从IOC容器获取对象，如果容器中没有，则会报错
     * applicationContext的getBeanProvider()：通过getObject()获取对象也会报错，效果和getBean()方法类似
     * <p>
     * 当相同类型同时出现多个名称时，getBean()和ObjectProvider的getIfAvailable()都会报错
     */
    @Test
    public void typeSafeTest() {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("name", "柯基")
                .addPropertyValue("age", "12")
                .addPropertyValue("birthDay", DateUtil.date())
                .getBeanDefinition();
        defaultListableBeanFactory.registerBeanDefinition("user", beanDefinition);
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, defaultListableBeanFactory);

        try {
            User bean = applicationContext.getBean(User.class);
            System.out.println("获取bean对象：" + bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        userObjectProvider.forEach(s -> System.out.println("forEach 的元素：" + s));

        try {
            User ifAvailable = userObjectProvider.getIfAvailable(() -> null);
            System.out.println("从IOC容器中获取对象，如果不存在则返回为null: " + ifAvailable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
