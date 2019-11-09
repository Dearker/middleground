package com.hanyi.demo.service.impl;

import com.hanyi.demo.entity.Employee;
import com.hanyi.demo.mapper.EmployeeMapper;
import com.hanyi.demo.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hanyi
 * @since 2019-11-09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
