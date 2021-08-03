package com.hanyi.thread.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统的工作订单
 *
 * @author wcwei@iflytek.com
 * @since 2021-08-03 10:38
 */
@Data
public class ScheduledJobPO implements Serializable {

    /**
     * 任务ID
     */
    private Long jobId;

    /**
     * bean名称
     */
    private String beanName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String methodParams;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 状态（1正常 0暂停）
     */
    private Integer jobStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
