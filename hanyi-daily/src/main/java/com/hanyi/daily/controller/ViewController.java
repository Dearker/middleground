package com.hanyi.daily.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @PackAge: middleground com.hanyi.daily.controller
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-22 17:18
 * @Version: 1.0
 */
@Controller
@Slf4j
public class ViewController {

    @GetMapping("/index")
    public String index(Map<String, Object> map) {
        map.put("name","柯基小短腿...");
        log.info(map.toString());
        return "index";
    }

    @GetMapping("/freemarkerIndex")
    public String freemarkerIndex(Map<String, Object> result) {
        result.put("name", "柯基");
        result.put("sex", "1");
        List<String> userlist = new ArrayList<>();
        userlist.add("zhangsan");
        userlist.add("lisi");
        userlist.add("itmayiedu");
        result.put("userlist", userlist);
        return "freemarkerIndex";
    }

    @GetMapping("/linkage")
    public String linkage(){
        return "linkage";
    }

}
