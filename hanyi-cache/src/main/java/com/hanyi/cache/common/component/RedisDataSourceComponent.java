package com.hanyi.cache.common.component;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author wcwei@iflytek.com
 * @since 2021-07-30 17:17
 */
@Component
@RequiredArgsConstructor
public class RedisDataSourceComponent {

    /**
     * 数据库列表
     */
    private static final List<Integer> DATABASE_LIST;

    static {
        List<Integer> dataList = new ArrayList<>(Short.SIZE);
        IntStream.rangeClosed(0, 15).forEach(dataList::add);
        DATABASE_LIST = Collections.unmodifiableList(dataList);
    }

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 改变数据源
     *
     * @param database 数据库
     */
    public void changeDataSource(int database) {
        //默认使用的redis的本机ip和端口，如果不为本机则需要修改对应的配置
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        stringRedisTemplate.setConnectionFactory(connectionFactory);
    }

    public List<Integer> getDatabaseList() {
        return DATABASE_LIST;
    }

}
