package com.hanyi.webflux.controller;

import com.hanyi.webflux.entity.User;
import com.hanyi.webflux.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:33 下午 2020/11/21
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * id 查询
     *
     * @param id id
     * @return 返回查询结果
     */
    @GetMapping("/user/{id}")
    public Mono<User> getUserId(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * 查询所有
     *
     * @return 返回所有结果
     */
    @GetMapping("/user")
    public Flux<User> getUsers() {
        return userService.getAllUser();
    }

    /**
     * 添加
     *
     * @param user 添加用户对象
     * @return 返回结果
     */
    @PostMapping("/saveUser")
    public Mono<Void> saveUser(@RequestBody User user) {
        Mono<User> userMono = Mono.just(user);
        return userService.saveUserInfo(userMono);
    }

}
