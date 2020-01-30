package com.hanyi.demo.common.design.adapter.impl;

import com.hanyi.demo.common.design.adapter.JP110VInterface;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.adapter.impl JP110VInterfaceImpl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 15:35
 * @Version: 1.0
 */
public class JP110VInterfaceImpl implements JP110VInterface {

    @Override
    public void connect() {
        System.out.println("日本110V,接通电源,开始工作..");
    }
}
