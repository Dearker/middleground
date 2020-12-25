package com.hanyi.daily.load;

import cn.hutool.core.date.*;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

        LocalDate localDate = LocalDate.parse("20161121", DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(localDate);
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

    //6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
    @Test
    public void test7() {
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(zdt);
    }

    /**
     * 获取所有的时区
     */
    @Test
    public void test6() {
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }


    //5. DateTimeFormatter : 解析和格式化日期或时间
    @Test
    public void test5() {
//		DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");

        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);

        System.out.println(strDate);

        LocalDateTime newLdt = LocalDateTime.parse(strDate, dtf);
        System.out.println(newLdt);
    }

    //4. TemporalAdjuster : 时间校正器
    @Test
    public void test4() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        //自定义：下一个工作日
        LocalDateTime ldt5 = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;

            DayOfWeek dow = ldt4.getDayOfWeek();

            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ldt4.plusDays(3);
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ldt4.plusDays(2);
            } else {
                return ldt4.plusDays(1);
            }
        });

        System.out.println(ldt5);

    }

    //3.
    //Duration : 用于计算两个“时间”间隔
    //Period : 用于计算两个“日期”间隔
    @Test
    public void test3() {
        Instant ins1 = Instant.now();

        System.out.println("--------------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }

        Instant ins2 = Instant.now();

        System.out.println("所耗费时间为：" + Duration.between(ins1, ins2));

        System.out.println("----------------------------------");

        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2011, 1, 1);

        Period pe = Period.between(ld2, ld1);
        System.out.println(pe.getYears());
        System.out.println(pe.getMonths());
        System.out.println(pe.getDays());
    }

    //2. Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
    @Test
    public void test2() {
        Instant ins = Instant.now();  //默认使用 UTC 时区
        System.out.println(ins);

        OffsetDateTime odt = ins.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        System.out.println(ins.getNano());

        Instant ins2 = Instant.ofEpochSecond(5);
        System.out.println(ins2);
    }

    //1. LocalDate、LocalTime、LocalDateTime
    @Test
    public void localDateTimeTest() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ld2 = LocalDateTime.of(2016, 11, 21, 10, 10, 10);
        System.out.println(ld2);

        LocalDateTime ldt3 = ld2.plusYears(20);
        System.out.println(ldt3);

        LocalDateTime ldt4 = ld2.minusMonths(2);
        System.out.println(ldt4);

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute());
        System.out.println(ldt.getSecond());
    }

}
