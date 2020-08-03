package com.hanyi.daily.common.aware;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 线程池系统环境组件类
 * </p>
 *
 * @author wenchangwei@wistronits.com
 * @since 20:39 2020/8/3
 */
@Slf4j
@Component
public class ThreadPoolEnvironmentComponent implements EnvironmentAware {


    @Override
    public void setEnvironment(Environment environment) {

        String[] activeProfiles = environment.getActiveProfiles();

        if(ArrayUtil.isNotEmpty(activeProfiles)){

            environment.getDefaultProfiles();

        }

        String[] defaultProfiles = environment.getDefaultProfiles();

        System.out.println(activeProfiles);

    }
}
