package com.hanyi.daily.load;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import com.hanyi.daily.pojo.CostInfo;
import com.hanyi.daily.pojo.Person;
import com.hanyi.daily.thread.pojo.Accumulator;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

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
     * range(): 生成指定范围内的数值流，不包括结束值
     * rangeClosed(): 生成指定范围内的数值流，包括结束值
     * boxed(): 将原始流转换为一般流
     */
    @Test
    public void intStreamTest() {
        IntStream.range(0, 10).forEach(System.out::println);
        System.out.println("-----");
        IntStream.rangeClosed(0, 10).forEachOrdered(System.out::println);
        System.out.println(IntStream.rangeClosed(1, 10_000).mapToObj(s -> "a").collect(Collectors.joining(StrUtil.EMPTY)));
    }

    /**
     * flatMap 主要是将集合中的元素进一步细化打平；builder()方法用于构建Stream对象，使用较少
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

        Map<String, List<Integer>> integerMap = new HashMap<>();
        integerMap.put("哈哈哈", Arrays.asList(1, 2));
        integerMap.put("看看看", Arrays.asList(2, 3));
        integerMap.put("啊啊啊", Arrays.asList(3, 4));

        Set<Integer> valueSet = integerMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        System.out.println(valueSet);

        List<String> temp = Stream.of("b, c", "a", "  ", "a, c")
                .filter(StrUtil::isNotBlank)
                .map(val -> val.split(StrUtil.COMMA))
                .flatMap(Arrays::stream)
                .map(String::trim)
                .collect(Collectors.toList());
        System.out.println(temp);
    }

    /**
     * flatMapToInt中遍历的是具体的元素，但是最后必须返回IntStream
     */
    @Test
    public void flatMapIntTest() {
        Map<String, List<Integer>> listMap = new HashMap<>();
        listMap.put("1", Arrays.asList(1, 2));
        listMap.put("2", Arrays.asList(3, 4));
        listMap.put("3", Arrays.asList(5, 6));

        //将数据结构打平之后再将stream<Integer>转换成IntStream
        int total = listMap.values().stream().flatMap(Collection::stream).mapToInt(Integer::intValue).sum();
        //21
        System.out.println(total);

        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "哈士奇"));
        personList.add(new Person(2, "柯基"));
        personList.add(new Person(3, "柴犬"));

        int count = personList.stream().flatMapToInt(person -> IntStream.of(person.getId())).sum();
        int sum = personList.stream().mapToInt(Person::getId).sum();
        //6||6
        System.out.println(count + "||" + sum);
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
     * 集合属性映射操作,Collectors.mapping()方法可使用map()函数代替
     */
    @Test
    public void mapTest() {
        List<CostInfo> costInfoList = new ArrayList<>();
        costInfoList.add(new CostInfo(new BigDecimal("12.02")));
        costInfoList.add(new CostInfo(new BigDecimal("32.01")));

        List<String> collect = costInfoList.stream().map(CostInfo::getCost).map(BigDecimal::toString).collect(Collectors.toList());
        System.out.println(collect);

        List<String> stringList = costInfoList.stream().map(s -> s.getCost().toString()).collect(Collectors.toList());
        System.out.println(stringList);
    }

    /**
     * reduce的第一个参数为初始值，第二个参数为函数，主要是将集合中的元素和初始值进行整合
     * collectingAndThen把第一个参数的结果，交给第二个参数Function函数的参数里面
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
        //求和
        BigDecimal bigDecimal = costInfoList.stream().map(CostInfo::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        //根据id去重，将新创建的treeSet放入到ArrayList的有参构造函数中
        List<CostInfo> costInfos = costInfoList.stream().collect(collectingAndThen(
                toCollection(() -> new TreeSet<>(comparingLong(CostInfo::getId))), ArrayList::new));

        System.out.println(infoList);
        System.out.println(bigDecimal);
        System.out.println(costInfos);

        //将元素用逗号拼接之后再进行大写
        String collect = Stream.of("aaa", "bbb", "ccc")
                .collect(collectingAndThen(Collectors.joining(StrUtil.COMMA), String::toUpperCase));
        System.out.println(collect);
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

        //该peek操作不会生效
        List<Integer> integerList = costInfos.stream().map(CostInfo::getId).peek(s -> {
            if (s == 1) {
                s = 4;
            }
        }).collect(Collectors.toList());

        //修改方案
        List<Integer> list = costInfos.stream().peek(s -> {
            if (s.getId() == 3) {
                s.setId(4);
            }
        }).map(CostInfo::getId).collect(Collectors.toList());

        System.out.println("获取的integer集合：" + integerList + "||" + list);
    }

    /**
     * 使用流拼接字符串
     */
    @Test
    public void joiningTest() {
        List<Person> personList = Arrays.asList(new Person(1, "柯基"), new Person(2, "哈士奇"));
        String join = personList.stream().map(Person::getName).collect(Collectors.joining());
        System.out.println("无拼接符：" + join);

        String collect = personList.stream().map(Person::getName)
                .filter(StrUtil::isNotBlank).collect(Collectors.joining(StrUtil.COMMA));
        System.out.println("逗号分隔：" + collect);

        String comma = personList.stream().map(Person::getName)
                .collect(Collectors.joining(StrUtil.COMMA, StrUtil.DELIM_START, StrUtil.DELIM_END));
        System.out.println("拼接符：" + comma);
    }

    /**
     * 分区,该key只能是boolean类型
     */
    @Test
    public void partitioningTest() {
        List<Person> personList = Arrays.asList(new Person(1, "柯基"), new Person(2, "哈士奇"));
        Map<Boolean, List<Person>> listMap = personList.stream().collect(Collectors.partitioningBy(s -> s.getId() < 2));
        System.out.println(listMap);
    }

    /**
     * 统计流中的元素数据，counting()已经被count()方法取代
     */
    @Test
    public void countingTest() {
        Long collect = Stream.of("111", "222", "333").count();
        System.out.println(collect);
    }

    /**
     * mapToInt() 方法可以替代summingInt()方法
     */
    @Test
    public void summingIntTest() {
        Integer sum = Stream.of(11, 22, 33).mapToInt(Integer::intValue).sum();
        System.out.println(sum);

        IntSummaryStatistics collect = Stream.of(11, 22, 33).collect(Collectors.summarizingInt(Integer::intValue));
        //66
        System.out.println(collect.getSum());
        //11
        System.out.println(collect.getMin());
        //33
        System.out.println(collect.getMax());
        //3
        System.out.println(collect.getCount());
        //22.0
        System.out.println(collect.getAverage());
    }

    /**
     * 内存泄漏
     */
    @Test
    public void hashSetTest() {
        Set<Person> personSet = new HashSet<>();

        Person person = new Person(1, "柯基");
        Person p = new Person(2, "哈士奇");

        personSet.add(p);
        personSet.add(person);

        p.setId(3);
        //修改了p对象的属性，则无法再删除该对象
        personSet.remove(p);

        System.out.println(personSet);
        //5秒
        TimeUnit.SECONDS.toMillis(5);
        //减法
        int i = Math.subtractExact(3, 1);
        System.out.println(i);
    }

    /**
     * 并行流和串行流，并行流会为每一个元素创建一线程去进行处理，底层使用ForkJoinPool线程池
     * 并行流的弊端：
     * 1、对于iterate来说，每次应用这个函数都要依赖于前一次应用的结果，而且需要拆装箱，所以会出现性能较低的情况
     * 2、无法解决多线程之间共享变量的修改问题
     * 3、集合元素中如果出现当前元素运算需要依赖上一次应用的结果的情况，无法明显的提高效率，即无法拆分任务
     * <p>
     * 尽量使用LongStream/IntStream/DoubleStream等原始数据流代替Stream来处理数字，以避免频繁拆装箱带来的额外开销
     */
    @Test
    public void parallelStreamTest() {
        TimeInterval timer = DateUtil.timer();

        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "哈士奇"));
        personList.add(new Person(2, "柯基"));
        personList.add(new Person(3, "柴犬"));

        //串行流
        personList.forEach(s -> {
            s.setId(s.getId() * 100);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("串行流的数据：" + personList);
        //耗时：3042
        System.out.println("串行流耗时：" + timer.intervalRestart());

        //并行流
        personList.parallelStream().forEach(s -> {
            s.setId(s.getId() * 100);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("并行流的数据：" + personList);
        //耗时：1548
        System.out.println("并行流耗时：" + timer.intervalRestart());
    }

    /**
     * 并行流计算，使用volatile关键字也无法解决多线程之间共享变量的修改问题，使用原子类可解决
     * parallel()方法是将流修改成并行流，sequential()方法是将流修改成顺序流
     */
    @Test
    public void parallelTest() {
        TimeInterval timer = DateUtil.timer();
        //并行流
        long count = LongStream.rangeClosed(1, 10).parallel().reduce(Long::sum).orElse(0);
        System.out.println(count);
        System.out.println("并行流计算耗时：" + timer.intervalRestart());

        long begin = 10;
        Accumulator accumulator = new Accumulator();
        //串行流
        LongStream.rangeClosed(1, begin).forEach(accumulator::add);
        System.out.println(accumulator.getTotal());
        System.out.println("串行流耗时：" + timer.intervalRestart());

        accumulator.clear();
        //并行流会出现数据不一致的情况
        LongStream.rangeClosed(1, begin).parallel().forEach(accumulator::add);
        System.out.println(accumulator.getTotal());
        System.out.println("并行流耗时：" + timer.intervalRestart());
    }

    /**
     * 将2个流的数据拼接在一起，只能拼接2个流
     */
    @Test
    public void concatTest() {
        List<String> stringList = Arrays.asList("1", "2", "3");
        List<String> strings = Arrays.asList("4", "5", "6");
        Stream.concat(stringList.stream(), strings.stream()).forEach(System.out::println);
    }

    /**
     * tryAdvance进行一次iterate并且判断是否有元素还存在，都是单线程处理
     * forEachRemaing是对剩下的元素都执行action，用法和forEach类似
     */
    @Test
    public void forEachRemainingTest() {
        List<String> list = Arrays.asList("Apple", "Banana", "Orange");
        Spliterator<String> spliterator = list.spliterator();

        boolean tryAdvance = spliterator.tryAdvance(System.out::println);
        System.out.println(tryAdvance);

        System.out.println("-- build traversal");
        //如果不使用tryAdvance方法则和forEach使用方式相同，使用tryAdvance则会处理剩下的元素
        spliterator.forEachRemaining(s -> System.out.println(Thread.currentThread().getName() + "---" + s));

        System.out.println("--- attempting try advance again");
        // false,无剩余属性则不打印元素
        System.out.println(spliterator.tryAdvance(System.out::println));
    }

    /**
     * trySplit虽然可以切割元素，但是依旧是单线程在处理,如果需要多线程处理需要自己创建多个线程进行处理
     */
    @Test
    public void trySplitTest() {
        List<String> list = Arrays.asList("Apple", "Banana", "Orange", "Aggie", "哈士奇");

        Spliterator<String> s = list.spliterator();
        //对集合元素进行切割，s中有3个元素，s1中有2个元素
        Spliterator<String> s1 = s.trySplit();

        s.forEachRemaining(a -> System.out.println(Thread.currentThread().getName() + "---" + a));
        System.out.println("-- traversing the other half of the spliterator --- ");
        s1.forEachRemaining(a -> System.out.println(Thread.currentThread().getName() + "---" + a));
        //4个线程执行
        list.parallelStream().forEach(a -> System.out.println(Thread.currentThread().getName() + "-fork-" + a));
    }

    /**
     * estimateSize(): 用于估算还剩下多少个元素需要遍历
     * getExactSizeIfKnown(): 当迭代器拥有SIZED特征时，返回剩余元素个数；否则返回-1
     * characteristics(): 返回当前对象有哪些特征值,多个特征值的总和
     * hasCharacteristics(): 是否具有当前特征值
     */
    @Test
    public void hasCharacteristicsTest() {
        List<String> list = Arrays.asList("哈士奇", "柯基");
        Spliterator<String> s = list.spliterator();

        if (s.hasCharacteristics(Spliterator.ORDERED)) {
            System.out.println("ORDERED: " + Spliterator.ORDERED);
        }
        if (s.hasCharacteristics(Spliterator.DISTINCT)) {
            System.out.println("DISTINCT: " + Spliterator.DISTINCT);
        }
        if (s.hasCharacteristics(Spliterator.SORTED)) {
            System.out.println("SORTED: " + Spliterator.SORTED);
        }
        if (s.hasCharacteristics(Spliterator.SIZED)) {
            System.out.println("SIZED: " + Spliterator.SIZED);
        }
        if (s.hasCharacteristics(Spliterator.CONCURRENT)) {
            System.out.println("CONCURRENT：" + Spliterator.CONCURRENT);
        }
        if (s.hasCharacteristics(Spliterator.IMMUTABLE)) {
            System.out.println("IMMUTABLE：" + Spliterator.IMMUTABLE);
        }
        if (s.hasCharacteristics(Spliterator.NONNULL)) {
            System.out.println("NONNULL:" + Spliterator.NONNULL);
        }
        if (s.hasCharacteristics(Spliterator.SUBSIZED)) {
            System.out.println("SUBSIZED：" + Spliterator.SUBSIZED);
        }

        System.out.println(s.estimateSize());
        System.out.println(s.getExactSizeIfKnown());
        //返回特性的相加总数
        System.out.println(s.characteristics());
    }

    /**
     * forEachOrdered将并行流中的数据顺序执行
     */
    @Test
    public void forEachOrderedTest() {
        //无需执行
        IntStream.range(0, 10).parallel().forEach(s -> System.out.println(Thread.currentThread().getName() + " || " + s));
        System.out.println("-------------------");
        //顺序执行
        IntStream.range(0, 10).parallel().forEachOrdered(s -> System.out.println(Thread.currentThread().getName() + " || " + s));
    }

    /**
     * 生成任何类型的集合
     */
    @Test
    public void toCollectionTest() {
        List<String> stringList = Arrays.asList("1", "2", "3");
        System.out.println(stringList);
        List<Integer> arrayList = stringList.stream().map(Integer::parseInt).collect(toCollection(ArrayList::new));
        System.out.println(arrayList);
    }

}
