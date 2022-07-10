package com.hanyi.web.service;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/10 10:41 AM
 */
public interface DisruptorService {

    /**
     * 发送消息
     *
     * @param message 消息对象
     */
    void sendMessageModel(String message);

    /**
     * 并行发送消息模型
     *
     * @param message 消息
     */
    void sendParallelMessageModel(String message);

}
