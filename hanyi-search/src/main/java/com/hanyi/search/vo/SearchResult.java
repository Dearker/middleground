package com.hanyi.search.vo;

import com.hanyi.search.bo.SkuEsModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 返回给前台页面显示的结果集封装对象
 * </p>
 *
 * @author wenchangwei
 * @since 8:45 下午 2020/11/10
 */
@Data
public class SearchResult implements Serializable {

    private static final long serialVersionUID = -1668789352521910889L;

    /**
     * 查询到的商品相关信息
     */
    private List<SkuEsModel> products;

    /**
     * 当前页面
     */
    private Integer pageNum;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页码
     */
    private Integer totalPages;

    /**
     * 用于遍历页码
     */
    private List<Integer> pageNavs;

    /**
     * 当前查询到的结果所涉及的所有的品牌
     */
    private List<BrandVo> brands;

    /**
     * 当前查询到的结果所涉及的所有属性
     */
    private List<AttrVo> attrs;

    /**
     * 当前查询到的结果所涉及的所有分类
     */
    private List<CatalogVo> catalogs;

    /**
     * 面包屑导航数据
     */
    private List<NavVo> navs = new ArrayList<>();

    private List<Long> attrIds = new ArrayList<>();

    @Data
    public static class NavVo implements Serializable {

        private static final long serialVersionUID = 1879135869433767032L;
        private String navName;

        private String navValue;

        private String link;
    }

    /* ================================= 以上的返回给页面显示的数据 ================================= */

    @Data
    public static class BrandVo implements Serializable {

        private static final long serialVersionUID = 5572636771596438821L;

        private Long brandId;

        private String brandName;

        private String brandImg;
    }

    @Data
    public static class AttrVo implements Serializable {

        private static final long serialVersionUID = 4865562878195574211L;

        private Long attrId;

        private String attrName;

        private List<String> attrValue;
    }

    @Data
    public static class CatalogVo implements Serializable {

        private static final long serialVersionUID = -6276055120757281061L;

        private Long catalogId;

        private String catalogName;
    }

}
