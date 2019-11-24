package com.hanyi.demo.lock.zookeeper;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: middleground com.hanyi.demo.component ZookeeperAbstractLock
 * @Author: weiwenchang
 * @Description: 使用模板设计模式
 * @CreateDate: 2019-11-23 22:48
 * @Version: 1.0
 */
//@Component
public abstract class ZookeeperAbstractLock implements ExtLock {

    //@Value("${zk.address}")
    private String zkAddres = "114.67.102.163:2181";

    protected ZkClient zkClient = new ZkClient(zkAddres);

    protected static final String LOCK_PATH = "/lock";

    protected CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void getLock() {
        if (tryLock()) {
            System.out.println("获取锁成功.....");
        } else {
            waitLock();
            getLock();
        }
    }

    /**
     * 获取锁
      * @return
     */
    abstract boolean tryLock();

    /**
     * 等待锁
      */
    abstract void waitLock();

    @Override
    public void unLock() {
        if (zkClient != null) {
            System.out.println("释放锁.....");
            zkClient.close();
        }
    }

}
