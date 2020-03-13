package com.hanyi.daily.mapper.one;

import com.hanyi.daily.pojo.User;

import java.util.List;

/**
 * @author weiwen
 */
public interface PrimaryUserMapper {

    /**
     * 查询Primary所有
     * @return
     */
    List<User> findAll();
}
