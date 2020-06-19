package com.hanyi.hikari.common.thread;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanyi.hikari.dao.BookDao;
import com.hanyi.hikari.pojo.Book;

import java.util.concurrent.Callable;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:15 下午 2020/6/19
 */
public class QueryCountTask implements Callable<Integer> {

    private final QueryWrapper<Book> wrapper;

    private final BookDao bookDao;

    public QueryCountTask(QueryWrapper<Book> wrapper, BookDao bookDao) {
        this.wrapper = wrapper;
        this.bookDao = bookDao;
    }

    @Override
    public Integer call() throws Exception {
        return bookDao.selectCount(wrapper);
    }
}
