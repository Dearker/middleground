package com.hanyi.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 缓存测试类
 * </p>
 *
 * @author wenchangwei
 * @since 8:49 下午 2020/11/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void buildDataTest() {
        stringRedisTemplate.opsForValue().set("1", "哈哈哈", 1, TimeUnit.HOURS);
    }

    /**
     * 测试redis的setNX和expire命令的原子性
     */
    @Test
    public void setIfAbsentTest() {
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", "111", 1, TimeUnit.MINUTES);
        //为true表示设置成功，false表示失败
        System.out.println(lock);
    }

    /**
     * 分布式设置锁以及解锁
     */
    @Test
    public void redisLockTest() {

        // 抢占分布式锁 setIfAbsent --> NX 不存在才占坑 EX 自动过期时间
        // 设置redis锁的自动过期时间 - 防止出现异常、服务崩塌等各种情况，没有执行删除锁操作导致的死锁问题
        // !!! 设置过期时间和加锁必须是同步的、原子的
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);

        System.out.println(lock);
        if (Objects.nonNull(lock) && lock) {
            System.out.println("获取分布式锁成功...");
            // 加锁成功...执行业务
            try {
                // 访问数据库
                System.out.println(System.currentTimeMillis());
            } finally {
                // 获取对比值和对比成功删除锁也是要同步的、原子的执行  参照官方使用lua脚本解锁
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                        Collections.singletonList("lock"), uuid);
            }
        }
    }


}
