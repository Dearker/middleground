package com.hanyi.rabbit.common.config;

import com.hanyi.rabbit.common.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * <p>
 * 队列路由绑定配置类
 * Queue、Exchange、Binding 会自动创建（在RabbitMQ）不存在的情况下
 * </p>
 *
 * @author wenchangwei
 * @since 9:51 下午 2020/11/16
 */
@Configuration
public class MessageConfig {

    /**
     * 死信队列
     *
     * @return 返回队列对象
     */
    @Bean
    public Queue orderDelayQueue() {
        HashMap<String, Object> arguments = new HashMap<>(RabbitConstant.THREE);
        //指定死信交换机
        arguments.put(RabbitConstant.X_DEAD_LETTER_EXCHANGE, RabbitConstant.ORDER_EVENT_EXCHANGE);
        //指定死信路由key
        arguments.put(RabbitConstant.X_DEAD_LETTER_ROUTING_KEY, RabbitConstant.ORDER_RELEASE_ORDER);
        // 消息过期时间 1分钟
        arguments.put(RabbitConstant.X_MESSAGE_TTL, RabbitConstant.EXPIRE_TIME);

        return new Queue(RabbitConstant.ORDER_DELAY_QUEUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, arguments);
    }

    /**
     * 普通队列
     *
     * @return 返回队列对象
     */
    @Bean
    public Queue orderReleaseQueue() {
        return new Queue(RabbitConstant.ORDER_RELEASE_ORDER_QUEUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
    }

    /**
     * TopicExchange
     *
     * @return 返回交换机对象
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange(RabbitConstant.ORDER_EVENT_EXCHANGE, Boolean.TRUE, Boolean.FALSE);
    }

    /**
     * 创建订单队列绑定到交换机
     *
     * @return 返回绑定对象
     */
    @Bean
    public Binding orderCreateBinding() {
        /*
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey, routingKey中使用#表示通配符，如：order.create.order.#
         * Map<String, Object> arguments
         * */
        return new Binding(RabbitConstant.ORDER_DELAY_QUEUE,
                Binding.DestinationType.QUEUE,
                RabbitConstant.ORDER_EVENT_EXCHANGE,
                RabbitConstant.ORDER_CREATE_ORDER,
                null);
    }

    /**
     * 订单发布队列绑定到交换机
     *
     * @return 返回绑定对象
     */
    @Bean
    public Binding orderReleaseBinding() {
        return new Binding(RabbitConstant.ORDER_RELEASE_ORDER_QUEUE,
                Binding.DestinationType.QUEUE,
                RabbitConstant.ORDER_EVENT_EXCHANGE,
                RabbitConstant.ORDER_RELEASE_ORDER,
                null);
    }

}
