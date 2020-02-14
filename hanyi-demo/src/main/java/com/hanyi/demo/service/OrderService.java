package com.hanyi.demo.service;

/**
 * @PackAge: middleground com.hanyi.demo.service
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-14 17:06
 * @Version: 1.0
 */
public interface OrderService {

    void placeOrder(String userId, String commodityCode, Integer count);

}
