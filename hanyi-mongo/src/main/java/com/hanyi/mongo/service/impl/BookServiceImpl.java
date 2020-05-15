package com.hanyi.mongo.service.impl;

import com.hanyi.mongo.pojo.Book;
import com.hanyi.mongo.service.BookService;
import com.hanyi.mongo.vo.BookStatisticsGroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.service.impl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 17:20
 * @Version: 1.0
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
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
}
