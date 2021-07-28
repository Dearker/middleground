package com.hanyi.rocket.common.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/7/28 8:01 下午
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "test-consumer-1")
public class TestMessageComponent implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("获取的测试消息：" + message);
    }

}
