package com.hanyi.demo.controller;

import com.hanyi.demo.component.MapEhcaChe;
import com.hanyi.demo.service.EmployeeService;
import com.hanyi.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName: middleground com.hanyi.demo.controller CacheController
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-12 21:20
 * @Version: 1.0
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MapEhcaChe<String, String> mapEhcaChe;

    @GetMapping("/remoKey")
    public void remoKey() {
        cacheManager.getCache("employeeCache").clear();
    }

    @GetMapping(value = "/{id}")
    public ResponseResult getEmployeeById(@PathVariable("id") int id) {
        return ResponseResult.success(employeeService.getById(id));
    }

    @GetMapping("/ehcaChePut")
    public String put(String key, String value) {
        mapEhcaChe.put(key, value);
        return "success";
    }

    @GetMapping("/get")
    public String get(String key) {
        return mapEhcaChe.get(key);
    }


}
