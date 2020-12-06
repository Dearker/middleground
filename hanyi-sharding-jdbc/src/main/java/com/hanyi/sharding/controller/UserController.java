package com.hanyi.sharding.controller;

import com.hanyi.sharding.pojo.User;
import com.hanyi.sharding.request.UserQueryPageParam;
import com.hanyi.sharding.service.UserService;
import com.hanyi.sharding.vo.PageVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户控制层
 * </p>
 *
 * @author wenchangwei
 * @since 10:06 下午 2020/11/30
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查询所有的用户数据
     *
     * @return 返回用户集合
     */
    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 新增用户信息
     *
     * @param user 新增用户对象
     * @return 返回新增结果
     */
    @PostMapping("/insert")
    public int insert(@RequestBody User user) {
        return userService.insert(user);
    }

    /**
     * 分页查询用户数据
     *
     * @param queryPageParam 查询条件
     * @return 返回分页结果
     */
    @PostMapping("/page")
    public PageVo<User> findUserByPage(@RequestBody UserQueryPageParam queryPageParam) {
        return userService.findUserByPage(queryPageParam);
    }

}
