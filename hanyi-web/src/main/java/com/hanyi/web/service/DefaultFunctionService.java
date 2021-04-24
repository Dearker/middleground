package com.hanyi.web.service;

/**
 * <p>
 * 默认方法服务，该方式可以简化适配器模式，接口不在需要抽象类实现接口的所有方法并设置默认属性
 * </p>
 *
 * @author wenchangwei
 * @since 6:32 下午 2021/4/17
 */
public interface DefaultFunctionService {

    /**
     * 默认接口实现
     *
     * @param name 名称
     * @return 返回默认值
     */
    default String function(String name) {
        return "default";
    }

    /**
     * 默认计算方法
     *
     * @param number 数字
     * @return 返回默认值
     */
    default Integer compute(Integer number) {
        return 1;
    }

}
