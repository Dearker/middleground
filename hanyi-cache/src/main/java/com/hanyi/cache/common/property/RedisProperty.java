package com.hanyi.cache.common.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * redis配置类
 * </p>
 *
 * @author wenchangwei
 * @since 4:20 下午 2020/11/14
 */
@Data
@Configuration
public class RedisProperty {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.maxIdle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.maxWait}")
    private long maxWaitMillis;

}
