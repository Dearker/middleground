package com.hanyi.daily;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @PackAge: middleground com.hanyi.daily
 * @Author: weiwenchang
 * @Description: 继承SpringBootServletInitializer是为了将springboot项目打成war包放在tomcat下访问
 * @CreateDate: 2020-03-21 09:51
 * @Version: 1.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DailyApplication.class);
    }
}
