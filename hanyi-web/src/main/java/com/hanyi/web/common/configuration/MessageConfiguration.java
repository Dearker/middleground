package com.hanyi.web.common.configuration;

import com.hanyi.web.common.annotation.ConditionOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 条件消息配置类
 * </p>
 *
 * @author wenchangwei
 * @since 10:59 下午 2021/4/12
 */
@Configuration
public class MessageConfiguration {

    @Bean("message")
    @ConditionOnProperty(name = "language", value = "zh")
    public String chineseMessage() {
        return "你好，世界";
    }

    @Bean("message")
    @ConditionOnProperty(name = "language", value = "en")
    public String englishMessage() {
        return "hello，world";
    }

}
