package com.hanyi.web.bo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    private static final long serialVersionUID = 7347534568356417643L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 开始时间,前端传递的日期格式为字符串或者时间戳都可以进行解析
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间，前端传递的只能是字符串，时间戳无法解析
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

}
