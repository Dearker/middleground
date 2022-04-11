package com.hanyi.daily.service.impl;

import com.hanyi.daily.pojo.Student;
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
 * @since 2022/1/15 8:52 上午
 */
@Service("readMeterService")
public class ReadMeterServiceImpl implements MeterService<Student> {

    @Override
    public Optional<List<Student>> getData(String resourceId) {
        return Optional.empty();
    }

    @Override
    public void saveData(String resourceId, List<Student> resourceList) {

    }
}
