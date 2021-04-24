package com.hanyi.web.service.impl;

import com.hanyi.web.service.DefaultFunctionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 6:44 下午 2021/4/17
 */
@Service
public class FunctionServiceImpl implements DefaultFunctionService {

    /**
     * 默认接口实现
     *
     * @param name 名称
     * @return 返回默认值
     */
    @Override
    public String function(String name) {
        return "functionServiceImpl";
    }

    /**
     * 默认计算方法
     *
     * @param number 数字
     * @return 返回默认值
     */
    @Override
    public Integer compute(Integer number) {
        return 2;
    }
}
