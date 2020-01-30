package com.hanyi.demo.common.design.facade.impl;

import com.hanyi.demo.common.design.facade.EamilSmsService;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.Facade.impl EamilSmsServiceImpl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 16:07
 * @Version: 1.0
 */
public class EamilSmsServiceImpl implements EamilSmsService {

    @Override
    public void sendSms() {
        System.out.println("发送邮件消息");
    }

}
