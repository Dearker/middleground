package com.hanyi.daily.load;

import org.junit.Test;

import java.util.Arrays;

/**
 * <p>
 * arrays测试类
 * </p>
 *
 * @author wenchangwei
 * @since 9:55 下午 2020/12/20
 */
public class ArraysTest {

    /**
     * 把数组array所有元素都赋值为指定数
     */
    @Test
    public void fillTest() {
        int[] array = {12, 21, 13, 24};
        Arrays.fill(array, 22);
        for (int value : array) {
            System.out.print(value + " ");
        }
    }

    /**
     * 将一个数组array转换成一个字符串
     */
    @Test
    public void toStringTest() {
        int[] array = {12, 21, 13, 24};
        //[12, 21, 13, 24]
        System.out.print(Arrays.toString(array));
    }

    /**
     * 把数组array复制成一个长度为length的新数组，返回类型与复制的数组一致
     */
    @Test
    public void copyOfTest() {
        int[] arr3 = {12, 21, 13, 24};
        int[] arr = Arrays.copyOf(arr3, 6);
        //12 21 13 24 0 0
        for (int value : arr) {
            System.out.print(value + " ");
        }
    }

    /**
     * 复制指定长度的数组
     */
    @Test
    public void copyOfRangeTest() {

        int[] a = new int[]{18, 62, 68, 82, 65, 9};

        // copyOfRange(int[] original, int from, int to)
        // 第一个参数表示源数组
        // 第二个参数表示开始位置(取得到)
        // 第三个参数表示结束位置(取不到)
        int[] b = Arrays.copyOfRange(a, 0, 3);
        //18 62 68
        for (int value : b) {
            System.out.print(value + " ");
        }
    }

    /**
     * 并行处理数组
     */
    @Test
    public void parallelTest() {

        //排序
        String[] arr1 = new String[]{"java", "fkava", "fkit", "ios", "android"};
        Arrays.parallelSort(arr1, (o1, o2) -> o1.length() - o2.length());
        //[ ios, java, fkit, fkava,  android]
        System.out.println(Arrays.toString(arr1));

        //计算
        int[] arr2 = new int[]{3, -4, 25, 16, 30, 18};
        //left代表数组中前一个索引处的元素，计算第一个元素时left为1，right代表数组中当前索引处的元素
        Arrays.parallelPrefix(arr2, (left, right) -> left * right);
        //[3, -12, -300, -4800, -144000, s 2592000]
        System.out.println(Arrays.toString(arr2));

        //设置值
        long[] arr3 = new long[5];
        //operand代表正在计算的元素索引
        Arrays.parallelSetAll(arr3, operand -> operand * 5);
        //[ 0, 5, 10, 15, 20]
        System.out.println(Arrays.toString(arr3));
    }

}
