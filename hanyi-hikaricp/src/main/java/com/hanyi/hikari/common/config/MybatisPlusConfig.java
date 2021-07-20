package com.hanyi.hikari.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
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
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
