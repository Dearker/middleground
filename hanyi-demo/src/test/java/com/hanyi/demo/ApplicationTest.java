package com.hanyi.demo;

import com.hanyi.demo.feign.PrivoderFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author weiwenchang
 * @since 2020-02-12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Resource
    private PrivoderFeignClient privoderFeignClient;

    @Test
    public void feignTest(){

        String userName = privoderFeignClient.getUserName();
        System.out.println("获取的数据"+userName);
    }

}
