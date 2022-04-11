package com.hanyi.daily.controller;

import com.hanyi.daily.pojo.Person;
import com.hanyi.daily.service.MeterService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/1/15 8:53 上午
 */
@RestController
@RequestMapping("/meter")
public class MeterController {

    @Resource
    private ApplicationContext applicationContext;

    private final MeterService<Person> meterService;

    public MeterController(MeterService<Person> meterService) {
        this.meterService = meterService;
     }

    @PostConstruct
    public void init(){
        Object personMeterService = applicationContext.getBean("personMeterService");
    }

}
