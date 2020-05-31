package com.hanyi.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * kafka消息统计拦截器
 * </p>
 *
 * @author wenchangwei
 * @since 3:06 下午 2020/5/31
 */
@Slf4j
@Component
public class CounterProducerInterceptor implements ProducerInterceptor<String, String> {

    private static final AtomicInteger SUCCESS_ATOMIC_INTEGER = new AtomicInteger(0);

    private static final AtomicInteger ERROR_ATOMIC_INTEGER = new AtomicInteger(0);

    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if (Objects.isNull(e)) {
            SUCCESS_ATOMIC_INTEGER.incrementAndGet();
        } else {
            ERROR_ATOMIC_INTEGER.incrementAndGet();
        }
    }

    @Override
    public void close() {
        log.info("成功发送消息的总数：" + SUCCESS_ATOMIC_INTEGER.get());
        log.info("失败发送消息的总数：" + ERROR_ATOMIC_INTEGER.get());
    }


}
