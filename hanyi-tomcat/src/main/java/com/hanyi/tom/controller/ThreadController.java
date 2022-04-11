package com.hanyi.tom.controller;

import com.hanyi.tom.pojo.DeferredTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/4/11 7:56 下午
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ThreadController {

    private final ThreadPoolExecutor threadPoolExecutor;

    @GetMapping("/helloworld")
    public String helloWorldController() {
        String name = Thread.currentThread().getName();
        return name + " 哈士奇";
    }

    @GetMapping("/hello")
    public Callable<String> helloController() {
        log.info(Thread.currentThread().getName() + " 进入helloController方法");
        Callable<String> callable = () -> {
            String name = Thread.currentThread().getName();

            log.info(name + " 进入call方法");
            String say = name + " 柯基！ ";
            log.info(name + " 从helloService方法返回");
            return say;
        };
        log.info(Thread.currentThread().getName() + " 从helloController方法返回");
        return callable;
    }

    @GetMapping("/deferred")
    public DeferredResult<String> executeSlowTask() {
        log.info(Thread.currentThread().getName() + "进入executeSlowTask方法");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        // 调用长时间执行任务
        threadPoolExecutor.execute(new DeferredTask(deferredResult));

        // 当长时间任务中使用deferred.setResult("world");这个方法时，会从长时间任务中返回，继续controller里面的流程
        log.info(Thread.currentThread().getName() + "从executeSlowTask方法返回");
        // 超时的回调方法
        deferredResult.onTimeout(() -> {
            log.info(Thread.currentThread().getName() + " onTimeout");
            // 返回超时信息
            deferredResult.setErrorResult("time out!");
        });

        // 处理完成的回调方法，无论是超时还是处理成功，都会进入这个回调方法
        deferredResult.onCompletion(() -> log.info(Thread.currentThread().getName() + " onCompletion"));

        return deferredResult;
    }

}
