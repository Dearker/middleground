package com.hanyi.daily.load;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.*;
import com.hanyi.daily.pojo.Person;
import com.hanyi.daily.pojo.Student;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @PackAge: middleground com.hanyi.daily.load
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-10 10:47
 * @Version: 1.0
 */
public class CollectionTest {

    /**
     * list 和 string的转换
     */
    @Test
    public void stringAndListConvertTest() {

        List<String> stringList = Arrays.asList("111", "222", "333");
        String join = StrUtil.join(StrUtil.COMMA, stringList);

        List<String> strings = Arrays.asList(StrUtil.split(join, StrUtil.COMMA));
        strings.forEach(System.out::println);
    }

    /**
     * ConcurrentHashMap允许一边更新、一边遍历，其他的map实现类则不行
     */
    @Test
    public void concurrentHashMapTest() {

        Map<String, Integer> integerMap = new ConcurrentHashMap<>();
        integerMap.put("a", 1);
        integerMap.put("b", 2);
        integerMap.put("c", 3);

        System.out.println(integerMap.size());
        for (Map.Entry<String, Integer> entry : integerMap.entrySet()) {
            integerMap.remove(entry.getKey());
        }
        System.out.println(integerMap.size());
    }

    /**
     * Multiset为无序可重复集合
     */
    @Test
    public void multisetTest() {

        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("d");

        System.out.println(multiset.size());
        System.out.println(multiset.count("a"));
    }

    /**
     * Multimap中一个key对应着多个value，返回为数组
     */
    @Test
    public void multimapTest() {

        Multimap<String, String> multiMap = ArrayListMultimap.create();

        multiMap.put("hanyi", "柯基");
        multiMap.put("hanyi", "哈士奇");
        multiMap.put("123", "1111");

        System.out.println(multiMap.get("hanyi"));
        System.out.println(multiMap.get("123"));

    }

    /**
     * BiMap 为双向map，可通过value获取key，也可通过key获取value
     * 该集合在key和value都是唯一时使用
     */
    @Test
    public void biMapTest() {

        BiMap<String, String> biMap = HashBiMap.create();

        biMap.put("柯基", "柯基小短腿");
        biMap.put("哈士奇", "无敌哈士奇");

        //通过value获取key
        System.out.println(biMap.inverse().get("柯基小短腿"));
        //通过key获取value
        System.out.println(biMap.get("柯基"));
    }

    /**
     * Table是可支持多个key的map集合
     */
    @Test
    public void tableTest() {

        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("张三", "计算机", 80);
        table.put("张三", "数学", 90);
        table.put("张三", "语文", 70);
        table.put("李四", "计算机", 70);
        table.put("李四", "数学", 60);
        table.put("李四", "语文", 100);

        Set<Table.Cell<String, String, Integer>> cellSet = table.cellSet();

        cellSet.forEach(cell -> System.out.println(cell.getRowKey() + "," + cell.getColumnKey() + "," + cell.getValue()));

        System.out.println("---------------------");

        Set<String> rowSet = table.rowKeySet();
        System.out.println(rowSet);

        System.out.println("---------------------");

        Set<String> columnKeySet = table.columnKeySet();
        System.out.println(columnKeySet);

        System.out.println("---------------------");

        //根据rowKey获取信息Map<column,value>
        System.out.println(table.row("张三"));

        System.out.println("---------------------");

        //根据column获取信息Map<row,value>
        System.out.println(table.column("计算机"));
    }

    /**
     * TreeMap 具有排序功能。它是基于红黑树数据结构实现的，每一个键值对<key, value>都是一个结点，
     * 默认情况下按照key自然排序，另一种是可以通过传入定制的Comparator进行自定义规则排序
     * <p>
     * 底层使用了数组+红黑树实现，线程不安全
     */
    @Test
    public void treeMapTest() {

        TreeMap<Integer, String> treeMap = new TreeMap<>();

        treeMap.put(2, "TWO");
        treeMap.put(1, "ONE");
        System.out.println(treeMap);

        TreeMap<Integer, String> stringTreeMap = new TreeMap<>((a, b) -> Integer.compare(b, a));

        stringTreeMap.put(2, "柯基");
        stringTreeMap.put(1, "哈士奇");
        System.out.println(stringTreeMap);
    }


