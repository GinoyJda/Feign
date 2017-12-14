package com.rmi.impl;

import com.bean.enums.MonitorType;
import com.bean.config.ScheduleJob;
import com.rmi.QuartzApiService;
import com.utils.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Reference;

/**
 * Created by Administrator on 2017/12/11.
 */
@Service("quartzApiService")
public class QuartzApiServiceImpl implements QuartzApiService {

    private static Logger logger = Logger.getLogger(QuartzApiServiceImpl.class);

    //job缓存
    public static Map<String, ScheduleJob> jobMap = new HashMap<String, ScheduleJob>();

    /**
     * 添加任务Job
     * @throws SchedulerException
     */
    public void addJob(List<ScheduleJob> jobList){
        logger.info("addJob method is invoked");
        try {
        Scheduler scheduler  = SpringContextUtil.getBean("scheduler");
        for (ScheduleJob job : jobList) {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
            CronTrigger trigger = null;
            trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //不存在，创建一个
            if (null == trigger) {
                Class c = MonitorType.valueOf(job.getMonitorType()).getClazz();
                if(null == c){
                    logger.error("QuartzApiInterface addJobMethod getMonitorTypeClass Null");
                }else{
                    JobDetail jobDetail = JobBuilder.newJob(c).withIdentity(job.getJobName(), job.getJobGroup()).build();
                    jobDetail.getJobDataMap().put("scheduleJob", job);
                    //表达式调度构建器
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                    //按新的cronExpression表达式构建一个新的trigger
                    trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
                    scheduler.scheduleJob(jobDetail, trigger);
                }

            } else {
                // Trigger已存在，那么更新相应的定时设置
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
            jobMap.put(job.getJobGroup() + "_" + job.getJobName(), job);
        }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的Job
     *
     * @throws SchedulerException
     */
    public List<ScheduleJob> getJobAll(){
        logger.info("getJobAll method is invoked");
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
        try {
        Scheduler scheduler  = SpringContextUtil.getBean("scheduler");
        // schedulerFactoryBean 由spring创建注入
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = null;
        jobKeys = scheduler.getJobKeys(matcher);

        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJob job = new ScheduleJob();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDesc("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                logger.info("all job is " + job.getJobName());
                jobList.add(job);

            }
        }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 获取正在运行的job
     *
     * @throws SchedulerException
     */
    public List<ScheduleJob> isRunJob(){
        try {
            logger.info("isRunJob method is invoked");
            Scheduler scheduler  = SpringContextUtil.getBean("scheduler");
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDesc("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            logger.info("runing job is " + job.getJobName());
            jobList.add(job);
            return jobList;
        }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 暂停Job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public  boolean pauseJob(ScheduleJob scheduleJob){
        logger.info("pauseJob method is invoked");
        try {
        Scheduler scheduler  = SpringContextUtil.getBean("scheduler");
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 恢复Job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public  boolean resumeJob(ScheduleJob scheduleJob){
        logger.info("resumeJob method is invoked");
        try {
        Scheduler scheduler  = SpringContextUtil.getBean("scheduler");
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 立即运行任务一次
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public  boolean triggerJob(ScheduleJob scheduleJob){
        logger.info("triggerJob method is invoked");
        try {
        Scheduler scheduler  = SpringContextUtil.getBean("scheduler");
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.triggerJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新任务表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public  boolean rescheduleJob(ScheduleJob scheduleJob){
        logger.info("rescheduleJob method is invoked");
        try {
        Scheduler scheduler  = SpringContextUtil.getBean("scheduler");
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        // 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除监控器
     * @param
     * @throws SchedulerException
     */
    public boolean deleteMonitor(List<ScheduleJob> list){
        logger.info("deleteMonitor method is invoked");
        try {
            Scheduler scheduler  = SpringContextUtil.getBean("scheduler");
            List<JobKey> jobKeys = new ArrayList<JobKey>();
            for(int i=0;i<list.size();i++){
                JobKey jobKey = JobKey.jobKey(list.get(i).getJobName(), list.get(i).getJobGroup());
                jobKeys.add(jobKey);
            }
            scheduler.deleteJobs(jobKeys);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

}
