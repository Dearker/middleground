package com.hanyi.hikari.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户统计实体类
 * </p>
 *
 * @author wenchangwei
 * @since 10:16 上午 2020/12/26
 */
@Data
public class UserTotalVo implements Serializable {

    private static final long serialVersionUID = 688994357623823567L;

    /**
     * 统计的版本号
     */
    private Integer version;

    /**
     * 统计总数
     */
    private long total;

}
