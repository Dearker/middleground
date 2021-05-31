package com.hanyi.daily.algorithm.common;

import org.junit.Test;

/**
 * <p>
 * 分治算法
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/24 8:01 下午
 */
public class DivideConquerTest {

    @Test
    public void divideTest() {
        tower(3, 'A', 'B', 'C');
    }

    private static void tower(int num, char first, char second, char third) {
        if (num == 1) {
            System.out.println("第1个盘从 " + first + "->" + third);
        } else {
            //如果我们有n>=2情况，我们总是可以看做是两个盘 1.最下边的一个盘 2.上面的所有盘/1.先把 最上面的所有盘 A->B，移动过程会使用到 c
            tower(num - 1, first, third, second);
            //2. 把最下边的盘 A->C
            System.out.println("第" + num + "个盘从 " + first + "->" + third);
            //3.把 B 塔的所有盘 从 B->C，移动过程使用到 a塔
            tower(num - 1, second, first, third);
        }
    }

}
