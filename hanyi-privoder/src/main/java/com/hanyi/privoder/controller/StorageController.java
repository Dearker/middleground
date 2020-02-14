package com.hanyi.privoder.controller;

import com.hanyi.privoder.service.StorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @PackAge: middleground com.hanyi.privoder.controller
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-14 15:44
 * @Version: 1.0
 */
@RestController
@RequestMapping("storage")
public class StorageController {

    @Resource
    private StorageService storageService;

    /**
     * 减库存
     * @param commodityCode 商品代码
     * @param count 数量
     * @return
     */
    @RequestMapping(path = "/deduct")
    public Boolean deduct(String commodityCode, Integer count) {
        storageService.deduct(commodityCode, count);
        return true;
    }

}
