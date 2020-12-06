package com.hanyi.sharding.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 字典实体类
 * </p>
 *
 * @author wenchangwei
 * @since 8:41 下午 2020/12/3
 */
@Data
public class Dict implements Serializable {

    private static final long serialVersionUID = -860106238458977949L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String value;

}
