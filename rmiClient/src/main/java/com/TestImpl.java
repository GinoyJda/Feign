package com;

import com.bean.config.ScheduleJob;
import com.inter.QuartzApiService;
import com.inter.RmiTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */
@Service("TestImpl")
public class TestImpl {
    @Resource
    RmiTest rt;
    @Resource
    QuartzApiService quartzApiService;

    public String getMessage(String s){
        return rt.test(s);
    }

    public void addMonitor(){
        List<ScheduleJob> list =  ClientWorkContext.getAllJob();
        quartzApiService.addJob(list);
    }

    public void getAliveMonitor(){
        List<ScheduleJob> list = quartzApiService.isRunJob();
        if(null == list){
            System.out.println("monitors is 0");
        }else{
            for(int i=0;i<list.size();i++){
                System.out.println(list.get(i).getJobName());
            }
        }


    }

    public void getAllMonitor(){
        List<ScheduleJob> list = quartzApiService.getJobAll();
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).getJobName() +"[desc is]"+ list.get(i).getDesc());
        }
    }

    public void stopMonitor(){
        ScheduleJob sj = new ScheduleJob();
        sj.setJobName("ElasticMonitor");
        sj.setJobGroup("Host");
        quartzApiService.pauseJob(sj);
    }
}
