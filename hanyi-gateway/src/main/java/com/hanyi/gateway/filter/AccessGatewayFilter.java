package com.hanyi.gateway.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.LinkedHashSet;

/**
 * 自定义网关过滤器
 * @author weiwenchang
 */
@Component
@Slf4j
public class AccessGatewayFilter implements GlobalFilter, Ordered {

    private static final String GATE_WAY_PREFIX = "/api";

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {

        ServerHttpRequest request = serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();
        //获取所有参数
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        //有token则放行
        /*String token = queryParams.getFirst("token");

        if (StrUtil.isEmpty(token)){
            //设置响应状态码，提示用户未授权
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //进行拦截，返回拒绝访问
            return response.setComplete();
        }*/
        //放行请求
        return gatewayFilterChain.filter(serverWebExchange);

    }

    private Mono<Void> checkToken(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain){
        log.info("check token and user permission....");
        LinkedHashSet<URI> requiredAttribute = serverWebExchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        ServerHttpRequest request = serverWebExchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        for (URI next : requiredAttribute) {
            if (next.getPath().startsWith(GATE_WAY_PREFIX)) {
                requestUri = next.getPath().substring(GATE_WAY_PREFIX.length());
            }
        }
        ServerHttpRequest.Builder mutate = request.mutate();
        // 不进行拦截的地址
        StrUtil.startWith(requestUri, StrUtil.COMMA);
        ServerHttpRequest build = mutate.build();
        return gatewayFilterChain.filter(serverWebExchange.mutate().request(build).build());
    }

    /**
     * 返回执行顺序，数字越小，执行顺序越靠前
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
