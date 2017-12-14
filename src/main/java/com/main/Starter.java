package com.main;

import com.rmi.QuartzApiService;
import com.test.DataWorkContext;
import com.bean.config.ScheduleJob;
import com.utils.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;
/**
 * Created by Administrator on 2017/12/8.
 */

public class Starter {
    private static Logger logger = Logger.getLogger(Starter.class);
    private List<ScheduleJob> jobList;
    /**
     *任务启动器
     */
    public static void main(String args[]){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        //这里获取任务信息数据
        List<ScheduleJob> jobList = DataWorkContext.getAllJob();
        if(!(null == jobList || jobList.size() == 0)){
            try {
                LoaderScheduler(jobList);
            } catch (SchedulerException e) {
                logger.error("starter failed loader error");
                e.printStackTrace();
            }
        }else{
                 logger.error("starter failed job list is null");
        }

    }
    /**
     *任务载入
     */
    public static void  LoaderScheduler(List<ScheduleJob> jobList) throws SchedulerException {
        QuartzApiService qa = SpringContextUtil.getBean("quartzApiService");
        qa.addJob(jobList);
    }

}
