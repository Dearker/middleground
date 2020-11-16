package com.hanyi.rabbit.common.constant;

/**
 * <p>
 * rabbit常量类
 * </p>
 *
 * @author wenchangwei
 * @since 9:57 下午 2020/11/16
 */
public class RabbitConstant {

    private RabbitConstant() {
    }

    /**
     * 死信交换机参数名称
     */
    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

    /**
     * 死信路由key参数名称
     */
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * 死信消息过期时间参数名称
     */
    public static final String X_MESSAGE_TTL = "x-message-ttl";

    /**
     * 订单事件交换机
     */
    public static final String ORDER_EVENT_EXCHANGE = "order-event-exchange";

    /**
     * 订单发布路由key
     */
    public static final String ORDER_RELEASE_ORDER = "order.release.order";

    /**
     * 过期时间
     */
    public static final int EXPIRE_TIME = 60000;

    /**
     * 死信队列名称
     */
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";

    /**
     * 订单发布队列名称
     */
    public static final String ORDER_RELEASE_ORDER_QUEUE = "order.release.order.queue";

    /**
     * 订单创建路由key
     */
    public static final String ORDER_CREATE_ORDER = "order.create.order";

    /**
     * 初始容量
     */
    public static final int THREE = 3;

}
