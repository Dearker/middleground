package com.hanyi.kafka.interceptor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.stereotype.Component;

/**
 * <p>
 * kafka消息拦截器
 * </p>
 *
 * @author wenchangwei
 * @since 5:09 下午 2020/5/31
 */
@Component
public class KafkaRecordInterceptor implements RecordInterceptor<String,String> {

    @Override
    public ConsumerRecord<String, String> intercept(ConsumerRecord<String, String> consumerRecord) {
        return new ConsumerRecord<>(consumerRecord.topic(), consumerRecord.partition(),
                consumerRecord.offset(), consumerRecord.key(), consumerRecord.value());
    }
}
