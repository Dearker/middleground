package com.hanyi.kafka.interceptor;

import cn.hutool.core.util.ClassUtil;
import com.hanyi.kafka.KafkaApplicationTest;
import org.apache.kafka.clients.producer.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 原生kafka拦截器
 * </p>
 *
 * @author wenchangwei
 * @since 5:22 下午 2020/5/31
 */
public class ProducerInterceptorTest extends KafkaApplicationTest {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void producerTest() {

        // 1 设置配置信息
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // 2 构建拦截链
        Map<String, ProducerInterceptor> interceptorMap = applicationContext.getBeansOfType(ProducerInterceptor.class);
        List<String> interceptorList = new ArrayList<>(interceptorMap.size());

        interceptorMap.values().forEach(s -> interceptorList.add(ClassUtil.getClassName(s, false)));
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptorList);

        Producer<String, String> producer = new KafkaProducer<>(props);

        // 3 发送消息
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("test", "message" + i);
            producer.send(record);
        }
        // 4 一定要关闭producer，这样才会调用interceptor的close方法
        producer.close();
    }

}
