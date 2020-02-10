package com.hanyi.demo.common.constant;

/**
 * @ClassName: middleground com.hanyi.demo.common.constant TokenConstant
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-08 22:09
 * @Version: 1.0
 */
public interface TokenConstant {

    // 过期时间, 60s, 一分钟
    Integer EXPIRE_TIME_MINUTE = 60;

    // 过期时间, 一小时
    Integer EXPIRE_TIME_HOUR = 60 * 60;

    // 过期时间, 一天
    Integer EXPIRE_TIME_DAY = 60 * 60 * 24;

    String TOKEN_PREFIX = "token:";

}
