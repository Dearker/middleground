package com.hanyi.mongo.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.mongo.MongodbApplicationTests;
import com.hanyi.mongo.common.thread.InsertTask;
import com.hanyi.mongo.common.thread.UpdateTask;
import com.hanyi.mongo.pojo.Book;
import com.hanyi.mongo.service.BookService;
import com.hanyi.mongo.vo.BookStatisticsGroupInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @PackAge: middleground com.hanyi.mongo.repository
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 12:07
 * @Version: 1.0
 */
@Slf4j
public class BookRepositoryTest extends MongodbApplicationTests {

    private static final ThreadPoolExecutor THREADPOOLEXECUTOR = ThreadUtil.newExecutor(20, 20);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testBatchInsert() throws InterruptedException {

        TimeInterval timer = DateUtil.timer();

        for (int j = 0; j < 700; j++) {

            List<InsertTask> insertTaskList = new ArrayList<>(20);

            for (int i = 0; i < 20; i++) {
                insertTaskList.add(new InsertTask(snowflake, mongoTemplate));
            }

            THREADPOOLEXECUTOR.invokeAll(insertTaskList);
            log.info("插入一万条数据: " + j + "完成");
        }
        System.out.println("插入一千万条数据总耗时：" + timer.intervalRestart());
    }

    @Test
    public void testDelete() {
        mongoTemplate.remove(new Query().limit(4000), Book.class);
    }

    @Test
    public void testQueryByAggregation() {

        TimeInterval timer = DateUtil.timer();

        for (int i = 0; i < 10; i++) {
            List<BookStatisticsGroupInfo> bookStatisticsGroupInfos = bookService.queryBookByAggregation();

            System.out.println("数据总数：" + bookStatisticsGroupInfos.get(0));
            System.out.println("总共耗时：" + timer.intervalRestart());
            System.out.println("---------------------");
        }
    }

    @Test
    public void testBatchUpdate() throws InterruptedException {

        for (int i = 101; i < 500; i++) {
            List<Book> bookList = mongoTemplate.find(new Query(Criteria.where("book_type").is(i)), Book.class);
            //对2万条数据进行拆分
            bookList.forEach(s -> s.setBookType(s.getBookType() % 100));
            List<UpdateTask> updateTaskList = new ArrayList<>(40);
            int length = bookList.size();
            for (int j = 0; j < 40; j++) {

                if (length < j * 500 + 500) {
                    updateTaskList.add(new UpdateTask(bookRepository, bookList.subList(j * 500, j * 500 + 500)));
                } else {
                    updateTaskList.add(new UpdateTask(bookRepository, bookList.subList(j * 500, length)));
                }
            }
            THREADPOOLEXECUTOR.invokeAll(updateTaskList);
            System.out.println("类型为: " + i + "的数据更新完成");
        }
    }

}
