package com.hanyi.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2020-10-12 13:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    /**
     * redis模板
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void redisTest(){
        stringRedisTemplate.opsForHash().put("1","21","323");
        Object o = stringRedisTemplate.opsForHash().get("1", "21");
        System.out.println(o);
    }

    @Test
    public void hashTest(){
        redisTemplate.opsForHash().put("2","32","13");
        Object o = redisTemplate.opsForHash().get("2", "32");
        System.out.println(o);
    }

}
