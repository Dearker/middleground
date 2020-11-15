package com.hanyi.kafka.component;

import com.hanyi.kafka.constant.KafkaConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * <p>
 * kafka消费者
 * </p>
 *
 * @author wenchangwei
 * @since 5:51 下午 2020/5/30
 */
@Slf4j
@Component
public class KafkaConsumerComponent {

    @KafkaListener(topics = KafkaConst.TOPIC_TEST, groupId = KafkaConst.GROUP_ID_DEMO, containerFactory = "ackContainerFactory")
    public void handleMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        try {
            String message = consumerRecord.value();
            log.info("收到消息: {}", message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
        }
    }

}
