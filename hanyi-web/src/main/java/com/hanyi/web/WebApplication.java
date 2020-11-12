package com.hanyi.web;

import com.hanyi.web.common.annotation.EnableThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author wenchangwei
 * @since 10:51 下午 2020/7/24
 */
@SpringBootApplication
@EnableThreadPool
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
