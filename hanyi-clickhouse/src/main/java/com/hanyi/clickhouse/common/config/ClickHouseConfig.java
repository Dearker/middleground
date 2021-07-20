package com.hanyi.clickhouse.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.hanyi.clickhouse.common.property.JdbcProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/7/17 10:10 上午
 */
@Configuration
public class ClickHouseConfig {

    @Bean
    public DataSource dataSource(JdbcProperty jdbcProperty){
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(jdbcProperty.getUrl());
        datasource.setDriverClassName(jdbcProperty.getDriverClassName());
        datasource.setInitialSize(jdbcProperty.getInitialSize());
        datasource.setMinIdle(jdbcProperty.getMinIdle());
        datasource.setMaxActive(jdbcProperty.getMaxActive());
        datasource.setMaxWait(jdbcProperty.getMaxWait());

        return datasource;
    }

}
