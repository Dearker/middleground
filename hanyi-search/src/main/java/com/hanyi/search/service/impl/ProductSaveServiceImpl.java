package com.hanyi.search.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hanyi.search.bo.SkuEsModel;
import com.hanyi.search.common.config.ElasticSearchConfig;
import com.hanyi.search.common.constant.ElasticSearchConstant;
import com.hanyi.search.common.constant.FiledConstant;
import com.hanyi.search.request.SearchParam;
import com.hanyi.search.service.ProductSaveService;
import com.hanyi.search.vo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    /**
     * es高级客户端
     */
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

    /**
     * 去es检索需要的信息
     *
     * @param param 检索的所有参数
     * @return 返回检索的结果, 页面需要显示的所以数据
     */
    @Override
    public SearchResult search(SearchParam param) {
        // 1、准备检索请求
        SearchRequest searchRequest = this.buildSearchRequest(param);

        SearchResult result = null;
        try {
            // 2、执行检索请求
            SearchResponse response = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            // 3、分析响应数据，封装成我们需要的格式
            result = this.buildSearchResult(response, param);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return result;
    }

    /**
     * 1、准备检索请求
     * # 模糊匹配 过滤（属性、分类、品牌、价格区间、库存）排序 分页 高亮 聚合分析
     *
     * @return 搜索请求
     */
    private SearchRequest buildSearchRequest(SearchParam param) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        /*  模糊匹配 过滤（属性、分类、品牌、价格区间、库存） */
        // 1、构建 bool - query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 1.1、must - query
        if (StrUtil.isNotBlank(param.getKeyword())) {
            boolQuery.must(QueryBuilders.matchQuery(FiledConstant.SKU_TITLE, param.getKeyword()));
        }

        /* ====================================== 1.2、构建 bool - filter (s)  =================================== */
        this.buildFilterQuery(param, boolQuery);
        /* ==================================== 1.2、构建 bool - filter (e)  =================================== */
        sourceBuilder.query(boolQuery);

        /*  排序 分页 高亮  */
        // 2.1、排序 sort
        //  saleCount_asc/desc  skuPrice_asc/desc hotScore_asc/desc
        if (StrUtil.isNotBlank(param.getSort())) {
            String sortStr = param.getSort();
            String[] strs = sortStr.split(StrUtil.UNDERLINE);
            SortOrder order = strs[1].equalsIgnoreCase(SortOrder.ASC.toString()) ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(strs[0], order);
        }
        // 2.2、分页
        if (Objects.nonNull(param.getPageNum())) {
            // from = (pageNum - 1) * pageSize
            sourceBuilder.from((param.getPageNum() - 1) * ElasticSearchConstant.PRODUCT_PAGE_SIZE);
            sourceBuilder.size(ElasticSearchConstant.PRODUCT_PAGE_SIZE);
        }
        // 2.3、高亮
        if (StrUtil.isNotBlank(param.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field(FiledConstant.SKU_TITLE);
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            sourceBuilder.highlighter(highlightBuilder);
        }
        /* ========================================= 3、构建聚合分析 (s)  ========================================= */

        // ① brand_agg 品牌聚合 聚合名
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg");
        // 聚合的字段名 和多少个
        brandAgg.field(FiledConstant.BRAND_ID).size(50);
        // brand_agg 品牌聚合里的子聚合 brand_name_agg 品牌名聚合
        brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field(FiledConstant.BRAND_NAME).size(1));
        // brand_agg 品牌聚合里的子聚合 brand_img_agg 品牌图片聚合
        brandAgg.subAggregation(AggregationBuilders.terms("brand_img_agg").field(FiledConstant.BRAND_IMG).size(1));

        sourceBuilder.aggregation(brandAgg);

        // ② catalog_agg 分类聚合
        TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalog_agg")
                .field(FiledConstant.CATALOG_ID).size(20);
        // catalog_agg 分类聚合里的子聚合 catalog_name_agg 分类名字聚合
        catalogAgg.subAggregation(AggregationBuilders.terms("catalog_name_agg")
                .field(FiledConstant.CATALOG_NAME).size(1));

        sourceBuilder.aggregation(catalogAgg);

        // ③ attr_agg 属性的聚合
        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", FiledConstant.ATTRS);
        // attr_id_agg 属性id聚合 聚合出当前所有的attrId
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attr_id_agg").field(FiledConstant.ATTR_ID);
        // attr_name_agg 属性名聚合 ---> attr_id_agg 属性id聚合里的子聚合
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field(FiledConstant.ATTR_NAME).size(1));
        // attr_value_agg 属性值聚合 ---> attr_id_agg 属性id聚合里的子聚合
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field(FiledConstant.ATTR_VALUE).size(50));

        attrAgg.subAggregation(attrIdAgg);

        sourceBuilder.aggregation(attrAgg);
        /* ========================================= 3、构建聚合分析 (e)  ========================================= */

        SearchRequest searchRequest = new SearchRequest(new String[]{ElasticSearchConstant.PRODUCT_INDEX}, sourceBuilder);

        String s = sourceBuilder.toString();
        log.info("构建的DSL {}", s);
        return searchRequest;
    }

    /**
     * 构建过滤的查询对象
     *
     * @param param     查询参数
     * @param boolQuery 布尔查询
     */
    private void buildFilterQuery(SearchParam param, BoolQueryBuilder boolQuery) {
        // ① 构建分类过滤 catalogId - filter
        if (Objects.nonNull(param.getCatalog3Id())) {
            boolQuery.filter(QueryBuilders.termQuery(FiledConstant.CATALOG_ID, param.getCatalog3Id()));
        }
        // ② 构建品牌过滤 brandId - filter
        if (CollUtil.isNotEmpty(param.getBrandId())) {
            boolQuery.filter(QueryBuilders.termsQuery(FiledConstant.BRAND_ID, param.getBrandId()));
        }
        // ③ 构建属性过滤 attrs - filter
        if (CollUtil.isNotEmpty(param.getAttrs())) {
            param.getAttrs().forEach(attrStr -> {
                // attrs=1_5寸:8寸&attrs=2_8G:16G
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();

                String[] str = attrStr.split(StrUtil.UNDERLINE);
                //检索的属性id
                String attrId = str[0];
                // 检索的属性的值
                String[] attrValue = str[1].split(StrUtil.COLON);
                nestedBoolQuery.must(QueryBuilders.termQuery(FiledConstant.ATTR_ID, attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery(FiledConstant.ATTR_VALUE, attrValue));
                // ScoreMode.None 不参与评分
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(FiledConstant.ATTRS,
                        nestedBoolQuery, ScoreMode.None);

                boolQuery.filter(nestedQuery);
            });
        }

        // ④ 构建库存过滤 hasStock - filter
        if (Objects.nonNull(param.getHasStock())) {
            boolQuery.filter(QueryBuilders.termQuery(FiledConstant.HAS_STOCK, param.getHasStock() == 1));
        }

        // ⑤ 构建价格过滤 skuPrice - filter
        if (StrUtil.isNotBlank(param.getSkuPrice())) {
            // skuPrice -> 1_500/_500/500_
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(FiledConstant.SKU_PRICE);

            String[] str = param.getSkuPrice().split(StrUtil.UNDERLINE);
            // skuPrice -> 1_500
            if (str.length == Short.BYTES) {
                rangeQuery.gte(str[0]).lte(str[1]);
            }
            if (str.length == 1) {
                // skuPrice -> _500
                if (param.getSkuPrice().startsWith(StrUtil.UNDERLINE)) {
                    rangeQuery.lte(str[0]);
                }
                // skuPrice -> 500_
                if (param.getSkuPrice().endsWith(StrUtil.UNDERLINE)) {
                    rangeQuery.gte(str[0]);
                }
            }
            boolQuery.filter(rangeQuery);
        }
    }

    /**
     * 3、分析响应数据，封装成我们需要的格式
     *
     * @param response 返回参数
     * @return 返回结果
     */
    private SearchResult buildSearchResult(SearchResponse response, SearchParam param) {
        SearchResult result = new SearchResult();

        // 1、封装商品信息
        SearchHits hits = response.getHits();
        if (ArrayUtil.isNotEmpty(hits.getHits())) {
            List<SkuEsModel> esModels = new ArrayList<>();
            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
                // 如果带有检索字段  则获取高亮显示的skuTitle
                if (StrUtil.isNotBlank(param.getKeyword())) {
                    HighlightField skuTitleHighlightField = hit.getHighlightFields().get(FiledConstant.SKU_TITLE);
                    String skuTitle = skuTitleHighlightField.getFragments()[0].string();
                    skuEsModel.setSkuTitle(skuTitle);
                }
                esModels.add(skuEsModel);
            }
            result.setProducts(esModels);
        }

        /* ========================================= 2、封装聚合信息 (s)  ========================================= */
        this.buildAggregateResult(response, result);
        /* ========================================= 2、封装聚合信息 (e)  ========================================= */
        result.setPageNum(param.getPageNum());

        long total = hits.getTotalHits();
        result.setTotal(total);
        int toIntExact = Math.toIntExact(total);
        int totalPages = toIntExact % ElasticSearchConstant.PRODUCT_PAGE_SIZE == 0 ?
                toIntExact / ElasticSearchConstant.PRODUCT_PAGE_SIZE :
                (toIntExact / ElasticSearchConstant.PRODUCT_PAGE_SIZE + 1);
        result.setTotalPages(totalPages);

        // 构建遍历需要的页码
        List<Integer> pageNavs = new ArrayList<>(totalPages);
        for (int i = 1; i <= totalPages; i++) {
            pageNavs.add(i);
        }
        result.setPageNavs(pageNavs);

        return result;
    }

    private void buildAggregateResult(SearchResponse response, SearchResult result) {
        // ① 品牌聚合数据
        List<SearchResult.BrandVo> brandVos = new ArrayList<>();
        ParsedLongTerms brandAgg = response.getAggregations().get("brand_agg");
        List<? extends Terms.Bucket> brandBuckets = brandAgg.getBuckets();
        for (Terms.Bucket bucket : brandBuckets) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            // 得到品牌的id
            long brandId = bucket.getKeyAsNumber().longValue();
            // 得到品牌聚合的子聚合 品牌图片聚合
            String brandImg = ((ParsedStringTerms) bucket.getAggregations().get("brand_img_agg"))
                    .getBuckets().get(0).getKeyAsString();
            // 得到品牌聚合的子聚合 品牌名字聚合
            String brandName = ((ParsedStringTerms) bucket.getAggregations().get("brand_name_agg"))
                    .getBuckets().get(0).getKeyAsString();

            brandVo.setBrandId(brandId);
            brandVo.setBrandImg(brandImg);
            brandVo.setBrandName(brandName);

            brandVos.add(brandVo);
        }
        result.setBrands(brandVos);

        // ② 分类聚合数据
        ParsedLongTerms catalogAgg = response.getAggregations().get("catalog_agg");
        List<SearchResult.CatalogVo> catalogVos = new ArrayList<>();
        List<? extends Terms.Bucket> buckets = catalogAgg.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();
            catalogVo.setCatalogId(bucket.getKeyAsNumber().longValue());

            // 获取分类名子聚合 catalog_name_agg
            ParsedStringTerms catalogNameAgg = bucket.getAggregations().get("catalog_name_agg");
            String catalogName = catalogNameAgg.getBuckets().get(0).getKeyAsString();
            catalogVo.setCatalogName(catalogName);

            catalogVos.add(catalogVo);
        }
        result.setCatalogs(catalogVos);

        // ③ 属性聚合信息
        List<SearchResult.AttrVo> attrVos = new ArrayList<>();
        ParsedNested attrAgg = response.getAggregations().get("attr_agg");
        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attr_id_agg");
        for (Terms.Bucket bucket : attrIdAgg.getBuckets()) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            // 得到属性的id
            long attrId = bucket.getKeyAsNumber().longValue();
            // 得到属性的名称
            String attrName = ((ParsedStringTerms) bucket.getAggregations().get("attr_name_agg"))
                    .getBuckets().get(0).getKeyAsString();
            // 得到属性的所有值
            List<String> attrValues = ((ParsedStringTerms) bucket.getAggregations()
                    .get("attr_value_agg")).getBuckets().stream()
                    .map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.toList());

            attrVo.setAttrId(attrId);
            attrVo.setAttrName(attrName);
            attrVo.setAttrValue(attrValues);

            attrVos.add(attrVo);
        }

        result.setAttrs(attrVos);
    }

}
