package com.hanyi.thread.common.component;

import com.hanyi.thread.domain.ScheduledTask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * cron任务注册器
 *
 * @author wcwei@iflytek.com
 * @since 2021-08-03 10:29
 */
@Component
@RequiredArgsConstructor
public class CronTaskRegistrar {

    /**
     * 计划任务
     */
    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);

    /**
     * 任务调度器
     */
    private final TaskScheduler taskScheduler;

    /**
     * 添加cron任务
     *
     * @param task           任务
     * @param cronExpression cron表达式
     */
    public void addCronTask(Runnable task, String cronExpression) {
        this.addCronTask(new CronTask(task, cronExpression));
    }

    /**
     * 添加cron任务
     *
     * @param cronTask cron任务
     */
    public void addCronTask(CronTask cronTask) {
        if (cronTask != null) {
            Runnable task = cronTask.getRunnable();
            this.removeCronTask(task);
            this.scheduledTasks.computeIfAbsent(task, k -> this.scheduledTasks.put(k, this.scheduleCronTask(cronTask)));
        }
    }

    /**
     * 删除cron任务
     *
     * @param task 任务
     */
    public void removeCronTask(Runnable task) {
        ScheduledTask scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask != null) {
            scheduledTask.cancel();
        }
    }

    /**
     * 安排cron任务
     *
     * @param cronTask cron任务
     * @return {@link ScheduledTask}
     */
    public ScheduledTask scheduleCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.setFuture(this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger()));
        return scheduledTask;
    }

    /**
     * 摧毁
     */
    @PreDestroy
    public void destroy() {
        this.scheduledTasks.values().forEach(ScheduledTask::cancel);
        this.scheduledTasks.clear();
    }
}
