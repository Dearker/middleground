package com.hanyi.daily.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 4:29 下午 2020/7/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostInfo {

    private int id;

    private BigDecimal cost;

    public CostInfo(BigDecimal cost) {
        this.cost = cost;
    }
}
