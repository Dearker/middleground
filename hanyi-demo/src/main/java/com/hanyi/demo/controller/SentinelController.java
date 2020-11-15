package com.hanyi.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.hanyi.demo.feign.PrivoderFeignClient;
import com.hanyi.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author weiwenchang
 * @since 2019-10-15
 */
@RestController
@Api("测试接口")
public class SentinelController {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private PrivoderFeignClient privoderFeignClient;

    @GetMapping("/hi")
    @SentinelResource(value = "hi")
    @ApiOperation(value = "熔断测试", notes = "Sentinel")
    public String hi(@RequestParam(value = "name", defaultValue = "hanyi", required = false) String name) {

        return "hello " + name;
    }

    @GetMapping("/test")
    @ApiOperation(value = "测试接口", notes = "test")
    public ResponseResult test() {

        String s = "柯基";
        return ResponseResult.success(s);
    }

    @GetMapping("/name")
    public String getUserName() {
        return privoderFeignClient.getUserName();
    }

}
