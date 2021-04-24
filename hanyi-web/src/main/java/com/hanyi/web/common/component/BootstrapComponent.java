package com.hanyi.web.common.component;

import com.hanyi.web.common.aware.ApplicationComponent;
import com.hanyi.web.export.ExportComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @PackAge: middleground com.hanyi.daily.common.component
 * @Author: weiwenchang
 * @Description: 启动时加载;order注解的值越小，优先级越高
 * @CreateDate: 2020-03-01 11:41
 * @Version: 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapComponent implements CommandLineRunner {

    private final ExportComponent exportComponent;

    private final ApplicationComponent applicationComponent;

    @Override
    public void run(String... args) throws Exception {
        Map<String, ExecutorService> implementor = applicationComponent.getTargetInterfaceAllImplementor(ExecutorService.class);
        log.info("implementor value is : {}", implementor.values().toString());
        log.info("BootstrapLoaderComponent load finished");

        exportComponent.buildData();
    }

}
