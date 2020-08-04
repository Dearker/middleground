package com.hanyi.daily.common.aware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Map;

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
     *
     * @param environment 系统环境对象
     */
    @Override
    public void setEnvironment(Environment environment) {

        if (environment instanceof StandardServletEnvironment) {
            StandardServletEnvironment standardServletEnvironment = (StandardServletEnvironment) environment;
            MutablePropertySources propertySources = standardServletEnvironment.getPropertySources();

            for (PropertySource<?> propertySource : propertySources) {
                String name = propertySource.getName();

                if(propertySource instanceof MapPropertySource){

                    MapPropertySource mapPropertySource = (MapPropertySource) propertySource;
                    Map<String, Object> stringObjectMap = mapPropertySource.getSource();

                    System.out.println(stringObjectMap);
                }

                log.info("propertySource 的名称：{}", name);
            }

            log.info(" StandardServletEnvironment 的属性来源：{} ", propertySources.toString());
        }

        if (environment instanceof StandardReactiveWebEnvironment) {
            StandardReactiveWebEnvironment standardReactiveWebEnvironment = (StandardReactiveWebEnvironment) environment;
            MutablePropertySources propertySources = standardReactiveWebEnvironment.getPropertySources();

            log.info(propertySources.toString());
        }

    }
}
