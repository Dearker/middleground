package com.hanyi.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.hanyi.framework.model.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sentinel控制层
 * </p>
 *
 * @author wenchangwei
 * @since 10:28 上午 2020/11/21
 */
@RestController
public class SentinelController {

    @GetMapping("/hi")
    @SentinelResource(value = "hi")
    public String hi(@RequestParam(value = "name", defaultValue = "hanyi", required = false) String name) {
        return "hello " + name;
    }

    @GetMapping("/test")
    public ResponseResult test() {
        String s = "柯基";
        return ResponseResult.success(s);
    }

}
