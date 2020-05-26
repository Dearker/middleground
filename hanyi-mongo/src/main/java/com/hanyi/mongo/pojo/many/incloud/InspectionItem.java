package com.hanyi.mongo.pojo.many.incloud;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 巡检模板详情实体类
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 11:58 2020/5/25
 */
@Data
@Builder
public class InspectionItem implements Serializable {

    private static final long serialVersionUID = 6388920217988858703L;

    /**
     * 模板详情id
     */
    private String templateItemId;

    /**
     * 模板详情名称
     */
    private String templateItemName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;
}
