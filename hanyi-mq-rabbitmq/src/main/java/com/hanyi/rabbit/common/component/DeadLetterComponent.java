package com.hanyi.rabbit.common.component;

import com.hanyi.rabbit.bo.Person;
import com.hanyi.rabbit.common.constant.RabbitConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 死信队列监听组件类
 * </p>
 *
 * @author wenchangwei
 * @since 10:24 下午 2020/11/16
 */
@Slf4j
@Component
public class DeadLetterComponent {

    @RabbitListener(queues = RabbitConstant.ORDER_RELEASE_ORDER_QUEUE)
    public void messageHandler(Person person, Message message, Channel channel) {
        log.info("获取到的死信消息为：{}", person);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            channel.basicAck(deliveryTag, Boolean.FALSE);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
