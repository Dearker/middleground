package com.hanyi.daily.algorithm.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/30 4:59 下午
 */
@Data
@Builder
@AllArgsConstructor
public class GraphParam {

    /**
     * 当前的起始节点
     */
    private Character cNode;

    /**
     * 当前起始节点的上一节点
     */
    private Character pNode;

    /**
     * 最初的起始节点
     */
    private Character sNode;

    /**
     * 终点
     */
    private Character eNode;

    /**
     * 所有符合条件的路线集合
     */
    private List<List<String>> allLineList;

    /**
     * 是否为第一次回路
     */
    private boolean firstFlag;

}
