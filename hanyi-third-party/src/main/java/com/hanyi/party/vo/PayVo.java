package com.hanyi.party.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 付款实体类
 * </p>
 *
 * @author wenchangwei
 * @since 8:27 下午 2020/11/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PayVo implements Serializable {

    private static final long serialVersionUID = -4304126471114417946L;

    /**
     * 商户订单号 必填
     */
    private String outTradeNo;

    /**
     * 订单名称 必填
     */
    private String subject;

    /**
     * 付款金额 必填
     */
    private String totalAmount;

    /**
     * 商品描述 可空
     */
    private String body;
}
