package com.hanyi.hikari.request;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 分类请求参数对象
 * </p>
 *
 * @author wenchangwei
 * @since 9:05 下午 2020/10/29
 */
@Data
public class CategoryQueryParam implements Serializable {

    private static final long serialVersionUID = 1887172892447899834L;

    /**
     * 当前页
     */
    private Long currentPage;

    /**
     * 当前页大小
     */
    private Long pageSize;

    /**
     * 父分类id
     */
    private Long parentCid;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 层级
     */
    private Integer catLevel;

}