    /**
     * 原子类增加和减少
     */
    @Test
    public void atomicLongTest() {

        AtomicLong atomicLong = new AtomicLong();
        atomicLong.set(100);
        atomicLong.incrementAndGet();
        atomicLong.decrementAndGet();

        System.out.println(atomicLong.get());
    }

    //算法
    @Test
    public void mapToBean() {

        Map<Integer, String> stringMap = new HashMap<>();
        stringMap.put(1, "柯基");
        stringMap.put(2, "哈士奇");

        AtomicReference<Person> person = new AtomicReference<>();
        List<Person> personList = new ArrayList<>();

        stringMap.forEach((k, v) -> {
            person.set(new Person(k, v));
            personList.add(person.get());
        });

        System.out.println(personList);
    }

    @Test
    public void finalTest() {

        List<Integer> integerList = new ArrayList<>();

        final int length = integerList.size();
        for (int i = 0; i < 3; i++) {
            integerList.add(i);
            System.out.println(length);
            System.out.println(integerList);
        }

        System.out.println("-----------------");

        final AtomicInteger atomicInteger = new AtomicInteger(0);

        for (int i = 0; i < 5; i++) {
            atomicInteger.incrementAndGet();
            System.out.println("atomic的数据：" + atomicInteger.get());
        }
    }

    @Test
    public void listTest() {

        List<Integer> integerList = new ArrayList<>();

        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

        integerList.retainAll(Arrays.asList(1, 3));
        integerList.removeIf(s -> s.equals(1));
        integerList.removeIf(Objects::isNull);

        integerList.replaceAll(s -> {
            if (s == 3) {
                s = 5;
            }
            return s;
        });

        System.out.println(integerList);
    }

    /**
     * 如果key的值在map中不存在，则将后面表达式中的值作为value和key一起存到map中；
     * 如果key存在，则将value的值返回，并不进行任何操作
     */
    @Test
    public void mapComputeIfAbsentTest() {

        Map<String, Integer> stringMap = new HashMap<>();
        stringMap.put("3", 1);
        stringMap.put("4", 2);

        stringMap.computeIfAbsent("5", k -> Integer.valueOf(k + 6));
        System.out.println(stringMap);

        Map<String, List<Integer>> listMap = new HashMap<>();
        listMap.put("1", Collections.singletonList(3));
        listMap.put("2", Collections.singletonList(4));

        listMap.computeIfAbsent("3", k -> new ArrayList<>()).add(12);
        listMap.computeIfAbsent("3", k -> new ArrayList<>()).add(13);
        listMap.computeIfAbsent("3", k -> new ArrayList<>()).add(14);

        System.out.println(listMap);

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("1", "柯基", 10));
        studentList.add(new Student("2", "柴犬", 15));
        studentList.add(new Student("1", "哈士奇", 12));
        studentList.add(new Student("3", "小短腿", 9));

        Map<String, List<Student>> studentMap = new HashMap<>();
        studentList.forEach(student ->
                studentMap.computeIfAbsent(student.getId(), k -> new ArrayList<>(2)).add(student));

