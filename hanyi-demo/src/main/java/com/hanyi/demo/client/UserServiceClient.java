package com.hanyi.demo.client;

import com.hanyi.demo.client.impl.UserServiceImpl;
import com.hanyi.demo.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author weiwenchang
 * @since 2020-02-12
 * 指定feign调用的服务
 */
@FeignClient(name = "hanyi-privoder",
        // 服务降级处理类
        fallback = UserServiceImpl.class,
        //feign客户端配置，日志级别的配置
        configuration = FeignConfiguration.class)
public interface UserServiceClient {

    @GetMapping("/hanyi-privoder/user/name")
    String getUserName();

}
