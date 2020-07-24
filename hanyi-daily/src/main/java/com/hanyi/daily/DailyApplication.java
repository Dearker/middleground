package com.hanyi.daily;

import com.hanyi.daily.common.annotation.EnableExtConfigPackage;
import com.hanyi.daily.common.annotation.EnableThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @ClassName: middleground com.hanyi.daily DailyApplication
 * @Author: weiwenchang
 * @Description:
 * @CreateDate: 2020-02-04 15:23
 * @Version: 1.0
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableExtConfigPackage
@EnableCaching
@EnableThreadPool
public class DailyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class, args);
    }

}
