package com.hanyi.daily.mapper.one;

import com.hanyi.daily.pojo.User;
import com.hanyi.daily.request.QueryUserParam;
import com.hanyi.daily.request.UpdateUserParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author weiwen
 */
public interface PrimaryUserMapper {

    /**
     * 查询Primary所有
     *
     * @return 返回所有数据
     */
    List<User> findAll();

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 返回新增条数
     */
    Integer insert(User user);

    /**
     * 批量新增用户
     *
     * @param userList 用户集合
     * @return 返回新增条数
     */
    Integer insertBatch(List<User> userList);

    /**
     * 批量更新用户信息
     *
     * @param userName    用户名称
     * @param integerList id集合
     * @return 返回更新的条数
     */
    Integer updateUserById(@Param("userName") String userName, @Param("ids") List<Integer> integerList);

    /**
     * 根据对象批量更新
     *
     * @param updateUserParam 更新对象
     * @return 返回更新条数
     */
    Integer updateBatchUser(UpdateUserParam updateUserParam);

    /**
     * 分页查询用户
     *
     * @param queryUserParam 查询对象
     * @return 返回用户集合
     */
    List<User> findUserByPage(QueryUserParam queryUserParam);

    /**
     * 根据用户名称统计查询
     *
     * @param userName 用户名称
     * @return 返回总数
     */
    Long countUserByUserName(@Param("userName") String userName);
}
