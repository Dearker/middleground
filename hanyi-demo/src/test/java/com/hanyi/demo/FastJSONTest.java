package com.hanyi.demo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.hanyi.demo.entity.Address;
import com.hanyi.demo.entity.User;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @ClassName: middleground com.hanyi.demo
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-12-12 20:05
 * @Version: 1.0
 */
public class FastJSONTest {

    private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    /**
     * 通过类名称获取包名
     */
    @Test
    public void ClassLoader() {
        String aPackage = ClassUtil.getPackage(FastJSONTest.class);
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(aPackage, SpringBootApplication.class);
        classes.forEach(s -> System.out.println(ClassUtil.getPackage(s)));
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
     * json中如果TypeReference的泛型是<T>则转换成JSON后会结果中依旧还是JSON，还需要再转换一次
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

        Map<String, Address> map = JSONObject.parseObject(jsonObject.toJSONString(),
                new TypeReference<Map<String, Address>>() {
                });
        System.out.println("JSONObject转Map-->" + map);

        List<Address> list = JSONObject.parseObject(jsonArray.toJSONString(), new TypeReference<List<Address>>() {
        });
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

        IntStream.range(0, size).forEach(bloomFilter::put);

        List<Integer> list = new ArrayList<>(1000);
        IntStream.range(size + 1, size + 10000).forEach(s -> {
            if (bloomFilter.mightContain(s)) {
                list.add(s);
            }
        });
        System.out.println("误判数量：" + list.size());
    }

    /**
     * 偏移量
     */
    @Test
    public void forTest() {
        Object obj = null;
        for (int i = 0; i <= 10; i++) {
            obj = new Object();
        }

        int i = 20;
        int i1 = i << 1;
        int i2 = i << 2;
        int i3 = i << 3;
        System.out.println("左偏移1-->" + i1);
        System.out.println("左偏移2-->" + i2);
        System.out.println("左偏移3-->" + i3);


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
     * 对JSONArray中JSONObject对象进行排序
     */
    @Test
    public void SortJSONTest() {
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time", "2019-10-20 19:50:00");
        jsonArray.add(jsonObject);

        JSONObject json = new JSONObject();
        json.put("time", "2019-10-21 11:10:00");
        jsonArray.add(json);

        jsonArray.sort((a, b) -> {
            Date time = JSONObject.parseObject(a.toString()).getDate("time");
            Date time1 = JSONObject.parseObject(b.toString()).getDate("time");
            return time1.compareTo(time);
        });

        jsonArray.forEach(System.out::println);
    }

    /**
     * json序列化
     */
    @Test
    public void serializableTest() {
        User user = new User();
        user.setId(1000);
        user.setName("柯基小短腿");
        user.setCreateDate(new Date());

        Map<Integer, Integer> dataMap = new HashMap<>();
        dataMap.put(1, 100);
        dataMap.put(2, 200);
        user.setDataMap(dataMap);

        System.out.println("默认序列化结果：\n" + JSON.toJSONString(user));
        //如果key不为String，则转换为String
        System.out.println("指定WriteNonStringKeyAsString序列化结果：\n" +
                JSON.toJSONString(user, SerializerFeature.WriteNonStringKeyAsString));
        //如果value不为String，则转换为String
        System.out.println("指定WriteNonStringValueAsString序列化结果：\n" +
                JSON.toJSONString(user, SerializerFeature.WriteNonStringValueAsString));
        //输出为空的字段
        System.out.println("指定WriteMapNullValue序列化结果：\n" +
                JSON.toJSONString(user, SerializerFeature.WriteMapNullValue));
        //String为null时输出""
        System.out.println("指定WriteNullStringAsEmpty序列化结果：\n" +
                JSON.toJSONString(user, SerializerFeature.WriteNullStringAsEmpty));
        //number为null时输出0
        System.out.println("指定WriteNullNumberAsZero序列化结果：\n" +
                JSON.toJSONString(user, SerializerFeature.WriteNullNumberAsZero));
        //修改日期格式,yyyy-MM-dd
        System.out.println("指定WriteDateUseDateFormat序列化结果：\n" +
                JSON.toJSONString(user, SerializerFeature.WriteDateUseDateFormat));
    }

    /**
     * 序列化为Json串后，Json串是没有Long类型呢。而且反序列化回来如果也是Object接收，
     * 数字小于Integer最大值的话，给转成Integer
     */
    @Test
    public void jsonLongTest() {
        Long idValue = 3000L;
        Map<String, Object> data = new HashMap<>(2);
        data.put("id", idValue);
        data.put("name", "捡田螺的小男孩");

        String jsonString = JSON.toJSONString(data);

        // 反序列化时Long被转为了Integer
        Map map = JSON.parseObject(jsonString, Map.class);
        Object idObj = map.get("id");
        //true
        System.out.println("反序列化的类型是否为Integer：" + (idObj instanceof Integer));

        JSONObject jsonObject = JSON.parseObject(jsonString, JSONObject.class);
        System.out.println(jsonObject.getLong("id"));
    }

}
