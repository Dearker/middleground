package com.hanyi.mongo.vo;

import lombok.Data;

/**
 * @PackAge: middleground com.hanyi.mongo.vo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 17:28
 * @Version: 1.0
 */
@Data
public class BookStatisticsGroupInfo {

    private String groupType;

    private String groupName;

    private int groupCount;

}
