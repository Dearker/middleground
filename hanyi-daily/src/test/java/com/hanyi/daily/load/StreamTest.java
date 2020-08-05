package com.hanyi.daily.load;

import com.hanyi.daily.pojo.CostInfo;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 数据结构的Stream测试类
 * </p>
 *
 * @author wenchangwei
 * @since 12:22 下午 2020/7/26
 */
public class StreamTest {

    /**
     * flatMap 主要是将集合中的元素进一步细化打平；
     * 如果使用map获取集合中的元素，则返回的是Lis<char[]>；如果需要获取里面的char元素则还需要循环遍历一次
     */
    @Test
    public void flatMapTest() {
        List<String> first = Arrays.asList("one", "two", "three", "four");
        List<String> second = Arrays.asList("A", "B", "C", "D");

        List<Character> characterList = first.stream().flatMap(StreamTest::buildChar).collect(Collectors.toList());
        System.out.println("characterList is: " + characterList);

        //将2个集合的元素合并到一个集合中
        Stream.of(first, second).flatMap(Collection::stream).forEach(System.out::println);
        List<String> collect = Stream.of(first, second).flatMap(Collection::stream).collect(Collectors.toList());

        System.out.println(collect);
    }

    /**
     * 将字符串转换char流
     *
     * @param string 字符串
     * @return 返回转换后的流对象
     */
    private static Stream<Character> buildChar(String string) {
        List<Character> characterList = new ArrayList<>(string.length());
        for (char c : string.toCharArray()) {
            characterList.add(c);
        }
        return characterList.stream();
    }

    /**
     * 集合属性映射操作
     */
    @Test
    public void mapTest() {
        List<CostInfo> costInfoList = new ArrayList<>();
        costInfoList.add(new CostInfo(new BigDecimal("12.02")));
        costInfoList.add(new CostInfo(new BigDecimal("32.01")));

        List<String> collect = costInfoList.stream().map(CostInfo::getCost).map(BigDecimal::toString).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * reduce的第一个参数为初始值，第二个参数为函数，主要是将集合中的元素和初始值进行整合
     */
    @Test
    public void reduceTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer reduce = integerList.stream().reduce(0, Integer::sum);
        System.out.println(reduce);

        List<CostInfo> costInfoList = Arrays.asList(new CostInfo(1, new BigDecimal("1.02")),
                new CostInfo(2, new BigDecimal("311.0")),
                new CostInfo(1, new BigDecimal("315.01")));
        //根据id进行分组之后，获取map的value值将数据整合到集合中返回
        List<CostInfo> infoList = costInfoList.stream().collect(Collectors.groupingBy(CostInfo::getId))
                .values().stream().reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                });
        System.out.println(infoList);
    }

    /**
     * 获取出集合中对应的元素构成intStream，并对该流进行操作
     * 注意：该流的类型已经确定为int类型流，无法改变
     */
    @Test
    public void mapToIntTest() {
        List<CostInfo> costInfoList = Arrays.asList(new CostInfo(1, new BigDecimal("1.02")), new CostInfo(2, new BigDecimal("311.0")));
        int sum = costInfoList.stream().mapToInt(CostInfo::getId).sum();
        System.out.println(sum);
    }

    /**
     * peek为中间操作，stream中如果没有终止操作则中间操作不会执行，即lazy加载
     * peek中的函数为消费者函数无返回值，主要用于对stream流中的数据进行修改；map为function函数需要有返回值
     * stream的其他操作不会对stream中的元素进行修改，主要用于获取和过滤、分组等
     * stream的foreach操作不是终止操作则peek中的操作不会生效
     */
    @Test
    public void peekTest() {
        List<CostInfo> costInfoList = Arrays.asList(new CostInfo(), new CostInfo(), new CostInfo());
        List<CostInfo> infoList = costInfoList.stream().peek(s -> s.setId(1)).peek(s -> s.setCost(new BigDecimal("101"))).collect(Collectors.toList());
        System.out.println(infoList);
        //这两个打印操作都会执行
        List<CostInfo> collect = infoList.stream().peek(System.out::println).collect(Collectors.toList());
        System.out.println(collect);

        //不会执行，forEach不是终止操作
        Stream.of("one", "two", "three").peek(String::toUpperCase).forEach(System.out::println);

        List<CostInfo> costInfos = new ArrayList<>();

        costInfos.add(new CostInfo(1, new BigDecimal("3.21")));
        costInfos.add(new CostInfo(2, new BigDecimal("5.25")));

        List<CostInfo> infos = costInfos.stream().peek(s -> {
            if (s.getId() == 1) {
                s.setId(3);
            }
        }).collect(Collectors.toList());

        System.out.println("获取的数据：" + infos);
    }


}
