package com.hanyi.rocket;

import cn.hutool.core.util.IdUtil;
import com.hanyi.rocket.pojo.UserInfo;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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

    /**
     * 发送同步消息
     */
    @Test
    public void sendTest() {
        SendResult sendResult = rocketMQTemplate.syncSend("test-topic-1", "这是一条同步消息");
        System.out.println(sendResult);
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


}
