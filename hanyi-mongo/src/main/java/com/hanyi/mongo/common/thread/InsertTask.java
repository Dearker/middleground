package com.hanyi.mongo.common.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.hanyi.mongo.pojo.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @PackAge: middleground com.hanyi.mongo.common.thread
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 14:19
 * @Version: 1.0
 */
@RequiredArgsConstructor
public class InsertTask implements Callable<String> {

    private static final int COUNT = 500;

    private final Snowflake snowflake;

    private final MongoTemplate mongoTemplate;

    @Override
    public String call() throws Exception {
        List<Book> bookList = new ArrayList<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            bookList.add(new Book(snowflake.nextId(), RandomUtil.randomString(15),
                    RandomUtil.randomString(20), i % 100, i, DateUtil.date()));
        }
        mongoTemplate.insertAll(bookList);
        return "插入"+ COUNT+"条数据完成";
    }
}
