package com.hanyi.demo.lock.zookeeper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;

import java.util.Date;

/**
 * @ClassName: middleground com.hanyi.demo.lock OrderNumGenerator
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-23 23:36
 * @Version: 1.0
 */
public class OrderNumGenerator {

    /**
     * 生成订单号规则
     */
    private int count = 0;

    public String getNumber() {
        ThreadUtil.sleep(200);
        return DateUtil.formatDateTime(new Date()) + "-" + ++count;
    }

}
