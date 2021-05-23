package com.hanyi.daily.algorithm.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <p>
 * 图表实体类
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/21 10:11 下午
 */
public class Graph {

    /**
     * 顶点集合
     */
    private final List<String> vertexList;

    /**
     * 边缘集合
     */
    private final int[][] edges;

    /**
     * 路线总数
     */
    private int numOfEdges;

    /**
     * 是否访问数组
     */
    private boolean[] isVisited;

    /**
     * 初始化数组容量
     *
     * @param n 容量大小
     */
    public Graph(int n) {
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0;
    }

    /**
     * 新增路线
     *
     * @param v1     开始或结束顶点下标
     * @param v2     开始或结束顶点下标
     * @param weight 开始和结束间距
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    /**
     * 添加顶点
     *
     * @param vertex 顶点
     */
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 展示图表数据
     */
    public void showGraph() {
        Stream.of(edges).forEach(s -> System.out.println(Arrays.toString(s)));
    }

    /**
     * 深度优先算法
     * <p>
     * 从初始访问结点出发，初始访问结点可能有多个邻接结点，深度优先遍历的策略就是首先访问第一个邻接结点，
     * 然后再以这个被访问的邻接结点作为初始结点，访问它的第一个邻接结点，
     * 可以这样理解：每次都在访问完当前结点后首先访问当前结点的第一个邻接结点
     */
    public void dfs() {
        isVisited = new boolean[vertexList.size()];
        for (int i = 0; i < vertexList.size(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
    }

    private void dfs(boolean[] isVisited, int i) {
        System.out.print(vertexList.get(i) + "->");
        isVisited[i] = true;
        int w = getFirstNeighbor(i);
        while (w != -1) {
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            w = getNextNeighbor(i, w);
        }
    }

    /**
     * 广度优先，类似分层查找
     * <p>
     * 1、访问初始结点v并标记结点v为已访问。
     * 2、结点v入队列
     * 3、当队列非空时，继续执行，否则算法结束。
     * 4、出队列，取得队头结点u。
     * 5、查找结点u的第一个邻接结点w。
     * 6、若结点u的邻接结点w不存在，则转到步骤3；否则循环执行以下三个步骤：
     * 6.1 若结点w尚未被访问，则访问结点w并标记为已访问。
     * 6.2 结点w入队列
     * 6.3 查找结点u的继w邻接结点后的下一个邻接结点w，转到步骤6。
     */
    public void bfs() {
        isVisited = new boolean[vertexList.size()];
        for (int i = 0; i < vertexList.size(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }

    private void bfs(boolean[] isVisited, int i) {
        int u;
        int w;
        LinkedList<Integer> queue = new LinkedList<>();
        System.out.print(vertexList.get(i) + "=>");
        isVisited[i] = true;
        queue.addLast(i);

        while (!queue.isEmpty()) {
            u = queue.removeFirst();
            w = getFirstNeighbor(u);
            while (w != -1) {
                if (!isVisited[w]) {
                    System.out.print(vertexList.get(w) + "=>");
                    isVisited[w] = true;
                    queue.addLast(w);
                }
                w = getNextNeighbor(u, w);
            }
        }
    }

    /**
     * 查询和当前节点第一个相邻的节点索引位置
     *
     * @param index 当前节点的索引位置
     * @return 如果存在则返回相邻节点的索引位置，不存在则返回-1
     */
    private int getFirstNeighbor(int index) {
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[index][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 查询和当前节点下一个相邻的节点索引位置
     *
     * @param v1 开始行索引
     * @param v2 开始列索引
     * @return 如果存在则返回下一个相邻节点索引位置，不存在则返回-1
     */
    private int getNextNeighbor(int v1, int v2) {
        for (int j = v2 + 1; j < vertexList.size(); j++) {
            if (edges[v1][j] > 0) {
                return j;
            }
        }
        return -1;
    }

}
