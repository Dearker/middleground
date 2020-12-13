package com.hanyi.rocket.common.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * SQL消息过滤处理类
 * 注：使用SQL92语法过滤消息，需要在broker中加上enablePropertyFilter=true的配置，否则启动失败
 * </p>
 *
 * @author wenchangwei
 * @since 10:16 上午 2020/12/13
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "test-sql",
        topic = "TopicTest",
        selectorType = SelectorType.SQL92,
        selectorExpression = "a >= 2")
public class SqlMessageComponent implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("这是一条SQL消息：{}", message);
    }

}
