package com.hanyi.mongo.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @PackAge: middleground com.hanyi.mongo.pojo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-14 22:34
 * @Version: 1.0
 */
@Data
public class UnwindProcInfo implements Serializable {

    private static final long serialVersionUID = -7827853370456029798L;

    private Long id;

    private ProcDescribe procDescribes;

    private String typeInfo;

    private Integer count;
}
