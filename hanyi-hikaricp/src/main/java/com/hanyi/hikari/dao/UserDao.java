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

    /**
     * 根据版本号查询用户集合
     *
     * @param version 版本号
     * @return 返回用户集合
     */
    List<UserEntity> findUserByExist(Integer version);

    /**
     * 根据版本号和用户名进行or查询
     *
     * @param version  版本号
     * @param userName 用户名称
     * @return 返回查询结果
     */
    List<UserEntity> findUserByUnion(Integer version, String userName);

    /**
     * 根据版本号和用户名进行or查询,包含重复数据
     *
     * @param version  版本号
     * @param userName 用户名称
     * @return 返回查询结果
     */
    List<UserEntity> findUerByUnionAll(Integer version, String userName);
}
