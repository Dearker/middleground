package com.hanyi.daily;

import com.hanyi.daily.common.annotation.EnableExtConfigPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ClassName: middleground com.hanyi.daily DailyApplication
 * @Author: weiwenchang
 * @Description: 继承SpringBootServletInitializer是为了将springboot项目打成war包放在tomcat下访问
 * @CreateDate: 2020-02-04 15:23
 * @Version: 1.0
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableExtConfigPackage
public class DailyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DailyApplication.class);
    }
}
