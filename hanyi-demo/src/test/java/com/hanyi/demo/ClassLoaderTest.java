package com.hanyi.demo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.hanyi.demo.entity.Address;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: middleground com.hanyi.demo ClassLoaderTest
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-12-12 20:05
 * @Version: 1.0
 */
public class ClassLoaderTest {

    private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    /**
     * 通过类名称获取包名
     */
    @Test
    public void ClassLoader() {
        String aPackage = ClassUtil.getPackage(ClassLoaderTest.class);
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(aPackage, SpringBootApplication.class);
        classes.forEach(s -> System.out.println(ClassUtil.getPackage(s)));

    }

    @Test
    public void ExecTest() {
        Process exec = RuntimeUtil.exec("ls -l");
        String str = RuntimeUtil.execForStr("ipconfig");

    }

    /**
     * 分布式id 雪花算法
     */
    @Test
    public void IdTest() {
        long l = snowflake.nextId();

        Console.log("This is Console log for {}.", l);
        //Props props = new Props("test.properties");
    }

    /**
     * 格式化时间
     */
    @Test
    public void DateTest() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", "20191220");

        String date = DateUtil.format(jsonObject.getDate("date"), DatePattern.NORM_DATE_PATTERN);
        System.out.println("获取的日期-->" + date);

    }

    /**
     * JSONArray转list
     */
    @Test
    public void JSONTest() {

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(Address.builder().city("1").zip("2").street("3").build());
        jsonArray.add(Address.builder().city("1").zip("2").street("3").build());

        List<Address> addresses = JSONObject.parseArray(jsonArray.toJSONString(), Address.class);
        System.out.println("获取的数据-->" + addresses);
        System.out.println("去重的数据-->" + jsonArray.stream().distinct().collect(Collectors.toList()));

        System.out.println("去重的数据-->" + addresses.stream().distinct().collect(Collectors.toList()));

        JSONArray array = JSONArray.parseArray(JSON.toJSONString(addresses));
        System.out.println("List转JSONArray-->" + array);

        List<Map> mapList = JSONObject.parseArray(jsonArray.toJSONString(), Map.class);
        System.out.println("JSONArray转map-->" + mapList);

        //JSONObject转map
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", Address.builder().city("1").zip("2").street("3").build());
        jsonObject.put("db", Address.builder().city("1").zip("2").street("3").build());

        Map map = JSONObject.parseObject(jsonObject.toJSONString(), Map.class);
        System.out.println("JSONObject转Map-->" + map);

        List list = JSONObject.parseObject(jsonArray.toJSONString(), List.class);
        System.out.println("JSONObject转list-->" + list);
    }

    /**
     * 布隆过滤器
     */
    @Test
    public void BloomFilterTest() {

        int size = 1000000;
        //布隆过滤器
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, 0.001);

        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        List<Integer> list = new ArrayList<>(1000);
        for (int i = size + 1; i < size + 10000; i++) {
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("误判数量：" + list.size());
    }

    /**
     * 偏移量
     */
    @Test
    public void forTest() {

        Object obj;
        for (int i = 0; i <= 10; i++) {
            obj = new Object();
        }

        int i = 20;
        int i1 = i << 1;
        int i2 = i << 2;
        int i3 = i << 3;
        System.out.println("左偏移1-->"+ i1);
        System.out.println("左偏移2-->"+ i2);
        System.out.println("左偏移3-->"+ i3);


        int i4 = i >> 1;
        int i5 = i >> 2;
        int i6 = i >> 3;
        System.out.println("右偏移1-->" + i4);
        System.out.println("右偏移2-->" + i5);
        System.out.println("右偏移3-->" + i6);

        int i7 = i / 8;
        int i8 = i >> 3;
        System.out.println("移位和计算-->" + i7 + "||" + i8);
    }

    /**
     * list的判断删除
     */
    @Test
    public void removeIfTest(){

        List<String> stringList = new ArrayList<> ();
        stringList.add("");
        stringList.add("1");

        stringList.removeIf(StrUtil::isEmpty);

        stringList.forEach(System.out::println);

    }

    /**
     * Map的merge方法，如果key存在，则相加，如果不存在，则向map中放入元素
     */
    @Test
    public void MergeTest(){

        Map<String,Integer> integerMap = Maps.newHashMap();

        integerMap.put("1",2);

        integerMap.merge("1",4,Integer::sum);

        integerMap.forEach((k,v)-> System.out.println(k+"||"+ v));

    }



    /**
     * 对JSONArray中JSONObject对象进行排序
     */
    @Test
    public void SortJSONTest(){

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time","2019-10-20 19:50:00");
        jsonArray.add(jsonObject);

        JSONObject json = new JSONObject();
        json.put("time","2019-10-21 11:10:00");
        jsonArray.add(json);

        jsonArray.sort((a,b)->{
            Date time = JSONObject.parseObject(a.toString()).getDate("time");
            Date time1 = JSONObject.parseObject(b.toString()).getDate("time");
            return time1.compareTo(time);
        });

        jsonArray.forEach(System.out::println);
    }



}
