package com.hanyi.hikari.controller;

import com.hanyi.framework.model.response.ResponseResult;
import com.hanyi.hikari.pojo.UserEntity;
import com.hanyi.hikari.request.UserQueryPageParam;
import com.hanyi.hikari.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

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
    public ResponseResult findUserByCondition(@RequestBody UserQueryPageParam userQueryPageParam) {
        return ResponseResult.success(userService.findUserByCondition(userQueryPageParam));
    }

    /**
     * 新增用户信息
     *
     * @param userEntity 用户对象
     * @return 返回新增结果
     */
    @PostMapping("/insert")
    public ResponseResult insert(@RequestBody UserEntity userEntity) {
        userEntity.setDeleted(Optional.of(userEntity).map(UserEntity::getDeleted).orElse(0));
        return ResponseResult.success(userService.save(userEntity));
    }

    /**
     * 如果存在则更新记录，否则插入一条记录
     *
     * @param userEntity 用户对象
     * @return 返回结果
     */
    @PostMapping("/saveOrUpdate")
    public ResponseResult saveOrUpdate(@RequestBody UserEntity userEntity) {
        userEntity.setDeleted(Optional.of(userEntity).map(UserEntity::getDeleted).orElse(0));
        return ResponseResult.success(userService.saveOrUpdate(userEntity));
    }

    /**
     * 根据用户id更新用户信息
     *
     * @param userEntity 用户对象
     * @return 返回更新结果
     */
    @PostMapping("/updateById")
    public ResponseResult updateById(@RequestBody UserEntity userEntity) {
        return ResponseResult.success(userService.updateById(userEntity));
    }

    /**
     * 根据id删除用户信息，逻辑删除
     *
     * @param id 用户id
     * @return 返回删除结果
     */
    @GetMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        return ResponseResult.success(userService.removeById(id));
    }

    /**
     * 分页查询
     *
     * @param userQueryPageParam 查询对象
     * @return 返回分页结果
     */
    @PostMapping("/page")
    public ResponseResult findUserByPage(@RequestBody UserQueryPageParam userQueryPageParam) {
        return ResponseResult.success(userService.findUserByPage(userQueryPageParam));
    }

}
