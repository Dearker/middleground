package com.hanyi.privoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author weiwenchang
 * @since 2020-02-12
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PrivoderApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PrivoderApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PrivoderApplication.class);
    }

}
