package com.hanyi.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: middleground com.hanyi.demo.config RedissonConfig
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-12-07 10:48
 * @Version: 1.0
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        //单机模式  依次设置redis地址和密码
        config.useSingleServer().
                setAddress("redis://" + redisHost + ":" + redisPort);
        //设置序列化
        config.setCodec(new StringCodec());
        return Redisson.create(config);
    }

}
