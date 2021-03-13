package com.hanyi.daily.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.MonthDay;
import java.time.YearMonth;

/**
 * <p>
 * 年份日期
 * </p>
 *
 * @author wenchangwei
 * @since 10:07 上午 2021/3/13
 */
@Data
public class YearDate implements Serializable {

    private static final long serialVersionUID = -1755692163102925533L;

    /**
     * 年份
     */
    private YearMonth yearMonth = YearMonth.now();

    /**
     * 月份
     */
    @JsonFormat(pattern = "MM-dd")
    private MonthDay monthDay = MonthDay.now();
}
