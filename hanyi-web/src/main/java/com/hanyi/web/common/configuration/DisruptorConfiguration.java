package com.hanyi.web.common.configuration;

import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.web.bo.MessageModel;
import com.hanyi.web.common.factory.MessageEventFactory;
import com.hanyi.web.common.handler.ParallelWorkHandler;
import com.hanyi.web.common.handler.RepeatEventHandler;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/10 10:33 AM
 */
@Configuration
public class DisruptorConfiguration {

    /**
     * 重复消费环形数组
     *
     * @return 返回数组对象
     */
    @Bean
    public RingBuffer<MessageModel> repeatRingBuffer() {
        //指定事件工厂
        MessageEventFactory factory = new MessageEventFactory();

        //指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        int bufferSize = 1024 * 1024;

        ThreadFactory threadFactory = ThreadUtil.newNamedThreadFactory("messageModel-", true);
        //单线程模式，获取额外的性能
        Disruptor<MessageModel> disruptor = new Disruptor<>(factory, bufferSize, threadFactory,
                ProducerType.SINGLE, new BusySpinWaitStrategy());

        //设置事件业务处理器---消费者，开启2个消费者
        disruptor.handleEventsWith(new RepeatEventHandler(), new RepeatEventHandler());

        // 启动disruptor线程
        disruptor.start();

        //获取ringbuffer环，用于接取生产者生产的事件
        return disruptor.getRingBuffer();
    }

    /**
     * 并发消费环形数组
     *
     * @return 返回数组对象
     */
    @Bean
    public RingBuffer<MessageModel> parallelRingBuffer() {
        //指定事件工厂
        MessageEventFactory factory = new MessageEventFactory();

        //指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        int bufferSize = 1024 * 1024;

        ThreadFactory threadFactory = ThreadUtil.newNamedThreadFactory("messageModel-", true);
        //单线程模式，获取额外的性能
        Disruptor<MessageModel> disruptor = new Disruptor<>(factory, bufferSize, threadFactory,
                ProducerType.MULTI, new BusySpinWaitStrategy());

        //设置事件业务处理器---消费者;开启10个消费者
        ParallelWorkHandler[] parallelWorkHandlers = IntStream.range(0, 10).mapToObj(s -> new ParallelWorkHandler())
                .toArray(ParallelWorkHandler[]::new);
        disruptor.handleEventsWithWorkerPool(parallelWorkHandlers);

        // 启动disruptor线程
        disruptor.start();

        //获取ringbuffer环，用于接取生产者生产的事件
        return disruptor.getRingBuffer();
    }

}
