package com.hanyi.thread.domain;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.hanyi.thread.pojo.ScheduledJobPO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 调度线程
 *
 * @author wcwei@iflytek.com
 * @since 2021-08-03 10:24
 */
@Slf4j
@EqualsAndHashCode
@AllArgsConstructor
public class SchedulingRunnable implements Runnable {

    /**
     * bean的名字
     */
    private final String beanName;

    /**
     * 方法名称
     */
    private final String methodName;

    /**
     * 参数个数
     */
    private final String params;

    public SchedulingRunnable(ScheduledJobPO scheduledJobPo) {
        this(scheduledJobPo.getBeanName(), scheduledJobPo.getMethodName(), scheduledJobPo.getMethodParams());
    }

    @Override
    public void run() {
        log.info("定时任务开始执行 - bean：{}，方法：{}，参数：{}", beanName, methodName, params);
        long startTime = System.currentTimeMillis();
        try {
            Object target = SpringUtil.getBean(beanName);

            Method method;
            if (StrUtil.isNotBlank(params)) {
                method = target.getClass().getDeclaredMethod(methodName, String.class);
            } else {
                method = target.getClass().getDeclaredMethod(methodName);
            }

            ReflectionUtils.makeAccessible(method);
            if (StrUtil.isNotBlank(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception ex) {
            log.error(String.format("定时任务执行异常 - bean：%s，方法：%s，参数：%s ", beanName, methodName, params), ex);
        }

        long times = System.currentTimeMillis() - startTime;
        log.info("定时任务执行结束 - bean：{}，方法：{}，参数：{}，耗时：{} 毫秒", beanName, methodName, params, times);
    }
}
