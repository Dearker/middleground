package com.hanyi.daily.common.property;

import com.hanyi.daily.pojo.BusinessThreadPool;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 8:51 下午 2020/7/31
 */
@Component
@ConfigurationProperties(prefix = "business.thread-pool-executor")
public class BusinessThreadPoolProperties {

    /**
     * 线程池对象集合
     */
    private Map<String, BusinessThreadPool> threadPools = new HashMap<>();

    public Map<String, BusinessThreadPool> getThreadPools() {
        return threadPools;
    }

    public void setThreadPools(Map<String, BusinessThreadPool> threadPools) {
        this.threadPools = threadPools;
    }
}
