package com.hanyi.demo.service.impl;

import com.hanyi.demo.entity.Order;
import com.hanyi.demo.feign.PrivoderFeignClient;
import com.hanyi.demo.mapper.OrderMapper;
import com.hanyi.demo.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @PackAge: middleground com.hanyi.demo.service.impl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-14 17:06
 * @Version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private PrivoderFeignClient privoderFeignClient;

    /**
     * 下单：创建订单、减库存，涉及到两个服务
     *
     * @param userId
     * @param commodityCode
     * @param count
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void placeOrder(String userId, String commodityCode, Integer count) {
        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
        Order order = Order.builder()
                .userId(userId)
                .commodityCode(commodityCode)
                .count(count)
                .money(orderMoney)
                .build();
        orderMapper.insert(order);
        privoderFeignClient.deduct(commodityCode, count);

    }


}
