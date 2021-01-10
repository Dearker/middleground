package com.hanyi.daily.load.fruit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 水果测试类
 * </p>
 *
 * @author wenchangwei
 * @since 11:10 上午 2021/1/10
 */
public class FruitDemo {

    private static final List<AbstractFruit> FRUIT_LIST = new ArrayList<>();

    static {
        FRUIT_LIST.add(new Apple(30));
        FRUIT_LIST.add(new Apple(61));
        FRUIT_LIST.add(new Watermelon(30));
        FRUIT_LIST.add(new Watermelon(62));
    }

    /**
     * 通过instance判断具体类型
     */
    @Test
    public void instantTest() {
        List<AbstractFruit> abstractFruitList = FRUIT_LIST.stream().filter(this::check).collect(Collectors.toList());
        System.out.println(abstractFruitList);
    }

    /**
     * 根据抽象类简化instance类型判断
     */
    @Test
    public void abstractTest() {
        List<AbstractFruit> abstractFruits = FRUIT_LIST.stream().filter(AbstractFruit::isTasty).collect(Collectors.toList());
        System.out.println(abstractFruits);
    }

    private boolean check(AbstractFruit e) {
        if (e instanceof Apple) {
            if (((Apple) e).isSweet()) {
                return true;
            }
        }
        if (e instanceof Watermelon) {
            return ((Watermelon) e).isJuicy();
        }
        return false;
    }
}
