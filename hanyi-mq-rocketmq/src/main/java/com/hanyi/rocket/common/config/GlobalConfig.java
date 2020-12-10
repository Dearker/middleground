package com.hanyi.rocket.common.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 全局配置类
 * </p>
 *
 * @author wenchangwei
 * @since 8:59 下午 2020/12/9
 */
@Configuration
public class GlobalConfig {

    /**
     * 分布式id
     *
     * @return 返回id对象
     */
    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1, 1);
    }

}
