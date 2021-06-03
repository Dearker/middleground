package com.hanyi.daily.algorithm.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 自定义长度参数
 *
 * @author wcwei@iflytek.com
 * @since 2021-06-03 9:51
 */
@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class CustomizeLengthParam {

    /**
     * 步长
     */
    private int stepLength;

    /**
     * 终点
     */
    private String originNode;

    /**
     * 循环总数
     */
    private int cycleCount;

    /**
     * 下个节点
     */
    private String nextNode;

    /**
     * 缓存的路线
     */
    private String bufferLine;

    /**
     * 结果路线集合
     */
    private List<String> resultLineList;

}
