package com.hanyi.hikari.controller;

import com.hanyi.framework.model.response.ResponseResult;
import com.hanyi.hikari.request.UserQueryPageParam;
import com.hanyi.hikari.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户控制层
 * </p>
 *
 * @author wenchangwei
 * @since 9:52 上午 2020/12/12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 用户服务层
     */
    @Resource
    private UserService userService;

    /**
     * 根据条件查询用户信息
     *
     * @param userQueryPageParam 查询对象
     * @return 返回分页结果
     */
    @PostMapping("/condition")
    public ResponseResult findUserByCondition(@RequestBody UserQueryPageParam userQueryPageParam){
        return ResponseResult.success(userService.findUserByCondition(userQueryPageParam));
    }

}
