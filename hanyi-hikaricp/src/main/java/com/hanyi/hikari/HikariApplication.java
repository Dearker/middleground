package com.hanyi.hikari;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * hikari 启动类
 * </p>
 *
 * @author wenchangwei
 * @since 10:20 下午 2020/6/5
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.hanyi.hikari.dao"})
public class HikariApplication {

    public static void main(String[] args) {
        SpringApplication.run(HikariApplication.class, args);
    }

}
