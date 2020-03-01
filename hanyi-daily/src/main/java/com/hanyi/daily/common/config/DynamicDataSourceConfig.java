package com.hanyi.daily.common.config;

import com.hanyi.daily.common.handover.DynamicDataSource;
import com.hanyi.daily.common.enums.DataType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: middleground com.hanyi.daily.common.config DynamicDataSourceConfig
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-05 09:42
 * @Version: 1.0
 */
@Configuration
public class DynamicDataSourceConfig {

    /**
     * 读取application.properties中的配置参数映射成为一个对象,prefix表示参数的前缀
     * @return
     */
    @Bean(name = "PrimaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource getDateSource1() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "SecondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource getDateSource2() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource(@Qualifier("PrimaryDataSource") DataSource primaryDataSource,
                                        @Qualifier("SecondaryDataSource") DataSource secondaryDataSource) {

        //这个地方是比较核心的targetDataSource 集合是我们数据库和名字之间的映射
        Map<Object, Object> targetDataSource = new HashMap<>(4 << 1);
        targetDataSource.put(DataType.PRIMARY, primaryDataSource);
        targetDataSource.put(DataType.SECONDARY, secondaryDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        return dataSource;
    }


    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        //设置我们的xml文件路径
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*/*.xml"));
        return bean.getObject();
    }


}
