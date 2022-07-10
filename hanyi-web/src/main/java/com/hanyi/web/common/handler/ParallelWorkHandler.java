package com.hanyi.web.common.handler;

import com.hanyi.web.bo.MessageModel;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 该消费者处理器效果：并行消费，即多个消费者不会进行重复消费
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/10 10:29 AM
 */
@Slf4j
public class ParallelWorkHandler implements WorkHandler<MessageModel> {

    @Override
    public void onEvent(MessageModel messageModel) throws Exception {
        log.info("并行消费消息：{}", messageModel.getMessage());
    }

}
