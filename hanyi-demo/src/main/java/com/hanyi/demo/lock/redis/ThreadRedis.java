package com.hanyi.demo.lock.redis;

/**
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-24 12:29
 * @Version: 1.0
 */
public class ThreadRedis extends Thread {
	private LockService lockService;

	private ThreadRedis(LockService lockService) {
		this.lockService = lockService;
	}

	@Override
	public void run() {
		lockService.seckill();
	}

	public static void main(String[] args) {
		LockService lockService = new LockService();
		for (int i = 0; i < 50; i++) {
			ThreadRedis threadRedis = new ThreadRedis(lockService);
			threadRedis.start();
		}
	}

}
