package com.hanyi.admin.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiwenchang
 * @since 2019-10-15
 */
@RestController
public class TestController {

    @GetMapping("/hi")
    @SentinelResource(value="hi")
    public String hi(@RequestParam(value = "name",defaultValue = "hanyi",required = false)String name){

        return "hello "+name;
    }


}
