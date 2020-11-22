package com.hanyi.canal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 * Canal启动类
 * </p>
 *
 * @author wenchangwei
 * @since 4:17 下午 2020/11/22
 */
@MapperScan(basePackages = {"com.hanyi.canal.dao"})
@EnableScheduling
@SpringBootApplication
public class CanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }

}
