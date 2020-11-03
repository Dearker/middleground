package com.hanyi.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
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
    public void redissonClientTest(){
        RLock lock = redissonClient.getLock("lock");

        lock.lock();
        System.out.println(System.currentTimeMillis());
        lock.unlock();
    }

}
