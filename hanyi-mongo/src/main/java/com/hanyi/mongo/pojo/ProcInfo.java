package com.hanyi.mongo.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * @PackAge: middleground com.hanyi.mongo.pojo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-14 21:39
 * @Version: 1.0
 */
@Data
@Builder
public class ProcInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private List<ProcDescribe> procDescribes;

    private String typeInfo;

}
