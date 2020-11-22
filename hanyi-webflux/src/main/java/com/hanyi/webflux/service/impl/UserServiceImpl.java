package com.hanyi.webflux.service.impl;

import com.hanyi.webflux.entity.User;
import com.hanyi.webflux.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 9:15 下午 2020/11/21
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 创建map集合存储数据
     */
    private static final Map<Integer, User> USER_MAP = new HashMap<>(Integer.BYTES);

    static {
        USER_MAP.put(1, new User("lucy", "nan", 20));
        USER_MAP.put(2, new User("mary", "nv", 30));
        USER_MAP.put(3, new User("jack", "nv", 50));
    }

    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return 返回用户
     */
    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(USER_MAP.get(id));
    }

    /**
     * 查询所有用户
     *
     * @return 返回用户集合
     */
    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(USER_MAP.values());
    }

    /**
     * 添加用户
     *
     * @param userMono 用户对象
     * @return 返回结果
     */
    @Override
    public Mono<Void> saveUserInfo(Mono<User> userMono) {
        return userMono.doOnNext(person -> {
            //向map集合里面放值
            int id = USER_MAP.size() + 1;
            USER_MAP.put(id, person);
        }).thenEmpty(Mono.empty());
    }
}
