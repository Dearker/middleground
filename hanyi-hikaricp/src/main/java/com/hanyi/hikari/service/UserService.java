package com.hanyi.hikari.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hanyi.hikari.pojo.UserEntity;
import com.hanyi.hikari.request.UserQueryPageParam;

/**
 * <p>
 * 用户接口层
 * </p>
 *
 * @author wenchangwei
 * @since 9:38 上午 2020/12/12
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 根据条件查询用户信息
     *
     * @param userQueryPageParam 查询对象
     * @return 返回分页结果
     */
    Page<UserEntity> findUserByCondition(UserQueryPageParam userQueryPageParam);

    /**
     * 分页查询用户信息
     *
     * @param userQueryPageParam 查询对象
     * @return 返回分页结果
     */
    Page<UserEntity> findUserByPage(UserQueryPageParam userQueryPageParam);
}
