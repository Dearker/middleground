package com.hanyi.daily.load;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

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
     * 计算类测试
     */
    @Test
    public void mathTest() {
        //加法 3
        System.out.println(Math.addExact(1, 2));
        //减法 1
        System.out.println(Math.subtractExact(2, 1));
        //乘法 12
        System.out.println(Math.multiplyExact(3, 4));
        //除法 3
        System.out.println(Math.floorDiv(6, 2));
        //加法 3
        System.out.println(Integer.sum(1, 2));
        //绝对值
        System.out.println(Math.abs(-10));
    }

    @Test
    public void compareTest(){
        List<Integer> integerList = Arrays.asList(1, 3, 0, 5);
        Set<Integer> integerSet =new HashSet<>(integerList);

        List<Integer> integers = new ArrayList<>();

        System.out.println(integerSet.stream().min(Integer::compareTo).orElse(null));
    }

    /**
     * BigDecimal的加减乘除方法
     */
    @Test
    public void bigDecimalTest(){

        BigDecimal bigNum1 = new BigDecimal("10");
        BigDecimal bigNum2 = new BigDecimal("5");

        System.out.println(BigDecimal.ZERO);
        //加法 15
        BigDecimal add = bigNum1.add(bigNum2);
        System.out.println("求和：" + add);

        //减法 5
        BigDecimal subtract = bigNum1.subtract(bigNum2);
        System.out.println("减法：" + subtract);

        //乘法 50
        BigDecimal multiply = bigNum1.multiply(bigNum2);
        System.out.println("乘法：" + multiply);

        //除法 2
        BigDecimal divide = bigNum1.divide(bigNum2);
        System.out.println("除法：" + divide);

        //保留2位小数，并且四舍五入
        BigDecimal bigDecimal = new BigDecimal("1.325");
        System.out.println(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
    }


}
