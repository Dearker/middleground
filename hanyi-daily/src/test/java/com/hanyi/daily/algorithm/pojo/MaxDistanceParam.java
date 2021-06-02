package com.hanyi.daily.algorithm.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 最大距离参数对象
 * </p>
 *
 * @author wenchangwei
 * @since 2021/6/2 10:32 下午
 */
@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class MaxDistanceParam {

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
     * 缓存距离长度
     */
    private int bufferDistance;

    /**
     * 最大距离
     */
    private int maxDistance;

    /**
     * 结果总数
     */
    private int resultTotal;

    /**
     * 结果路径集合
     */
    private List<String> resultLineList;

}
