package com.hanyi.daily.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.hanyi.daily.common.enums.DataType;
import com.hanyi.daily.common.handover.DynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @PackAge: middleground com.hanyi.daily.common.config
 * @Author: weiwenchang
 * @Description: druid监控及数据源配置
 * @CreateDate: 2020-03-11 20:10
 * @Version: 1.0
 */
@Configuration
@MapperScan("com.hanyi.daily.mapper")
public class DruidConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.primary")
    public DataSource primaryDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.secondary")
    public DataSource secondaryDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Primary
    @Bean
    public DynamicDataSource dynamicDataSource(DataSource primaryDataSource, DataSource secondaryDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataType.PRIMARY, primaryDataSource);
        targetDataSources.put(DataType.SECONDARY, secondaryDataSource);
        return new DynamicDataSource(primaryDataSource, targetDataSources);
    }

    /**
     * 配置一个监控Druid的后台的Servlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        Map<String, String> initParams = new HashMap<>(4);
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "admin");
        bean.setInitParameters(initParams);

        return bean;
    }

    /**
     * 配置一个web监控的filter
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();

        bean.setFilter(new WebStatFilter());
        //添加不需要忽略的格式信息.
        bean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        bean.setUrlPatterns(Collections.singletonList("/*"));

        return bean;
    }

}
