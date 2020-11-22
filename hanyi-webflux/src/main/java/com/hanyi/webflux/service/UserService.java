package com.hanyi.webflux.service;

import com.hanyi.webflux.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 用户接口层
 * </p>
 *
 * @author wenchangwei
 * @since 9:13 下午 2020/11/21
 */
public interface UserService {

    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return 返回用户
     */
    Mono<User> getUserById(int id);

    /**
     * 查询所有用户
     *
     * @return 返回用户集合
     */
    Flux<User> getAllUser();

    /**
     * 添加用户
     *
     * @param user 用户对象
     * @return 返回结果
     */
    Mono<Void> saveUserInfo(Mono<User> user);

}
