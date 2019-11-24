package com.hanyi.demo.lock.zookeeper;

/**
 * @ClassName: middleground com.hanyi.demo.lock OrderService
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-23 23:35
 * @Version: 1.0
 */
public class OrderService implements Runnable {

    private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();

    private ExtLock extLock = new ZookeeperDistrbuteLock();

    @Override
    public void run() {
        getNumber();
    }

    public void getNumber() {
        try {
            extLock.getLock();
            String number = orderNumGenerator.getNumber();
            System.out.println("线程:" + Thread.currentThread().getName() + ",生成订单id:" + number);
        } catch (Exception e) {

        } finally {
            extLock.unLock();
        }
    }

    public static void main(String[] args) {
        System.out.println("多线程生成number");
        for (int i = 0; i < 100; i++) {
            new Thread(new OrderService()).start();
        }
    }

}
