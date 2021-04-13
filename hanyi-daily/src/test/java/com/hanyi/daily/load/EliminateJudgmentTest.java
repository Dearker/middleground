package com.hanyi.daily.load;

import cn.hutool.core.util.StrUtil;
import com.hanyi.daily.pojo.Person;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>
 * 使用表驱动法消除if判断
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
    public void consumerTest(){
        Map<String, Consumer<? super Person>> consumerMap = new HashMap<>();
        consumerMap.put("1", this::buildId);
        consumerMap.put("2", this::buildName);

        Person person = new Person();
        consumerMap.getOrDefault("1", s ->{}).accept(person);
        consumerMap.getOrDefault("2", s ->{}).accept(person);
        consumerMap.getOrDefault("3", s ->{}).accept(person);
        System.out.println(person);
    }

    /**
     * 设置id
     *
     * @param person 用户对象
     */
    private void buildId(Person person){
        person.setId(1);
    }

    /**
     * 设置名称
     *
     * @param person 用户对象
     */
    private void buildName(Person person){
        person.setName("哈哈哈");
    }

    /**
     * 根据参数不同获取用户不同的属性修改后返回
     */
    @Test
    public void functionTest(){
        Map<String, Function<? super Person, Object>> functionMap = new HashMap<>();
        functionMap.put("1", this::changeId);
        functionMap.put("2", this::changeName);

        Person person = new Person(1,"哈哈哈");
        Object apply = functionMap.getOrDefault("1", s -> StrUtil.EMPTY).apply(person);
        System.out.println(apply);
        Object object = functionMap.getOrDefault("2", s -> StrUtil.EMPTY).apply(person);
        System.out.println(object);

        Object other = functionMap.getOrDefault("3", s -> StrUtil.EMPTY).apply(person);
        System.out.println(other);
    }

    private Integer changeId(Person person){
        return person.getId() + 10;
    }

    private String changeName(Person person){
        return person.getName() + "111";
    }


}
