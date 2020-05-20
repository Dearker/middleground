package com.hanyi.mongo.repository;

import com.hanyi.mongo.pojo.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @PackAge: middleground com.hanyi.mongo.repository
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-20 20:18
 * @Version: 1.0
 */
public interface OrderRepository extends MongoRepository<Order, Long> {
}
