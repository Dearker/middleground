package com.hanyi.sharding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanyi.sharding.pojo.User;

import java.util.List;

/**
 * <p>
 * 用户接口层
 * </p>
 *
 * @author wenchangwei
 * @since 10:01 下午 2020/11/30
 */
public interface UserService extends IService<User> {

    /**
     * 查询所有的用户数据
     *
     * @return 返回集合
     */
    List<User> findAll();

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 返回是否新增成功
     */
    int insert(User user);
}
