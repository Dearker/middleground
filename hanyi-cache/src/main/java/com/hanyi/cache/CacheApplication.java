package com.hanyi.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @PackAge: middleground com.hanyi.cache
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-03-12 20:18
 * @Version: 1.0
 */
@SpringBootApplication
@EnableCaching
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

}