        System.out.println(studentMap);
    }

    /**
     * 如果key的值在map中存在且value不为null时，才对该k，v进行操作；
     * 如果想删除对应的key，则将其value值设置为null即可，如果value的值本身为null则无法删除
     */
    @Test
    public void mapComputeIfPresentTest() {

        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("1", "3");
        stringMap.put("2", "4");
        stringMap.put("3", "5");
        stringMap.put("4", "6");
        stringMap.put("5", null);

        System.out.println(stringMap);

        stringMap.computeIfPresent("3", (k, v) -> v + "哈哈");
        stringMap.computeIfPresent("4", (k, v) -> null);
        stringMap.computeIfPresent("5", (k, v) -> v + "7");

        System.out.println(stringMap);
    }

    /**
     * 不管key值是否存在，都会执行后面的表达式，如果对应key的value值为空，则会进行删除操作
     */
    @Test
    public void mapComputeTest() {

        List<String> stringList = Arrays.asList("1", "2", "3", "1", "4", "1", "2");

        Map<String, Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put("5", null);

        stringList.forEach(s -> stringIntegerMap.compute(s, (k, v) -> Objects.isNull(v) ? 1 : v + 1));
        stringIntegerMap.compute("5", (k, v) -> v);

        System.out.println(stringIntegerMap);
    }

    /**
     * 第一个是所选map的key，第二个是需要合并的值，第三个是进行如何合并
     * Map的merge方法，如果key存在，则相加，如果不存在，则向map中放入元素
     */
    @Test
    public void mapMergeTest() {

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("张三", "男", 18));
        studentList.add(new Student("李四", "男", 20));
        studentList.add(new Student("韩梅梅", "女", 18));
        studentList.add(new Student("小红", "女", 45));

        //按照属性男女分组，然后把年龄汇总
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        studentList.forEach(s -> stringIntegerMap.merge(s.getName(), s.getAge(), Integer::sum));

        System.out.println(stringIntegerMap);

        Map<String, IntSummaryStatistics> collectMap = studentList.stream()
                .collect(Collectors.groupingBy(Student::getName, Collectors.summarizingInt(Student::getAge)));

        Map<String, Long> nameMap = new HashMap<>();
        collectMap.forEach((k, v) -> nameMap.put(k, v.getSum()));

        System.out.println(nameMap);
    }

    /**
     * 给map对应的key设置默认值
     */
    @Test
    public void mapGetOrDefaultTest() {

        Map<String, Integer> map = new HashMap<>(3);
        map.put("john", null);
        map.put("jack", 180);
        map.put("jane", 168);

        // 不会使用提供的默认值
        Integer jane = map.getOrDefault("jane", 166);
        map.put("jane", jane);
        // 会使用提供的默认值
        Integer tom = map.getOrDefault("tom", 166);
        map.put("tom", tom);
        // 不会使用提供的默认值
        Integer john = map.getOrDefault("john", 185);
        map.put("john", john);

        System.out.println(map);
    }

    /**
     * 替换map中key的value的值
     */
    @Test
    public void mapReplaceTest() {

        Map<String, Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put("1", 3);

        stringIntegerMap.replace("1", 5);
        //只有精准匹配到时才会替换，即key和value都相同时
        stringIntegerMap.replace("1", 5, 0);
        stringIntegerMap.replaceAll((k, v) -> v = 1);

        System.out.println(stringIntegerMap);
    }

    /**
     * 使用remove删除元素之后，如果集合中的元素已经为空，则后面的remove不会执行，返回一个空的集合
     */
    @Test
    public void removeMapTest() {

        Map<String, Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put("1", 2);
        stringIntegerMap.put("2", 3);

        stringIntegerMap.remove("1");
        stringIntegerMap.remove("1", 2);
        System.out.println(stringIntegerMap);
    }

    /**
     * 如果key的value不存在则将value放到map中，如果存在则将value返回
     */
    @Test
    public void putIfAbsentMapTest() {

        Map<String, Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put("1", 2);
        stringIntegerMap.putIfAbsent("2", 3);
        stringIntegerMap.putIfAbsent("2", 4);

        System.out.println(stringIntegerMap);
    }

    /**
     * 使用filter过滤之后，如果没有匹配的数据，则返回一个空的数组
     */
    @Test
    public void filterListTest() {

        List<String> stringList = new ArrayList<>();
        stringList.add("1");

        List<String> filterList = stringList.stream().filter(s -> s.equals("2")).collect(Collectors.toList());
        List<String> collect = filterList.stream().map(String::toUpperCase).collect(Collectors.toList());

        System.out.println(filterList);
        System.out.println(collect.size());
    }

    /**
     * 在foreach中创建对象的两种方式
     */
    @Test
    public void foreachTest() {

        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);

        List<Person> personList = new ArrayList<>();
        AtomicReference<Person> personAtomicReference = new AtomicReference<>();
        integerList.forEach(s -> {
            personAtomicReference.set(new Person(s, "刚好"));
            personList.add(personAtomicReference.get());
        });

        final Person[] person = new Person[1];
        integerList.forEach(s -> {
            person[0] = new Person(s, "还不错");
            personList.add(person[0]);
        });

        final Person[] p = new Person[1];
        integerList.forEach(s -> {
            p[0] = new Person();
            p[0].setId(s);
            p[0].setName("柯基");
            personList.add(p[0]);
        });

        personList.forEach(System.out::println);
    }

    /**
     * Guava不可变集合
     */
    @Test
    public void immutableSetTest() {

        // 创建方式1：of
        ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b", "c");
        immutableSet.forEach(System.out::println);

        // 创建方式2：builder
        ImmutableSet<String> immutableSet2 = ImmutableSet.<String>builder()
                .add("hello")
                .add("未读代码")
                .build();
        immutableSet2.forEach(System.out::println);

        // 创建方式3：从其他集合中拷贝创建
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("www.wdbyte.com");
        arrayList.add("https");
        ImmutableSet<String> immutableSet3 = ImmutableSet.copyOf(arrayList);
        immutableSet3.forEach(System.out::println);

        // JDK Collections 创建不可变 List
        List<String> list = Collections.unmodifiableList(arrayList);
        list.forEach(System.out::println);

    }

    /**
     * 创建集合
     */
    @Test
    public void collectionNewTest() {
        HashSet<Integer> integerHashSet = Sets.newHashSet(1, 2, 3);
        ArrayList<Integer> arrayList = Lists.newArrayList(1, 2, 3);
        System.out.println(integerHashSet);
        System.out.println(arrayList);
    }

    /**
     * 处理集合中的交并集
     */
    @Test
    public void unionTest() {

        Set<String> newHashSet1 = Sets.newHashSet("a", "a", "b", "c");
        Set<String> newHashSet2 = Sets.newHashSet("b", "b", "c", "d");

        // 交集
        Sets.SetView<String> intersectionSet = Sets.intersection(newHashSet1, newHashSet2);
        System.out.println(intersectionSet); // [b, c]

        // 并集
        Sets.SetView<String> unionSet = Sets.union(newHashSet1, newHashSet2);
        System.out.println(unionSet); // [a, b, c, d]

        // newHashSet1 中存在，newHashSet2 中不存在
        Sets.SetView<String> setView = Sets.difference(newHashSet1, newHashSet2);
        System.out.println(setView); // [a]

    }

    @Test
    public void stringListTest() {
        String str = ",a,,b,";
        String[] splitArr = str.split(",");
        Arrays.stream(splitArr).filter(StrUtil::isNotBlank).forEach(System.out::println);
    }

    /**
     * 将动态数组中的容量调整为数组中的元素个数
     * ensureCapacity(): 用于指定集合的容量大小，以减少扩容的次数
     */
    @Test
    public void trimToSizeTest(){
        // 创建一个动态数组
        ArrayList<String> sites = new ArrayList<>();

        sites.add("Google");
        sites.add("Runoob");
        sites.add("Taobao");

        System.out.println("ArrayList : " + sites + "---" + sites.size());

        // 调整容量为3
        sites.trimToSize();
        sites.ensureCapacity(3);
        System.out.println("ArrayList 大小: " + sites.size());
    }

    @Test
    public void linkedListTest(){

        LinkedList<String> linkedList = new LinkedList<>();



    }

    @Test
    public void concurrentHashMap01Test(){

        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    }

    @Test
    public void treeMap01Test(){

    }

}
