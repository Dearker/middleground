package com.hanyi.mongo.repository;

import com.hanyi.mongo.pojo.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @PackAge: middleground com.hanyi.mongo.repository
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-08 21:33
 * @Version: 1.0
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    /**
     * 根据页面名称查询
     * @param pageName
     * @return
     */
    CmsPage findByPageName(String pageName);

    /**
     * 根据页面名称、站点Id、页面webpath查询
     * @param pageName
     * @param siteId
     * @param pageWebPath
     * @return
     */
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath);

}
