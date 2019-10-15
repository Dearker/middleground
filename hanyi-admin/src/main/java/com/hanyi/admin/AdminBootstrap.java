package com.hanyi.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ${DESCRIPTION}
 *
 * @author hanyi
 * @create 2019-10-24
 */
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableAdminServer
public class AdminBootstrap {
    public static void main(String[] args) {
        //new SpringApplicationBuilder(AdminBootstrap.class).run(args);
        SpringApplication.run(AdminBootstrap.class,args);
    }
}
