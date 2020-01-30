package com.hanyi.gateway.config;

import com.hanyi.gateway.handler.RequestBodyRoutePredicateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hanyi
 * @create 2019/10/14.
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RequestBodyRoutePredicateFactory requestBodyRoutePredicateFactory() {
        return new RequestBodyRoutePredicateFactory();
    }
}
