package com.hanyi.daily.service.impl;

import com.hanyi.daily.pojo.Person;
import com.hanyi.daily.service.MeterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/1/15 8:17 上午
 */
@Service("personMeterService")
public class MeterServiceImpl implements MeterService<Person> {

    @Override
    public Optional<List<Person>> getData(String resourceId) {

        return Optional.empty();
    }

    @Override
    public void saveData(String resourceId, List<Person> resourceList) {

    }
}
