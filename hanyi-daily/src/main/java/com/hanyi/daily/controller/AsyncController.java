package com.hanyi.daily.controller;

import com.hanyi.daily.common.component.AsyncComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:17 下午 2020/7/31
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Resource
    private AsyncComponent asyncComponent;

    @GetMapping("/task")
    public String task(){

        asyncComponent.taskOne();
        asyncComponent.taskTwo();

        return "success";
    }

}
