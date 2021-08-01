package com.hanyi.cache.common.component;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: middleground com.hanyi.demo.component RedisKeyExpirationListener
 * @Author: weiwenchang
 * @Description: redis缓存失效监听类
 * @CreateDate: 2020-01-04 18:07
 * @Version: 1.0
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String s = message.toString();

        log.info("过期的key值: {}",s);
        if(StrUtil.isNotBlank(s)){
            stringRedisTemplate.opsForValue().set(s,"456",20,TimeUnit.SECONDS);
            log.info("设置新的缓存成功");
        }

    }
}
