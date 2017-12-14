package com.rmi;

import com.bean.config.ScheduleJob;
import org.quartz.*;

import java.rmi.Remote;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */
public interface QuartzApiService{

    /**
     * 添加任务Job
     * @throws SchedulerException
     */
    public void addJob(List<ScheduleJob> jobList);

    /**
     * 获取所有的Job
     *
     * @throws SchedulerException
     */
    public  List<ScheduleJob>  getJobAll();

    /**
     * 获取正在运行的job
     *
     * @throws SchedulerException
     */
    public  List<ScheduleJob> isRunJob();

    /**
     * 暂停Job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public  boolean pauseJob(ScheduleJob scheduleJob);

    /**
     * 恢复Job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public  boolean resumeJob(ScheduleJob scheduleJob);

    /**
     * 立即运行任务一次
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public  boolean triggerJob(ScheduleJob scheduleJob);

    /**
     * 更新任务表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public  boolean rescheduleJob(ScheduleJob scheduleJob);

    /**
     * 删除监控器
     * @param scheduleJob
     * @throws SchedulerException
     */
    public boolean deleteMonitor(List<ScheduleJob> list);
}
