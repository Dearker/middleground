package com.hanyi.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * <p>
 * springboot集成session启动类
 * </p>
 *
 * @author wenchangwei
 * @since 11:10 下午 2020/11/14
 */
@EnableRedisHttpSession
@SpringBootApplication
public class SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApplication.class, args);
    }

}
