package com.hanyi.daily.load;

import java.util.*;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @PackAge: middleground com.hanyi.daily.load
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-10 10:47
 * @Version: 1.0
 */
public class CollectionTest {

    /**
     * list 和 string的转换
     */
    @Test
    public void stringAndListConvertTest() {

        List<String> stringList = Arrays.asList("111", "222", "333");
        String join = StrUtil.join(",", stringList);

        List<String> strings = Arrays.asList(StrUtil.split(join, ","));
        strings.forEach(System.out::println);
    }

    /**
     * ConcurrentHashMap允许一边更新、一边遍历，其他的map实现类则不行
     */
    @Test
    public void concurrentHashMapTest() {

        Map<String, Integer> integerMap = new ConcurrentHashMap<>();
        integerMap.put("a", 1);
        integerMap.put("b", 2);
        integerMap.put("c", 3);

        System.out.println(integerMap.size());
        for (Map.Entry<String, Integer> entry : integerMap.entrySet()) {
            integerMap.remove(entry.getKey());
        }
        System.out.println(integerMap.size());
    }

    /**
     * Multiset为无序可重复集合
     */
    @Test
    public void multisetTest() {

        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("d");

        System.out.println(multiset.size());
        System.out.println(multiset.count("a"));
    }

    /**
     * Multimap中一个key对应着多个value，返回为数组
     */
    @Test
    public void multimapTest() {

        Multimap<String, String> multiMap = ArrayListMultimap.create();

        multiMap.put("hanyi", "柯基");
        multiMap.put("hanyi", "哈士奇");
        multiMap.put("123", "1111");

        System.out.println(multiMap.get("hanyi"));
        System.out.println(multiMap.get("123"));
    }

    /**
     * BiMap 为双向map，可通过value获取key，也可通过key获取value
     * 该集合在key和value都是唯一时使用
     */
    @Test
    public void biMapTest() {

        BiMap<String, String> biMap = HashBiMap.create();

        biMap.put("柯基", "柯基小短腿");
        biMap.put("哈士奇", "无敌哈士奇");

        //通过value获取key
        System.out.println(biMap.inverse().get("柯基小短腿"));
        //通过key获取value
        System.out.println(biMap.get("柯基"));
    }

    /**
     * Table是可支持多个key的map集合
     */
    @Test
    public void tableTest() {

        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("张三", "计算机", 80);
        table.put("张三", "数学", 90);
        table.put("张三", "语文", 70);
        table.put("李四", "计算机", 70);
        table.put("李四", "数学", 60);
        table.put("李四", "语文", 100);

        Set<Table.Cell<String, String, Integer>> cellSet = table.cellSet();

        cellSet.forEach(cell -> System.out.println(cell.getRowKey() + "," + cell.getColumnKey() + "," + cell.getValue()));

        System.out.println("---------------------");

        Set<String> rowSet = table.rowKeySet();
        System.out.println(rowSet);

        System.out.println("---------------------");

        Set<String> columnKeySet = table.columnKeySet();
        System.out.println(columnKeySet);

        System.out.println("---------------------");

        //根据rowKey获取信息Map<column,value>
        System.out.println(table.row("张三"));

        System.out.println("---------------------");

        //根据column获取信息Map<row,value>
        System.out.println(table.column("计算机"));
    }

}
