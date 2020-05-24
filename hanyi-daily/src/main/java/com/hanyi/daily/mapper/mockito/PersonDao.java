package com.hanyi.daily.mapper.mockito;

import com.hanyi.daily.pojo.Person;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 15:26 2020/5/21
 */
public interface PersonDao {

    /**
     * 查询用户
     *
     * @param id 用户id
     * @return 返回用户信息
     */
    Person getPerson(int id);

    /**
     * 更新用户
     *
     * @param person 更新的用户对象
     * @return 返回更新结果
     */
    boolean update(Person person);

}
