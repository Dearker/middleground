package com.hanyi.daily.common.atomic;

import lombok.AllArgsConstructor;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    public static void main(String[] args) {
        Parking parking = new Parking(3);
        for (int i = 0; i < 5; i++) {
            new Car(parking).start();
        }
    }

    static class Parking {
        /**
         * 信号量
         */
        private final Semaphore semaphore;

        Parking(int count) {
            semaphore = new Semaphore(count);
        }

        void park() {
            try {
                //获取信号量
                semaphore.acquire();
                long time = (long) (Math.random() * 10);
                System.out.println(Thread.currentThread().getName() + "进入停车场，停车" + time + "秒...");
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName() + "开出停车场...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //释放信号量
                semaphore.release();
            }
        }
    }

    @AllArgsConstructor
    static class Car extends Thread {
        private final Parking parking;

        @Override
        public void run() {
            //进入停车场
            parking.park();
        }
    }
}