package com.hanyi.cache.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.HashUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanyi.cache.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 缓存逻辑层
 * </p>
 *
 * @author wenchangwei
 * @since 10:42 下午 2020/12/17
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    /**
     * redis模板
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据当前账号生成缓存id
     *
     * @param accounts 账号
     * @return 返回缓存id
     */
    @Override
    public String createCache(String accounts) {
        TimeInterval timer = DateUtil.timer();

        String cacheId = Integer.toString(HashUtil.jsHash(accounts));
        log.info("生成的缓存id为：{}", cacheId);

        JSONObject json = new JSONObject();

        //构建json数据，此处一般为数据库查询的操作
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("哈哈哈", "12321");
        jsonArray.add(jsonObject);
        json.put("RES003", jsonArray);

        stringRedisTemplate.opsForValue().set(cacheId, json.toJSONString());
        // 设置过期时间，1个小时
        stringRedisTemplate.expire(cacheId, 60, TimeUnit.MINUTES);
        log.info("生成缓存数据耗时：{}", timer.intervalRestart());

        return cacheId;
    }

    /**
     * 根据缓存id获取缓存数据
     *
     * @param cacheId 缓存id
     * @return 返回缓存数据
     */
    @Override
    public JSONObject getCache(String cacheId) {
        String cacheStr = stringRedisTemplate.opsForValue().get(cacheId);
        // 将缓存过期时间往后延长10分钟
        stringRedisTemplate.expire(cacheId, 30, TimeUnit.MINUTES);
        return JSON.parseObject(cacheStr);
    }

    /**
     * 根据缓存id和账号获取缓存数据
     *
     * @param cacheId  缓存id
     * @param accounts 账号
     * @return 返回缓存数据
     */
    @Override
    public JSONObject getCache(String cacheId, String accounts) {
        Boolean hasKey = stringRedisTemplate.hasKey(cacheId);
        boolean exist = Objects.isNull(hasKey) ? Boolean.FALSE : hasKey;
        if (!exist) {
            this.createCache(accounts);
        }
        return this.getCache(cacheId);
    }

    /**
     * 根据缓存id和账号以及缓存key获取缓存缓存数据
     *
     * @param cacheId  缓存id
     * @param accounts 账号
     * @param dataset  缓存key
     * @return 返回缓存数据
     */
    @Override
    public JSONArray getCache(String cacheId, String accounts, String dataset) {
        return this.getCache(cacheId, accounts).getJSONArray(dataset);
    }
}
