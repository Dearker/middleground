package com.hanyi.thread.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanyi.thread.common.component.CronTaskRegistrar;
import com.hanyi.thread.common.enums.ScheduledJobStatus;
import com.hanyi.thread.dao.ScheduledJobMapper;
import com.hanyi.thread.domain.SchedulingRunnable;
import com.hanyi.thread.pojo.ScheduledJobPO;
import com.hanyi.thread.pojo.SchedulerTaskInfo;
import com.hanyi.thread.service.ScheduledJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 定时任务工作服务实现，查看文档：https://mp.weixin.qq.com/s/UG5AmRMfKEd9Ma9O0-adQA
 *
 * @author wcwei@iflytek.com
 * @date 2021/08/03
 * @since 2021-08-03 11:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledJobServiceImpl extends ServiceImpl<ScheduledJobMapper, ScheduledJobPO> implements ScheduledJobService {

    /**
     * 雪花
     */
    private final Snowflake snowflake;

    /**
     * cron任务注册
     */
    private final CronTaskRegistrar cronTaskRegistrar;

    /**
     * 线程池的任务调度器
     */
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 添加系统工作
     *
     * @param scheduledJobPo 系统的工作订单
     * @return 添加结果
     */
    @Override
    public boolean addScheduledJob(ScheduledJobPO scheduledJobPo) {
        scheduledJobPo.setJobId(snowflake.nextId());
        int insertTotal = baseMapper.insert(scheduledJobPo);

        if (insertTotal == 0) {
            return false;
        }

        //启动
        if (Objects.equals(ScheduledJobStatus.NORMAL.ordinal(), scheduledJobPo.getJobStatus())) {
            SchedulingRunnable task = new SchedulingRunnable(scheduledJobPo);
            cronTaskRegistrar.addCronTask(task, scheduledJobPo.getCronExpression());
        }
        return true;
    }

    /**
     * 编辑系统工作
     *
     * @param scheduledJobPo 系统的工作订单
     * @return 修改结果
     */
    @Override
    public boolean editScheduledJob(ScheduledJobPO scheduledJobPo) {
        int updateTotal = baseMapper.updateById(scheduledJobPo);
        if (updateTotal == 0) {
            return false;
        }
        //先移除再添加
        if (scheduledJobPo.getJobStatus().equals(ScheduledJobStatus.NORMAL.ordinal())) {
            SchedulingRunnable task = new SchedulingRunnable(scheduledJobPo);
            cronTaskRegistrar.removeCronTask(task);
            cronTaskRegistrar.addCronTask(task, scheduledJobPo.getCronExpression());
        }
        return true;
    }

    /**
     * 删除系统工作通过id
     *
     * @param scheduledJobPo 系统的工作订单
     * @return 删除结果
     */
    @Override
    public boolean deleteScheduledJobById(ScheduledJobPO scheduledJobPo) {
        int deleteTotal = baseMapper.deleteById(scheduledJobPo.getJobId());
        if (deleteTotal == 0) {
            return false;
        }
        if (scheduledJobPo.getJobStatus().equals(ScheduledJobStatus.NORMAL.ordinal())) {
            SchedulingRunnable task = new SchedulingRunnable(scheduledJobPo);
            cronTaskRegistrar.removeCronTask(task);
        }
        return true;
    }

    /**
     * 更新的工作状态
     *
     * @param scheduledJobPo 系统的工作订单
     */
    @Override
    public void updateScheduledJobStatus(ScheduledJobPO scheduledJobPo) {
        SchedulingRunnable task = new SchedulingRunnable(scheduledJobPo);
        if (scheduledJobPo.getJobStatus().equals(ScheduledJobStatus.NORMAL.ordinal())) {
            cronTaskRegistrar.addCronTask(task, scheduledJobPo.getCronExpression());
        } else {
            cronTaskRegistrar.removeCronTask(task);
        }
    }

    /**
     * 获取调度的任务信息
     *
     * @return 调度任务详情
     */
    @Override
    public SchedulerTaskInfo getScheduledTaskInfo() {
        SchedulerTaskInfo schedulerTaskInfo = new SchedulerTaskInfo();
        schedulerTaskInfo.setPoolSize(threadPoolTaskScheduler.getPoolSize());
        schedulerTaskInfo.setActiveCount(threadPoolTaskScheduler.getActiveCount());
        schedulerTaskInfo.setThreadPriority(threadPoolTaskScheduler.getThreadPriority());
        schedulerTaskInfo.setThreadNamePrefix(threadPoolTaskScheduler.getThreadNamePrefix());

        log.info("定时线程池信息：{}", threadPoolTaskScheduler.getScheduledThreadPoolExecutor());
        return schedulerTaskInfo;
    }
}
