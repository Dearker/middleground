package com.hanyi.daily.load;

import org.junit.Test;

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
    }

    @Test
    public void compareTest(){
        List<Integer> integerList = Arrays.asList(1, 3, 0, 5);
        Set<Integer> integerSet =new HashSet<>(integerList);

        List<Integer> integers = new ArrayList<>();

        System.out.println(integerSet.stream().min(Integer::compareTo).orElse(null));
    }

    @Test
    public void integerTest(){

        Integer integer = 35;
        int i = 35;

        if(integer.equals(i)){
            System.out.println("相等");
        }else{
            System.out.println("不等");
        }

        Integer integer1 = null;

        if(0 == integer1){
            System.out.println("111");
        }else{
            System.out.println("222");
        }

    }

}
