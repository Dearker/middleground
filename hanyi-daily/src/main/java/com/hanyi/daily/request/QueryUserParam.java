package com.hanyi.daily.request;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 查询用户参数对象
 * </p>
 *
 * @author wenchangwei
 * @since 5:03 下午 2020/10/1
 */
@Data
public class QueryUserParam implements Serializable {

    private static final long serialVersionUID = -4663047301312648769L;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 开始索引
     */
    private Integer startIndex;

    /**
     * 每页数量
     */
    private Integer pageSize;

}
