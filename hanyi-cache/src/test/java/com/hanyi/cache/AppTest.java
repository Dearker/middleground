package com.hanyi.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hanyi.cache.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2020-10-12 13:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    /**
     * redis模板
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 使用stringRedisTemplate
     */
    @Test
    public void redisTest() {
        stringRedisTemplate.opsForHash().put("1", "21", "323");
        Object o = stringRedisTemplate.opsForHash().get("1", "21");
        System.out.println(o);
    }

    /**
     * 使用redisTemplate
     */
    @Test
    public void hashTest() {
        redisTemplate.opsForHash().put("2", "32", "13");
        Object o = redisTemplate.opsForHash().get("2", "32");
        System.out.println(o);
    }

    /**
     * redis会自动过滤掉属性为null的字段，存储进去的实际上只有name的值
     */
    @Test
    public void hash02Test() {
        Book book = new Book(null, "柯基", null);
        stringRedisTemplate.opsForHash().put("1", "哈哈哈", JSON.toJSONString(book));
        stringRedisTemplate.expire("1", 10, TimeUnit.MINUTES);

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "柯基", "123"));
        bookList.add(new Book(2, "哈士奇", "234"));
        bookList.add(new Book(3, "柴犬", "345"));

        String hashKey = "hashKey";

        Map<String, String> stringMap = bookList.stream().collect(Collectors.toMap(Book::getName, Book::getAuthor));
        stringRedisTemplate.opsForHash().put("2", hashKey, JSON.toJSONString(stringMap));
        stringRedisTemplate.expire("2", 10, TimeUnit.MINUTES);

        Map<String, String> parseObject = JSON.parseObject(stringRedisTemplate.opsForHash().get("2", hashKey).toString(), new TypeReference<Map<String, String>>() {
        });
        System.out.println(parseObject);
        parseObject.forEach((k, v) -> System.out.println(k + " || " + v));

        //获取全部信息
        Map<Object, Object> objectMap = stringRedisTemplate.opsForHash().entries("2");
        System.out.println(objectMap);
        Map<String, String> map = JSON.parseObject(objectMap.get(hashKey).toString(), new TypeReference<Map<String, String>>() {
        });
        map.forEach((k, v) -> System.out.println(k + " || " + v));
    }

    /**
     * String结构
     */
    @Test
    public void stringTest() {
        stringRedisTemplate.opsForValue().set("1", "哈哈哈", 1, TimeUnit.HOURS);
        System.out.println(stringRedisTemplate.opsForValue().get("1"));
    }

    /**
     * List结构
     */
    @Test
    public void listTest() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "柯基", "123"));
        bookList.add(new Book(2, "哈士奇", "234"));
        bookList.add(new Book(3, "柴犬", "345"));

        String key = "list";
        //存储元素
        stringRedisTemplate.opsForList().leftPushAll(key, JSON.toJSONString(bookList));
        stringRedisTemplate.expire(key, 20, TimeUnit.MINUTES);
        List<String> stringList = stringRedisTemplate.opsForList().range(key, 0, -1);

        stringList.forEach(s -> System.out.println(JSON.parseArray(s, Book.class)));

        System.out.println("-----------------------------------------");

        String arrayKey = "arrayKey";
        List<Book> books = new ArrayList<>();
        books.add(new Book(4, "柯基11", "123"));
        books.add(new Book(5, "哈士奇22", "234"));
        books.add(new Book(6, "柴犬33", "345"));

        String[] toArray = books.stream().map(JSON::toJSONString).collect(Collectors.toList()).toArray(new String[0]);

        stringRedisTemplate.opsForList().leftPushAll(arrayKey, toArray);
        stringRedisTemplate.expire(arrayKey, 20, TimeUnit.MINUTES);

        //一个很简易的消息队列 ，假设从头到尾去消费队列中的消息；被消费掉的消息将用list 在移除掉
        //从list的左侧开始取数据
        String rightPop = stringRedisTemplate.opsForList().rightPop(arrayKey);
        System.out.println("rightPop: " + JSON.parseObject(rightPop, Book.class));
        //从list的右侧开始去数据
        String leftPop = stringRedisTemplate.opsForList().leftPop(arrayKey);
        System.out.println("leftPop: " + JSON.parseObject(leftPop, Book.class));

        System.out.println("剩余的数据：" + stringRedisTemplate.opsForList().range(arrayKey, 0, -1));
    }

    /**
     * Set结构
     */
    @Test
    public void setTest() {
        String setKey = "Set";

        Set<Book> bookSet = new HashSet<>();
        bookSet.add(new Book(1, "柯基", "123"));
        bookSet.add(new Book(2, "哈士奇", "234"));
        bookSet.add(new Book(3, "柴犬", "345"));

        String[] toArray = bookSet.stream().map(JSON::toJSONString).collect(Collectors.toSet()).toArray(new String[0]);

        stringRedisTemplate.opsForSet().add(setKey, toArray);
        stringRedisTemplate.expire(setKey, 20, TimeUnit.MINUTES);

        Set<String> stringSet = stringRedisTemplate.opsForSet().members(setKey);
        System.out.println(stringSet);

        stringSet.forEach(s -> System.out.println(JSON.parseObject(s, Book.class)));
    }

    /**
     * ZSet结构，有序集合
     */
    @Test
    public void ZSetTest() {
        Set<Book> bookSet = new HashSet<>();
        Book firstBook = new Book(1, "柯基", "123");
        bookSet.add(firstBook);
        bookSet.add(new Book(2, "哈士奇", "234"));
        bookSet.add(new Book(3, "柴犬", "345"));

        String setKey = "ZSet";

        ZSetOperations<String, String> opsForZSet = stringRedisTemplate.opsForZSet();
        bookSet.forEach(s -> opsForZSet.add(setKey, JSON.toJSONString(s), s.getId()));
        stringRedisTemplate.expire(setKey, 20, TimeUnit.MINUTES);

        //从低到高排行榜
        Set<String> range = opsForZSet.range(setKey, 0, -1);
        System.out.println(range);
        range.forEach(s -> System.out.println(JSON.parseObject(s, Book.class)));

        //从高到低排行榜
        Set<String> reverseRange = opsForZSet.reverseRange(setKey, 0, -1);
        System.out.println(reverseRange);
        reverseRange.forEach(s -> System.out.println(JSON.parseObject(s, Book.class)));

        Set<String> rangeByScore = opsForZSet.rangeByScore(setKey, 1, 2);
        System.out.println("获取zset中指定score范围值内的元素: " + rangeByScore);

        Long size = opsForZSet.size(setKey);
        System.out.println("当前key元素总数：" + size);

        String firstString = JSON.toJSONString(firstBook);
        Double score = opsForZSet.score(setKey, firstString);
        System.out.println("获取元素的得分：" + score);
        opsForZSet.incrementScore(setKey, firstString, 10);
        System.out.println("获取增加后的得分：" + opsForZSet.score(setKey, firstString));
    }

}
