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

    Person getPerson(int id);

    boolean update(Person person);

}
