package com.hanyi.daily.algorithm.structure;

import com.hanyi.daily.algorithm.pojo.Graph;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * <p>
 * 图结构测试
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/21 9:48 下午
 */
public class GraphTest {

    @Test
    public void graphDFSTest() {
        int n = 5;
        String[] vertex = {"A", "B", "C", "D", "E"};

        Graph graph = new Graph(n);
        //添加路线集合
        Stream.of(vertex).forEach(graph::insertVertex);

        //初始化路线相关数据
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);

        graph.showGraph();
        // A->B->C->D->E
        graph.dfs();
        System.out.println();
        //广度优先
        graph.bfs();
    }


}
