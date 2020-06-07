package com.hanyi.hikari.common.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * <p>
 * 数据源配置类
 * </p>
 *
 * @author wenchangwei
 * @since 10:23 下午 2020/6/5
 */
@Configuration
public class DataSourceConfig {

    /**
     * 创建 orders 数据源的配置对象
     * 读取 spring.datasource.orders 配置到 DataSourceProperties 对象
     */
    @Primary
    @Bean(name = "ordersDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.orders")
    public DataSourceProperties ordersDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 创建 orders 数据源
     * 读取 spring.datasource.orders 配置到 HikariDataSource 对象
     */
    @Bean(name = "ordersDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.orders.hikari")
    public DataSource ordersDataSource() {
        // 获得 DataSourceProperties 对象
        DataSourceProperties properties = this.ordersDataSourceProperties();
        // 创建 HikariDataSource 对象
        return createHikariDataSource(properties);
    }

    /**
     * 创建 users 数据源的配置对象
     * 读取 spring.datasource.users 配置到 DataSourceProperties 对象
     */
    @Bean(name = "usersDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.users")
    public DataSourceProperties usersDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 创建 users 数据源
     */
    @Bean(name = "usersDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.users.hikari")
    public DataSource usersDataSource() {
        // 获得 DataSourceProperties 对象
        DataSourceProperties properties = this.usersDataSourceProperties();
        // 创建 HikariDataSource 对象
        return createHikariDataSource(properties);
    }

    private static HikariDataSource createHikariDataSource(DataSourceProperties properties) {
        // 创建 HikariDataSource 对象
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        // 设置线程池名
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }

}
