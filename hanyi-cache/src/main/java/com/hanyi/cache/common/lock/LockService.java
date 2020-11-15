package com.hanyi.cache.common.lock;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-24 12:29
 * @Version: 1.0
 */
@Slf4j
public class LockService {
    private static final JedisPool POOL;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(200);
        // 设置最大空闲数
        config.setMaxIdle(8);
        // 设置最大等待时间
        config.setMaxWaitMillis(Math.multiplyExact(100L, 1000L));
        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        POOL = new JedisPool(config, "114.67.102.163", 6379, 3000);
    }

    private final LockRedis lockRedis = new LockRedis(POOL);

    public void seckill() {
        String identifier = lockRedis.lockWithTimeout("hanyi", 5000L, 5000L);
        if (StrUtil.isEmpty(identifier)) {
            // 获取锁失败
            log.info(Thread.currentThread().getName() + ",获取锁失败，原因时间超时!!!");
            return;
        }
        log.info(Thread.currentThread().getName() + "获取锁成功,锁id identifier:" + identifier + "，执行业务逻辑");
        try {
            Thread.sleep(30);
        } catch (Exception ignored) {

        }
        // 释放锁
        boolean releaseLock = lockRedis.releaseLock("hanyi", identifier);
        if (releaseLock) {
            log.info(Thread.currentThread().getName() + "释放锁成功,锁id identifier:" + identifier);
        }
    }
}
