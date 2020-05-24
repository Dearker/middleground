package com.hanyi.daily.controller;

import com.hanyi.daily.pojo.User;
import com.hanyi.daily.service.UserService;
import com.hanyi.framework.model.response.ResponseResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: middleground com.hanyi.daily.controller UserController
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-05 09:51
 * @Version: 1.0
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("primary")
    public List<User> primary() {
        return userService.primary();
    }

    @GetMapping("secondary")
    public List<User> secondary() {
        return userService.secondary();
    }

    @GetMapping("/common")
    public ResponseResult getUserList(){
        List<User> userList = new ArrayList<>();
        userList.add(User.builder().userId(1).userName("柯基").userAge(20).build());
        userList.add(User.builder().userId(2).userName("哈士奇").userAge(23).build());
        return ResponseResult.success(userList);
    }

    @GetMapping("/success")
    public ResponseResult success(){
        return ResponseResult.success();
    }

    @GetMapping("/user")
    public ResponseResult getUser(@Validated @RequestBody User user){
        return ResponseResult.success(user);
    }

}
