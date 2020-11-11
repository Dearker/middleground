package com.hanyi.search.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 封装页面所有可能传递过来的检索字段
 * </p>
 *
 * @author wenchangwei
 * @since 8:51 下午 2020/11/10
 */
@Data
public class SearchParam implements Serializable {

    private static final long serialVersionUID = 2760351403109836419L;

    /**
     * 全文匹配关键字
     */
    private String keyword;

    /**
     * 三级分类id
     */
    private Long catalog3Id;

    /**
     * saleCount_asc/desc
     * skuPrice_asc/desc
     * hotScore_asc/desc
     * 排序
     */
    private String sort;

    /**
     * 是否只显示有货 0 无库存 1 有库存
     */
    private Integer hasStock;

    /**
     * 价格区间
     */
    private String skuPrice;

    /**
     * 品牌id
     */
    private List<Long> brandId;

    /**
     * 规格属性
     */
    private List<String> attrs;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 查询字符串
     */
    private String queryString;

}
