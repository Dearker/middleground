package com.hanyi.web.bo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * 邮箱事件
 * </p>
 *
 * @author wenchangwei
 * @since 2021/4/30 10:19 下午
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class EmailEvent extends ApplicationEvent {

    private static final long serialVersionUID = -4191436365959640079L;

    /**
     * 消息内容
     */
    @Getter
    @Setter
    private String context;

    public EmailEvent(Object source, String context) {
        super(source);
        this.context = context;
    }

}
