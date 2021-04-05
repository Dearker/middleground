package com.hanyi.daily.pojo;

import lombok.*;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * 消息事件传输对象
 * </p>
 *
 * @author wenchangwei
 * @since 11:47 上午 2021/4/4
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class MessageEvent extends ApplicationEvent {

    private static final long serialVersionUID = -8731730007722456236L;

    @Getter
    @Setter
    private String name;

    public MessageEvent(Object source, String name) {
        super(source);
        this.name = name;
    }
}
