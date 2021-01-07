package com.hanyi.daily.guava;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * guava空判断测试类
 *
 * @author wcwei@iflytek.com
 * @since 2021-01-07 9:18
 */
public class OptionalTest {

    /**
     * of用法和jdk8的Optional的of方法相同
     */
    @Test
    public void ofTest(){
        Optional<Integer> optional = Optional.of(5);
        System.out.println(optional.isPresent());
        System.out.println(optional.get());
    }

    /**
     * fromNullable用法和jdk8的Optional的ofNullable方法相同
     */
    @Test
    public void formTest(){
        Optional<Object> optional = Optional.fromNullable(null);
        System.out.println(optional.isPresent());
        System.out.println(optional.or("1"));
        System.out.println(optional.orNull());
    }

    /**
     * 返回一个Set包含this中的实例的不可变单例Optional（如果有一个实例），否则返回一个空的不可变集合。
     */
    @Test
    public void asSetTest(){
        List<String> stringList = null;

        Set<List<String>> listSet = Optional.fromNullable(stringList).asSet();
        //[]
        System.out.println(listSet);

        stringList = new ArrayList<>();
        listSet = Optional.fromNullable(stringList).asSet();
        //[[]]
        System.out.println(listSet);

        Integer integer = 1;
        Set<Integer> integerSet = Optional.fromNullable(integer).asSet();
        //[1]
        System.out.println(integerSet);
    }

    @Test
    public void stringsTest(){
        String emptyToNull = Strings.emptyToNull("");
        System.out.println(emptyToNull);

        boolean nullOrEmpty = Strings.isNullOrEmpty("");
        System.out.println(nullOrEmpty);

        String nullToEmpty = Strings.nullToEmpty(null);
        System.out.println(nullToEmpty);
    }


}
