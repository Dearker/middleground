package com.hanyi.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @ClassName: middleground com.hanyi.demo.config RedissonConfig
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-12-07 10:48
 * @Version: 1.0
 */
@Configuration
@EnableCaching
public class RedissonConfig extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.maxIdle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.maxWait}")
    private long maxWaitMillis;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        //单机模式  依次设置redis地址和密码
        config.useReplicatedServers()
                // 主节点变化扫描间隔时间
                .setScanInterval(2000)
                .addNodeAddress("redis://" + redisHost + ":" + redisPort);
        //设置序列化
        config.setCodec(new StringCodec());
        return Redisson.create(config);
    }

    /**
     * 配置redis监听容器
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        return redisMessageListenerContainer;
    }

    @Bean
    public JedisPool redisPoolFactory() {

        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + redisHost + ":" + redisPort);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        return new JedisPool(jedisPoolConfig, redisHost, Integer.parseInt(redisPort),timeout);
    }

}
