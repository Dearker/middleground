package com.hanyi.daily.common.component;

import com.hanyi.daily.pojo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @PackAge: middleground com.hanyi.daily.common.component
 * @Author: weiwenchang
 * @Description: 启动时加载;order注解的值越小，优先级越高
 * @CreateDate: 2020-03-01 11:41
 * @Version: 1.0
 */
@Slf4j
@Component
public class BootstrapLoaderComponent implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Person hanyi123;

    @Autowired
    private ThreadPoolExecutor hanyi;

    @Override
    public void run(String... args) throws Exception {

        log.info("获取的bean：" + hanyi123);
        log.info("获取的线程池bean：" + hanyi);

        Map<String, ThreadPoolExecutor> threadPoolExecutorMap = applicationContext.getBeansOfType(ThreadPoolExecutor.class);

        log.info("获取的数据：" + threadPoolExecutorMap);
        log.info("BootstrapLoaderComponent load finished");
    }

}
