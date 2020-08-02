package com.hanyi.daily.common.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:10 下午 2020/7/31
 */
@Slf4j
@Component
public class AsyncComponent {

    @Async("hanyi")
    public void taskOne(){
        String name = Thread.currentThread().getName();
        log.info("this thread one name is : {}", name);
    }

    @Async("keji")
    public void taskTwo(){
        String name = Thread.currentThread().getName();
        log.info("this thread two name is : {}", name);
    }

}
