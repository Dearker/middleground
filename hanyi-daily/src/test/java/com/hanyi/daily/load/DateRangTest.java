package com.hanyi.daily.load;

import org.junit.Test;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 日期范围测试类
 * </p>
 *
 * @author wenchangwei
 * @since 2022/6/25 10:55 PM
 */
public class DateRangTest {

    /**
     * 时间转换
     */
    @Test
    public void timeConvertTest() {
        Instant instant = Instant.now();
        System.out.println(instant);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        System.out.println(localDateTime);

        System.out.println(System.currentTimeMillis());
        long epochSecond = localDateTime.toEpochSecond(ZoneOffset.UTC);
        System.out.println(epochSecond);

        Instant toInstant = localDateTime.toInstant(ZoneOffset.UTC);
        System.out.println(toInstant);
        System.out.println("获取时间戳：" + toInstant.toEpochMilli());
    }

    /**
     * 查询符合从今天开始到今天结束时间的数据
     * isBefore() 和 isAfter() 不能判断边界值，isBefore()在结束时间之前，isAfter()在开始时间之后
     */
    @Test
    public void todayTimeRangTest() {
        //今天的开始时间
        LocalDateTime dateMinTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        //今天的结束时间
        LocalDateTime dateMaxTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        System.out.println(dateMinTime);
        System.out.println(dateMaxTime);

        //向后推迟1s
        LocalDateTime plusSeconds = dateMinTime.plusSeconds(1);
        //向前推迟1s
        LocalDateTime minusSeconds = dateMinTime.minusSeconds(1);

        List<LocalDateTime> localDateTimeList = Arrays.asList(dateMinTime, dateMaxTime, plusSeconds, minusSeconds);

        List<LocalDateTime> dateTimeList = localDateTimeList.stream().filter(s -> s.isEqual(dateMinTime) || s.isEqual(dateMaxTime) ||
                (s.isAfter(dateMinTime) && s.isBefore(dateMaxTime))).collect(Collectors.toList());

        System.out.println(dateTimeList);
    }

    /**
     * 查询符合从周一开始到周末结束时间的数据
     * previous()上一个出现的日期，如：当前是星期天，获取上一个星期一的日期，则获取的是本周的星期一；获取上一个星期天则为上周星期天
     */
    @Test
    public void weekTimeRangTest() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("当前时间：" + localDateTime);
        /*LocalDateTime previousMondayTime = localDateTime.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDateTime previousSundayTime = localDateTime.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        System.out.println(previousMondayTime);
        System.out.println(previousSundayTime);*/

        int value = localDateTime.getDayOfWeek().getValue();

        //本周的数据
        LocalDateTime startTime = localDateTime.minusDays(value - 1);
        LocalDateTime endTime = startTime.plusDays(6);
        System.out.println("本周周一开始时间：" + startTime);
        System.out.println("本周周末开始时间：" + endTime);

        LocalDateTime minusWeeks = localDateTime.minusWeeks(1);
        System.out.println("上周当前时间：" + minusWeeks);
        LocalDateTime lastWeekStartTime = minusWeeks.minusDays(value - 1);
        LocalDateTime lastWeekEndTime = lastWeekStartTime.plusDays(6);

        System.out.println("上周周一开始时间：" + lastWeekStartTime);
        System.out.println("上周周末开始时间：" + lastWeekEndTime);
    }

    /**
     * 指定星期几的时间范围
     */
    @Test
    public void specifyWeekTimeRangTest() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("当前时间：" + localDateTime);

        LocalDateTime wednesdayDateTime = localDateTime.with(DayOfWeek.WEDNESDAY);
        System.out.println("本周三时间：" + wednesdayDateTime);

        LocalDateTime minusWeeks = wednesdayDateTime.minusWeeks(1);
        System.out.println("上周周三时间：" + minusWeeks);
    }

    /**
     * 查询符合从月初开始到月末结束时间的数据
     */
    @Test
    public void monthTimeRangTest() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("当前时间：" + localDateTime);

        LocalDateTime firstDayOfMonthTime = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("本月的开始时间：" + firstDayOfMonthTime);

        LocalDateTime lastDayOfMonthTime = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("本月的结束时间：" + lastDayOfMonthTime);

        LocalDateTime firstDayOfNextMonthTime = localDateTime.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("下个月的开始时间：" + firstDayOfNextMonthTime);

        LocalDateTime lastDayOfNextMonthTime = firstDayOfNextMonthTime.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("下个月的结束时间：" + lastDayOfNextMonthTime);

        System.out.println("====================");

        LocalDateTime minusMonths = localDateTime.minusMonths(1);
        System.out.println("当前时间前一个月的日期：" + minusMonths);
    }

    /**
     * 指定月份日期时间范围
     */
    @Test
    public void specifyMonthTimeRangTest() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("当前时间：" + localDateTime);

        LocalDateTime withDayOfMonth = localDateTime.withDayOfMonth(5);
        System.out.println("本月5号的时间：" + withDayOfMonth);
    }

    /**
     * 查询符合从年初开始到年末结束时间的数据
     */
    @Test
    public void yearTimeRangTest() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("当前时间：" + localDateTime);

        LocalDateTime firstDayOfYearTime = localDateTime.with(TemporalAdjusters.firstDayOfYear());
        System.out.println("今年的开始日期：" + firstDayOfYearTime);

        LocalDateTime lastDayOfYearTime = localDateTime.with(TemporalAdjusters.lastDayOfYear());
        System.out.println("今年的结束日期：" + lastDayOfYearTime);

        System.out.println("====================");
        LocalDateTime minusYears = localDateTime.minusYears(1);
        System.out.println("去年的当前日期：" + minusYears);
    }

}
