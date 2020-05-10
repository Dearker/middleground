package com.hanyi.mongo.service;

import com.hanyi.mongo.vo.BookStatisticsGroupInfo;

import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.service
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 17:18
 * @Version: 1.0
 */
public interface BookService {

    /**
     * 通过聚合查询数据
     *
     * @return
     */
    List<BookStatisticsGroupInfo> queryBookByAggregation();

}
