package com.hanyi.daily.load;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.NumberUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

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

    /**
     * ceil: 向上取整
     * floor: 向下取整，非四舍五入
     * rint: 方法返回最接近参数的整数值,四舍五入
     * pow：用于返回第一个参数的第二个参数次方
     * round：四舍五入
     * sqrt: 正平方根
     * abs：绝对值
     * round(double a)：返回参数中最接近的long类型之，四舍五入
     */
    @Test
    public void math01Test() {
        //2.0
        System.out.println(Math.ceil(1.2));
        //1.0
        System.out.println(Math.floor(1.5));
        //1.0
        System.out.println(Math.rint(1.4));
        //2.0
        System.out.println(Math.rint(1.5));
        //次方：8.0
        System.out.println("pow: " + Math.pow(2, 3));
        //正平方根：2.0
        System.out.println("sqrt: " + Math.sqrt(4));

        System.out.println(Math.toIntExact(30L));
    }

    @Test
    public void compareTest() {
        List<Integer> integerList = Arrays.asList(1, 3, 0, 5);
        Set<Integer> integerSet = new HashSet<>(integerList);
        System.out.println(integerSet.stream().min(Integer::compareTo).orElse(null));
    }

    /**
     * BigDecimal的加减乘除方法
     *
     * <p>
     * RoundingMode模式：
     * UP: 若舍入位为非零，则对舍入部分的前一位数字加1；若舍入位为零，则直接舍弃。即为向外取整模式
     * DOWN: 不论舍入位是否为零，都直接舍弃。即为向内取整模式
     * CEILING：向上取整
     * FLOOR: 向下取整
     * HALF_UP：四舍五入
     * HALF_DOWN: 五舍六入
     * HALF_EVEN: 整数位若是奇数则四舍五入，若是偶数则五舍六入
     * UNNECESSARY: 断言请求的操作具有精确的结果，因此不需要舍入。如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException
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
        BigDecimal bigDecimal = new BigDecimal("1.322");
        //1.32
        System.out.println(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());

        //1.33
        System.out.println(bigDecimal.setScale(2, RoundingMode.UP).doubleValue());
    }

    /**
     * 使用BigDecimal.valueOf()用于构建BigDecimal对象
     *
     * @throws InterruptedException 线程异常
     */
    @Test
    public void valueOfTest() throws InterruptedException {

        TimeInterval timer = DateUtil.timer();

        TimeUnit.SECONDS.sleep(1);
        List<Integer> integerList = new ArrayList<>();
        IntStream.range(0, 10).forEach(integerList::add);

        System.out.println(integerList);

        long intervalMs = timer.intervalMs();
        BigDecimal div = NumberUtil.div(new BigDecimal(Long.toString(intervalMs)), new BigDecimal("1000"), 3, RoundingMode.FLOOR);
        System.out.println(div);

        BigDecimal bigDecimal = NumberUtil.div(BigDecimal.valueOf(intervalMs), BigDecimal.valueOf(1000), 3, RoundingMode.FLOOR);
        System.out.println(bigDecimal);
        System.out.println(intervalMs);
    }

    /**
     * compareTo的返回值为0则表示相等，1表示大于，-1表示小于
     */
    @Test
    public void compareToTest() {

        BigDecimal bigDecimal = new BigDecimal("12");
        BigDecimal decimal = new BigDecimal("11");

        if (bigDecimal.compareTo(decimal) > 0) {
            System.out.println("大于");
        } else {
            System.out.println("小于");
        }

        int compareTo = BigDecimal.valueOf(0).compareTo(BigDecimal.ZERO);
        System.out.println(compareTo);
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

    /**
     * 通过forEach计算bigDecimal
     */
    @Test
    public void computeTest() {
        Map<BigDecimal, Integer> bigDecimalMap = new HashMap<>();
        bigDecimalMap.put(BigDecimal.valueOf(10), 15);
        bigDecimalMap.put(BigDecimal.valueOf(14), 15);

        final BigDecimal[] total = {BigDecimal.ZERO};

        AtomicReference<BigDecimal> bigDecimalAtomicReference = new AtomicReference<>(BigDecimal.ZERO);
        bigDecimalMap.forEach((k, v) -> {
            total[0] = NumberUtil.mul(k, v).add(total[0]);
            bigDecimalAtomicReference.set(bigDecimalAtomicReference.get().add(NumberUtil.mul(k, v)));
        });

        System.out.println(total[0]);
        System.out.println(bigDecimalAtomicReference.get());
    }

}
