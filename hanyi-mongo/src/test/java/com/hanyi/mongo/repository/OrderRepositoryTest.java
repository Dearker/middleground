package com.hanyi.mongo.repository;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.hanyi.mongo.MongodbApplicationTests;
import com.hanyi.mongo.pojo.Order;
import com.hanyi.mongo.pojo.OrderStatistics;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.repository
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-20 20:19
 * @Version: 1.0
 */
public class OrderRepositoryTest extends MongodbApplicationTests {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private Snowflake snowflake;

    @Test
    public void insertTest(){
        Order order;
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            order = new Order(snowflake.nextId(), RandomUtil.randomString(5),i);
            orderList.add(order);
        }
        orderRepository.saveAll(orderList);
    }

    @Test
    public void groupTest(){

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("orderType")
                .count().as("count")
                .last("orderType").as("orderType"));

        AggregationResults<OrderStatistics> aggregate = mongoTemplate.aggregate(aggregation, Order.class, OrderStatistics.class);

        aggregate.getMappedResults().forEach(s-> System.out.println("获取的数据："+ s));

    }


}
