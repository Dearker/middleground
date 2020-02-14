package com.hanyi.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: middleground com.hanyi.demo DemoApplication
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-07 19:49
 * @Version: 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.hanyi.demo","com.hanyi.common"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.hanyi.demo.mapper")
@EnableCaching
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

}
