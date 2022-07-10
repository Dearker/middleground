package com.hanyi.web.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 事件消息体
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/10 10:18 AM
 */
@Data
public class MessageModel implements Serializable {

    private static final long serialVersionUID = -829754638376492874L;

    /**
     * 消息
     */
    private String message;

}
