package com.hanyi.daily.algorithm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 最大路径参数对象
 *
 * @author wcwei@iflytek.com
 * @since 2021-06-02 11:00
 */
@Data
@AllArgsConstructor
public class MaxLineParam {

    /**
     * 起点
     */
    private Character start;

    /**
     * 终点
     */
    private Character end;

    /**
     * 长度
     */
    private int weight;

    /**
     * 最大距离
     */
    private int maxDistance;

}
