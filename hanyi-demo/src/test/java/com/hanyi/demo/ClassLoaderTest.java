package com.hanyi.demo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanyi.demo.entity.Address;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public void ExecTest(){
        Process exec = RuntimeUtil.exec("ls -l");
        String str = RuntimeUtil.execForStr("ipconfig");

    }

    @Test
    public void IdTest(){
        long l = snowflake.nextId();

        Console.log("This is Console log for {}.", l);
        //Props props = new Props("test.properties");
    }

    @Test
    public void DateTest(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date","20191220");

        String date = DateUtil.format(jsonObject.getDate("date"), DatePattern.NORM_DATE_PATTERN);
        System.out.println("获取的日期-->"+ date);

    }

    /**
     * JSONArray转list
     *
     */
    @Test
    public void JSONTest(){

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(Address.builder().city("1").zip("2").street("3").build());
        jsonArray.add(Address.builder().city("1").zip("2").street("3").build());

        List<Address> addresses = JSONObject.parseArray(jsonArray.toJSONString(), Address.class);
        System.out.println("获取的数据-->"+ addresses);
        System.out.println("去重的数据-->"+ jsonArray.stream().distinct().collect(Collectors.toList()));

        System.out.println("去重的数据-->"+addresses.stream().distinct().collect(Collectors.toList()));

        JSONArray array= JSONArray.parseArray(JSON.toJSONString(addresses));
        System.out.println("List转JSONArray-->"+array);

        List<Map> mapList = JSONObject.parseArray(jsonArray.toJSONString(), Map.class);
        System.out.println("JSONArray转map-->"+ mapList);

        //JSONObject转map
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",Address.builder().city("1").zip("2").street("3").build());
        jsonObject.put("db",Address.builder().city("1").zip("2").street("3").build());

        Map map = JSONObject.parseObject(jsonObject.toJSONString(), Map.class);
        System.out.println("JSONObject转Map-->"+ map);

        List list = JSONObject.parseObject(jsonArray.toJSONString(), List.class);
        System.out.println("JSONObject转list-->"+list);
    }


}
