package com.hanyi.daily.common.strategy.impl;

import com.hanyi.daily.common.strategy.SearchTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 8:11 下午 2021/3/18
 */
@Service("one")
public class SearchOneServiceImpl implements SearchTypeService {
    /**
     * 搜索类型方法
     *
     * @return 返回搜索结果
     */
    @Override
    public int search() {
        return 1;
    }
}
