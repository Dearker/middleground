package com.hanyi.demo.lock.zookeeper;

import java.text.SimpleDateFormat;
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
    private static int count = 0;

    public String getNumber() {
        try {
            Thread.sleep(200);
        } catch (Exception ignored) {

        }
        SimpleDateFormat simpt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return simpt.format(new Date()) + "-" + ++count;
    }

}
