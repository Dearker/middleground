package com.hanyi.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.hanyi.search.bo.SkuEsModel;
import com.hanyi.search.common.config.ElasticSearchConfig;
import com.hanyi.search.common.constant.ElasticSearchConstant;
import com.hanyi.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品保存逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 11:53 上午 2020/11/1
 */
@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 上传到es
     *
     * @param esModels 模型集合
     * @return true 有错误 false 无错误
     * @throws IOException 异常
     */
    @Override
    public Boolean productStatusUp(List<SkuEsModel> esModels) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        for (SkuEsModel esModel : esModels) {
            IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.PRODUCT_INDEX);
            indexRequest.id(esModel.getSkuId().toString());
            String json = JSON.toJSONString(esModel);
            indexRequest.source(json, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);

        boolean hasFailures = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
        log.info("ProductSaveServiceImpl.productStatusUp 上架成功商品：{} ,返回的数据：{}", collect, bulk.toString());

        return hasFailures;
    }
}
