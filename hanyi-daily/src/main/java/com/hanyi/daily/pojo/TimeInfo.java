package com.hanyi.daily.pojo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 时间详情实体类
 * </p>
 *
 * @author wenchangwei
 * @since 8:08 下午 2020/10/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeInfo implements Serializable {

    private static final long serialVersionUID = -1701006003537136143L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 格式化名称
     */
    private String formatName;

    /**
     * 时间毫秒值 int(10)类型最长十位,10位时间戳/秒，占4个字节；bigint占8个字节
     */
    private Long timeExtent;

    /**
     * 日期时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

}
