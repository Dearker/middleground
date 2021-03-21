package com.hanyi.mongo.common.thread;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 17:05 2020/6/18
 */
@RequiredArgsConstructor
public class QueryTask implements Callable<List<String>> {

    private final Query query;

    private final MongoTemplate mongoTemplate;

    @Override
    public List<String> call() throws Exception {
        return mongoTemplate.find(query,String.class,"tb_book");
    }
}
