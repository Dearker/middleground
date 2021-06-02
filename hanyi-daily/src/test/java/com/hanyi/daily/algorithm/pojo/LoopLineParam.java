package com.hanyi.daily.algorithm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/6/1 9:21 下午
 */
@Data
@AllArgsConstructor
public class LoopLineParam {

    /**
     * 开始节点索引
     */
    private int startIndex;

    /**
     * 结束节点索引
     */
    private int endIndex;

    /**
     * 路线总长度
     */
    private int lineTotal;

    /**
     * 存储每条可能的路径集合
     */
    private LinkedList<Character> pathList;

    /**
     * 所有可能路线集合
     */
    private List<LinkedList<Character>> allLineList;

    public LoopLineParam(int startIndex, int endIndex, int lineTotal) {
        this(startIndex, endIndex, lineTotal, new LinkedList<>(), new ArrayList<>());
    }


}
