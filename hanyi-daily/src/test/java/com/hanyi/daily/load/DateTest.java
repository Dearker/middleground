package com.hanyi.daily.load;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * 日期测试类
 * </p>
 *
 * @author wenchangwei
 * @since 11:18 下午 2020/10/4
 */
public class DateTest {

    /**
     * 时间对象转换
     */
    @Test
    public void localDateTest() {

        Date date = new Date();
        // Date-----> LocalDateTime 这里指定使用当前系统默认时区
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // LocalDateTime------> Date 这里指定使用当前系统默认时区
        Date newDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime now = LocalDateTime.now();
        // 年
        int year = now.getYear();
        // 月
        int month = now.getMonthValue();
        // 日
        int day = now.getDayOfMonth();
        // 当前时间加一天
        LocalDateTime plusDays = now.plusDays(1);
        // 当前时间减一个小时
        LocalDateTime minusHours = now.minusHours(1);

        DateTime dateTime = DateUtil.date();
        LocalDateTime toLocalDateTime = DateUtil.toLocalDateTime(dateTime);

    }

    /**
     * 格式化时间
     */
    @Test
    public void formatTest() {

        // 按照 yyyy-MM-dd HH:mm:ss 转化时间
        DateTime parse = DateUtil.parse("2020-05-07 22:34:00", DatePattern.NORM_DATETIME_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse("2020-05-07 22:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // 将 LocalDateTime 格式化字符串
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
        String format1 = DateUtil.format(parse, DatePattern.NORM_DATETIME_PATTERN);

    }

}
