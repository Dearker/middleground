package com.hanyi.mongo.common.util;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @PackAge: middleground com.hanyi.mongo.common.util
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-05-10 09:15
 * @Version: 1.0
 */
public class ThreadPoolUtil {

    private static ThreadPoolExecutor THREADPOOLEXECUTOR = ThreadUtil.newExecutor(32, 32);



}
