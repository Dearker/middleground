package com.hanyi.daily.guava;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * guava集合测试类
 *
 * @author wcwei@iflytek.com
 * @since 2021-01-07 11:25
 */
public class CollTest {

    /**
     * transform方法的使用方式和Stream的map函数类似
     */
    @Test
    public void listsTest(){
        List<Integer> arrayList = Lists.newArrayList(1, 2, 3);
        System.out.println(arrayList);

        // List 笛卡尔乘积
        List<String> list1 = Lists.newArrayList("1", "2");
        List<String> list2 = Lists.newArrayList("A", "B");
        List<List<String>> result = Lists.cartesianProduct(list1, list2);
        //[[1, A], [1, B], [2, A], [2, B]]
        System.out.println(result);

        // List 集合分区
        List<String> list = Lists.newArrayList("A", "B", "C","D","E");
        List<List<String>> listList = Lists.partition(list, 2);
        //[[A, B], [C, D], [E]]
        System.out.println(listList);
    }

}
