package com.hanyi.daily.common.aware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletContextPropertySource;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 线程池系统环境组件类
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 20:39 2020/8/3
 */
@Slf4j
@Component
public class ThreadPoolEnvironmentComponent implements EnvironmentAware {

    /**
     * 获取的系统环境属性
     * 注：MapPropertySource为多个具体的propertySources的父类
     *
     * @param environment 系统环境对象
     */
    @Override
    public void setEnvironment(Environment environment) {
        //非 Web 应用，Environment 接口的实现为 StandardEnvironment
        if (environment instanceof StandardServletEnvironment) {
            StandardServletEnvironment standardServletEnvironment = (StandardServletEnvironment) environment;
            MutablePropertySources propertySources = standardServletEnvironment.getPropertySources();

            Map<String, Object> systemEnvironment = standardServletEnvironment.getSystemEnvironment();
            Map<String, Object> systemProperties = standardServletEnvironment.getSystemProperties();

            log.info("获取的 systemEnvironment: " + systemEnvironment);
            log.info("获取的 systemProperties ：" + systemProperties);

            propertySources.forEach(this::buildSpecificPropertySource);

            log.info(" StandardServletEnvironment 的属性来源：{} ", propertySources.toString());
        }

        if (environment instanceof StandardReactiveWebEnvironment) {
            StandardReactiveWebEnvironment standardReactiveWebEnvironment = (StandardReactiveWebEnvironment) environment;
            MutablePropertySources propertySources = standardReactiveWebEnvironment.getPropertySources();

            log.info(propertySources.toString());
        }

    }

    /**
     * 将propertySource转换成具体的属性类
     *
     * @param propertySource 属性来源类
     */
    private void buildSpecificPropertySource(PropertySource<?> propertySource) {
        //systemProperties
        if (propertySource instanceof PropertiesPropertySource) {
            PropertiesPropertySource propertiesPropertySource = (PropertiesPropertySource) propertySource;

            String name = propertiesPropertySource.getName();
            Map<String, Object> stringObjectMap = propertiesPropertySource.getSource();

            log.info("propertiesPropertySource 的名称：" + name);
            log.info("{}", stringObjectMap.toString());
        }

        //random
        if (propertySource instanceof RandomValuePropertySource) {
            RandomValuePropertySource randomValuePropertySource = (RandomValuePropertySource) propertySource;

            String name = randomValuePropertySource.getName();
            Random source = randomValuePropertySource.getSource();

            log.info("randomValuePropertySource 的名称：" + name);
            log.info(source.toString());
        }

        //servletConfigPropertySource
        if (propertySource instanceof PropertySource.StubPropertySource) {
            PropertySource.StubPropertySource servletConfigPropertySource = (PropertySource.StubPropertySource) propertySource;

            String name = servletConfigPropertySource.getName();
            Object source = servletConfigPropertySource.getSource();

            log.info("servletConfigPropertySource 的名称：" + name);
            log.info(source.toString());
        }

        //servletContextInitParams
        if (propertySource instanceof ServletContextPropertySource) {
            ServletContextPropertySource servletContextPropertySource = (ServletContextPropertySource) propertySource;

            String name = servletContextPropertySource.getName();
            ServletContext source = servletContextPropertySource.getSource();

            log.info("ServletContextPropertySource 的名称：" + name);
            log.info(source.toString());
        }

        //applicationConfig
        if (propertySource instanceof OriginTrackedMapPropertySource) {
            OriginTrackedMapPropertySource originTrackedMapPropertySource = (OriginTrackedMapPropertySource) propertySource;

            String name = originTrackedMapPropertySource.getName();
            Map<String, Object> source = originTrackedMapPropertySource.getSource();

            log.info("OriginTrackedMapPropertySource 的名称：" + name);
            log.info(source.toString());
        }

        //systemEnvironment
        if (propertySource instanceof SystemEnvironmentPropertySource) {
            SystemEnvironmentPropertySource systemEnvironmentPropertySource = (SystemEnvironmentPropertySource) propertySource;

            String name = systemEnvironmentPropertySource.getName();
            Map<String, Object> source = systemEnvironmentPropertySource.getSource();

            log.info("systemEnvironmentPropertySource 的名称：" + name);
            log.info(source.toString());
        }
    }


}
