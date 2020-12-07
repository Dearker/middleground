package com.hanyi.rabbit.common.component;

import com.hanyi.rabbit.bo.Person;
import com.hanyi.rabbit.bo.PersonInfo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 消息处理类
 * </p>
 *
 * @author wenchangwei
 * @since 7:19 下午 2020/11/15
 */
@Slf4j
@RabbitListener(queues = {"hello-java-queue"})
@Component
public class HandlerComponent {

    @RabbitHandler
    public void personHandler(Person person, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //开启事务模式
            channel.txSelect();

            //参数二表示是否批量签收消息
            channel.basicAck(deliveryTag, Boolean.FALSE);

            //提交事务
            channel.txCommit();
        } catch (IOException e) {
            //回滚
            channel.txRollback();
            log.error(e.getMessage());
        }

        log.info("获取到的用户对象：{}", person);
    }

    @RabbitHandler
    public void personInfoHandler(PersonInfo personInfo, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //参数二表示是否批量签收消息
            channel.basicAck(deliveryTag, Boolean.FALSE);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("获取到的用户详情对象：{}", personInfo);
    }

    @RabbitHandler
    public void stringHandler(String msg, Message message, Channel channel) {

        log.info("获取的消息：{}", msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //参数二表示是否批量签收消息
            channel.basicAck(deliveryTag, Boolean.FALSE);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
