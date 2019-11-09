package com.hanyi.demo.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: middleground com.hanyi.demo.config MybatisPlusConfig
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2019-11-09 11:10
 * @Version: 1.0
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
