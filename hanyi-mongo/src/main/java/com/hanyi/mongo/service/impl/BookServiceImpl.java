package com.hanyi.mongo.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.mongo.common.thread.QueryCountTask;
import com.hanyi.mongo.common.thread.QueryTask;
import com.hanyi.mongo.pojo.Book;
import com.hanyi.mongo.pojo.many.incloud.QueryStats;
import com.hanyi.mongo.service.BookService;
import com.hanyi.mongo.vo.BookStatisticsGroupInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @PackAge: middleground com.hanyi.mongo.service.impl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 17:20
 * @Version: 1.0
 */
@Service
public class BookServiceImpl implements BookService {

    private static final ThreadPoolExecutor THREADPOOLEXECUTOR = ThreadUtil.newExecutor(8, 8);

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<BookStatisticsGroupInfo> queryBookByAggregation() {
        Criteria criteria = Criteria.where("book_total").lte(100);

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),
                Aggregation.group("book_type").count().as("groupCount")
                        .last("book_type").as("groupType")
                        .last("book_name").as("groupName"),
                Aggregation.project("groupType", "groupName", "groupCount"),
                Aggregation.sort(Sort.Direction.ASC, "groupType"));

        AggregationResults<BookStatisticsGroupInfo> groupInfoAggregationResults = mongoTemplate.aggregate(aggregation, "tb_book", BookStatisticsGroupInfo.class);

        return groupInfoAggregationResults.getMappedResults();
    }

    @Override
    public List<Book> queryBookByInclude() {
        Query query = new Query(Criteria.where("book_type").is(0)).limit(10);
        query.fields().include("book_title").include("book_name");
        return mongoTemplate.find(query, Book.class);
    }

    /**
     * 使用线程池统计
     *
     * @return 返回总数
     */
    @Override
    public QueryStats threadCount() {

        TimeInterval timer = DateUtil.timer();

        Query query;
        List<QueryCountTask> queryCountTaskList = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            query = new Query(Criteria.where("book_type").gte(i * 10).lt((i + 1) * 10));
            queryCountTaskList.add(new QueryCountTask(query, mongoTemplate));
        }

        Long count = 0L;
        try {
            List<Future<Long>> invokeAll = THREADPOOLEXECUTOR.invokeAll(queryCountTaskList);
            for (Future<Long> longFuture : invokeAll) {
                count += longFuture.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("耗时：" + timer.interval());

        return new QueryStats(count, timer.intervalRestart());
    }

    /**
     * 批量异步统计
     *
     * @return 返回总数
     */
    @Override
    public QueryStats countExecAsync() {

        TimeInterval timer = DateUtil.timer();

        Query query = new Query(Criteria.where("book_type").gte(0).lt(10));
        Future<Long> longFuture = ThreadUtil.execAsync(new QueryCountTask(query, mongoTemplate));

        Query query1 = new Query(Criteria.where("book_type").gte(10).lt(20));
        Future<Long> longFuture1 = ThreadUtil.execAsync(new QueryCountTask(query1, mongoTemplate));

        Query query2 = new Query(Criteria.where("book_type").gte(20).lt(30));
        Future<Long> longFuture2 = ThreadUtil.execAsync(new QueryCountTask(query2, mongoTemplate));

        Query query3 = new Query(Criteria.where("book_type").gte(30).lt(40));
        Future<Long> longFuture3 = ThreadUtil.execAsync(new QueryCountTask(query3, mongoTemplate));

        long count = 0L;
        try {
            Long l = longFuture.get();
            Long l1 = longFuture1.get();
            Long l2 = longFuture2.get();
            Long l3 = longFuture3.get();

            count = l + l1 + l2 + l3;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new QueryStats(count, timer.intervalRestart());
    }

    /**
     * 单线程统计
     *
     * @return 返回总数
     */
    @Override
    public QueryStats queryCount() {

        TimeInterval timer = DateUtil.timer();
        Query query = new Query(Criteria.where("book_type").gte(0).lt(40));

        long count = mongoTemplate.count(query, Book.class);
        return new QueryStats(count, timer.intervalRestart());
    }

    /**
     * 查询集合总数
     *
     * @return 返回总数
     */
    @Override
    public QueryStats queryList() {

        TimeInterval timer = DateUtil.timer();
        Query query = new Query(Criteria.where("book_type").is(0));

        query.fields().include("_id");
        List<String> stringList = mongoTemplate.find(query, String.class, "tb_book");
        List<String> strings = mongoTemplate.find(query, String.class, "tb_book");

        int count = stringList.size() + strings.size();

        return new QueryStats(count, timer.intervalRestart());
    }

    /**
     * 多个线程查询总数
     *
     * @return 返回总数
     */
    @Override
    public QueryStats queryThread() {
        TimeInterval timer = DateUtil.timer();
        List<QueryTask> queryTaskList = new ArrayList<>(2);

        Query query;
        for (int i = 0; i < 1; i++) {
            query = new Query(Criteria.where("book_type").is(i));
            query.fields().include("_id");
            queryTaskList.add(new QueryTask(query, mongoTemplate));
        }

        Query type = new Query(Criteria.where("book_type").is(0));
        type.fields().include("_id");
        queryTaskList.add(new QueryTask(type, mongoTemplate));


        int count = 0;
        try {
            List<Future<List<String>>> futureList = THREADPOOLEXECUTOR.invokeAll(queryTaskList);
            for (Future<List<String>> listFuture : futureList) {
                count += listFuture.get().size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new QueryStats(count, timer.intervalRestart());
    }

    /**
     * 查询相同条件
     *
     * @return 返回总数
     */
    @Override
    public QueryStats queryCountSame() {
        TimeInterval timer = DateUtil.timer();
        Query query = new Query(Criteria.where("book_type").is(0));
        long count = mongoTemplate.count(query, Book.class);
        long count1 = mongoTemplate.count(query, Book.class);

        long total = count + count1;
        return new QueryStats(total, timer.intervalRestart());
    }

    /**
     * 多线程查询相同条件
     *
     * @return 返回总数
     */
    @Override
    public QueryStats queryCountThreadSame() {
        TimeInterval timer = DateUtil.timer();
        List<QueryCountTask> queryCountTaskList = new ArrayList<>(2);

        Query query = new Query(Criteria.where("book_type").is(0));
        QueryCountTask queryCountTask = new QueryCountTask(query, mongoTemplate);
        queryCountTaskList.add(queryCountTask);
        queryCountTaskList.add(queryCountTask);

        long count = 0L;
        try {
            List<Future<Long>> invokeAll = THREADPOOLEXECUTOR.invokeAll(queryCountTaskList);
            for (Future<Long> longFuture : invokeAll) {
                count += longFuture.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new QueryStats(count, timer.intervalRestart());
    }
}
