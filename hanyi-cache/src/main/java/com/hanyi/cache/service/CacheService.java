package com.hanyi.cache.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * 缓存接口层
 * </p>
 *
 * @author wenchangwei
 * @since 10:39 下午 2020/12/17
 */
public interface CacheService {

    /**
     * 根据当前账号生成缓存id
     *
     * @param accounts 账号
     * @return 返回缓存id
     */
    String createCache(String accounts);

    /**
     * 根据缓存id获取缓存数据
     *
     * @param cacheId 缓存id
     * @return 返回缓存数据
     */
    JSONObject getCache(String cacheId);

    /**
     * 根据缓存id和账号获取缓存数据
     *
     * @param cacheId  缓存id
     * @param accounts 账号
     * @return 返回缓存数据
     */
    JSONObject getCache(String cacheId, String accounts);

    /**
     * 根据缓存id和账号以及缓存key获取缓存缓存数据
     *
     * @param cacheId  缓存id
     * @param accounts 账号
     * @param dataset  缓存key
     * @return 返回缓存数据
     */
    JSONArray getCache(String cacheId, String accounts, String dataset);

}
