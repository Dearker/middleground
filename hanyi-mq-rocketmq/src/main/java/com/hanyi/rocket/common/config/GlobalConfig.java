package com.hanyi.rocket.common.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 全局配置类
 * </p>
 *
 * @author wenchangwei
 * @since 8:59 下午 2020/12/9
 */
@Slf4j
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

    @Bean
    public MQConsumer mqConsumer() throws MQClientException {

        //instance
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        //group
        consumer.setConsumerGroup("test-consumer-2");
        //setNamesrvAddr，cluster with ; spit
        consumer.setNamesrvAddr("114.67.102.117");

        //consumer offset
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //subscribe, the subExpression is tags in message send
        //subscribe topic store in map
        consumer.subscribe("test-topic-1", "*");
        //can subscribe more
        //consumer.subscribe("demoTopic2", "*");
        //or use setSubscription, method is deprecated
        //consumer.setSubscription();

        //batch consumer max message limit
        consumer.setConsumeMessageBatchMaxSize(1000);
        //min thread
        consumer.setConsumeThreadMin(30);
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            List<String> collect = list.stream().map(s -> Arrays.toString(s.getBody())).collect(Collectors.toList());
            log.info("获取的监听消息：" + collect);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();

        return consumer;
    }

}
