package com.hanyi.demo.controller;


import com.hanyi.common.model.response.CommonCode;
import com.hanyi.common.model.response.QueryResponseResult;
import com.hanyi.demo.entity.Employee;
import com.hanyi.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hanyi
 * @since 2019-11-09
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/list")
    public QueryResponseResult getEmployees() {
        return new QueryResponseResult(CommonCode.SUCCESS,employeeService.list(null));
    }

    @GetMapping(value = "/{id}")
    public QueryResponseResult getEmployeeById(@PathVariable("id") int id) {
        return new QueryResponseResult(CommonCode.SUCCESS,employeeService.getById(id));
    }

    @PutMapping(value = "/{id}")
    public String updateEmployee(@PathVariable("id") int id,
                                 @RequestParam(value = "lastName", required = true) String lastName,
                                 @RequestParam(value = "email", required = true) String email,
                                 @RequestParam(value = "gender", required = true) int gender,
                                 @RequestParam(value = "dId", required = true) int dId) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setLastName("张");
        employee.setEmail("zhang@163.com");
        employee.setGender(1);
        employee.setDId(1);

        boolean b = employeeService.updateById(employee);

        if (b) {
            return "update success";
        } else {
            return "update fail";
        }

    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable(value = "id") int id) {
        boolean b = employeeService.removeById(id);

        if (b) {
            return "delete success";
        } else {
            return "delete fail";
        }

    }

    @PostMapping(value = "")
    public String postEmployee() {

        Employee employee = new Employee();
        employee.setLastName("王");
        employee.setEmail("wang@163.com");
        employee.setGender(2);
        employee.setDId(2);
        boolean b = employeeService.save(employee);

        if (b) {
            return "sava success";
        } else {
            return "sava fail";
        }

    }

}

