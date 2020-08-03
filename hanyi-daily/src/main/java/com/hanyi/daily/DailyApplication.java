package com.hanyi.daily;

import com.hanyi.daily.common.annotation.EnableExtConfigPackage;
import com.hanyi.daily.common.annotation.EnableThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @ClassName: middleground com.hanyi.daily DailyApplication
 * @Author: weiwenchang 普通
 * @Description:
 * @CreateDate: 2020-02-04 15:23
 * @Version: 1.0
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableExtConfigPackage
@EnableThreadPool
@EnableAsync
public class DailyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class, args);
    }

}
