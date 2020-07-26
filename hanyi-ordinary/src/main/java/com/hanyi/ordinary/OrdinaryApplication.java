package com.hanyi.ordinary;

import com.hanyi.ordinary.common.annotation.EnableThreadPool;
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
public class OrdinaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdinaryApplication.class, args);
    }

}
