package com.hanyi.webflux.common.config;

import com.hanyi.webflux.common.handler.QuoteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 10:50 上午 2020/11/22
 */
@Configuration
public class QuoteRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(QuoteHandler quoteHandler) {
        return RouterFunctions
                .route(GET("/hello").and(accept(TEXT_PLAIN)), quoteHandler::hello)
                .andRoute(POST("/echo").and(accept(TEXT_PLAIN).and(contentType(TEXT_PLAIN))), quoteHandler::echo)
                .andRoute(GET("/quotes").and(accept(APPLICATION_JSON)), quoteHandler::fetchQuotes)
                .andRoute(GET("/quotes").and(accept(APPLICATION_STREAM_JSON)), quoteHandler::streamQuotes);
    }

}
