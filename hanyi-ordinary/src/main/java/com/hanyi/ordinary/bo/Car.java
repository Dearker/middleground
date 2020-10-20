package com.hanyi.ordinary.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 车实体类
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2020-10-10 14:39
 */
@Data
public class Car implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
