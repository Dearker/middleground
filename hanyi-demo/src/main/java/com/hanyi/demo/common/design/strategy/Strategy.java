package com.hanyi.demo.common.design.strategy;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.strategy Strategy
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-21 09:12
 * @Version: 1.0
 */
public interface Strategy {

    /**
     * 计费方法
     * @param money
     * @return
     */
    double compute(long money);

    /**
     * 返回 type
      * @return
     */
    int getType();

}
