package com.hanyi.cache;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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
public class CacheLockTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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

    @Test
    public void resourceTest() throws InterruptedException {
        List<Integer> integerList = new ArrayList<>();
        ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(3, 3);

        String fastId = IdUtil.fastSimpleUUID();
        IntStream.rangeClosed(1, 3).forEach(s ->
                threadPoolExecutor.execute(() -> {
                    if (this.lock(fastId)) {
                        try {
                            integerList.add(1);
                        } finally {
                            this.unlock(fastId);
                        }
                    }
                }));

        IntStream.rangeClosed(1, 3).forEach(s -> {
            threadPoolExecutor.execute(() -> buildData(integerList));
        });

        TimeUnit.SECONDS.sleep(10);

        System.out.println(integerList);
    }

    private void buildData(List<Integer> integerList) {
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 3, TimeUnit.SECONDS);

        System.out.println("当前线程：" + Thread.currentThread().getName() + "获取锁结果：" + lock);
        if (Objects.nonNull(lock) && lock) {
            System.out.println("获取分布式锁成功...");
            // 加锁成功...执行业务
            try {
                // 访问数据库
                integerList.add(2);
            } finally {
                // 获取对比值和对比成功删除锁也是要同步的、原子的执行  参照官方使用lua脚本解锁
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                        Collections.singletonList("lock"), uuid);
            }
        }
    }

    private boolean lock(String uuid) {
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 3, TimeUnit.SECONDS);
        System.out.println("lock当前线程：" + Thread.currentThread().getName() + "获取锁结果：" + lock);
        return Objects.nonNull(lock) && lock;
    }

    private void unlock(String uuid) {
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                Collections.singletonList("lock"), uuid);
    }

}
