package com.hanyi.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.sharding.dao.UserMapper;
import com.hanyi.sharding.pojo.User;
import com.hanyi.sharding.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 10:03 下午 2020/11/30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 查询所有的用户数据
     *
     * @return 返回集合
     */
    @Override
    public List<User> findAll() {
        return baseMapper.selectList(null);
    }

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 返回是否新增成功
     */
    @Override
    public int insert(User user) {
        return baseMapper.insert(user);
    }

}
