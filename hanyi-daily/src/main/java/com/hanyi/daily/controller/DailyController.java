package com.hanyi.daily.controller;

import cn.hutool.core.lang.Dict;
import com.hanyi.daily.common.property.UserProperty;
import com.hanyi.daily.common.strategy.SearchTypeService;
import com.hanyi.daily.pojo.Company;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: middleground com.hanyi.daily.controller DailyController
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-04 15:50
 * @Version: 1.0
 */
@RestController
public class DailyController {

    @Resource
    private UserProperty userProperty;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private List<SearchTypeService> searchTypeServiceList;

    @GetMapping("/test")
    public String getString() {
        return "柯基小短腿";
    }

    @GetMapping("/property")
    public Dict getProps() {
        return Dict.create().set("userProperty", userProperty);
    }

    @GetMapping("/name")
    public Company getName() {
        return new Company();
    }

    /**
     * 直接通过注入的方式获取接口下的所有实现，并完成调用返回
     *
     * @return 返回执行后的结果集合
     */
    @GetMapping("/search")
    public List<Integer> searchTest() {
        return searchTypeServiceList.stream().map(SearchTypeService::search).collect(Collectors.toList());
    }

    /**
     * 根据接口名称获取对应的beanName和接口实现执行的结果的集合
     *
     * @return 返回执行的结果
     */
    @GetMapping("/map")
    public Map<String, Integer> map() {
        Map<String, SearchTypeService> serviceMap = applicationContext.getBeansOfType(SearchTypeService.class);
        Map<String, Integer> stringMap = new HashMap<>(serviceMap.size());
        serviceMap.forEach((k, v) -> stringMap.put(k, v.search()));
        return stringMap;
    }

}
