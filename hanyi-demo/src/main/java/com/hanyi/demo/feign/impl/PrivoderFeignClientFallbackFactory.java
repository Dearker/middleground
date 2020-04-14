package com.hanyi.demo.feign.impl;

import com.hanyi.demo.feign.PrivoderFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @PackAge: middleground com.hanyi.demo.feign.impl
 * @Author: weiwenchang
 * @Description: 在feign调用出错之后可以获取到对应的异常
 * @CreateDate: 2020-04-14 09:49
 * @Version: 1.0
 */
@Component
@Slf4j
public class PrivoderFeignClientFallbackFactory implements FallbackFactory<PrivoderFeignClient> {
    @Override
    public PrivoderFeignClient create(Throwable throwable) {
        return new PrivoderFeignClient() {

            @Override
            public String getUserName() {
                log.error("{}",throwable.toString());
                return null;
            }

            @Override
            public Boolean deduct(String commodityCode, Integer count) {
                return null;
            }
        };
    }
}
