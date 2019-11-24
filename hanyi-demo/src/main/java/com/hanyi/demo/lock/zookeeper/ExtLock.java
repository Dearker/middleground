package com.hanyi.demo.lock.zookeeper;

/**
 * @ClassName: middleground com.hanyi.demo.lock ExtLock
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-23 22:48
 * @Version: 1.0
 */
public interface ExtLock {

	/**
	 * 获取锁
	 */
	public void getLock();

	/**
	 * 释放锁
 	 */
	public void unLock();
}
