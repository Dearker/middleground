package com.hanyi.web.bo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * 微信事件实体类
 * </p>
 *
 * @author wenchangwei
 * @since 2021/4/30 10:11 下午
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class WeCatEvent extends ApplicationEvent {

    private static final long serialVersionUID = -7296642387465438563L;

    /**
     * 消息内容
     */
    @Getter
    @Setter
    private String context;

    public WeCatEvent(Object source, String context) {
        super(source);
        this.context = context;
    }

}
