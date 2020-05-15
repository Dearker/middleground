package com.hanyi.mongo.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @PackAge: middleground com.hanyi.mongo.pojo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-14 21:47
 * @Version: 1.0
 */
@Data
@Builder
public class ProcDescribe implements Serializable {

    private static final long serialVersionUID = 6458730678911239546L;
    private Integer type;

    private String name;

}
