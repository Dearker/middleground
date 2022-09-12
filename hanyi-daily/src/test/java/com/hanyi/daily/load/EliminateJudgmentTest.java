package com.hanyi.daily.load;

import cn.hutool.core.util.StrUtil;
import com.hanyi.daily.common.util.LambdaUtil;
import com.hanyi.daily.pojo.Person;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>
 * 使用表驱动法消除if判断，静态方法则使用类名点的方式代替
 * </p>
 *
 * @author wenchangwei
 * @since 10:40 下午 2021/4/13
 */
public class EliminateJudgmentTest {

    /**
     * 根据条件判断设置对应的用户属性
     */
    @Test
    public void consumerTest() {
        Map<String, Consumer<? super Person>> consumerMap = new HashMap<>();
        consumerMap.put("1", this::buildId);
        consumerMap.put("2", this::buildName);

        Person person = new Person();
        consumerMap.getOrDefault("1", s -> {
        }).accept(person);
        consumerMap.getOrDefault("2", s -> {
        }).accept(person);
        consumerMap.getOrDefault("3", s -> {
        }).accept(person);
        System.out.println(person);
    }

    /**
     * 设置id
     *
     * @param person 用户对象
     */
    private void buildId(Person person) {
        person.setId(1);
    }

    /**
     * 设置名称
     *
     * @param person 用户对象
     */
    private void buildName(Person person) {
        person.setName("哈哈哈");
    }

    /**
     * 根据参数不同获取用户不同的属性修改后返回
     */
    @Test
    public void functionTest() {
        Map<String, Function<? super Person, Object>> functionMap = new HashMap<>();
        functionMap.put("1", this::changeId);
        functionMap.put("2", this::changeName);

        Person person = new Person(1, "哈哈哈");
        Object apply = functionMap.getOrDefault("1", s -> StrUtil.EMPTY).apply(person);
        System.out.println(apply);
        Object object = functionMap.getOrDefault("2", s -> StrUtil.EMPTY).apply(person);
        System.out.println(object);

        Object other = functionMap.getOrDefault("3", s -> StrUtil.EMPTY).apply(person);
        System.out.println(other);
    }

    private Integer changeId(Person person) {
        return person.getId() + 10;
    }

    private String changeName(Person person) {
        return person.getName() + "111";
    }

    /**
     * Supplier<T>接口无参数，有返回值
     */
    @Test
    public void supplierTest() {
        Map<String, Supplier<Object>> supplierMap = new HashMap<>();
        Person person = new Person(1, "哈哈哈");

        supplierMap.put("1", () -> person.getId() + 10);
        supplierMap.put("2", () -> person.getName() + "柯基");

        Object o = supplierMap.getOrDefault("1", () -> StrUtil.EMPTY).get();
        System.out.println(o);
        Object name = supplierMap.getOrDefault("2", () -> StrUtil.EMPTY).get();
        System.out.println(name);
        Object other = supplierMap.getOrDefault("3", () -> StrUtil.EMPTY).get();
        System.out.println(other);
    }

    /**
     * Predicate<T>断言接口，有参数，返回值必须为boolean类型
     */
    @Test
    public void predicateTest() {
        Map<String, Predicate<? super Person>> predicateMap = new HashMap<>();
        predicateMap.put("1", this::predicateId);
        predicateMap.put("2", this::predicateName);

        Person person = new Person(1, "哈哈哈");
        boolean testId = predicateMap.getOrDefault("1", s -> false).test(person);
        System.out.println(testId);
        boolean testName = predicateMap.getOrDefault("2", s -> false).test(person);
        System.out.println(testName);
        boolean testOther = predicateMap.getOrDefault("3", s -> false).test(person);
        System.out.println(testOther);
    }

    private boolean predicateId(Person person) {
        return person.getId() != 0;
    }

    private boolean predicateName(Person person) {
        return person.getName().isEmpty();
    }

    @Test
    public void predicateConsumerTest() {
        Person person = new Person(2, "哈哈哈");
        LambdaUtil.of(person).predicateConsumer(p -> p.getId() == 1, p -> p.setId(2), p -> p.setName("看看看"));
        System.out.println(person);
        LambdaUtil.of().nullPredicateConsumer(Objects::isNull, p -> System.out.println(1111), p -> System.out.println(2222));
    }

    @Test
    public void predicateFunctionTest() {
        Person person = new Person(2, "哈哈哈");
        LambdaUtil<String> function = LambdaUtil.of(person).predicateFunction(p -> p.getId() == 1,
                p -> p.getId() + " ||", Person::getName);
        System.out.println(function);
    }

    @Test
    public void predicateSupplierTest() {
        Person person = new Person(1, "哈哈哈");
        LambdaUtil<Person> supplier = LambdaUtil.of(person).predicateSupplier(p -> p.getId() == 1, () -> person, () -> null);
        System.out.println(supplier);
    }

}
