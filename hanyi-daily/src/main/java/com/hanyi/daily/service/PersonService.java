package com.hanyi.daily.service;

import com.hanyi.daily.mapper.mockito.PersonDao;
import com.hanyi.daily.pojo.Person;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 15:26 2020/5/21
 */
public class PersonService {

    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public boolean update(int id, String name) {
        Person person = personDao.getPerson(id);
        if (person == null) {
            return false;
        }

        Person personUpdate = new Person(person.getId(), name);
        return personDao.update(personUpdate);
    }

}
