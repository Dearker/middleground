package com.hanyi.server.protocoltcp;

import lombok.Data;

/**
 * <p>
 * 协议包
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/31 11:05 AM
 */
@Data
public class MessageProtocol {

    //关键
    private int len;

    private byte[] content;
}
