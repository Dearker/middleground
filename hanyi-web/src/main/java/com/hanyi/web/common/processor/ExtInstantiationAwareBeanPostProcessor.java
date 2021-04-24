package com.hanyi.web.common.processor;

import cn.hutool.core.date.DateUtil;
import com.hanyi.web.bo.Student;
import com.hanyi.web.bo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 扩展实例化接口
 * </p>
 *
 * @author wenchangwei
 * @since 10:51 下午 2021/4/18
 */
@Slf4j
@Component
public class ExtInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor,
        SmartInitializingSingleton, DestructionAwareBeanPostProcessor {

    /**
     * 需要扩展的bean名称
     */
    private static final String BEAN_NAME = "student";

    /**
     * 用户bean名称
     */
    private static final String USER_NAME = "user";

    /**
     * bean在实例化之前的操作，如果返回为null，则会自动创建bean对象，如果不为null，则不会再自动创建对象
     *
     * @param beanClass bean的Class对象
     * @param beanName  bean的名称
     * @return 返回的对象
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(BEAN_NAME, beanName)) {
            return new Student();
        }
        return null;
    }

    /**
     * 实例化后的属性赋值操作，如果接口返回为true，则会进行属性赋值，返回为false则不会进行属性赋值，可自定义属性返回。
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @return 返回是否赋值
     * @throws BeansException 异常
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * 如果赋值接口的回调返回为false则，该阶段会直接跳过；该阶段主要用于属性赋值前的回调，可对对象的属性进行添加和修改
     * 如果上述postProcessBeforeInstantiation方法已经做了自定义返回处理，则postProcessAfterInstantiation和当前
     * 方法都不会再调用
     *
     * @param pvs      对象的属性集合
     * @param bean     bean对象
     * @param beanName bean名称
     * @return 返回需要设置的属性集合
     * @throws BeansException 异常
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(USER_NAME, beanName)) {
            final MutablePropertyValues propertyValues;
            if (pvs instanceof MutablePropertyValues) {
                propertyValues = (MutablePropertyValues) pvs;
            } else {
                propertyValues = new MutablePropertyValues();
            }

            //如果属性集合中已经包含了相应的属性，则需要先删除对应的属性赋值再添加
            String propertyName = "name";
            if (propertyValues.contains(propertyName)) {
                propertyValues.removePropertyValue(propertyName);
            }
            //添加属性
            propertyValues.addPropertyValue(propertyName, "1");
            return propertyValues;
        }

        return null;
    }

    /**
     * bean对象的初始化阶段，该阶段在bean的实例化以及属性赋值之后
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @return 返回bean对象
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(USER_NAME, beanName)) {
            User user = (User) bean;
            user.setAge("123");
        }
        return bean;
    }

    /**
     * 初始化之后的操作
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @return 返回bean对象
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(USER_NAME, beanName)) {
            User user = (User) bean;
            user.setBirthDay(DateUtil.date());
        }
        return bean;
    }

    /**
     * 初始化完成之后调用该方法
     */
    @Override
    public void afterSingletonsInstantiated() {
        log.info("ExtInstantiationAwareBeanPostProcessor 的初始化已经完成");
    }

    /**
     * 销毁前的操作
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @throws BeansException 异常
     */
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(USER_NAME, beanName)) {
            User user = (User) bean;
            user.setName("11111");
        }
    }
}
