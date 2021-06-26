package com.hanyi.dynamic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 动态启动类
 * </p>
 *
 * @author wenchangwei
 * @since 2021/6/26 5:34 下午
 */
@MapperScan(basePackages = "com.hanyi.dynamic.dao")
@SpringBootApplication
public class DynamicApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicApplication.class, args);
    }

}
