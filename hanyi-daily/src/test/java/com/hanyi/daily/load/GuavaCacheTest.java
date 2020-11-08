package com.hanyi.daily.load;

import com.google.common.cache.*;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 缓存测试类
 * </p>
 *
 * @author wenchangwei
 * @since 4:03 下午 2020/11/8
 */
public class GuavaCacheTest {

    @Test
    public void testCache() throws ExecutionException, InterruptedException {

        CacheLoader<String,Animal> cacheLoader = new CacheLoader<String, Animal>() {
            // 如果找不到元素，会调用这里
            @Override
            public Animal load(String s) {
                return null;
            }
        };

        LoadingCache<String, Animal> loadingCache = CacheBuilder.newBuilder()
                // 容量
                .maximumSize(1000)
                // 过期时间
                .expireAfterWrite(3, TimeUnit.SECONDS)
                // 失效监听器
                .removalListener(new MyRemovalListener())
                .build(cacheLoader);

        loadingCache.put("狗", new Animal("旺财", 1));
        loadingCache.put("猫", new Animal("汤姆", 3));
        loadingCache.put("狼", new Animal("灰太狼", 4));

        loadingCache.invalidate("猫"); // 手动失效

        Animal animal = loadingCache.get("狼");
        System.out.println(animal);
        TimeUnit.SECONDS.sleep(4);
        // 狼已经自动过去，获取为 null 值报错
        System.out.println(loadingCache.get("狼"));
        /**
         * key=猫,value=Animal{name='汤姆', age=3},reason=EXPLICIT
         * Animal{name='灰太狼', age=4}
         * key=狗,value=Animal{name='旺财', age=1},reason=EXPIRED
         * key=狼,value=Animal{name='灰太狼', age=4},reason=EXPIRED
         *
         * com.google.common.cache.CacheLoader$InvalidCacheLoadException: CacheLoader returned null for key 狼.
         */
    }

    /**
     * 缓存移除监听器
     */
    static class MyRemovalListener implements RemovalListener<String, Animal> {

        @Override
        public void onRemoval(RemovalNotification<String, Animal> notification) {
            String reason = String.format("key=%s,value=%s,reason=%s", notification.getKey(), notification.getValue(), notification.getCause());
            System.out.println(reason);
        }
    }

    @ToString
    @AllArgsConstructor
    static class Animal {
        private final String name;
        private final Integer age;
    }

}
