package com.hanyi.sharding.request;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 字典分页请求实体类
 * </p>
 *
 * @author wenchangwei
 * @since 12:11 下午 2020/12/5
 */
@Data
public class UserQueryPageParam implements Serializable {

    private static final long serialVersionUID = -1439670985945700562L;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 当前页码
     */
    private Long currentPage;

    /**
     * 每页大小
     */
    private Long pageSize;

}
