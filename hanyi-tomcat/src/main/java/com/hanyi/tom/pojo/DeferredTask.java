package com.hanyi.tom.pojo;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/4/11 9:48 下午
 */
@Slf4j
@RequiredArgsConstructor
public class DeferredTask implements Runnable {

    private final DeferredResult<String> deferredResult;

    @Override
    public void run() {
        ThreadUtil.sleep(1000);
        int randomInt = RandomUtil.randomInt();
        log.info("当前DeferredTask 名称：" + Thread.currentThread().getName());
        deferredResult.setResult("柯基小短腿:" + randomInt);
    }
}
