package com.hanyi.rocket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * rocket 启动类
 * </p>
 *
 * @author wenchangwei
 * @since 11:07 下午 2020/12/6
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.hanyi.rocket.dao"})
public class RocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketApplication.class, args);
    }

}
