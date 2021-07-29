package com.hanyi.rocket.common.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * <p>
 * 通过注解的方式一次只能消费一条消息，如果同一个topic指定了多个消费组，消费的消息是同一份，即重复消费
 * </p>
 *
 * @author wenchangwei
 * @since 2021/7/28 8:01 下午
 */
@Slf4j
//@Component
//@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "test-consumer-1")
public class TestMessageComponent implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("获取的测试消息：" + message);
    }

}
