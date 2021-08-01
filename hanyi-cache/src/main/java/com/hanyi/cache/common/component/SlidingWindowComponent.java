package com.hanyi.cache.common.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 滑动窗口组件
 * </p>
 *
 * @author wenchangwei
 * @since 2021/7/31 10:25 下午
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SlidingWindowComponent {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 统计指定时间范围内key的总数是否超出限制
     *
     * @param key            指定的key
     * @param windowInSecond 窗口大小
     * @param maxCount       限制的总数
     * @return 返回校验结果
     */
    public boolean isActionAllowed(String key, Integer windowInSecond, Integer maxCount) {
        // 当前时间
        long currentMs = System.currentTimeMillis();
        // 窗口时间
        long maxScoreMs = currentMs - windowInSecond * 1000;

        BoundZSetOperations<String, String> boundSetOps = null;
        try {
            stringRedisTemplate.multi();
            boundSetOps = stringRedisTemplate.boundZSetOps(key);

            boundSetOps.removeRangeByScore(0, maxScoreMs);
            // 添加当前时间分数
            boundSetOps.add(currentMs + "_" + Math.random(), currentMs);
            // 设置超时时间
            boundSetOps.expire(windowInSecond, TimeUnit.SECONDS);
            stringRedisTemplate.exec();
        } catch (Exception e) {
            stringRedisTemplate.discard();
            log.error("窗口操作失败：", e);
        }

        Long count = Optional.ofNullable(boundSetOps).map(BoundZSetOperations::zCard).orElse(0L);
        return count >= maxCount;
    }

    /**
     * 统计指定时间范围内key的总数是否超出限制，推荐使用该方式
     *
     * @param key            指定的key
     * @param windowInSecond 窗口大小
     * @param maxCount       限制的总数
     * @return 返回校验结果
     */
    public boolean increment(String key, Integer windowInSecond, Integer maxCount) {
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        //当前时间
        long currentMs = System.currentTimeMillis();

        ZSetOperations<String, String> opsForSet = stringRedisTemplate.opsForZSet();
        //并发下单靠时间戳可能出现重复
        opsForSet.add(key, currentMs + "_" + Math.random(), currentMs);
        stringRedisTemplate.expire(key, windowInSecond, TimeUnit.SECONDS);
        if (Objects.nonNull(hasKey) && hasKey) {
            // 窗口时间
            long maxScoreMs = currentMs - windowInSecond * 1000;
            //0表示最开始的索引位置，-1表示取出所有数据
            opsForSet.removeRangeByScore(key, 0, maxScoreMs);
        }

        long count = Optional.ofNullable(opsForSet.zCard(key)).orElse(0L);
        return count >= maxCount;
    }

}
