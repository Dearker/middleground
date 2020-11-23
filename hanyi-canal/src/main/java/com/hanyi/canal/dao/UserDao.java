package com.hanyi.canal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanyi.canal.pojo.User;

import java.util.List;

/**
 * <p>
 * 用户数据层
 * </p>
 *
 * @author wenchangwei
 * @since 11:02 下午 2020/11/22
 */
public interface UserDao extends BaseMapper<User> {

    /**
     * 批量新增用户
     *
     * @param userList 用户集合
     * @return 返回新增条数
     */
    int batchInsertUser(List<User> userList);

}
