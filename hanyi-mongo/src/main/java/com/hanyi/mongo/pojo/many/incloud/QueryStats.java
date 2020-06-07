package com.hanyi.mongo.pojo.many.incloud;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 查询统计
 * </p>
 *
 * @author wenchangwei
 * @since 9:09 下午 2020/6/7
 */
@Data
@AllArgsConstructor
public class QueryStats {

    /**
     * 总数
     */
    private long count;

    /**
     * 耗时
     */
    private long duration;
}
