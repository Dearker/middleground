package com.hanyi.thread.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hanyi.thread.pojo.ScheduledJobPO;
import com.hanyi.thread.pojo.SchedulerTaskInfo;

/**
 * 系统的工作服务
 *
 * @author wcwei@iflytek.com
 * @since 2021-08-03 10:44
 */
public interface ScheduledJobService extends IService<ScheduledJobPO> {

    /**
     * 添加系统工作
     *
     * @param scheduledJobPo 系统的工作订单
     * @return 添加结果
     */
    boolean addScheduledJob(ScheduledJobPO scheduledJobPo);

    /**
     * 编辑系统工作
     *
     * @param scheduledJobPo 系统的工作订单
     * @return 修改结果
     */
    boolean editScheduledJob(ScheduledJobPO scheduledJobPo);

    /**
     * 删除系统工作通过id
     *
     * @param scheduledJobPo 系统的工作订单
     * @return 删除结果
     */
    boolean deleteScheduledJobById(ScheduledJobPO scheduledJobPo);

    /**
     * 更新的工作状态
     *
     * @param scheduledJobPo 系统的工作订单
     */
    void updateScheduledJobStatus(ScheduledJobPO scheduledJobPo);

    /**
     * 获取调度的任务信息
     *
     * @return 调度任务详情
     */
    SchedulerTaskInfo getScheduledTaskInfo();
}
