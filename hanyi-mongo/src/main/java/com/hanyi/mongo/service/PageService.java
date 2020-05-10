package com.hanyi.mongo.service;

import com.hanyi.mongo.pojo.CmsPage;
import com.hanyi.mongo.vo.CmsPageStatistics;
import com.hanyi.mongo.vo.QueryPageRequest;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * @PackAge: middleground com.hanyi.mongo.service
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-08 21:35
 * @Version: 1.0
 */
public interface PageService {

    Page<CmsPage> findList(int page, int size, QueryPageRequest queryPageRequest);

    String add(CmsPage cmsPage);

    CmsPage getById(String id);

    void update(String id, CmsPage cmsPage);

    void delete(String id);

    List<CmsPageStatistics> findListByGroup();
}
