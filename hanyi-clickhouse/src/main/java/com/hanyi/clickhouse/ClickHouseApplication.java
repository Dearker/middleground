package com.hanyi.clickhouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/7/16 11:18 下午
 */
@MapperScan(basePackages = "com.hanyi.clickhouse.dao")
@SpringBootApplication
public class ClickHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickHouseApplication.class,args);
    }

}
