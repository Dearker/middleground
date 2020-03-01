package com.hanyi.daily.thread;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @PackAge: middleground com.hanyi.daily.thread
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-23 10:08
 * @Version: 1.0
 */
public class DateTest {

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
