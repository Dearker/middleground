package com.hanyi.demo.lock.zookeeper;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: middleground com.hanyi.demo.lock OrderService
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-23 23:35
 * @Version: 1.0
 */
@Slf4j
public class OrderService implements Runnable {

    private final OrderNumGenerator orderNumGenerator = new OrderNumGenerator();

    private final ExtLock extLock = new ZookeeperDistrbuteLock();

    @Override
    public void run() {
        getNumber();
    }

    public void getNumber() {
        try {
            extLock.getLock();
            String number = orderNumGenerator.getNumber();
            log.info("线程:" + Thread.currentThread().getName() + ",生成订单id:" + number);
        } finally {
            extLock.unLock();
        }
    }

    public static void main(String[] args) {
        log.info("多线程生成number");
        for (int i = 0; i < 100; i++) {
            new Thread(new OrderService()).start();
        }
    }

}
