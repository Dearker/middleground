package com.hanyi.daily.controller;

import com.hanyi.daily.pojo.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 事件控制层
 * </p>
 *
 * @author wenchangwei
 * @since 11:36 上午 2021/4/4
 */
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class ApplicationEventController {

    private final ApplicationContext applicationContext;

    @GetMapping("/message")
    public String message(String name){
        applicationContext.publishEvent(new MessageEvent(this, name));
        return "finish";
    }

}
