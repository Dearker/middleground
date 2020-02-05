package com.hanyi.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.hanyi.demo.common.annotation.ApiIdempotent;
import com.hanyi.demo.service.TokenService;
import com.hanyi.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiwenchang
 * @since 2019-10-15
 */
@RestController
@Api("测试接口")
public class TestController {

    @Autowired
    private TokenService tokenService;


    @GetMapping("/createToken")
    public String createToken(){
        return tokenService.createToken();
    }

    @GetMapping("/hi")
    @SentinelResource(value="hi")
    @ApiOperation(value="熔断测试", notes="Sentinel")
    public String hi(@RequestParam(value = "name",defaultValue = "hanyi",required = false)String name){

        return "hello "+name;
    }

    @ApiIdempotent
    @GetMapping("/test")
    @ApiOperation(value="测试接口", notes="test")
    public ResponseResult test(){

        String s = "柯基";
        return ResponseResult.success(s);
    }

}
