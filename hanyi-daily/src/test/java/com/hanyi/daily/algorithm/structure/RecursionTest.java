package com.hanyi.daily.algorithm.structure;

import org.junit.Test;

/**
 * <p>
 * 递归测试类
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/18 8:12 下午
 */
public class RecursionTest {

    /**
     * 阶乘测试
     */
    @Test
    public void factorialTest() {
        System.out.println(factorial(3));
    }

    private static int factorial(int num) {
        // 1 * 2 * 3
        return num == 1 ? 1 : factorial(num - 1) * num;
    }

}
