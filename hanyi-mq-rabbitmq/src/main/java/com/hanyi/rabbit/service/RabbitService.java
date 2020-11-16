package com.hanyi.rabbit.service;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 7:13 下午 2020/11/15
 */
public interface RabbitService {

    /**
     * 发送消息条数
     *
     * @param number 条数
     * @return 返回条数
     */
    int sendMessage(Integer number);

    /**
     * 发送死信队列
     *
     * @param number 消息条数
     * @return 返回条数
     */
    int sendDeadMessage(Integer number);

}
