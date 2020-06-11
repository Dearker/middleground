package com.hanyi.hikari.common.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:18 下午 2020/6/8
 */
@Configuration
public class GlobalConfiguration {

    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1, 1);
    }

}
