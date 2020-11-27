package com.hanyi.hikari.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 书籍分页查询对象
 *
 * @author wcwei@iflytek.com
 * @since 2020-11-27 16:14
 */
@Data
public class BookQueryPageParam implements Serializable {

    /**
     * 书籍类型
     */
    private Integer bookType;

    /**
     * 当前页码
     */
    private Long currentPage;

    /**
     * 每页大小
     */
    private Long pageSize;

}
