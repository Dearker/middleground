package com.hanyi.web.service.impl;

import com.hanyi.web.bo.MessageModel;
import com.hanyi.web.service.DisruptorService;
import com.lmax.disruptor.RingBuffer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发送消息实现层
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/10 10:48 AM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DisruptorServiceImpl implements DisruptorService {

    /**
     * 重复循环缓冲区
     */
    private final RingBuffer<MessageModel> repeatRingBuffer;

    /**
     * 并行循环缓冲区
     */
    private final RingBuffer<MessageModel> parallelRingBuffer;

    /**
     * 发送消息
     *
     * @param message 消息对象
     */
    @Override
    public void sendMessageModel(String message) {
        this.sendMessage(repeatRingBuffer, message);
    }

    /**
     * 并行发送消息模型
     *
     * @param message 消息
     */
    @Override
    public void sendParallelMessageModel(String message) {
        this.sendMessage(parallelRingBuffer, message);
    }

    private void sendMessage(RingBuffer<MessageModel> ringBuffer, String message) {
        log.info("record the message: {}", message);
        //获取下一个Event槽的下标
        long sequence = ringBuffer.next();
        try {
            //给Event填充数据
            MessageModel event = ringBuffer.get(sequence);
            event.setMessage(message);
            log.info("往消息队列中添加消息：{}", event);
        } catch (Exception e) {
            log.error("failed to add event to messageModelRingBuffer for : e = {},{}", e, e.getMessage());
        } finally {
            //发布Event，激活观察者去消费，将sequence传递给改消费者
            //注意最后的publish方法必须放在finally中以确保必须得到调用；如果某个请求的sequence未被提交将会堵塞后续的发布操作或者其他的producer
            ringBuffer.publish(sequence);
        }
    }

}
