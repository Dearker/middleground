package com.hanyi.daily.load;

import cn.hutool.core.date.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void rangTest(){
        DateTime dateTime = DateUtil.date();
        DateTime dateTime1 = DateUtil.offset(dateTime, DateField.MONTH, -6);

        DateRange range = DateUtil.range(dateTime1, dateTime, DateField.MONTH);
        List<String> dateTimeList = new ArrayList<>(6);
        range.forEach(s->dateTimeList.add(s.toString()));

        List<String> stringList = new ArrayList<>(6);
        for (int i = 0, length = dateTimeList.size(); i < length; i++) {
            if(i+1<length){
                String s = dateTimeList.get(i) + "||" + dateTimeList.get(i+1);
                stringList.add(s);
            }
        }
        stringList.forEach(System.out::println);
    }

    /**
     * 获取开始时间和结束时间之间总共多少天
     */
    @Test
    public void dateTest(){

        DateTime start = DateUtil.date();
        DateTime end = DateUtil.lastWeek();
        long l = DateUtil.betweenDay(start, end, true);
        System.out.println(l);
        DateRange range = DateUtil.range(start, end, DateField.DAY_OF_YEAR);

        range.forEach(s-> System.out.println(s.toString()));
    }

    /**
     * 时间转换类
     */
    @Test
    public void LocalDateTimeTest(){

        long localTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long localTimeTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));

        System.out.println("获取的时间-->"+ localTime +"||"+localTimeTime);

        long currentSeconds = DateUtil.currentSeconds();
        System.out.println("获取的秒数-->"+currentSeconds);

        DateTime dateTime = DateUtil.lastMonth();
        String now = DateUtil.now();

        DateTime parse = DateUtil.parse("2019-12-20");
        System.out.println(dateTime+"||"+now+"||"+parse.getTime());
    }

}
