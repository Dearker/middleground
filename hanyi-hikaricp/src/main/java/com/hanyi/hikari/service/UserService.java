package com.hanyi.hikari.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hanyi.hikari.pojo.UserEntity;
import com.hanyi.hikari.request.UserQueryPageParam;
import com.hanyi.hikari.vo.UserTotalVo;

import java.util.List;

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

    /**
     * 根据删除状态进行分组
     *
     * @return 返回分组的结果
     */
    List<UserTotalVo> findUserByGroup();

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


    /**
     * 删除用户的用户名
     *
     * @param userName 用户名
     */
    void removeUserByUserName(String userName);

    /**
     * 获取用户的用户名
     *
     * @param userName 用户名
     * @return 返回用户集合
     */
    List<UserEntity> getUserByUserName(String userName);
}
