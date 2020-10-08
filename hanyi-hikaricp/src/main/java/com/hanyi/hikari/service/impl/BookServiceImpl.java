package com.hanyi.hikari.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.hikari.common.constant.HikariConstant;
import com.hanyi.hikari.common.thread.QueryCountTask;
import com.hanyi.hikari.dao.BookDao;
import com.hanyi.hikari.pojo.Book;
import com.hanyi.hikari.service.BookService;
import com.hanyi.hikari.vo.QueryStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 书本逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 9:56 下午 2020/6/8
 */
@Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements BookService {

    private static final ThreadPoolExecutor THREADPOOLEXECUTOR = ThreadUtil.newExecutor(Byte.SIZE, Byte.SIZE);

    @Resource
    private BookDao bookDao;

    @Override
    public List<Book> findBookList() {
        Book book = bookDao.selectById(1);
        return CollUtil.emptyIfNull(Collections.singletonList(book));
    }

    /**
     * 统计查询
     *
     * @return 返回查询的结果
     */
    @Override
    public QueryStats queryCount() {
        TimeInterval timer = DateUtil.timer();
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.ge(HikariConstant.BOOK_TYPE, 0);
        wrapper.le(HikariConstant.BOOK_TYPE, 1);
        Integer integer = bookDao.selectCount(wrapper);

        return new QueryStats(integer, timer.intervalRestart());
    }

    /**
     * 多线程统计
     *
     * @return 返回查询的结果
     */
    @Override
    public QueryStats queryThreadCount() {

        TimeInterval timer = DateUtil.timer();

        List<QueryCountTask> queryCountTaskList = new ArrayList<>();
        QueryWrapper<Book> wrapper;
        for (int i = 0; i < Short.BYTES; i++) {
            wrapper = new QueryWrapper<>();
            wrapper.eq(HikariConstant.BOOK_TYPE, i);
            queryCountTaskList.add(new QueryCountTask(wrapper, bookDao));
        }

        Integer count = 0;
        try {
            List<Future<Integer>> futureList = THREADPOOLEXECUTOR.invokeAll(queryCountTaskList);
            for (Future<Integer> integerFuture : futureList) {
                count += integerFuture.get();
            }
        } catch (Exception e) {
            log.error("queryThreadCount have exception is : {}", e.toString());
        }

        return new QueryStats(count, timer.intervalRestart());
    }
}
