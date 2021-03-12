package com.hanyi.daily.pojo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hanyi.daily.common.util.LongJsonDeserializer;
import com.hanyi.daily.common.util.LongJsonSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
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
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
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
     * 创建日期
     */
    private LocalDate createDate;

    /**
     * 日期时间,如果返回的类型为Date,则相差8小时，需要加上timezone = "GMT+8"
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

}
