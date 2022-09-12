package com.hanyi.daily.load;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.daily.pojo.CostInfo;
import com.hanyi.daily.pojo.Person;
import com.hanyi.daily.pojo.Student;
import com.hanyi.daily.pojo.StudentSort;
import com.hanyi.daily.thread.pojo.Accumulator;
import com.hanyi.framework.exception.BizException;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.*;

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
     * 在获取第一个集合中一个元素时，然后根据该元素的字段过滤出另一个集合中的一个元素并获取其相应的字段时，
     * 可使用flatMap直接获取，不需要多次使用Optional#orElse()
     * <p>
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

        List<String> stringList = Arrays.asList("1", "2", "3");
        List<Integer> integerList = Arrays.asList(1, 4, 5);

        Integer integer = stringList.stream().min(Comparator.naturalOrder())
                .flatMap(s -> integerList.stream().filter(a -> a.toString().equals(s)).findFirst())
                .orElse(null);
        System.out.println("两个optional获取对象中的元素可以直接使用flatMap：" + integer);
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
        costInfoList.add(new CostInfo(BigDecimal.valueOf(12.02)));
        costInfoList.add(new CostInfo(BigDecimal.valueOf(32.04)));

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
     * 分组统计
     */
    @Test
    public void groupingByTest() {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<Student> studentList = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(s -> {
            int i = s % 3;
            String randomString = RandomUtil.randomString(2);
            int randomInt = RandomUtil.randomInt(20, 30);
            LocalDateTime dateTime = localDateTime.plusMonths(randomInt);
            double randomDouble = RandomUtil.randomDouble(3.5, 20.6, 2, RoundingMode.UP);
            studentList.add(new Student(randomString, "柯基--" + i, randomInt, randomDouble, dateTime));
        });

        System.out.println(studentList);

        Map<String, List<LocalDateTime>> dateTimeMap = studentList.stream().sorted(Comparator.comparing(Student::getCreateTime))
                .collect(groupingBy(Student::getName, mapping(Student::getCreateTime, toList())));
        System.out.println("时间排序后的集合：" + dateTimeMap);

        Map<String, Long> stringLongMap = studentList.stream().collect(groupingBy(Student::getName, counting()));
        System.out.println("根据学生名称进行分组统计各自的总个数：" + stringLongMap);

        Map<String, Double> stringDoubleMap = studentList.stream().collect(
                Collectors.groupingBy(Student::getName, Collectors.summingDouble(Student::getTotalPrice)));
        System.out.println("根据学生名称分组并统计各自的总金额：" + stringDoubleMap);

        //默认是从小到大排序
        List<Map.Entry<String, Double>> entryList = studentList.stream()
                .collect(Collectors.groupingBy(Student::getName, Collectors.summingDouble(Student::getTotalPrice)))
                .entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).collect(toList());
        System.out.println("根据学生名称分组并根据总金额从大到小排序：" + entryList);

        //以下两种方式功能相同
        Map<String, Student> studentMap = studentList.stream().collect(Collectors.groupingBy(Student::getName,
                collectingAndThen(Collectors.maxBy(Comparator.comparingDouble(Student::getTotalPrice)), Optional::get)));

        Map<String, Student> stringStudentMap = studentList.stream().collect(toMap(Student::getName,
                Function.identity(), BinaryOperator.maxBy(Comparator.comparingDouble(Student::getTotalPrice))));
        System.out.println(stringStudentMap);
        System.out.println("根据学生名称分组并获取金额最大的学生：" + studentMap);

        Map<String, Double> doubleMap = studentList.stream().collect(groupingBy(Student::getName,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingDouble(Student::getTotalPrice)),
                        s -> s.map(Student::getTotalPrice).orElse(null))));

        System.out.println("根据名称分组并获取最大的金额：" + doubleMap);

        Map<String, Double> avgDoubleMap = studentList.stream().collect(Collectors.groupingBy(Student::getName,
                Collectors.averagingInt(Student::getAge)));
        System.out.println("根据名称分组并获取年龄的平均数：" + avgDoubleMap);

        Map<YearMonth, List<String>> yearMonthListMap = studentList.stream().collect(Collectors.groupingBy(s -> {
            LocalDateTime createTime = s.getCreateTime();
            return YearMonth.of(createTime.getYear(), createTime.getMonthValue());
        }, Collectors.mapping(Student::getId, toList())));

        System.out.println("根据学生创建时间年月分组，并统计学生id列表 " + yearMonthListMap);
    }

    /**
     * 参数含义分别是：
     * keyMapper：Key 的映射函数
     * valueMapper：Value 的映射函数
     * mergeFunction：当 Key 冲突时，调用的合并方法
     * mapSupplier：Map 构造器，在需要返回特定的 Map 时使用
     */
    @Test
    public void toMapTest() {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<Student> studentList = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(s -> {
            int i = s % 3;
            LocalDateTime dateTime = localDateTime.plusMonths(i);
            String randomString = RandomUtil.randomString(2);
            int randomInt = RandomUtil.randomInt(20, 30);
            double randomDouble = RandomUtil.randomDouble(3.5, 20.6, 2, RoundingMode.UP);
            studentList.add(new Student(randomString, "柯基--" + i, randomInt, randomDouble, dateTime));
        });

        System.out.println(studentList);

        Map<String, Double> groupDoubleMap = studentList.stream().collect(groupingBy(Student::getName,
                collectingAndThen(maxBy(Comparator.comparingDouble(Student::getTotalPrice)),
                        s -> s.map(Student::getTotalPrice).orElse(0.0))));

        System.out.println("根据名称分组并获取最大的金额：" + groupDoubleMap);

        Map<String, Double> doubleMap = studentList.stream().collect(toMap(Student::getName,
                Student::getTotalPrice, Double::sum));
        System.out.println("根据名称分组并统计总金额：" + doubleMap);

        //该方式适用于所有的比较场景，如果为基本类型则可以使用比较器进行处理
        Map<String, Double> stringDoubleMap = studentList.stream().collect(toMap(Student::getName, Student::getTotalPrice,
                (oldValue, newValue) -> oldValue > newValue ? oldValue : newValue));

        System.out.println("根据名称进行分组并获取最大的总金额：" + stringDoubleMap);

        Map<String, Double> compareDoubleMap = studentList.stream().collect(toMap(Student::getName, Student::getTotalPrice,
                BinaryOperator.maxBy(Comparator.comparingDouble(Double::doubleValue))));
        System.out.println("根据名称分组并根据比较器获取最大的金额：" + compareDoubleMap);

        TreeMap<String, Double> treeMap = studentList.stream().collect(toMap(Student::getName, Student::getTotalPrice,
                (a, b) -> b, TreeMap::new));
        System.out.println("创建指定类型的map集合：" + treeMap);
    }

    /**
     * list转map并完成排序,map的key默认是根据hashCode值的大小进行排序，values是直接获取排序后键的值
     * toMap()函数构建的map，value不能为null，否则会空指针
     * <p>
     * HashMap键或者值都可以null，只不过允许一个key为null，多个value为null，key相同覆盖先前的值
     * TreeMap的value可以是null，（没有自定义比较器）key不能为null，自定义比较器后可以为null，
     * 该比较器需要对null进行特殊处理。
     */
    @Test
    public void toMapSortTest() {
        int size = 10;
        List<CostInfo> costInfoList = new ArrayList<>(size);
        IntStream.range(0, size).forEach(s ->
                costInfoList.add(new CostInfo(RandomUtil.randomInt(0, 100), null)));

        //消除toMap()函数value为空的方式一：
        Map<Integer, BigDecimal> bigDecimalMap = new HashMap<>(size);
        costInfoList.forEach(s -> bigDecimalMap.put(s.getId(), s.getCost()));
        System.out.println(bigDecimalMap);

        //方式二：推荐方式一，方式二可能需要创建2个hashMap来添加合并最后的结果
        Map<Integer, BigDecimal> hashMap = costInfoList.stream().collect(HashMap::new,
                (n, v) -> n.put(v.getId(), v.getCost()), HashMap::putAll);
        System.out.println(hashMap);

        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "哈哈哈"));
        personList.add(new Person(3, "看看看"));
        personList.add(new Person(5, "略略略"));
        personList.add(new Person(2, "去去去"));
        personList.add(new Person(4, "嗷嗷嗷"));

        Map<Integer, String> personMap = personList.stream().collect(toMap(Person::getId, Person::getName));
        System.out.println(personMap);
        System.out.println(personMap.keySet());
        System.out.println(personMap.values());
    }

    /**
     * map集合过滤
     */
    @Test
    public void mapFilterTest() {
        Map<Integer, String> stringMap = new HashMap<>();
        stringMap.put(1, "哈哈哈");
        stringMap.put(2, "哈哈哈222");
        stringMap.put(3, "哈哈哈333");
        stringMap.put(4, "哈哈哈444");

        //该方式不会修改原有的map，并创建一个新的map集合
        Map<Integer, String> filterMap = stringMap.keySet().stream().filter(s -> !s.equals(1))
                .collect(toMap(Function.identity(), stringMap::get));

        System.out.println("过滤后的map：" + filterMap);
        System.out.println(stringMap);

        //以下两种方式直接修改map集合本身
        stringMap.keySet().removeIf(s -> s.equals(1));
        System.out.println(stringMap);

        stringMap.values().removeIf(s -> s.equals("哈哈哈222"));
        System.out.println(stringMap);

        stringMap.keySet().removeIf(s -> s.equals(4) && stringMap.get(s).equals("哈哈哈444"));
        System.out.println("测试4: " + stringMap);

        // map.forEach中不能直接或间接调用map.remove方法，会报错
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        //调用computeIfPresent和compute方法将key对应的value设置为null都会自动删除对应的元素
        integerList.forEach(s -> stringMap.computeIfPresent(s, (k, v) -> v = null));

        System.out.println(stringMap);
    }

    /**
     * 计算处理测试
     */
    @Test
    public void reducingTest() {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<Student> studentList = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(s -> {
            int i = s % 3;
            LocalDateTime dateTime = localDateTime.plusMonths(i);
            String randomString = RandomUtil.randomString(2);
            int randomInt = RandomUtil.randomInt(20, 30);
            double randomDouble = RandomUtil.randomDouble(3.5, 20.6, 2, RoundingMode.UP);
            studentList.add(new Student(randomString, "柯基--" + i, randomInt, randomDouble, dateTime));
        });

        System.out.println(studentList);

        Map<String, Optional<Integer>> optionalMap = studentList.stream().collect(groupingBy(Student::getName,
                mapping(Student::getAge, reducing((a, b) -> {
                    System.out.println(a + " || " + b);
                    a += b;
                    return a;
                }))));
        System.out.println(optionalMap);

        Map<String, Integer> integerMap = studentList.stream().collect(groupingBy(Student::getName,
                mapping(Student::getAge, reducing(0, (a, b) -> {
                    System.out.println(a + " || " + b);
                    a += b;
                    return a;
                }))));
        System.out.println("指定初始值进行计算：" + integerMap);

        Map<String, Integer> stringIntegerMap = studentList.stream().collect(groupingBy(Student::getName,
                reducing(0, Student::getAge, (a, b) -> {
                    System.out.println(a + " || " + b);
                    a += b;
                    return a;
                })));
        System.out.println("指定初始值并且指定字段进行计算：" + stringIntegerMap);
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

    /**
     * 排序接口测试
     * <p>
     * 实现了Comparable接口的实体类可直接使用比较器进行排序
     * <p>
     * 不能直接使用thenComparing()方法，该类型方法不是静态方法；
     * comparing()方法可以直接使用，该类型的方法为静态方法
     */
    @Test
    public void comparatorTest() {
        LocalDateTime localDateTime = LocalDateTime.now();

        List<Student> studentList = new ArrayList<>();
        IntStream.rangeClosed(1, 3).forEach(s -> {
            int i = s % 3;
            LocalDateTime dateTime = localDateTime.plusMonths(i);
            int randomInt = RandomUtil.randomInt(20, 30);
            double randomDouble = RandomUtil.randomDouble(3.5, 20.6, 2, RoundingMode.UP);
            studentList.add(new Student(null, "柯基--" + i, randomInt, randomDouble, dateTime));
        });

        studentList.forEach(System.out::println);
        System.out.println("--------------------------");

        //单字段排序
        studentList.sort(Comparator.comparing(Student::getName));
        System.out.println("按名称升序排序：" + studentList);

        studentList.sort(Comparator.comparing(Student::getName).reversed());
        System.out.println("按名称降序排序：" + studentList);

        //多字段排序
        studentList.sort(Comparator.comparing(Student::getName).thenComparingInt(Student::getAge));
        System.out.println("按名称升序然后年龄升序排序：" + studentList);

        studentList.sort(Comparator.comparing(Student::getName).thenComparing(Student::getAge, Comparator.reverseOrder()));
        System.out.println("按名称升序然后年龄降序排序：" + studentList);

        //如下比较器里面的元素不能为空
        List<Integer> integerList = Arrays.asList(1, 3, 2, 5, 4);
        integerList.sort(Comparator.naturalOrder());
        System.out.println("自然升序排序：" + integerList);

        integerList.sort(Comparator.reverseOrder());
        System.out.println("自然降序排序：" + integerList);

        //如下比较器里面的元素可以为空
        List<Integer> integers = Arrays.asList(1, null, 3, null, 2, 5, 4);
        integers.sort(Comparator.nullsFirst(Integer::compareTo));
        System.out.println("将空元素放在前排：" + integers);

        integers.sort(Comparator.nullsLast(Integer::compareTo));
        System.out.println("将空元素放在后排：" + integers);

        //实现了Comparable接口的排序
        List<StudentSort> studentSortList = new ArrayList<>(3);
        studentSortList.add(new StudentSort(2, "哈哈哈"));
        studentSortList.add(new StudentSort(1, "看看看"));
        studentSortList.add(new StudentSort(3, "略略略"));

        studentSortList.sort(Comparator.naturalOrder());
        System.out.println("自然升序排序：" + studentSortList);

        studentSortList.sort(Comparator.reverseOrder());
        System.out.println("自然降序排序：" + studentSortList);
    }

    /**
     * 抛出异常测试，异常之后的代码不会再执行
     */
    @Test
    public void throwTest() {
        //Optional.ofNullable(null).orElseThrow(BizException::new);
        StudentSort studentSort = new StudentSort();
        try {
            //该代码会直接报错
            Optional.empty().orElseThrow(BizException::new);
            Optional.ofNullable(studentSort).filter(s -> Objects.nonNull(s.getId()))
                    .orElseThrow(() -> new BizException("空指针"));
            //该代码不会执行
            System.out.println("111111111");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void predicateTest(){
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> andList = integerList.stream().filter(((Predicate<Integer>) integer -> integer == 1)
                .and(a -> a == 2)).collect(toList());
        //1,2
        System.out.println("andList: " + andList);
        //(integer == 1 && integer == 2) || integer == 3
        List<Integer> filterList = integerList.stream().filter(((Predicate<Integer>) integer -> integer == 1)
                .and(integer -> integer == 2)
                .or(integer -> integer == 3)).collect(toList());
        //3
        System.out.println(filterList);
    }

    @Test
    public void consumerTest(){
        StudentSort studentSort = new StudentSort(1,"哈哈哈");
        Optional.of(studentSort).ifPresent(((Consumer<StudentSort>) studentSort1 -> studentSort1.setId(2))
                .andThen(studentSort12 -> studentSort12.setName("看看看")));
        System.out.println(studentSort);
    }

}
