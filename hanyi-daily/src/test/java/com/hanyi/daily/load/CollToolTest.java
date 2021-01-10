package com.hanyi.daily.load;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.map.MapUtil;
import com.hanyi.daily.pojo.Person;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * HuTool集合测试类
 * </p>
 *
 * @author wenchangwei
 * @since 8:31 下午 2021/1/8
 */
public class CollToolTest {

    @Test
    public void CollUtilTest() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, "柴犬"));
        personList.add(new Person(2, "哈士奇"));
        personList.add(new Person(3, "柯基"));

        Map<Integer, String> stringMap = personList.stream().collect(Collectors.toMap(Person::getId, Person::getName));

        List<Integer> integerList = Arrays.asList(1, 2);
        List<String> arrayList = CollUtil.valuesOfKeys(stringMap, integerList);
        //[柴犬, 哈士奇]
        System.out.println(arrayList);
    }

    @Test
    public void mapUtilTest() {
        Map<String, Integer> integerMap = new HashMap<>();
        integerMap.put("123", 1);
        integerMap.put("1234", 2);
        integerMap.put("12345", 3);

        List<String> stringList = Arrays.asList("123", "1234");
        Map<String, Integer> filterMap = MapUtil.filter(integerMap, stringList.toArray(new String[0]));
        System.out.println(filterMap);

        Map<String, Integer> filter = MapUtil.filter(integerMap, (Filter<Map.Entry<String, Integer>>) entry -> entry.getValue() > 1);
        System.out.println(filter);

        integerMap.put("12", null);
        System.out.println(integerMap);

        //删除值为null的键值对
        MapUtil.removeNullValue(integerMap);
        System.out.println(integerMap);

        //重新命名key的名称
        MapUtil.renameKey(integerMap, "123", "哈哈");
        System.out.println(integerMap);
    }

}
