package com.hanyi.mongo.pojo;

import lombok.Data;

/**
 * @PackAge: middleground com.hanyi.mongo.pojo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-08 21:27
 * @Version: 1.0
 */
@Data
class CmsPageParam {

    /**
     * 参数名称
     */
    private String pageParamName;

    /**
     * 参数值
     */
    private String pageParamValue;

}
