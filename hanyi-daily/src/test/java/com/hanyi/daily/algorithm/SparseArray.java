package com.hanyi.daily.algorithm;

import org.junit.Test;

/**
 * <p>
 * 五子棋算法，二维数组和稀疏数组之间的转换
 * </p>
 *
 * @author wenchangwei
 * @since 5:16 下午 2020/6/21
 */
public class SparseArray {

    @Test
    public void arrayTest() {
        //创建原始二维数组 11 * 11
        // 0 表示没有棋子，1 表示黑子 2 表示蓝子
        int[][] chessArray = new int[11][11];

        chessArray[1][2] = 1;
        chessArray[2][3] = 2;
        chessArray[4][5] = 2;

        System.out.println("原始的二维数组----");

        int sum = 0;
        for (int[] row : chessArray) {
            for (int data : row) {
                if (data != 0) {
                    sum++;
                }
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        System.out.println("获取不为0的总数：" + sum);

        //二维数组转稀疏数组
        int[][] buildSparseArray = this.buildSparseArray(chessArray, sum);
        //稀疏数组转二维数组
        this.buildChessArray(buildSparseArray);
    }

    /**
     * 稀疏数组转二维数组
     *
     * @param sparseArray 稀疏数组
     */
    private void buildChessArray(int[][] sparseArray) {

        int[][] chessArray = new int[sparseArray[0][0]][sparseArray[0][1]];

        for (int i = 1; i < sparseArray.length; i++) {
            chessArray[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }

        System.out.println("稀疏数组转换二维数组---------");

        for (int[] row : chessArray) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }


    /**
     * 二维数组转换稀疏数组
     *
     * @param chessArray 二维数组
     * @param sum        不为0的总数
     */
    private int[][] buildSparseArray(int[][] chessArray, int sum) {
        //将二维数组转换成稀疏数组
        int[][] sparseArray = new int[sum + 1][3];

        //稀疏数组的第一列
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;

        //稀疏数组的其他列
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArray[i][j] != 0) {
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArray[i][j];
                }
            }
        }

        System.out.println("转换后的稀疏数组----------");
        for (int[] row : sparseArray) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
        return sparseArray;
    }

    @Test
    public void intTest() {
        int[][] array = new int[4][5];
        System.out.println(array.length);
    }

}
