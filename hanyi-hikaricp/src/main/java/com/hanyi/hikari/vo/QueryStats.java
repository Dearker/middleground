package com.hanyi.hikari.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 统计实体类
 * </p>
 *
 * @author wenchangwei
 * @since 10:10 下午 2020/6/19
 */
@Data
@AllArgsConstructor
public class QueryStats {

    /**
     * 总数
     */
    private Integer count;

    /**
     * 耗时
     */
    private long duration;

}
