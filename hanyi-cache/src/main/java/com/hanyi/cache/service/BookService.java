package com.hanyi.cache.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 书服务层
 * </p>
 *
 * @author wenchangwei
 * @since 10:33 下午 2020/10/4
 */
@Service
public class BookService {

    /**
     * redis模板
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 构建用户是否在线数据
     * Redis对于String类型有最大值限制不得超过512M，即2^32次方byte
     *
     * @return 返回结果
     */
    public String buildRedisData() {
        String key = "order";
        long userId = 1;
        stringRedisTemplate.opsForValue().setBit(key, userId, Boolean.TRUE);
        stringRedisTemplate.expire(key, 1, TimeUnit.MINUTES);
        Boolean bit = stringRedisTemplate.opsForValue().getBit(key, userId);
        if (Objects.nonNull(bit) && bit) {
            return "success";
        }
        return "fail";
    }

    public String findCacheData(){
        return stringRedisTemplate.opsForValue().get("1");
    }

}
