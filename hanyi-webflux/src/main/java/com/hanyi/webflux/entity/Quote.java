package com.hanyi.webflux.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;

/**
 * <p>
 * 股票报价实体类
 * </p>
 *
 * @author wenchangwei
 * @since 10:40 上午 2020/11/22
 */
@Data
@NoArgsConstructor
public class Quote implements Serializable {

    private static final MathContext MATH_CONTEXT = new MathContext(Short.BYTES);

    private static final long serialVersionUID = -4909399557577112277L;

    private String ticker;

    private BigDecimal price;

    private Instant instant;

    public Quote(String ticker, BigDecimal price) {
        this.ticker = ticker;
        this.price = price;
    }

    public Quote(String ticker, Double price) {
        this(ticker, new BigDecimal(price, MATH_CONTEXT));
    }


}
