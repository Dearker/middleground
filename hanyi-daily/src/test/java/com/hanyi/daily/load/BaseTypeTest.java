package com.hanyi.daily.load;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * 基础类型测试类
 *
 * @author wcwei@iflytek.com
 * @since 2020-12-23 9:45
 */
public class BaseTypeTest {

    /**
     * intern()方法用于将字符串对象加入常量池中
     * copyValueOf(char[] data): 返回指定数组中表示该字符序列的字符串
     * copyValueOf(char[] data, int offset, int count): 返回指定数组中表示该字符序列的 字符串
     */
    @Test
    public void stringTest() {
        List<String> stringList = new ArrayList<>();
        stringList.add("哈士奇");
        stringList.add("柯基");
        stringList.add("柴犬");

        String join = String.join(StrUtil.COMMA, stringList);

        String[] split = join.split(StrUtil.COMMA);

        Stream.of(split).forEach(System.out::println);

        String name = " 哈 士奇 ";

        String trim = name.trim();
        System.out.println(trim);
        Stream.of(trim.toCharArray()).forEach(System.out::println);
    }

    /**
     * 用于检测两个字符串在一个区域内是否相等
     * ignoreCase -- 如果为 true，则比较字符时忽略大小写。
     * toffset -- 此字符串中子区域的起始偏移量。
     * other -- 字符串参数。
     * ooffset -- 字符串参数中子区域的起始偏移量。
     * len -- 要比较的字符数。
     */
    @Test
    public void regionMatchesTest() {

        String str1 = "www.runoob.com";
        String str2 = "runoob";
        String str3 = "RUNOOB";

        //true
        System.out.println("返回值 :" + str1.regionMatches(4, str2, 0, 5));
        //false
        System.out.println("返回值 :" + str1.regionMatches(4, str3, 0, 5));
        //true
        System.out.println("返回值 :" + str1.regionMatches(true, 4, str3, 0, 5));
    }

    /**
     * compareTo（）：根据字符串中每个字符的Unicode编码进行比较
     * compareToIgnoreCase（）：根据字符串中每个字符的Unicode编码进行忽略大小写比较
     */
    @Test
    public void compareToTest() {
        String s = "abc";
        String b = "def";

        System.out.println(s.compareTo(b));
        System.out.println(s.compareToIgnoreCase(b));
    }

    @Test
    public void integerTest() {
        Integer integer = 20;
        System.out.println(integer.byteValue());
        System.out.println(integer.longValue());
        //671088640
        System.out.println(Integer.reverse(integer));

        System.out.println(Integer.sum(1, 3));
        System.out.println(Integer.max(1, 2));
        System.out.println(Integer.min(1, 4));
    }

    @Test
    public void arrayTest(){
        Object newInstance = Array.newInstance(boolean.class, 1);
        System.out.println(ArrayUtil.isArray(newInstance));
        System.out.println(newInstance);
        System.out.println(Array.get(newInstance, 0));
    }

    /**
     * 通过初始化一个具有1个元素的数组，然后通过获取这个数组的值来获取基本类型默认值
     */
    @Test
    public void baseTypeTest() {
        Map<Class<?>, Object> defaultValues = Stream.of(boolean.class, byte.class,
                char.class, double.class, float.class, int.class, long.class, short.class)
                .collect(toMap(clazz -> clazz, clazz -> Array.get(Array.newInstance(clazz, 1), 0)));

        System.out.println(defaultValues.get(boolean.class));
        System.out.println(defaultValues.get(byte.class));
        System.out.println(defaultValues.get(double.class));
        System.out.println(defaultValues.get(float.class));
    }

}
