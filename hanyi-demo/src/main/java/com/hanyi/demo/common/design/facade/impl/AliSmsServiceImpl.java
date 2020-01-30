package com.hanyi.demo.common.design.facade.impl;

import com.hanyi.demo.common.design.facade.AliSmsService;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.Facade.impl AliSmsServiceImpl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 16:12
 * @Version: 1.0
 */
public class AliSmsServiceImpl implements AliSmsService {

    @Override
    public void sendSms() {
        System.out.println("支付宝发送消息...");
    }
}
