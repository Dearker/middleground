package com.hanyi.daily.load;

import cn.hutool.core.util.NumberUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

/**
 * <p>
 * 数字计算测试类
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
    public void compareTest() {
        List<Integer> integerList = Arrays.asList(1, 3, 0, 5);
        Set<Integer> integerSet = new HashSet<>(integerList);

        List<Integer> integers = new ArrayList<>();

        System.out.println(integerSet.stream().min(Integer::compareTo).orElse(null));
    }

    /**
     * BigDecimal的加减乘除方法
     */
    @Test
    public void bigDecimalTest() {

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

        //除法,如果无法除尽则会报错，需要设置保留的小数位数，保留2位小数，并四舍五入 2.00
        BigDecimal divide = bigNum1.divide(bigNum2, 2, RoundingMode.HALF_UP);
        System.out.println("除法：" + divide);

        //保留2位小数，并且四舍五入
        BigDecimal bigDecimal = new BigDecimal("1.325");
        System.out.println(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

    /**
     * 使用工具类进行运算
     */
    @Test
    public void numberTest() {

        List<BigDecimal> bigDecimalList = new ArrayList<>();
        bigDecimalList.add(new BigDecimal("3.5"));
        bigDecimalList.add(new BigDecimal("-1.5"));
        bigDecimalList.add(new BigDecimal("5.5"));

        BigDecimal[] bigDecimals = bigDecimalList.toArray(new BigDecimal[0]);

        //加法
        BigDecimal add = NumberUtil.add(bigDecimals);
        System.out.println("加法：" + add);

        //减法
        BigDecimal sub = NumberUtil.sub(bigDecimals);
        System.out.println("减法：" + sub);

        //乘法
        BigDecimal mul = NumberUtil.mul(bigDecimals);
        System.out.println("乘法：" + mul);

        //除法
        BigDecimal bigDecimal = new BigDecimal("10");
        BigDecimal decimal = new BigDecimal("2");
        BigDecimal div = NumberUtil.div(bigDecimal, decimal, 2, RoundingMode.HALF_UP);
        System.out.println("除法：" + div);
    }

    /**
     * 百分比测试方法
     */
    @Test
    public void percentageTest() {
        double score = 0.32568;
        String decimalFormat = NumberUtil.decimalFormat("#.##%", score);
        //32.57%
        System.out.println(decimalFormat);

        BigDecimal bigDecimal = new BigDecimal("0.45687");
        String format = NumberUtil.decimalFormat("#.##%", bigDecimal);
        //45.69%
        System.out.println(format);

        String percent = NumberUtil.formatPercent(score, 3);
        //32.568%
        System.out.println(percent);
    }

}
