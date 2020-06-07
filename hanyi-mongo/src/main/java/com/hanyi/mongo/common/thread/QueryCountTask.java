package com.hanyi.mongo.common.thread;

import com.hanyi.mongo.pojo.Book;
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
public class QueryCountTask implements Callable<Long> {

    private final Query query;

    private final MongoTemplate mongoTemplate;

    public QueryCountTask(Query query, MongoTemplate mongoTemplate) {
        this.query = query;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Long call() {
        return mongoTemplate.count(query, Book.class);
    }
}
