package com.hanyi.hikari.common.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.hikari.dao.BookDao;
import com.hanyi.hikari.pojo.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:19 下午 2020/6/8
 */
public class BatchInsertTask implements Callable<String> {

    private static final int COUNT = 500;

    private final Snowflake snowflake;

    private final BookDao bookDao;

    public BatchInsertTask(Snowflake snowflake, BookDao bookDao) {
        this.snowflake = snowflake;
        this.bookDao = bookDao;
    }

    @Override
    public String call() throws Exception {

        List<Book> bookList = new ArrayList<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            bookList.add(new Book(snowflake.nextId(), RandomUtil.randomString(15),
                    RandomUtil.randomString(20), i % 100, i, DateUtil.date()));
        }
        bookDao.batchInsertBook(bookList);

        return StrUtil.EMPTY;
    }
}
