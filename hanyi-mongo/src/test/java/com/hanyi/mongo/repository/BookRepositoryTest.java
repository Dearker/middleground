package com.hanyi.mongo.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.mongo.MongodbApplicationTests;
import com.hanyi.mongo.common.thread.*;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @PackAge: middleground com.hanyi.mongo.repository
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 12:07
 * @Version: 1.0
 */
@Slf4j
public class BookRepositoryTest extends MongodbApplicationTests {

    private static final ThreadPoolExecutor THREADPOOLEXECUTOR = ThreadUtil.newExecutor(10, 10);

    private static final TimeInterval timer = DateUtil.timer();

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

        for (int j = 0; j < 2000; j++) {

            List<InsertTask> insertTaskList = new ArrayList<>(10);

            for (int i = 0; i < 10; i++) {
                insertTaskList.add(new InsertTask(snowflake, mongoTemplate));
            }

            THREADPOOLEXECUTOR.invokeAll(insertTaskList);
            log.info("插入5000条数据: " + j + "完成");
        }
        System.out.println("插入一千万条数据总耗时：" + timer.intervalRestart());
    }

    @Test
    public void testDelete() {
        mongoTemplate.remove(new Query().limit(40000), Book.class);
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

            long bookTypeCount = mongoTemplate.count(new Query(Criteria.where("book_type").is(i)), Book.class);
            System.out.println("获取的类型总数为：" + bookTypeCount);

            int length = (int) Math.ceil(bookTypeCount / 500);
            List<UpdateTask> updateTaskList = new ArrayList<>(length);
            for (int j = 0; j < length; j++) {
                List<Book> bookList;
                if (j == 0) {
                    bookList = mongoTemplate.find(new Query(Criteria.where("book_type").is(i)).limit(500), Book.class);
                    bookList.forEach(s -> s.setBookType(s.getBookType() % 100));
                } else {
                    bookList = mongoTemplate.find(new Query(Criteria.where("book_type").is(i)).skip(j * 500).limit(500), Book.class);
                    bookList.forEach(s -> s.setBookType(s.getBookType() % 100));
                }
                updateTaskList.add(new UpdateTask(bookRepository, bookList));
            }
            THREADPOOLEXECUTOR.invokeAll(updateTaskList);
            System.out.println("类型为: " + i + "的数据更新完成");
        }

    }

    @Test
    public void includeTest() {
        List<Book> bookList = bookService.queryBookByInclude();
        bookList.forEach(s -> System.out.println("获取的数据：" + s));
    }

    @Test
    public void sleepTest() {

        TimeInterval timer = DateUtil.timer();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            THREADPOOLEXECUTOR.execute(new WaitTask(atomicInteger));
        }
        System.out.println("获取的数据：" + atomicInteger.get());
        System.out.println("总共耗时：" + timer.intervalRestart());
    }

    @Test
    public void countTest() {
        TimeInterval timer = DateUtil.timer();
        Query query = new Query(Criteria.where("book_type").is(0));

        /*query.fields().include("id");
        List<String> stringList = mongoTemplate.find(query, String.class, "tb_book");
        //6.6s
        System.out.println("获取的总数：" + stringList.size());*/

        long count = mongoTemplate.count(query, Book.class);
        System.out.println("获取的总数：" + count);
        //4.3s
        System.out.println("总共耗时：" + timer.intervalRestart());
    }

    @Test
    public void countThreadTest() throws Exception {

        TimeInterval timer = DateUtil.timer();
        Query query;
        List<QueryCountTask> queryCountTaskList = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            query = new Query(Criteria.where("book_type").gte(i * 10).lt((i + 1) * 10));
            queryCountTaskList.add(new QueryCountTask(query, mongoTemplate));
        }

        List<Future<Long>> invokeAll = THREADPOOLEXECUTOR.invokeAll(queryCountTaskList);

        Long count = 0L;
        for (Future<Long> longFuture : invokeAll) {
            count += longFuture.get();
        }
        System.out.println("获取的总数：" + count);
        System.out.println("总共耗时：" + timer.intervalRestart());
    }

    @Test
    public void betweenCountTest() {
        Query query = new Query(Criteria.where("book_type").gte(0).lte(70));
        long count = mongoTemplate.count(query, Book.class);
        //7100000
        System.out.println("获取的总数：" + count);
        //10860
        System.out.println("总共耗时：" + timer.intervalRestart());
    }

    @Test
    public void queryTest() {

        Query query = new Query(Criteria.where("_id").is(1273431166254452737L));
        query.fields().include("book_name");
        List<Book> bookList = mongoTemplate.find(query, Book.class);
        System.out.println(bookList);

        List<String> stringList = mongoTemplate.find(query, String.class, "tb_book");
        stringList.forEach(System.out::println);
    }

    @Test
    public void queryListTest() {
        TimeInterval timer = DateUtil.timer();
        Query query = new Query(Criteria.where("book_type").gte(0).lte(1));

        query.fields().include("_id");
        List<String> stringList = mongoTemplate.find(query, String.class, "tb_book");
        System.out.println("总数为：" + stringList.size());
        //24224
        System.out.println("消耗的时间：" + timer.intervalRestart());
    }

    @Test
    public void queryThreadTest() throws InterruptedException, ExecutionException {

        TimeInterval timer = DateUtil.timer();
        List<QueryTask> queryTaskList = new ArrayList<>(2);

        Query query;
        for (int i = 0; i < 2; i++) {
            query = new Query(Criteria.where("book_type").is(i));
            query.fields().include("_id");
            queryTaskList.add(new QueryTask(query, mongoTemplate));
        }

        List<Future<List<String>>> futureList = THREADPOOLEXECUTOR.invokeAll(queryTaskList);

        int count = 0;
        for (Future<List<String>> listFuture : futureList) {
            count += listFuture.get().size();
        }
        System.out.println("获取的总数：" + count);
        System.out.println("消耗的时间：" + timer.intervalRestart());
    }


}
