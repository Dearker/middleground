package com.hanyi.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>
 * kafka生产者拦截器
 * </p>
 *
 * @author wenchangwei
 * @since 2:25 下午 2020/5/31
 */
@Component
public class KafkaProducerInterceptor implements ProducerInterceptor<String, String> {

    /**
     * 拦截消息处理
     *
     * @param producerRecord 接收发送的消息
     * @return 返回处理后的消息
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        //处理消息后，重新创建一个消息
        String value = System.currentTimeMillis() + ":" + producerRecord.value();
        return new ProducerRecord<>(producerRecord.topic(),
                producerRecord.partition(), producerRecord.key(), value);
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
