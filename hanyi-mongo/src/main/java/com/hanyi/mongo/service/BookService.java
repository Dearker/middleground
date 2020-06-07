package com.hanyi.mongo.service;

import com.hanyi.mongo.pojo.Book;
import com.hanyi.mongo.pojo.many.incloud.QueryStats;
import com.hanyi.mongo.vo.BookStatisticsGroupInfo;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2:13 下午 2020/6/7
 */
public interface BookService {

    /**
     * 通过聚合查询数据
     *
     * @return 返回分组后的数据
     */
    List<BookStatisticsGroupInfo> queryBookByAggregation();

    /**
     * 查询指定字段
     *
     * @return 返回集合
     */
    List<Book> queryBookByInclude();

    /**
     * 使用线程池统计
     *
     * @return 返回总数
     */
    QueryStats threadCount();

    /**
     * 批量异步统计
     *
     * @return 返回总数
     */
    QueryStats countExecAsync();

    /**
     * 单线程统计
     *
     * @return 返回总数
     */
    QueryStats queryCount();
}
