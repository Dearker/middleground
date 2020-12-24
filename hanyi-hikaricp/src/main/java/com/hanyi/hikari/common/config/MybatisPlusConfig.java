package com.hanyi.hikari.common.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * mybatis-plus 配置
 * </p>
 *
 * @author wenchangwei
 * @since 10:07 下午 2020/6/11
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.hanyi.hikari.dao"})
public class MybatisPlusConfig {

    /**
     * 性能分析拦截器，不建议生产使用
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        //ms 设置sql执行的最大时间，如果超过了则不执行
        performanceInterceptor.setMaxTime(1000);
        //格式化打印的SQL
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 注册乐观锁插件
     *
     * -- A
     * update user set name = "kwhua", version = version + 1 where id = 2 and version = 1
     *
     * -- B 线程抢先完成，这个时候 version = 2，会导致 A 修改失败！
     * update user set name = "kwhua", version = version + 1 where id = 2 and version = 1
     *
     * @return 返回乐观锁对象
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
