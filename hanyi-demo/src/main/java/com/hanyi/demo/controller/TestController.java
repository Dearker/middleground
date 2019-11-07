package com.hanyi.demo.controller;
import	java.awt.Desktop.Action;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.hanyi.common.model.response.CommonCode;
import com.hanyi.common.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping("/hi")
    @SentinelResource(value="hi")
    @ApiOperation(value="熔断测试", notes="Sentinel")
    public String hi(@RequestParam(value = "name",defaultValue = "hanyi",required = false)String name){

        return "hello "+name;
    }

    @GetMapping("/test")
    @ApiOperation(value="测试接口", notes="test")
    public QueryResponseResult test(){

        String s = "柯基";
        return new QueryResponseResult(CommonCode.SUCCESS,s);
    }

}
