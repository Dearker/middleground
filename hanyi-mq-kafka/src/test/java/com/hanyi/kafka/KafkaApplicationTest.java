package com.hanyi.kafka;

import com.hanyi.kafka.constant.KafkaConst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * <p>
 * kafka 测试类
 * </p>
 *
 * @author wenchangwei
 * @since 4:49 下午 2020/5/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaApplicationTest {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 测试发送消息
     */
    @Test
    public void testSend() {
        kafkaTemplate.send(KafkaConst.TOPIC_TEST, "hello,kafka...");
    }

}
