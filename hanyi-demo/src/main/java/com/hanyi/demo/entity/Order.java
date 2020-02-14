package com.hanyi.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @PackAge: middleground com.hanyi.demo.entity
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-14 17:01
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@TableName("order_tbl")
@Builder
public class Order {

    private Integer id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private BigDecimal money;

}
