package com.hanyi.daily.algorithm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 路线参数
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/31 9:45 下午
 */
@Data
@AllArgsConstructor
public class LineParam {

    /**
     * 开始节点索引
     */
    private int startIndex;

    /**
     * 结束节点索引
     */
    private int endIndex;

    /**
     * 用于在dfs中记录访问过的顶点信息
     */
    private int[] visited;

    /**
     * 存储每条可能的路径集合
     */
    private List<Character> pathList;

    /**
     * 所有可能路线集合
     */
    private List<String> allLineList;

    public LineParam(int startIndex, int endIndex, int[] visited) {
        this(startIndex, endIndex, visited, new ArrayList<>(visited.length), new ArrayList<>(visited.length));
    }

}
