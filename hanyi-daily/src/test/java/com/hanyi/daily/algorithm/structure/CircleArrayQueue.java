package com.hanyi.daily.algorithm.structure;

import org.junit.Test;

/**
 * <p>
 * 环形数组队列
 * </p>
 *
 * @author wenchangwei
 * @since 10:10 下午 2020/6/22
 */
public class CircleArrayQueue {

    @Test
    public void test01() {
        int i = 1 % 3;

        int i1 = 0 % 3;

        int i2 = -3 % 3;

        System.out.println(i + "||" + i1 + "||" + i2);
    }

    @Test
    public void queueTest() {
        CircleArray circleArray = new CircleArray(3);
        for (int i = 0; i < 6; i++) {
            circleArray.addQueue(i);
            while (true) {
                if (circleArray.isFull()) {
                    System.out.println(circleArray.getQueue());
                } else {
                    break;
                }
            }
        }
    }

    static class CircleArray {

        /**
         * 数组最大容量
         */
        private final int maxSize;

        /**
         * 队列头，初始值为0，即指向队列的第一个元素
         */
        private int front;

        /**
         * 队列尾，初始值为0，即指向队列的最后一个元素的后一天位置，空出一个空间做约定
         */
        private int rear;

        /**
         * 用于存放数据
         */
        private final int[] array;

        public CircleArray(int maxSize) {
            this.maxSize = maxSize;
            array = new int[maxSize];
        }

        /**
         * 判断队列是否满
         *
         * @return 返回结果
         */
        public boolean isFull() {
            return (rear + 1) % maxSize == front;
        }

        /**
         * 判断队列是否为空
         *
         * @return 返回结果
         */
        public boolean isEmpty() {
            return rear == front;
        }

        /**
         * 添加队列
         *
         * @param n 添加的元素
         */
        public void addQueue(int n) {
            if (isFull()) {
                System.out.println("队列已满");
                return;
            }
            //加入数据
            array[rear] = n;

            //将rear后移
            rear = (rear + 1) % maxSize;
        }

        /**
         * 获取队列的数据，出队列
         *
         * @return 返回数据
         */
        public int getQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空");
            }
            int value = array[front];
            front = (front + 1) % maxSize;

            return value;
        }

    }


}
