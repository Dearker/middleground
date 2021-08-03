package com.hanyi.thread.common.config;

import com.hanyi.thread.pojo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author wcwei@iflytek.com
 * @since 2021-08-03 17:04
 */
@Slf4j
@Configuration
public class ThreadPoolBeanConfig {

    @PostConstruct
    public void init(){
        log.info("线程池初始化方法");
    }

    @Bean
    public Person person(DefaultListableBeanFactory defaultListableBeanFactory){
        log.info("ioc容器对象：{}", defaultListableBeanFactory);
        return new Person(123,"柯基");
    }

}
