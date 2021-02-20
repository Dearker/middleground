package com.hanyi.cache;

import com.hanyi.cache.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * redis类型测试类
 * </p>
 *
 * @author wenchangwei
 * @since 11:24 上午 2021/2/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTypeTest {

    /**
     * 不能同时注入多个不同泛型的RedisTemplate实例，否则会报错
     */
    @Resource
    private RedisTemplate<String, Book> redisTemplate;

    @Test
    public void saveTest(){
        Book book = new Book(1,"哈哈哈","韩毅");
        redisTemplate.opsForValue().set("1", book, 10, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get("1"));
    }

    @Test
    public void findTest(){
        Book book = redisTemplate.opsForValue().get("1");
        System.out.println(book);
    }

}
