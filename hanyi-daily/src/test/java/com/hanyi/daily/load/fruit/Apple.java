package com.hanyi.daily.load.fruit;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 苹果具体实现
 * </p>
 *
 * @author wenchangwei
 * @since 10:55 上午 2021/1/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Apple extends AbstractFruit {

    private final int sweetDegree;

    public Apple(int sweetDegree) {
        this.sweetDegree = sweetDegree;
    }

    /**
     * 水果是否甜
     *
     * @return 返回判断结果
     */
    public boolean isSweet() {
        return sweetDegree > 60;
    }

    /**
     * 水果是否好吃
     *
     * @return 返回结果
     */
    @Override
    public boolean isTasty() {
        return sweetDegree > 60;
    }

}
