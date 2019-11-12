package com.hanyi.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanyi.demo.entity.Employee;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hanyi
 * @since 2019-11-09
 */
@CacheConfig(cacheNames = "employeeCache")
@Cacheable
public interface EmployeeMapper extends BaseMapper<Employee> {

}
