package com.hanyi.demo.lock.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;

/**
 * @ClassName: middleground com.hanyi.demo.lock ZookeeperDistrbuteLock
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-23 22:48
 * @Version: 1.0
 */
public class ZookeeperDistrbuteLock extends ZookeeperAbstractLock {

    @Override
    boolean tryLock() {
        try {
            zkClient.createEphemeral(LOCK_PATH);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    void waitLock() {

        // 使用zk临时事件监听
        IZkDataListener iZkDataListener = new IZkDataListener() {

            @Override
            public void handleDataDeleted(String path) {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }

            @Override
            public void handleDataChange(String arg0, Object arg1) {

            }
        };
        // 注册事件通知
        zkClient.subscribeDataChanges(LOCK_PATH, iZkDataListener);
        if (zkClient.exists(LOCK_PATH)) {
            try {
                countDownLatch.await();
            } catch (Exception ignored) {
            }
        }
        // 监听完毕后，移除事件通知
        zkClient.unsubscribeDataChanges(LOCK_PATH, iZkDataListener);
    }

}
