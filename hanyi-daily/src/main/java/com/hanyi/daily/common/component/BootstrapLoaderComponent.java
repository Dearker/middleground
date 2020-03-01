package com.hanyi.daily.common.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @PackAge: middleground com.hanyi.daily.common.component
 * @Author: weiwenchang
 * @Description: 启动时加载;order注解的值越小，优先级越高
 * @CreateDate: 2020-03-01 11:41
 * @Version: 1.0
 */
@Order(value = 1)
@Component
public class BootstrapLoaderComponent implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }


}
