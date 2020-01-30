package com.hanyi.demo.common.design.facade;

import com.hanyi.demo.common.design.facade.impl.AliSmsServiceImpl;
import com.hanyi.demo.common.design.facade.impl.EamilSmsServiceImpl;
import com.hanyi.demo.common.design.facade.impl.WeiXinSmsServiceImpl;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.Facade Computer
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 16:13
 * @Version: 1.0
 */
public class Computer {

    private AliSmsService aliSmsService;
    private EamilSmsService eamilSmsService;
    private WeiXinSmsService weiXinSmsService;

    public Computer() {
        aliSmsService = new AliSmsServiceImpl();
        eamilSmsService = new EamilSmsServiceImpl();
        weiXinSmsService = new WeiXinSmsServiceImpl();
    }

    public void sendMsg() {
        aliSmsService.sendSms();
        eamilSmsService.sendSms();
        weiXinSmsService.sendSms();
    }

}
