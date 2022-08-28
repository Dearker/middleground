package com.hanyi.hikari.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanyi.hikari.pojo.UserInfoEntity;

import java.util.List;

/**
 * <p>
 * 用户详情数据层
 * </p>
 *
 * @author wenchangwei
 * @since 9:37 上午 2020/12/12
 */
public interface UserInfoDao extends BaseMapper<UserInfoEntity> {

    /**
     * 批量保存用户信息
     *
     * @param userInfoEntityList 用户信息实体列表
     * @return int
     */
    int batchSaveUserInfo(List<UserInfoEntity>userInfoEntityList);

}
