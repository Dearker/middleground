package com.hanyi.sharding.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 分页实体类
 * </p>
 *
 * @author wenchangwei
 * @since 12:24 下午 2020/12/5
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = 8094027755976938731L;

    /**
     * 总数
     */
    private Long total;

    /**
     * 返回的数据
     */
    private List<T> recordList;

}
