package com.hanyi.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * sharding-jdbc启动类
 * 注：创建user_0和user_1表时在两个数据源中都需要创建，只是查询数据的时候从这4张表中获取数据，插入数据时会根据age来
 * 判断具体插入的数据库和表名是哪个
 * sharding-proxy 中间件主要是将sharding-jdbc配置文件中数据源的内容转移到了中间件的配置文件中
 * </p>
 *
 * @author wenchangwei
 * @since 9:52 下午 2020/11/30
 */
@MapperScan(basePackages = {"com.hanyi.sharding.dao"})
@SpringBootApplication
public class ShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApplication.class, args);
    }

}
