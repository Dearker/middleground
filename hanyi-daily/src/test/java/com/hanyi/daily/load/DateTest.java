package com.hanyi.daily.load;

import cn.hutool.core.date.*;
import org.junit.Test;

import java.time.Month;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 * 日期测试类
 * Instant          时间戳
 * Duration         持续时间，时间差
 * LocalDate        只包含日期
 * LocalTime        只包含时间
 * LocalDateTime    包含日期和时间
 * Period           时间段
 * ZoneOffset       时区偏移量
 * ZonedDateTime    带时区的时间
 * Clock            时钟，比如获取目前美国纽约的时间
 * DateTimeFormatter 时间格式化
 * </p>
 *
 * @author wenchangwei
 * @since 11:18 下午 2020/10/4
 */
public class DateTest {

    /**
     * 时间对象转换
     * <p>
     * 把 Date 转换为 LocalDateTime 的时候，需要通过 Date 的 toInstant 方法得到一个 UTC 时间戳进行转换，
     * 并需要提供当前的时区，这样才能把 UTC 时间转换为本地日期时间（的表示）。
     * 把 LocalDateTime 的时间表示转换为 Date 时，也需要提供时区，用于指定是哪个时区的时间表示，
     * 也就是先通过 atZone 方法把 LocalDateTime 转换为 ZonedDateTime，然后才能获得 UTC 时间戳：
     */
    @Test
    public void localDateTest() {
        Date date = new Date();
        // Date-----> LocalDateTime 这里指定使用当前系统默认时区
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        // LocalDateTime------> Date 这里指定使用当前系统默认时区
        Date newDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        LocalDate localDate = LocalDate.now();
        LocalDate plusDays = localDate.plusDays(10);

        long until = localDate.until(plusDays, ChronoUnit.DAYS);
        System.out.println("相差多少天：" + until);
        System.out.println("获取今年的开始日期到当前时间的间隔天数：" + localDate.getDayOfYear());

        System.out.println("一天的开始时间" + localDate.atStartOfDay());

        long epochSecond = localDate.atStartOfDay().toEpochSecond(ZoneOffset.ofHours(8));
        System.out.println("获取一天的开始时间的秒数：" + epochSecond);

        long epochMilli = localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        System.out.println("获取一天的开始时间的秒数：" + epochMilli);

        System.out.println("获取当前周的星期一：" + localDate.with(ChronoField.DAY_OF_WEEK, 1));
        System.out.println("获取今年的第一天：" + localDate.with(ChronoField.DAY_OF_YEAR, 1));
    }

    @Test
    public void localTimeTest() {
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
    }

    /**
     * 时间转换类
     */
    @Test
    public void LocalDateTimeTest() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ld2 = LocalDateTime.of(2016, 11, 21, 10, 10, 10);
        System.out.println(ld2);

        //获取20年后的日期
        LocalDateTime ldt3 = ld2.plusYears(20);
        System.out.println(ldt3);

