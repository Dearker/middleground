package com.hanyi.daily.common.atomic;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * @author weiwenchang
 * @since 2020-03-17
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Athlete(cyclicBarrier, "运动 员" + i));
            threadList.add(t);
        }
        for (Thread t : threadList) {
            t.start();
        }
    }

    @AllArgsConstructor
    static class Athlete implements Runnable {

        private CyclicBarrier cyclicBarrier;
        private String name;

        @Override
        public void run() {
            System.out.println(name + "就位");
            try {
                cyclicBarrier.await();
                Thread.sleep(1000);
                System.out.println(name + "到达终点。");
            } catch (Exception ignored) {
            }
        }
    }
}

