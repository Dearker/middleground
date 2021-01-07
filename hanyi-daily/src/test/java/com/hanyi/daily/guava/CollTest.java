package com.hanyi.daily.guava;

import com.google.common.collect.*;
import com.hanyi.daily.pojo.Person;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * guava集合测试类
 *
 * @author wcwei@iflytek.com
 * @since 2021-01-07 11:25
 */
public class CollTest {

    /**
     * transform方法的使用方式和Stream的map函数类似
     */
    @Test
    public void listsTest() {
        List<Integer> arrayList = Lists.newArrayList(1, 2, 3);
        System.out.println(arrayList);

        // List 笛卡尔乘积
        List<String> list1 = Lists.newArrayList("1", "2");
        List<String> list2 = Lists.newArrayList("A", "B");
        List<List<String>> result = Lists.cartesianProduct(list1, list2);
        //[[1, A], [1, B], [2, A], [2, B]]
        System.out.println(result);

        // List 集合分区
        List<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
        List<List<String>> listList = Lists.partition(list, 2);
        //[[A, B], [C, D], [E]]
        System.out.println(listList);
    }

    /**
     * Set集合测试方法
     */
    @Test
    public void setsTest() {
        Set<Integer> hashSet = Sets.newHashSet(1, 2, 3);
        System.out.println(hashSet);

        Set<String> wordsWithPrimeLength = Sets.newHashSet("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = Sets.newHashSet("two", "three", "five", "seven");

        //求两个集合的交集，并且该set集合不能再添加元素
        Set<String> intersection = Sets.intersection(primes, wordsWithPrimeLength);
        //[two, three, seven]
        System.out.println(intersection);

        //两个集合的差集，并且该set集合不能再添加元素
        Set<String> difference = Sets.difference(wordsWithPrimeLength, primes);
        //[six, one, eight]
        System.out.println(difference);

        Set<String> animals = ImmutableSet.of("gerbil", "hamster");
        Set<Set<String>> animalSets = Sets.powerSet(animals);
        // {{}, {"gerbil"}, {"hamster"}, {"gerbil", "hamster"}}
        System.out.println(animalSets);

        //返回指定大小的集合中所有的组合
        Set<Set<Integer>> setSet = Sets.combinations(hashSet, 2);
        setSet.forEach(System.out::println);
    }

    /**
     * 将集合转换成map
     */
    @Test
    public void mapsAsMapTest() {
        Set<String> hashSet = Sets.newHashSet("1", "2", "3");
        //将Set集合转换成map集合，无法再修改元素
        Map<String, Integer> asMap = Maps.asMap(hashSet, input -> Optional.ofNullable(input).map(Integer::parseInt).orElse(0));
        System.out.println(asMap);

        Map<String, Integer> integerMap = Maps.asMap(hashSet, input -> 0);
        System.out.println(integerMap);

        Map<String, Integer> collect = hashSet.stream().collect(Collectors.toMap(String::toString, Integer::parseInt));
        System.out.println(collect);

        System.out.println("------------------");

        List<String> stringList = Arrays.asList("1", "2", "3");

        Map<String, Integer> immutableMap = Maps.toMap(stringList, input -> Optional.ofNullable(input).map(Integer::parseInt).orElse(0));
        System.out.println(immutableMap);

        ImmutableMap<String, Integer> toMap = Maps.toMap(stringList, input -> 0);
        System.out.println(toMap);

        Map<String, Integer> map = stringList.stream().collect(Collectors.toMap(k -> k, Integer::parseInt));
        System.out.println(map);
        Map<String, Integer> zeroMap = stringList.stream().collect(Collectors.toMap(k -> k, v -> 0));
        System.out.println(zeroMap);
    }

    /**
     * map的转换方法
     */
    @Test
    public void transMapTest() {
        List<Person> arrayList = Lists.newArrayList(new Person(1, "哈士奇"),
                new Person(2, "柯基"),
                new Person(3, "柴犬"));

        Map<String, Person> map = Maps.uniqueIndex(arrayList,
                person -> Optional.ofNullable(person).map(Person::getName).orElse(""));
        System.out.println(map);

        //使用Key和Value作为输入，计算出一个新的Value
        Map<String, Integer> map2 = Maps.transformEntries(map, (s, person) ->
                Optional.ofNullable(person).map(Person::getId).orElse(0));
        System.out.println(map2);

        //使用Function计算出一个新的Value
        Map<String, Integer> map3 = Maps.transformValues(map, person ->
                Optional.ofNullable(person).map(s -> s.getId() + 5).orElse(0));
        System.out.println(map3);
    }

    /**
     * map的filter方法
     */
    @Test
    public void filterMapTest() {
        Map<Integer, String> stringMap = new HashMap<>();
        stringMap.put(1, "哈哈哈");
        stringMap.put(2, "看看看");
        stringMap.put(3, "略略略");

        Map<Integer, String> filterEntries = Maps.filterEntries(stringMap, input -> (input != null ? input.getKey() : 0) > 2);
        System.out.println(filterEntries);

        Map<Integer, String> filterKeys = Maps.filterKeys(stringMap, k ->
                Optional.ofNullable(k).orElse(0) > 1);
        System.out.println(filterKeys);

        Map<Integer, String> filterValues = Maps.filterValues(stringMap, "哈哈哈"::equals);
        System.out.println(filterValues);
    }

}
