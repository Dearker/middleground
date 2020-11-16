package com.hanyi.rabbit.controller;

import com.hanyi.rabbit.service.RabbitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 发送普通消息
     *
     * @param number 消息条数
     * @return 返回条数
     */
    @GetMapping("/send")
    public Integer sendMessage(Integer number) {
        return rabbitService.sendMessage(number);
    }

    /**
     * 发送死信消息
     *
     * @param number 消息条数
     * @return 返回条数
     */
    @GetMapping("/dead")
    public Integer sendDeadMessage(@RequestParam(defaultValue = "5") Integer number) {
        return rabbitService.sendDeadMessage(number);
    }

}
