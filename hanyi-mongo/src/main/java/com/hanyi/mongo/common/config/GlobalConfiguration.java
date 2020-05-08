package com.hanyi.mongo.common.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @PackAge: middleground com.hanyi.mongo.common.config
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-08 21:24
 * @Version: 1.0
 */
@Configuration
public class GlobalConfiguration {

    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1, 1);
    }

}
