package com.hanyi.demo.common.design.facade.impl;

import com.hanyi.demo.common.design.facade.WeiXinSmsService;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.Facade.impl WeiXinSmsServiceImpl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 16:10
 * @Version: 1.0
 */
public class WeiXinSmsServiceImpl implements WeiXinSmsService {

    @Override
    public void sendSms() {
        System.out.println("发送微信消息");
    }

}
