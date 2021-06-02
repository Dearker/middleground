package com.hanyi.daily.algorithm.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.TreeMap;

/**
 * <p>
 * 最短路径参数对象
 * </p>
 *
 * @author wenchangwei
 * @since 2021/6/2 9:58 下午
 */
@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class ShortLineParam {

    /**
     * 开始节点
     */
    private String startNode;

    /**
     * 结束节点
     */
    private String originNode;

    /**
     * 缓存的路线
     */
    private String bufferLine;

    /**
     * 路线长度和路径集合
     */
    private TreeMap<Integer, List<String>> resultMap;

}
