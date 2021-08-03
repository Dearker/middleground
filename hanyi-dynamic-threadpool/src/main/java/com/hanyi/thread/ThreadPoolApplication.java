package com.hanyi.thread;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 线程池启动类
 *
 * @author wcwei@iflytek.com
 * @since 2021-08-03 10:01
 */
@MapperScan(basePackages = "com.hanyi.thread.dao")
@SpringBootApplication
public class ThreadPoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadPoolApplication.class, args);
    }

}
