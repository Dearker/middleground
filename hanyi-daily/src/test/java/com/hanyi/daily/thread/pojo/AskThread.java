package com.hanyi.daily.thread.pojo;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/4/12 9:08 下午
 */
@RequiredArgsConstructor
public class AskThread implements Runnable{

    private final CompletableFuture<String> completableFuture;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("askThread 当前线程名称：" + name + " : " + LocalDateTime.now());
        try {
            System.out.println(completableFuture.get());
            System.out.println("askThread 当前线程名称：" + name + " : " + LocalDateTime.now());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
