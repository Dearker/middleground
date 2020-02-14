package com.hanyi.demo.feign;

import com.hanyi.demo.config.FeignConfiguration;
import com.hanyi.demo.feign.impl.PrivoderFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @PackAge: middleground com.hanyi.demo.feign
 * @Author: weiwenchang
 * @Description: 指定feign调用的服务
 * @CreateDate: 2020-02-14 16:50
 * @Version: 1.0
 */
@FeignClient(name = "hanyi-privoder",
        // 服务降级处理类,调用方如果出现异常也会走该处理类
        //fallback = PrivoderFeignClientImpl.class,
        //feign客户端配置，日志级别的配置
        configuration = FeignConfiguration.class)
public interface PrivoderFeignClient {

    @GetMapping("/hanyi-privoder/user/name")
    String getUserName();

    @GetMapping("/hanyi-privoder/storage/deduct")
    Boolean deduct(@RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count);

}
