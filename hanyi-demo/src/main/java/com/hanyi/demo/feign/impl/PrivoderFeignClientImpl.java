package com.hanyi.demo.feign.impl;

import com.hanyi.demo.feign.PrivoderFeignClient;
import org.springframework.stereotype.Component;

/**
 * @PackAge: middleground com.hanyi.demo.feign.impl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-14 16:50
 * @Version: 1.0
 */
@Component
public class PrivoderFeignClientImpl implements PrivoderFeignClient {

    @Override
    public String getUserName() {
        return "fail";
    }

    @Override
    public Boolean deduct(String commodityCode, Integer count) {
        return null;
    }

}
