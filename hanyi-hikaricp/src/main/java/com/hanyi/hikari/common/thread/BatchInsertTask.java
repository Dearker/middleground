package com.hanyi.hikari.common.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.hanyi.hikari.dao.BookDao;
import com.hanyi.hikari.pojo.Book;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>
 * 批量插入任务
 * </p>
 *
 * @author wenchangwei
 * @date 2021/07/04
 * @since 10:19 下午 2020/6/8
 */
@RequiredArgsConstructor
public class BatchInsertTask implements Callable<Integer> {

    private static final int COUNT = 500;

    private final Snowflake snowflake;

    private final BookDao bookDao;

    @Override
    public Integer call() throws Exception {
        List<Book> bookList = new ArrayList<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            bookList.add(new Book(snowflake.nextId(), RandomUtil.randomString(15),
                    RandomUtil.randomString(20), i % 100, i, DateUtil.date()));
        }
        return bookDao.batchInsertBook(bookList);
    }
}
