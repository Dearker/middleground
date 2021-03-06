package com.hanyi.hikari.common.thread;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hanyi.hikari.dao.BookDao;
import com.hanyi.hikari.pojo.Book;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Callable;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:15 下午 2020/6/19
 */
@RequiredArgsConstructor
public class QueryCountTask implements Callable<Integer> {

    private final LambdaQueryWrapper<Book> wrapper;

    private final BookDao bookDao;

    @Override
    public Integer call() throws Exception {
        return bookDao.selectCount(wrapper);
    }
}
