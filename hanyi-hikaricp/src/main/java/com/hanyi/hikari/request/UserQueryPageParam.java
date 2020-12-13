package com.hanyi.hikari.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户分页查询条件实体类
 * </p>
 *
 * @author wenchangwei
 * @since 9:41 上午 2020/12/12
 */
@Data
public class UserQueryPageParam implements Serializable {

    private static final long serialVersionUID = -5033096572720864376L;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 开始年龄
     */
    private String beginAge;

    /**
     * 结束年龄
     */
    private String endAge;

    /**
     * 从事领域集合
     */
    private List<String> infoCodeList;

    /**
     * 当前页码
     */
    private Long currentPage;

    /**
     * 开始索引
     */
    private Long startIndex;

    /**
     * 每页条数
     */
    private Long pageSize;
}
