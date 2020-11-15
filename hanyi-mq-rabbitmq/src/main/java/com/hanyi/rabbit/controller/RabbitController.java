package com.hanyi.rabbit.controller;

import com.hanyi.rabbit.service.RabbitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 7:11 下午 2020/11/15
 */
@RestController
public class RabbitController {

    @Resource
    private RabbitService rabbitService;

    @GetMapping("/send")
    public Integer sendMessage(Integer number){
        return rabbitService.sendMessage(number);
    }

}
