package com.hanyi.web.common.factory;

import com.hanyi.web.bo.MessageModel;
import com.lmax.disruptor.EventFactory;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/10 10:20 AM
 */
public class MessageEventFactory implements EventFactory<MessageModel> {

    @Override
    public MessageModel newInstance() {
        return new MessageModel();
    }

}
