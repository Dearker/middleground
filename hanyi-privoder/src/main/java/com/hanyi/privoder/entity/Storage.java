package com.hanyi.privoder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @PackAge: middleground com.hanyi.privoder.entity
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-14 15:38
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@TableName("storage_tbl")
public class Storage implements Serializable {

    private Long id;
    private String commodityCode;
    private Long count;

}
