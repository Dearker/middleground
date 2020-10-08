package com.hanyi.daily.service;

import com.hanyi.daily.mapper.one.PrimaryUserMapper;
import com.hanyi.daily.mapper.two.SecondaryUserMapper;
import com.hanyi.daily.pojo.User;
import com.hanyi.daily.request.QueryUserParam;
import com.hanyi.daily.request.UpdateUserParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


/**
 * @author weiwen
 */
@Service
public class UserService {

    @Resource
    private PrimaryUserMapper primaryUserMapper;

    @Resource
    private SecondaryUserMapper secondaryUserMapper;

    public List<User> primary() {
        return primaryUserMapper.findAll();
    }

    public List<User> secondary() {
        return secondaryUserMapper.findAll();
    }

    /**
     * 批量更新用户
     *
     * @return 返回更新条数
     */
    public Integer updateBatch() {
        return primaryUserMapper.updateUserById("小短腿", Arrays.asList(1, 6));
    }

    /**
     * 根据对象批量更新
     *
     * @return 返回更新条数
     */
    public Integer updateBatchUser() {
        return primaryUserMapper.updateBatchUser(new UpdateUserParam(Arrays.asList(1, 6), "小短腿"));
    }

    /**
     * 批量新增
     *
     * @param userList 用户集合
     * @return 返回新增条数
     */
    public Integer insertBatch(List<User> userList) {
        return primaryUserMapper.insertBatch(userList);
    }

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 返回主键id
     */
    public Integer insert(User user) {
        return primaryUserMapper.insert(user);
    }

    /**
     * 分页查询用户
     *
     * @param queryUserParam 查询对象
     * @return 返回用户集合
     */
    public List<User> findUserByPage(QueryUserParam queryUserParam) {
        Integer currentPage = queryUserParam.getCurrentPage();
        Integer pageSize = queryUserParam.getPageSize();
        queryUserParam.setStartIndex((currentPage - 1) * pageSize);
        return primaryUserMapper.findUserByPage(queryUserParam);
    }

    /**
     * 根据用户名称统计查询
     *
     * @param userName 用户名称
     * @return 返回总数
     */
    public Long countUserByUserName(String userName){
        return primaryUserMapper.countUserByUserName(userName);
    }

}
