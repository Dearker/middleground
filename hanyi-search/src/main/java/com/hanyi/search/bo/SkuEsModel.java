package com.hanyi.search.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * sku在es保存的数据模型
 * </p>
 *
 * @author wenchangwei
 * @since 11:50 上午 2020/11/1
 */
@Data
public class SkuEsModel implements Serializable {

    private static final long serialVersionUID = -6554546859046772253L;

    private Long skuId;

    private Long spuId;

    private String skuTitle;

    private BigDecimal skuPrice;

    private String skuImg;

    private Long saleCount;

    /**
     * 是否有库存
     */
    private Boolean hasStock;

    /**
     * 热度评分
     */
    private Long hotScore;

    private Long brandId;

    private Long catalogId;

    private String brandName;

    private String brandImg;

    private String catalogName;

    private List<Attrs> attrs;


    @Data
    public static class Attrs implements Serializable{

        private static final long serialVersionUID = 1594159709283671712L;

        private Long attrId;

        private String attrName;

        private String attrValue;
    }

}
