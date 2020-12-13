package com.hanyi.hikari.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanyi.hikari.pojo.UserEntity;
import com.hanyi.hikari.request.UserQueryPageParam;

import java.util.List;

/**
 * <p>
 * 用户数据层
 * </p>
 *
 * @author wenchangwei
 * @since 9:37 上午 2020/12/12
 */
public interface UserDao extends BaseMapper<UserEntity> {

    /**
     * 根据条件查询用户信息
     *
     * @param userQueryPageParam 查询对象
     * @return 返回分页结果
     */
    List<UserEntity> findUserByCondition(UserQueryPageParam userQueryPageParam);

    /**
     * 根据条件统计用户信息
     *
     * @param userQueryPageParam 查询对象
     * @return 返回用户总数
     */
    Long countUserByCondition(UserQueryPageParam userQueryPageParam);
}
