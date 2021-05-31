package com.hanyi.daily.algorithm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 村庄路线实体类
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/26 10:30 下午
 */
@Data
@AllArgsConstructor
public class VillageLine {

    /**
     * 开始节点
     */
    private char startVillage;

    /**
     * 结束节点
     */
    private char originVillage;

    /**
     * 间距
     */
    private int weight;

    /**
     * 是否访问过
     */
    private boolean visited;

    public VillageLine(char startVillage, char originVillage, int weight) {
        this(startVillage, originVillage, weight, false);
    }
}