        //获取2个月之前的日期
        LocalDateTime ldt4 = ld2.minusMonths(2);
        System.out.println(ldt4);

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());

        LocalDateTime min = ldt.with(LocalTime.MIN);
        System.out.println("一天的开始时间：" + min);
        LocalDateTime max = ldt.with(LocalTime.MAX);
        System.out.println("一天的结束时间：" + max);

        System.out.println("---------------------------------");

        //获取毫秒
        long epochMilli = LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        //获取秒
        long epochSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        System.out.println("获取的时间-->" + epochMilli + "||" + epochSecond);

        long currentSeconds = DateUtil.currentSeconds();
        System.out.println("获取的秒数-->" + currentSeconds);

        DateTime dateTime = DateUtil.lastMonth();
        String now = DateUtil.now();

        DateTime parse = DateUtil.parse("2019-12-20");
        System.out.println(dateTime + "||" + now + "||" + parse.getTime());

        LocalDateTime toLocalDateTime = DateUtil.toLocalDateTime(DateUtil.date());
        System.out.println("日期工具类：" + toLocalDateTime);
        System.out.println(toLocalDateTime.toLocalDate());
        System.out.println(toLocalDateTime.toLocalTime());

        LocalDateTime ofInstant = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault());
        System.out.println("毫秒值转日期时间：" + ofInstant);
    }

    /**
     * 格式化时间
     */
    @Test
    public void formatTest() {
        // 按照 yyyy-MM-dd HH:mm:ss 转化时间
        String dateStr = "2020-05-07 22:34:00";
        DateTime parse = DateUtil.parse(dateStr, DatePattern.NORM_DATETIME_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 将 LocalDateTime 格式化字符串
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
        String format1 = DateUtil.format(parse, DatePattern.NORM_DATETIME_PATTERN);

        LocalDate localDate = LocalDate.parse("20161121", DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(localDate);


        String time = "20160102";
        //2016-01-02
        System.out.println(LocalDate.parse(time, DateTimeFormatter.BASIC_ISO_DATE));
        String date = "2016-01-02";
        //2016-01-02
        System.out.println(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE));
    }

    /**
     * Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
     */
    @Test
    public void instantTest() {
        Instant ins = Instant.now();  //默认使用 UTC 时区
        System.out.println("当前时间：" + ins);
        //2021-03-10T00:00:00Z
        System.out.println("截取的时间：" + ins.truncatedTo(ChronoUnit.DAYS));

        OffsetDateTime odt = ins.atOffset(ZoneOffset.ofHours(8));
        System.out.println("加8小时后的时间：" + odt);

        Instant ins2 = Instant.ofEpochSecond(5);
        System.out.println(ins2);

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("毫秒：" + ins.toEpochMilli());
        System.out.println("系统毫秒：" + currentTimeMillis);

        //获取当前秒数
        System.out.println("秒：" + Instant.now().getEpochSecond());
        System.out.println("系统秒：" + currentTimeMillis / 1000);

        Instant instant = Instant.ofEpochMilli(currentTimeMillis);
        //毫秒值转instance对象  默认时间差8小时
        System.out.println("时间戳转时间：" + instant);
        //需要推后8小时才能和当前时间匹配
        Instant plus = instant.plus(8, ChronoUnit.HOURS);
        System.out.println("时间戳转时间plus：" + plus);

        //转LocalDateTime时，不需要使用+8小时的instance对象
        System.out.println(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
    }

    @Test
    public void instantApiTest() {
        Instant ofEpochMilli = Instant.ofEpochMilli(1614603520000L);
        System.out.println(ofEpochMilli);

        Instant instant = Instant.now();
        System.out.println("utc时间(差8小时)：" + instant);
        //将参数里面的时间设置成instant的时间
        Temporal temporal = instant.adjustInto(ofEpochMilli);
        System.out.println(temporal);
        System.out.println("---------------------");

        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println("当前时间：" + zonedDateTime);

        //该方法只适用于：OffsetDateTime 和 ZonedDateTime ,将其他时间对象转换成instant对象
        System.out.println("转换时间对象：" + Instant.from(OffsetDateTime.now()));

        System.out.println("获取当前秒数：" + instant.getLong(ChronoField.INSTANT_SECONDS));
        System.out.println("仅获取当前毫秒的3位数：" + instant.getLong(ChronoField.MILLI_OF_SECOND));
    }

    /**
     * ZonedDateTime=LocalDateTime+ZoneId，具有时区属性
     * LocalDateTime 只能认为是一个时间表示，ZonedDateTime 才是一个有效的时间
     */
    @Test
    public void zonedDateTimeTest() {
        //一个时间表示
        String stringDate = "2020-01-02 22:00:00";
        //初始化三个时区
        ZoneId timeZoneSH = ZoneId.of("Asia/Shanghai");
        ZoneId timeZoneNY = ZoneId.of("America/New_York");
        ZoneId timeZoneJST = ZoneOffset.ofHours(9);

        //格式化器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.parse(stringDate, dateTimeFormatter), timeZoneJST);

        //使用DateTimeFormatter格式化时间，可以通过withZone方法直接设置格式化使用的时区
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        System.out.println(timeZoneSH.getId() + " || " + outputFormat.withZone(timeZoneSH).format(date));
        System.out.println(timeZoneNY.getId() + " || " + outputFormat.withZone(timeZoneNY).format(date));
        System.out.println(timeZoneJST.getId() + " || " + outputFormat.withZone(timeZoneJST).format(date));
    }

    @Test
    public void rangTest() {
        DateTime dateTime = DateUtil.date();
        DateTime dateTime1 = DateUtil.offset(dateTime, DateField.MONTH, -6);

        DateRange range = DateUtil.range(dateTime1, dateTime, DateField.MONTH);
        List<String> dateTimeList = new ArrayList<>(6);
        range.forEach(s -> dateTimeList.add(s.toString()));

        List<String> stringList = new ArrayList<>(6);
        for (int i = 0, length = dateTimeList.size(); i < length; i++) {
            if (i + 1 < length) {
                String s = dateTimeList.get(i) + "||" + dateTimeList.get(i + 1);
                stringList.add(s);
            }
        }
        stringList.forEach(System.out::println);
    }

    /**
     * 获取开始时间和结束时间之间总共多少天，可直接使用localDate中的until方法
     */
    @Test
    public void dateTest() {
        DateTime start = DateUtil.date();
        DateTime end = DateUtil.lastWeek();
        long l = DateUtil.betweenDay(start, end, true);
        System.out.println(l);
        DateRange range = DateUtil.range(start, end, DateField.DAY_OF_YEAR);

        range.forEach(System.out::println);
    }

    /**
     * ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
     */
    @Test
    public void zonedTest() {
        System.out.println(ZoneId.systemDefault());
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(zdt);

        System.out.println("----------获取所有的时区------------");
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }

    /**
     * TemporalAdjuster : 时间校正器
     * 使用 TemporalAdjusters.firstDayOfMonth 得到当前月的第一天；
     * 使用 TemporalAdjusters.firstDayOfYear() 得到当前年的第一天；
     * 使用 TemporalAdjusters.previous(DayOfWeek.SATURDAY) 得到上一个周六；
     * 使用 TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY) 得到本月最后一个周五。
     */
    @Test
    public void temporalAdjusterTest() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println("当前月份的第10天：" + ldt2);

        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        //自定义：下一个工作日
        LocalDateTime ldt5 = ldt.with(l -> {
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

        //为当前时间增加 100 天以内的随机天数
        System.out.println(LocalDate.now().with(temporal ->
                temporal.plus(ThreadLocalRandom.current().nextInt(100), ChronoUnit.DAYS)));
    }

    /**
     * Duration : 用于计算两个“时间”间隔
     * 间隔输出的单位可以是：天、小时、分钟、秒、毫秒，相当于LocalDateTime的unit方法单位更多，可选择使用
     * <p>
     * 注：Duration的between、addTo、subtractFrom都不能使用localDate这类只有日期没有时间的对象
     * 可使用ChronoUnit.DAYS.between() 方法解决
     */
    @Test
    public void durationTest() {
        Instant ins1 = Instant.now();
        Instant ins2 = ins1.plusMillis(1000);

        Duration between = Duration.between(ins1, ins2);
        System.out.println("所耗费毫秒为：" + between.toMillis());
        System.out.println("所耗费秒为：" + between.getSeconds());

        Duration duration = Duration.ofDays(-1);
        System.out.println(duration.toHours());
        System.out.println("绝对值" + duration.abs().toHours());
        System.out.println("相反数：" + duration.negated().toHours());

        //该方法的参数中的对象日期可为空，时间不能为空，即localDate不能使用，localTime和localDateTime可以使用
        Temporal temporal = duration.addTo(LocalDateTime.now());
        System.out.println("加负一天后的时间：" + temporal);
        System.out.println("减负一天后的时间：" + duration.subtractFrom(LocalDateTime.now()));
    }

    /**
     * Period : 用于计算两个“日期”间隔
     * <p>
     * 通过 Period.between 得到了两个 LocalDate 的差，返回的是两个日期差几年零几月零几天。
     * 如果希望得知两个日期之间差几天，直接调用 Period 的 getDays() 方法得到的只是最后的“零几天”，而不是算总的间隔天数。
     * <p>
     * 使用 ChronoUnit.DAYS.between 解决
     */
    @Test
    public void periodTest() {
        LocalDate today = LocalDate.of(2019, 12, 12);
        LocalDate specifyDate = LocalDate.of(2018, 10, 1);
        Period between = Period.between(specifyDate, today);
        System.out.println(between);
        System.out.println(between.getDays());
        //localDate不能使用Duration
        System.out.println(ChronoUnit.DAYS.between(specifyDate, today));

        //addTo系列方法不能使用只有时间没有日期的对象
        Temporal temporal = between.addTo(LocalDateTime.now());
        System.out.println(temporal);
    }

    /**
     * 检查生日周期性事件
     */
    @Test
    public void monthDayTest() {
        LocalDate date1 = LocalDate.now();

        LocalDate date2 = LocalDate.of(2018, 2, 6);
        MonthDay birthday = MonthDay.of(date2.getMonth(), date2.getDayOfMonth());
        //将localDate转换成MonthDay
        MonthDay currentMonthDay = MonthDay.from(date1);

        System.out.println(birthday);
        System.out.println(currentMonthDay);
        if (currentMonthDay.equals(birthday)) {
            System.out.println("是你的生日");
        } else {
            System.out.println("你的生日还没有到");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMdd");
        //--01-03
        System.out.println(MonthDay.parse("--01-03"));
        //--01-03
        System.out.println(MonthDay.parse("0103", dateTimeFormatter));

        MonthDay monthDay = MonthDay.now();
        //0307
        System.out.println(monthDay.format(dateTimeFormatter));
    }

    /**
     * 年月组合类
     */
    @Test
    public void yearMonthTest() {
        YearMonth yearMonth = YearMonth.parse("2020-07");
        System.out.println(yearMonth);

        System.out.println("当前是否为闰年：" + yearMonth.isLeapYear());
        System.out.println(yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")));

        YearMonth currentYearMonth = YearMonth.now();
        //获取当前月份的总共天数
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
        YearMonth creditCardExpiry = YearMonth.of(2019, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
    }

    /**
     * 单独的年和月日期格式
     */
    @Test
    public void yearAndMonthTest() {
        //2
        System.out.println(Month.FEBRUARY.getValue());
        System.out.println(Month.of(10).getValue());

        System.out.println(Year.now());
        System.out.println(Year.of(2021));
    }

    /**
     * 检查闰年
     */
    @Test
    public void leapYearTest() {
        LocalDate today = LocalDate.now();
        if (today.isLeapYear()) {
            System.out.println("This year is Leap year");
        } else {
            System.out.println("This is not a Leap year");
        }
    }

    /**
     * 时钟测试类
     */
    @Test
    public void clockTest() {
        // 获取UTC时间
        Clock clock = Clock.systemUTC();
        System.out.println("Clock : " + clock.millis());
        System.out.println("instance: " + clock.instant());

        System.out.println("--------------------------------");

        // 获取系统默认时区时间
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println("Clock : " + defaultClock.millis());
        System.out.println(defaultClock.instant());
    }

}
