package com.hanyi.daily.load.fruit;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 西瓜具体实现
 * </p>
 *
 * @author wenchangwei
 * @since 11:05 上午 2021/1/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Watermelon extends AbstractFruit {

    private final int waterDegree;

    public Watermelon(int waterDegree) {
        this.waterDegree = waterDegree;
    }

    /**
     * 水果是否多汁
     *
     * @return 返回结果
     */
    public boolean isJuicy() {
        return waterDegree > 60;
    }

    /**
     * 水果是否好吃
     *
     * @return 返回结果
     */
    @Override
    public boolean isTasty() {
        return waterDegree > 60;
    }
}
