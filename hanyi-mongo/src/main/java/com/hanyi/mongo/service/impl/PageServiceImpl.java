package com.hanyi.mongo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.hanyi.mongo.pojo.CmsPage;
import com.hanyi.mongo.repository.CmsPageRepository;
import com.hanyi.mongo.service.PageService;
import com.hanyi.mongo.vo.CmsPageStatistics;
import com.hanyi.mongo.vo.QueryPageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @PackAge: middleground com.hanyi.mongo.service.impl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-08 21:56
 * @Version: 1.0
 */
@Slf4j
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 页面查询方法
     *
     * @param page             页码，从1开始记数
     * @param size             每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    @Override
    public Page<CmsPage> findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (Objects.isNull(queryPageRequest)) {
            queryPageRequest = new QueryPageRequest();
        }
        //自定义条件查询
        //定义条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //设置条件值（站点id）
        if (StrUtil.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //设置模板id作为查询条件
        if (StrUtil.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //设置页面别名作为查询条件
        if (StrUtil.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //定义条件对象Example
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        //分页参数
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);
        //实现自定义条件查询并且分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        System.out.println();
        log.info("数据列表：{}", all.getContent());
        log.info("数据总记录数:{}", all.getTotalElements());
        return all;
    }

    /**
     * 新增页面
     *
     * @param cmsPage
     * @return
     */
    @Override
    public String add(CmsPage cmsPage) {
        //校验页面名称、站点Id、页面webpath的唯一性
        //根据页面名称、站点Id、页面webpath去cms_page集合，如果查到说明此页面已经存在，如果查询不到再继续添加
        CmsPage cmsPageByCondition = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        if (Objects.isNull(cmsPageByCondition)) {
            //调用dao新增页面
            cmsPage.setPageId(null);
            CmsPage page = cmsPageRepository.save(cmsPage);
            return page.getPageId();
        }
        return null;
    }

    /**
     * 根据页面id查询页面
     *
     * @param id
     * @return
     */
    @Override
    public CmsPage getById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 修改页面
     *
     * @param id
     * @param cmsPage
     * @return
     */
    @Override
    public void update(String id, CmsPage cmsPage) {
        //根据id从数据库查询页面信息
        CmsPage one = this.getById(id);
        if (Objects.nonNull(one)) {
            //准备更新数据
            //设置要修改的数据
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            one.setDataUrl(cmsPage.getDataUrl());
            //提交修改
            cmsPageRepository.save(one);
        }
    }

    /**
     * 根据id删除页面
     *
     * @param id
     * @return
     */
    @Override
    public void delete(String id) {
        //先查询一下
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            cmsPageRepository.deleteById(id);
        }
    }

    @Override
    public List<CmsPageStatistics> findListByGroup() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("pageType").count().as("count")
                        .last("pageType").as("type"),
                Aggregation.project("type", "count"));

        AggregationResults<CmsPageStatistics> jsonObjects = mongoTemplate.aggregate(aggregation, "cms_page", CmsPageStatistics.class);
        return jsonObjects.getMappedResults();
    }


}
