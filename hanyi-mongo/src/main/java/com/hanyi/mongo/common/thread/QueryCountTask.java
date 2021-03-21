package com.hanyi.mongo.common.thread;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.concurrent.Callable;

/**
 * <p>
 * 统计查询线程任务
 * </p>
 *
 * @author wenchangwei
 * @since 2:13 下午 2020/6/7
 */
@RequiredArgsConstructor
public class QueryCountTask implements Callable<Long> {

    private final Query query;

    private final MongoTemplate mongoTemplate;

    @Override
    public Long call() {
        return mongoTemplate.count(query, "tb_book");
    }
}
