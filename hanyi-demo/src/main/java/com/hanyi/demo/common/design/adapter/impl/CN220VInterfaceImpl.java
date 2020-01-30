package com.hanyi.demo.common.design.adapter.impl;

import com.hanyi.demo.common.design.adapter.CN220VInterface;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.adapter.impl CN220VInterfaceImpl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 15:36
 * @Version: 1.0
 */
public class CN220VInterfaceImpl implements CN220VInterface {

    @Override
    public void connect() {
        System.out.println("中国220V,接通电源,开始工作");
    }

}
