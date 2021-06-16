package com.hanyi.daily.algorithm.common;

import org.junit.Test;

import java.util.Arrays;

/**
 * <p>
 * Floyd算法，用于解决各个顶点到其他顶点的最短距离，该算法时间复杂度较高，为N ^ 3次方
 * </p>
 *
 * @author wenchangwei
 * @since 2021/6/7 8:30 下午
 */
public class FloydAlgorithmTest {

    @Test
    public void floydTest() {
        int vertexLength = 7;
        //创建邻接矩阵，7表示所有节点数
        int[][] matrix = new int[vertexLength][vertexLength];
        final int N = 65535;
        matrix[0] = new int[]{0, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, 0, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, 0, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, 0, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, 0, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, 0, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, 0};

        Graph graph = new Graph(vertexLength, matrix);
        graph.floyd();
        graph.show();
    }

    static class Graph {
        private final int[][] dis; // 保存，从各个顶点出发到其它顶点的距离，最后的结果，也是保留在该数组
        private final int[][] pre;// 保存到达目标顶点的前驱顶点

        /**
         * @param length 大小
         * @param matrix 邻接矩阵
         */
        public Graph(int length, int[][] matrix) {
            this.dis = matrix;
            this.pre = new int[length][length];
            // 对pre数组初始化,注意存放的是前驱顶点的下标
            for (int i = 0; i < length; i++) {
                Arrays.fill(pre[i], i);
            }
        }

        public void show() {
            char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
            for (int k = 0; k < dis.length; k++) {
                // 先将pre数组输出的一行
                for (int i = 0; i < dis.length; i++) {
                    System.out.print(vertex[pre[k][i]] + " ");
                }
                System.out.println();
                // 输出dis数组的一行数据
                for (int i = 0; i < dis.length; i++) {
                    System.out.print("(" + vertex[k] + "到" + vertex[i] + "的最短路径是" + dis[k][i] + ")");
                }
                System.out.println();
            }
        }

        //算法实现
        public void floyd() {
            //对中间顶点遍历，k就是中间顶点的下标[A,B,C,D,E,F,G]
            for (int k = 0; k < dis.length; k++) { //
                //从i顶点开始出发 [A, B, C, D, E, F, G]
                for (int i = 0; i < dis.length; i++) {
                    //到达j顶点 // [A, B, C, D, E, F, G]
                    for (int j = 0; j < dis.length; j++) {
                        int len = dis[i][k] + dis[k][j];// => 求出从i顶点出发，经过k中间顶点，到达j顶点距离
                        if (len < dis[i][j]) {//如果len小于dis[i][j]
                            dis[i][j] = len;//更新距离
                            pre[i][j] = pre[k][j];//更新前驱顶点
                        }
                    }
                }
            }
        }
    }

}
