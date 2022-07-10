package com.hanyi.web.common.handler;

import com.hanyi.web.bo.MessageModel;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 该消费者处理器效果：多个消费者时，每个消费者都会重复消费，类似于MQ的广播消息。如果环形数组存储满了，
 * 则生产者需要等所有消费者消费完成后才能写入数据
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/10 10:21 AM
 */
@Slf4j
public class RepeatEventHandler implements EventHandler<MessageModel> {

    @Override
    public void onEvent(MessageModel event, long sequence, boolean endOfBatch) throws Exception {
        try {
            //这里停止1000ms是为了确定消费消息是异步的
            log.info("消费者处理消息开始");
            if (event != null) {
                log.info("消费者消费的信息是：{}", event.getMessage());
            }
        } catch (Exception e) {
            log.info("消费者处理消息失败");
        }
    }

}
