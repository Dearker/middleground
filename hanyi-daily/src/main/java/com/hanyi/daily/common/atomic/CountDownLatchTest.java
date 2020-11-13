package com.hanyi.daily.common.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CountDownLatchTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            //起点运动员
            Thread t1 = new Thread(new Athlete(cyclicBarrier, countDownLatch, "起点运动员" + i));

            //接力运动员
            Thread t2 = new Thread(new Athlete(countDownLatch, "接力运动员" + i));

            threadList.add(t1);
            threadList.add(t2);
        }

        for (Thread t : threadList) {
            t.start();
        }
    }

    static class Athlete implements Runnable {

        private CyclicBarrier cyclicBarrier;
        private final String name;
        private final CountDownLatch countDownLatch;

        /**
         * 起点运动员
         *
         * @param cyclicBarrier
         * @param countDownLatch
         * @param name
         */
        Athlete(CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch, String name) {
            this.cyclicBarrier = cyclicBarrier;
            this.countDownLatch = countDownLatch;
            this.name = name;
        }

        /**
         * 接力运动员
         *
         * @param countDownLatch
         * @param name
         */
        Athlete(CountDownLatch countDownLatch, String name) {
            this.countDownLatch = countDownLatch;
            this.name = name;
        }

        @Override
        public void run() {
            //判断是否是起点运动员
            if (cyclicBarrier != null) {

                System.out.println(name + "就位");
                try {
                    cyclicBarrier.await();
                    System.out.println(name + "到达交接点。");

                    //已经到达交接点
                    countDownLatch.countDown();
                } catch (Exception ignored) {
                }
            }

            //判断是否是接力运动员
            if (cyclicBarrier == null) {
                System.out.println(name + "就位");
                try {
                    countDownLatch.await();
                    System.out.println(name + "到达终点。");
                } catch (Exception ignored) {
                }
            }
        }
    }
}