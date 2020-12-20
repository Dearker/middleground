package com.hanyi.daily.load;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Collections工具测试类
 * </p>
 *
 * @author wenchangwei
 * @since 8:28 下午 2020/12/20
 */
public class CollectionsTest {

    /**
     * 逆转测试方法
     */
    @Test
    public void reverseTest() {
        List<String> stringList = Arrays.asList("111", "222", "333");
        Collections.reverse(stringList);
        System.out.println(stringList);
    }

    /**
     * 将指定列表中的两个索引进行位置互换
     */
    @Test
    public void swapTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        Collections.swap(integerList, 0, 1);
        System.out.println(integerList);
    }

    /**
     * 随机变换集合中元素的位置
     */
    @Test
    public void shuffleTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        Collections.shuffle(integerList);
        System.out.println(integerList);
    }

    /**
     * 使用指定的对象填充指定列表的所有元素
     */
    @Test
    public void fillTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        Collections.fill(integerList, 4);
        System.out.println(integerList);
    }

    /**
     * 集合中的元素向后移m个位置，在后面被遮盖的元素循环到前面来。
     * 移动列表中的元素，负数向左移动，正数向右移动
     */
    @Test
    public void rotateTest() {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(intList);
        Collections.rotate(intList, 2);
        //[4, 5, 1, 2, 3]
        System.out.println(intList);
    }

    /**
     * 返回指定集合中等于指定对象的元素出现的次数
     */
    @Test
    public void frequencyTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 1, 1);
        //3
        System.out.println(Collections.frequency(integerList, 1));
    }

    /**
     * 如果两个指定集合完全没有共同的元素返回true,否则返回false
     */
    @Test
    public void disjointTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        List<Integer> integers = Arrays.asList(4, 5, 6);
        //false
        System.out.println(Collections.disjoint(integerList, integers));
    }

    /**
     * 返回一个不可变集合组成的n个拷贝的指定对象
     */
    @Test
    public void nCopiesTest() {
        List<String> stringList = Collections.nCopies(3, "a");
        //[a, a, a]
        System.out.println(stringList);
    }

    /**
     * 把源列表中的数据覆盖到目标列表
     */
    @Test
    public void copeTest() {
        //源集合
        List<Integer> sourceList = Arrays.asList(1, 2, 3);

        //目标集合
        List<Integer> targetList = Arrays.asList(4, 4, 4, 4);
        Collections.copy(targetList, sourceList);
        //[1, 2, 3, 4]
        System.out.println(targetList);
    }

    /**
     * 使用二分查找法查找指定元素在指定列表的索引位置
     */
    @Test
    public void binarySearchTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 4);
        int binarySearch = Collections.binarySearch(integerList, 1);
        //0
        System.out.println(binarySearch);
    }

    /**
     * 查找subList在list中首次出现位置的索引,如果subList中有多个元素则只会去第一个元素进行查询并返回结果
     */
    @Test
    public void indexOfSubListTest() {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 6, 7, 3);
        List<Integer> targetList = Arrays.asList(5, 6);
        //4
        System.out.println(Collections.indexOfSubList(intList, targetList));
    }

    /**
     * 查找subList在list中最后一次出现位置的索引,如果subList中有多个元素则只会返回-1，单个元素则返回索引位置
     */
    @Test
    public void lastIndexOfSubListTest() {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 6, 7, 3);
        List<Integer> targetList = Arrays.asList(3, 2);
        //1
        System.out.println(Collections.lastIndexOfSubList(intList, targetList));
    }

}
