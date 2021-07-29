package com.hanyi.rocket;

import cn.hutool.core.util.IdUtil;
import com.hanyi.rocket.pojo.UserInfo;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * rocket 测试类
 * </p>
 *
 * @author wenchangwei
 * @since 8:56 下午 2020/12/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketAppTest {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private DefaultMQProducer defaultMQProducer;

    /**
     * 发送同步消息
     */
    @Test
    public void sendTest() {
        SendResult sendResult = rocketMQTemplate.syncSend("test-topic-1", "这是一条同步消息");
        System.out.println(sendResult);
    }

    /**
     * 批量发送测试
     *
     * @throws Exception 异常
     */
    @Test
    public void batchSendTest() throws Exception {
        final int total = 50;
        String messageStr = "test-message-";
        List<org.apache.rocketmq.common.message.Message> messageList = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            org.apache.rocketmq.common.message.Message message = new org.apache.rocketmq.common.message.Message();
            message.setTopic("test-topic-1");
            String s = messageStr + i;
            message.setBody(s.getBytes(RemotingHelper.DEFAULT_CHARSET));
            messageList.add(message);
        }

        defaultMQProducer.send(messageList);
    }

    @Test
    public void sendTimeTest() throws Exception{
        final int total = 5;

        String messageStr = "test-message-";
        for (int i = 0; i < total; i++) {
            final int batchTotal = 300;
            List<org.apache.rocketmq.common.message.Message> messageList = new ArrayList<>(batchTotal);
            int start = i * batchTotal;
            for (int j = 0; j < batchTotal; j++) {
                org.apache.rocketmq.common.message.Message message = new org.apache.rocketmq.common.message.Message();
                message.setTopic("test-topic-1");
                int startIndex = start + j;
                String s = messageStr + startIndex;
                message.setBody(s.getBytes(RemotingHelper.DEFAULT_CHARSET));
                messageList.add(message);
            }
            defaultMQProducer.send(messageList);
            //休眠50毫秒
            //TimeUnit.MILLISECONDS.sleep(50);
        }
    }

    /**
     * 发送异步消息
     */
    @Test
    public void sendAsyncMsg() {
        rocketMQTemplate.asyncSend("test-topic-1", MessageBuilder.withPayload("这是一个异步消息").build(),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        // 处理消息发送成功逻辑
                        System.out.println(sendResult);
                    }

                    @Override
                    public void onException(Throwable e) {
                        // 处理消息发送异常逻辑
                        System.out.println(e.getMessage());
                    }
                });
    }

    /**
     * 单向消息
     */
    @Test
    public void testOneWay() {
        rocketMQTemplate.sendOneWay("test-topic-1", "这是一条单向消息");
    }

    /**
     * 同步顺序消息[异步顺序 单向顺序写法类似]
     */
    @Test
    public void testSyncSendOrderly() {
        //第三个参数用于队列的选择
        rocketMQTemplate.syncSendOrderly("test-topic-1", "这是一条异步顺序消息", "xxxx");
    }

    /**
     * 发送半事务消息
     */
    @Test
    public void testSendMessageInTransaction() {
        UserInfo userInfo = new UserInfo(1, "柯基", 12);

        //发送半事务消息
        rocketMQTemplate.sendMessageInTransaction("tx_producer_group", "tx_topic",
                MessageBuilder.withPayload(userInfo)
                        .setHeader("txId", IdUtil.fastSimpleUUID()).build(), userInfo);
    }

    /**
     * 延迟消息
     * 默认messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * 配置项从1级开始各级延时的时间，如1表示延时1s，2表示延时5s，14表示延时10m，
     * 时间单位支持：s、m、h、d，分别表示秒、分、时、天；可手工调整指定级别的延时时间同时需要修改时间对应的level级别的值
     */
    @Test
    public void delayTimeTest() {
        Message<String> message = MessageBuilder.withPayload("这是一个延迟消息").build();
        SendResult sendResult = rocketMQTemplate.syncSend("test-topic-1", message, 200, 3);
        System.out.println(sendResult);
    }

    /**
     * 使用tag过滤消息
     */
    @Test
    public void filterMessageTest(){
        Message<String> message = MessageBuilder.withPayload("这是一个tag消息").build();
        SendResult sendResult = rocketMQTemplate.syncSend("test-topic-1:tag1", message, 200);
        System.out.println(sendResult);
    }

    /**
     * 使用sql语法进行消息过滤
     */
    @Test
    public void sqlMessageTest() throws Exception {
        for (int i = 0; i < 3; i++) {
            org.apache.rocketmq.common.message.Message message = new
                    org.apache.rocketmq.common.message.Message("TopicTest",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            message.putUserProperty("a", String.valueOf(i));
            SendResult sendResult = defaultMQProducer.send(message);
            System.out.println(sendResult);
        }
    }

}
