package com.hanyi.daily.controller;

import cn.hutool.core.lang.Dict;
import com.hanyi.daily.common.config.UserProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @GetMapping("/test")
    public String getString(){
        return "柯基小短腿";
    }

    @GetMapping("/property")
    public Dict getProps(){
        return Dict.create().set("userProperty",userProperty);
    }

}
