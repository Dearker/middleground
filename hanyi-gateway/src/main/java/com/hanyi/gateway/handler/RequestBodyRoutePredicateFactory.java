/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.hanyi.gateway.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.handler.AsyncPredicate;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

import static org.springframework.cloud.gateway.filter.AdaptCachedBodyGlobalFilter.CACHED_REQUEST_BODY_KEY;

/**
 * This predicate is BETA and may be subject to change in a future release.
 *
 * @author weiwen
 */
public class RequestBodyRoutePredicateFactory extends AbstractRoutePredicateFactory<RequestBodyRoutePredicateFactory.Config> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestBodyRoutePredicateFactory.class);

    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();

    private static final String REQUEST_BODY_ATTR = "requestBodyAttr";

    private static final String POST = "POST";

    public RequestBodyRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public AsyncPredicate<ServerWebExchange> applyAsync(Config config) {
        return exchange -> {
            if (!POST.equals(exchange.getRequest().getMethodValue())) {
                return Mono.just(true);
            }
            Object cachedBody = exchange.getAttribute(REQUEST_BODY_ATTR);
            if (cachedBody != null) {
                try {
                    return Mono.just(true);
                } catch (ClassCastException e) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Predicate test failed because class in predicate does not match the cached body object",
                                e);
                    }
                }
                return Mono.just(true);
            } else {
                return DataBufferUtils.join(exchange.getRequest().getBody())
                        .flatMap(dataBuffer -> {
                            DataBufferUtils.retain(dataBuffer);
                            Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
                            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                                @Override
                                public Flux<DataBuffer> getBody() {
                                    return cachedFlux;
                                }
                            };
                            return ServerRequest.create(exchange.mutate().request(mutatedRequest).build(), MESSAGE_READERS)
                                    .bodyToMono(String.class)
                                    .doOnNext(objectValue -> {
                                        exchange.getAttributes().put(REQUEST_BODY_ATTR, objectValue);
                                        exchange.getAttributes().put(CACHED_REQUEST_BODY_KEY, cachedFlux);
                                    })
                                    .map(objectValue -> true);
                        });

            }
        };
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        throw new UnsupportedOperationException(
                "ReadBodyPredicateFactory is only async.");
    }

    public static class Config {
        private String attr;

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }
    }
}
