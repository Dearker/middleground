package com.hanyi.mongo.vo;

import lombok.Data;

/**
 * @PackAge: middleground com.hanyi.mongo.vo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-08 21:40
 * @Version: 1.0
 */
@Data
public class QueryPageRequest {

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 页面ID
     */
    private String pageId;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 别名
     */
    private String pageAliase;

    /**
     * 模版id
     */
    private String templateId;

}
