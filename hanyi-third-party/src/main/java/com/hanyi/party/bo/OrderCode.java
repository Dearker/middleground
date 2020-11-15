package com.hanyi.party.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 消息返回状态实体类
 * </p>
 *
 * @author wenchangwei
 * @since 8:27 下午 2020/11/14
 */
@Data
public class OrderCode implements Serializable {

    private static final long serialVersionUID = 5879108105251564766L;

    /**
     * 返回状态码
     */
    private String returnCode;

    /**
     * 订单id
     */
    private String orderId;

}
