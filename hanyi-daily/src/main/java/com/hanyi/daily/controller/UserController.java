package com.hanyi.daily.controller;

import com.hanyi.daily.pojo.User;
import com.hanyi.daily.request.QueryUserParam;
import com.hanyi.daily.service.UserService;
import com.hanyi.framework.model.response.ResponseResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseResult getUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(User.builder().userId(1).userName("柯基").userAge(20).build());
        userList.add(User.builder().userId(2).userName("哈士奇").userAge(23).build());
        return ResponseResult.success(userList);
    }

    @GetMapping("/success")
    public ResponseResult success() {
        return ResponseResult.success();
    }

    @GetMapping("/user")
    public ResponseResult getUser(@Validated @RequestBody User user) {
        return ResponseResult.success(user);
    }

    @PostMapping("/save")
    public ResponseResult saveUser(@RequestBody User user) {
        return ResponseResult.success(user);
    }

    /**
     * 批量更新
     *
     * @return 返回更新条数
     */
    @GetMapping("update")
    public ResponseResult updateBatch() {
        return ResponseResult.success(userService.updateBatch());
    }

    /**
     * 根据对象批量更新
     *
     * @return 返回更新条数
     */
    @GetMapping("/updateBatch")
    public ResponseResult updateBatchUser() {
        return ResponseResult.success(userService.updateBatchUser());
    }

    /**
     * 批量新增
     *
     * @param userList 用户集合
     * @return 返回新增条数
     */
    @PostMapping("/insertBatch")
    public ResponseResult insertBatch(@RequestBody List<User> userList) {
        return ResponseResult.success(userService.insertBatch(userList));
    }

    /**
     * 新增用户
     *
     * @return 返回主键id
     */
    @PostMapping("/insert")
    public ResponseResult insert(@RequestBody User user) {
        return ResponseResult.success(userService.insert(user));
    }

    /**
     * 分页查询用户
     *
     * @param queryUserParam 查询对象
     * @return 返回用户集合
     */
    @PostMapping("/query")
    public ResponseResult findUserByPage(@RequestBody QueryUserParam queryUserParam) {
        return ResponseResult.success(userService.findUserByPage(queryUserParam));
    }

    /**
     * 根据用户名称统计
     *
     * @param userName 用户名称
     * @return 返回总数
     */
    @GetMapping("/count")
    public ResponseResult countUserByUserName(String userName) {
        return ResponseResult.success(userService.countUserByUserName(userName));
    }

    @PostMapping("/saveAll")
    public ResponseResult saveAll(@RequestBody User user){
        userService.insertAll(user);
        return ResponseResult.success();
    }

}
