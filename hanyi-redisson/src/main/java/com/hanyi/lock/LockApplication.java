package com.hanyi.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 分布式锁启动类
 * </p>
 *
 * @author wenchangwei
 * @since 10:18 下午 2020/11/2
 */
@SpringBootApplication
public class LockApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockApplication.class, args);
    }

}
