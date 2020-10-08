package com.hanyi.daily.load;

import org.junit.Test;

import java.text.NumberFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:11 下午 2020/7/21
 */
public class NumberTest {

    @Test
    public void intTest() {
        //0 byte 总共30个
        System.out.println(Character.UNASSIGNED);
        //0
        System.out.println(NumberFormat.INTEGER_FIELD);
        //1
        System.out.println(Byte.BYTES);
        //2
        System.out.println(Short.BYTES);
        //2
        System.out.println(Character.MIN_RADIX);
        //4
        System.out.println(Integer.BYTES);
        //8
        System.out.println(Long.BYTES);
        //8
        System.out.println(Byte.SIZE);
        //16
        System.out.println(Short.SIZE);
        //32
        System.out.println(Integer.SIZE);
        //36
        System.out.println(Character.MAX_RADIX);
        //64
        System.out.println(Long.SIZE);
        //127
        System.out.println(Byte.MAX_VALUE);
        //1023
        System.out.println(Double.MAX_EXPONENT);
    }

    /**
     * 计算测试
     */
    @Test
    public void mathTest() {
        //加法
        System.out.println(Math.addExact(1, 3));
        //减法
        System.out.println(Math.subtractExact(3, 1));
        //乘法
        System.out.println(Math.multiplyExact(4, 2));
        //除法
        System.out.println(Math.floorDiv(4, 2));
    }


}
