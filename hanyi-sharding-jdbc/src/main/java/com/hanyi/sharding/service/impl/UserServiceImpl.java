package com.hanyi.sharding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.sharding.dao.UserMapper;
import com.hanyi.sharding.pojo.User;
import com.hanyi.sharding.request.UserQueryPageParam;
import com.hanyi.sharding.service.UserService;
import com.hanyi.sharding.vo.PageVo;
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

    /**
     * 根据分页查询数据
     *
     * @param userQueryPageParam 分页条件
     * @return 返回查询结果
     */
    @Override
    public PageVo<User> findUserByPage(UserQueryPageParam userQueryPageParam) {

        //构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getName, userQueryPageParam.getName());
        wrapper.orderByAsc(User::getAge);
        //构建分页查询对象
        Page<User> queryPage = new Page<>(userQueryPageParam.getCurrentPage(), userQueryPageParam.getPageSize());
        IPage<User> userPage = baseMapper.selectPage(queryPage, wrapper);
        return new PageVo<>(userPage.getTotal(), userPage.getRecords());
    }

}
