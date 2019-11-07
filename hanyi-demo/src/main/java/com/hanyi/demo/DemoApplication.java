package com.hanyi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: middleground com.hanyi.demo DemoApplication
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-07 19:49
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

}
