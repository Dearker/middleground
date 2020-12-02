package com.hanyi.cache.common.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyi.cache.common.property.RedisProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @PackAge: middleground com.hanyi.cache.common.config
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-12 21:27
 * @Version: 1.0
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置value的序列化规则和 key的序列化规则,并开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setValueSerializer(this.createJacksonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //过期时间
                .entryTtl(Duration.ofSeconds(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.createJacksonRedisSerializer()))
                .disableCachingNullValues();
        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }

    /**
     * 使用Jackson2JsonRedisSerialize 替换默认序列化
     *
     * @return 返回序列化对象
     */
    private Jackson2JsonRedisSerializer<Object> createJacksonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 配置序列化（解决乱码的问题）
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    /**
     * 配置redis监听容器
     *
     * @param redisConnectionFactory redis连接工厂
     * @return 返回redis的key监听器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(3600);
        executor.setThreadNamePrefix("redis");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER\_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        // 设置Redis的连接工厂
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        // 设置监听使用的线程池
        redisMessageListenerContainer.setTaskExecutor(executor);

        return redisMessageListenerContainer;
    }

    /**
     * 注入redis连接池
     *
     * @param redisProperty redis属性配置类
     * @return 返回连接池
     */
    @Bean
    public JedisPool redisPoolFactory(RedisProperty redisProperty) {
        log.info("JedisPool注入成功！！");
        log.info("redis地址：" + redisProperty.getHost() + ":" + redisProperty.getPort());
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperty.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperty.getMaxWaitMillis());

        return new JedisPool(jedisPoolConfig, redisProperty.getHost(),
                redisProperty.getPort(), redisProperty.getTimeout());
    }

    @Bean
    public Snowflake snowflake(){
        return IdUtil.createSnowflake(1, 1);
    }

}
