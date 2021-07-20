package com.hanyi.hikari.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import com.hanyi.hikari.ApplicationTest;
import com.hanyi.hikari.common.thread.BatchInsertTask;
import com.hanyi.hikari.dao.BookDao;
import com.hanyi.hikari.pojo.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:01 下午 2020/6/8
 */
@Slf4j
public class BookRepositoryTest extends ApplicationTest {

    private static final ThreadPoolExecutor THREADPOOLEXECUTOR = ThreadUtil.newExecutor(Byte.SIZE, Byte.SIZE);

    @Resource
    private BookDao bookDao;

    @Resource
    private Snowflake snowflake;

    /**
     * 批量插入
     *
     * @throws InterruptedException 异常
     */
    @Test
    public void batchInsertTest() throws InterruptedException {
        TimeInterval timer = DateUtil.timer();

        for (int j = 0; j < 2000; j++) {
            List<BatchInsertTask> insertTaskList = new ArrayList<>(10);

            for (int i = 0; i < 11; i++) {
                insertTaskList.add(new BatchInsertTask(snowflake, bookDao));
            }

            THREADPOOLEXECUTOR.invokeAll(insertTaskList);
            log.info("插入5000条数据: " + j + "完成");
        }
        System.out.println("插入一千万条数据总耗时：" + timer.intervalRestart());
    }

    @Test
    public void insertBok() {
        int insert = bookDao.insert(new Book(snowflake.nextId(), "西游记", "四大名著", 1, 99, DateUtil.date()));
        log.info("插入成功，总数为：" + insert);
    }

    @Test
    public void batchInsertsTest() {
        TimeInterval timer = DateUtil.timer();
        final int count = 5000;
        List<Book> bookList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            bookList.add(new Book(snowflake.nextId(), RandomUtil.randomString(15),
                    RandomUtil.randomString(20), i % 100, i, DateUtil.date()));
        }
        bookDao.batchInsertBook(bookList);
        System.out.println("插入" + count + "条数据总耗时：" + timer.intervalRestart());
    }

}
