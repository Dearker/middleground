package com.hanyi.demo.common.design.adapter;

import com.hanyi.demo.common.design.adapter.impl.CN220VInterfaceImpl;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.adapter AdaptorTest
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 15:40
 * @Version: 1.0
 */
public class AdaptorTest {

    public static void main(String[] args) {

        CN220VInterface cn220VInterface = new CN220VInterfaceImpl();
        PowerAdaptor powerAdaptor = new PowerAdaptor(cn220VInterface);
        // 电饭煲
        ElectricCooker cooker = new ElectricCooker(powerAdaptor);
        //使用了适配器,在220V的环境可以工作了。
        cooker.cook();

    }

}
