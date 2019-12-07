package com.hanyi.demo.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: middleground com.hanyi.demo.controller RedisController
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-09 11:37
 * @Version: 1.0
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @GetMapping("")
    public void setKey() {

        //保存字符串
        stringRedisTemplate.opsForValue().set("hanyi", "123");
    }

    /**
     * 调用本地redis可正常运行，云服务器连接超时
     */
    @GetMapping("/redisson")
    public void redisson() {

        String recordId = "hanyi";

        RLock lock = redissonClient.getLock(recordId);
        try {
            boolean bs = lock.tryLock(5, 6, TimeUnit.SECONDS);
            if (bs) {
                // 业务代码
                logger.info("进入业务代码: " + recordId);
                System.out.println("进入业务代码："+ recordId);
                lock.unlock();
            } else {
                Thread.sleep(300);
            }
        } catch (Exception e) {
            logger.error("", e);
            lock.unlock();
        }
    }

}
