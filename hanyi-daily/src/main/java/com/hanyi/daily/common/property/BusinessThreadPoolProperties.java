package com.hanyi.daily.common.property;

import com.hanyi.daily.pojo.BusinessThreadPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * <p>
 * 业务线程池属性类
 * </p>
 *
 * @author wenchangwei
 * @since 8:51 下午 2020/7/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessThreadPoolProperties {

    /**
     * 线程池对象集合
     */
    private Map<String, BusinessThreadPool> threadPools;

}
