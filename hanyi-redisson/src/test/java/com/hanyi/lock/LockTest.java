package com.hanyi.lock;

import cn.hutool.core.util.IdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 分布式锁测试类
 * </p>
 *
 * @author wenchangwei
 * @since 10:19 下午 2020/11/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LockTest {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void buildDataTest() {
        stringRedisTemplate.opsForValue().set("1", "哈哈哈", 10, TimeUnit.MINUTES);
    }

    /**
     * 分布式锁
     */
    @Test
    public void redissonClientTest() {
        RLock lock = redissonClient.getLock("lock");

        lock.lock();
        System.out.println(System.currentTimeMillis());
        lock.unlock();
    }

    /**
     * 分布式信号量测试
     */
    @Test
    public void semaphoreTest() throws InterruptedException {
        RSemaphore semaphore = redissonClient.getSemaphore(IdUtil.fastSimpleUUID());
        //设置信号量总数
        semaphore.trySetPermits(100);
        semaphore.expire(1L, TimeUnit.MINUTES);

        //占位之前先尝试是否能获取到占位
        boolean tryAcquire = semaphore.tryAcquire(2, 200L, TimeUnit.MILLISECONDS);
        if (tryAcquire) {
            semaphore.acquire(2);
            System.out.println("占位成功");
        } else {
            System.out.println("占位失败");
        }
    }

}
