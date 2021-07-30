package com.hanyi.rocket.common.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.rocket.common.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 全局配置类
 * </p>
 *
 * @author wenchangwei
 * @since 8:59 下午 2020/12/9
 */
@Slf4j
@Configuration
public class GlobalConfig {

    /**
     * 线程池的大小
     */
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    @Value("${rocketmq.name-server}")
    private String nameServerAddress;

    /**
     * 分布式id
     *
     * @return 返回id对象
     */
    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1, 1);
    }

    @Bean
    public MQConsumer mqConsumer() throws MQClientException {
        //instance
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        //group
        consumer.setConsumerGroup("test-consumer-2");
        //setNamesrvAddr，cluster with ; spit
        consumer.setNamesrvAddr(nameServerAddress);

        //consumer offset
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //subscribe, the subExpression is tags in message send
        //subscribe topic store in map
        consumer.subscribe("test-topic-1", "*");
        //can subscribe more
        //consumer.subscribe("demoTopic2", "*");
        //or use setSubscription, method is deprecated
        //consumer.setSubscription();

        consumer.setConsumeMessageBatchMaxSize(MessageConstant.MESSAGE_BATCH_SIZE);
        //一次拉取1000条消息
        consumer.setPullBatchSize(MessageConstant.MESSAGE_BATCH_SIZE);
        //min thread
        consumer.setConsumeThreadMin(30);
        consumer.setConsumeThreadMax(64);

        LinkedList<List<String>> totalMessageList = new LinkedList<>();

        List<String> messageList = new ArrayList<>(MessageConstant.MESSAGE_BATCH_SIZE);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            List<String> realMessageList = list.stream().map(s -> {
                try {
                    return new String((s.getBody()), RemotingHelper.DEFAULT_CHARSET);
                } catch (UnsupportedEncodingException e) {
                    log.error("",e);
                    return null;
                }
            }).filter(StrUtil::isNotBlank).collect(Collectors.toList());
            int messageSize = realMessageList.size();
            atomicInteger.getAndAdd(messageSize);

            int dataTotal = atomicInteger.get();
            if (dataTotal == MessageConstant.MESSAGE_BATCH_SIZE){
                totalMessageList.add(messageList);
                messageList.clear();
                atomicInteger.set(0);
            } else {
                if(dataTotal > MessageConstant.MESSAGE_BATCH_SIZE){
                    //超出的总数
                    int overStepTotal =  dataTotal - MessageConstant.MESSAGE_BATCH_SIZE;
                    List<String> subList = realMessageList.subList(0, overStepTotal);
                    List<String> matchList = realMessageList.subList(overStepTotal, messageSize);
                    messageList.addAll(matchList);
                    totalMessageList.add(messageList);
                    messageList.clear();
                    messageList.addAll(subList);
                    atomicInteger.set(0);
                } else {
                    messageList.addAll(realMessageList);
                }
            }

            log.info("获取的监听消息：" + realMessageList);
            log.info("消息总数为：" + realMessageList.size());
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        AtomicInteger threadNumber = new AtomicInteger(0);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(THREAD_POOL_SIZE, r -> {
            Thread thread = new Thread(r);
            //设置为守护线程
            thread.setDaemon(true);
            thread.setName("scheduled_" + threadNumber.incrementAndGet());
            return thread;
        });

        executorService.scheduleAtFixedRate(() -> {
            List<String> pollList = totalMessageList.poll();
            if(CollUtil.isNotEmpty(pollList)){
                log.info("当前消息的数组总数为：" + pollList.size());
            }
        },2,100, TimeUnit.MILLISECONDS);

        consumer.start();
        return consumer;
    }

}
