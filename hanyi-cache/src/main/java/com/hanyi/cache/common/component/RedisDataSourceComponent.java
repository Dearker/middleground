package com.hanyi.cache.common.component;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wcwei@iflytek.com
 * @since 2021-07-30 17:17
 */
@Component
@RequiredArgsConstructor
public class RedisDataSourceComponent {

    private final StringRedisTemplate stringRedisTemplate;

    public void changeDataSource(int database){
        JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory)stringRedisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(database);
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
    }

}
