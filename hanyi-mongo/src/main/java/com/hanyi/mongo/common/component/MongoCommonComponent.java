package com.hanyi.mongo.common.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.mongo.pojo.many.BatchUpdate;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * mongo 通用组件
 * </p>
 *
 * @author wenchangwei
 * @since 10:52 下午 2020/5/27
 */
@Component
public class MongoCommonComponent {

    /**
     * 是否批量更新
     */
    private static final String UNSEAT = "upsert";

    /**
     * 更新的集合的key值
     */
    private static final String UPDATE = "update";

    /**
     * 更新的对象集合的key值
     */
    private static final String UPDATES = "updates";

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * MongoDB 批量更新
     *
     * @param collectionName  更新的表名
     * @param batchUpdateList 更新的对象集合
     * @return 返回更新的结果
     */
    public CommandResult buildBatchUpdateResult(String collectionName, List<BatchUpdate> batchUpdateList) {

        if (StrUtil.isNotBlank(collectionName) && CollUtil.isNotEmpty(batchUpdateList)) {
            BasicDBObject basicObject;
            List<BasicDBObject> updateList = new ArrayList<>(batchUpdateList.size());
            for (BatchUpdate batchUpdate : batchUpdateList) {
                basicObject = new BasicDBObject();
                basicObject.put("q", batchUpdate.getQuery().getQueryObject());
                basicObject.put("u", batchUpdate.getUpdate().getUpdateObject());
                basicObject.put(UNSEAT, true);
                updateList.add(basicObject);
            }

            DBObject command = new BasicDBObject();
            command.put(UPDATE, collectionName);
            command.put(UPDATES, updateList);

            //return mongoTemplate.getCollection(collectionName).command(command);
        }
        return null;
    }

}
