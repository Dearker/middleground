package com.hanyi.cache.controller;

import com.hanyi.cache.common.annotation.ApiIdempotent;
import com.hanyi.cache.service.TokenService;
import com.hanyi.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * token控制层
 * </p>
 *
 * @author wenchangwei
 * @since 4:54 下午 2020/11/14
 */
@RestController
public class TokenController {

    @Resource
    private TokenService tokenService;

    @GetMapping("/createToken")
    public String createToken() {
        return tokenService.createToken();
    }

    @ApiIdempotent
    @GetMapping("/test")
    @ApiOperation(value = "测试接口", notes = "test")
    public ResponseResult test() {

        String s = "柯基";
        return ResponseResult.success(s);
    }

}
