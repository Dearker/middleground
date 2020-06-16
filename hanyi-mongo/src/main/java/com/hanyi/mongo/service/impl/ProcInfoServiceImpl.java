package com.hanyi.mongo.service.impl;

import com.hanyi.mongo.pojo.UnwindProcInfo;
import com.hanyi.mongo.service.ProcInfoService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.service.impl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-14 21:52
 * @Version: 1.0
 */
@Service
public class ProcInfoServiceImpl implements ProcInfoService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 内嵌集合分组查询
     *
     * @return 返回集合对象
     */
    @Override
    public List<UnwindProcInfo> findProInfoByCondition() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.unwind("procDescribes"),
                    Aggregation.group("procDescribes").count().as("count"));
        AggregationResults<UnwindProcInfo> aggregationResults = mongoTemplate.aggregate(aggregation, "procInfo", UnwindProcInfo.class);
        return aggregationResults.getMappedResults();
    }
}
