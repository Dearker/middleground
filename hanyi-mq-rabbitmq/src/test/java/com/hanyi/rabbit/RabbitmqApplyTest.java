package com.hanyi.rabbit;

import com.hanyi.rabbit.bo.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * rabbitmq测试类
 * </p>
 *
 * @author wenchangwei
 * @since 5:05 下午 2020/11/15
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplyTest {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 1、如果创建Exchange、Queue、Binding
     * 	① 使用AmqpAdmin进行创建
     * 2、如何收发消息
     */
    @Test
    public void creatExchange() {

        Exchange exchange = new DirectExchange("hello-java-exchange", true, false);
        amqpAdmin.declareExchange(exchange);

        log.info("Exchange[{}]创建成功！", "hello-java-exchange");
    }

    /**
     * 创建队列
     */
    @Test
    public void createQueue() {

        Queue queue = new Queue("hello-java-queue", true, false, false);
        amqpAdmin.declareQueue(queue);

        log.info("Queue[{}]创建成功！", "hello-java-queue");
    }

    /**
     * 创建Binding
     */
    @Test
    public void createBinding() {

        // Binding(String destination,  【目的地（交换机或者Queue)】
        // DestinationType destinationType, 【类型 （Exhange、Queue）】
        // String exchange, 【交换机】
        // String routingKey, 【路由键】
        // Map<String, Object> arguments)
        Binding binding = new Binding("hello-java-queue",
                Binding.DestinationType.QUEUE,
                "hello-java-exchange",
                "hello.java",
                null);
        amqpAdmin.declareBinding(binding);

        log.info("Binding[{}]创建成功！", "binding");
    }


    /**
     * 发送消息
     * 如果发送的消息是个对象，会使用序列化机制，将对象写出去，所以对象必须实现Serializable
     */
    @Test
    public void sendMessage() {
        for (int i = 0; i < 10; i++) {
            Person person = new Person();
            person.setUserName("哈士奇"+i);
            person.setPassword("123456"+i);
            rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java", person);
            log.info("消息发送成功！");
        }
    }

    /**
     * 发送单条消息
     */
    @Test
    public void send(){
        rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java", "柯基小短腿");
        log.info("消息发送成功");
    }


}
