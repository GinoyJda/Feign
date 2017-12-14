package com.jobs.storm;

import com.bean.ClientInfo;
import com.bean.StormBean;
import com.bean.config.StormConfig;
import com.utils.SpringContextUtil;
import org.apache.log4j.Logger;
import org.apache.storm.generated.*;
import org.apache.thrift.TException;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */
@DisallowConcurrentExecution
public class StormMonitorJob  implements Job {
    private static Logger logger = Logger.getLogger(StormMonitorJob.class);

    /**
     * 前台配置项
     * storm监控配置
     * nimbus.host=10.2.4.12
     * nimbus.port=6627
     * 配置主机名称
     * supervisors=SOC-12,SOC-13,SOC-14
     */
    private String nimbus_host = "";
    private String supervisors_hosts = "";
    private List<String> alivelist = new ArrayList<String>();
    private List<String> supervisorshostslist = new ArrayList<String>();
    private boolean capabilityFlag = false;
    private StormClient sc;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        StormConfig scheduleJob = (StormConfig)context.getMergedJobDataMap().get("scheduleJob");
        logger.info("[ thread id "+ Thread.currentThread().getId()+"]" + "任务名称 = [" + scheduleJob.getJobName() + "]");
        capabilityFlag = scheduleJob.isCapabilityFlag();
        supervisors_hosts = scheduleJob.getSupervisors_hosts();
        nimbus_host = scheduleJob.getNimbus_host();
        ClientInfo client = null;
        ClusterSummary clusterSummary = null;
        try {
            sc = SpringContextUtil.getBean(StormClient.class);
        }catch (NoSuchBeanDefinitionException e){
            HashMap attributes = new HashMap();
            attributes.put("nimbus_host",scheduleJob.getNimbus_host());
            attributes.put("nimbus_port",scheduleJob.getNimbus_port());
            SpringContextUtil.autoRegisterBean(attributes,"stormClient",StormClient.class);
            sc = SpringContextUtil.getBean(StormClient.class);
            sc.getClient();
        }
        try {
            client = sc.stormClient;
            clusterSummary = client.getClient().getClusterInfo();
        } catch (AuthorizationException e) {
            logger.info("StormMonitor get clusterSummary error ");
            e.printStackTrace();
        } catch (TException e) {
            logger.info("StormMonitor get clusterSummary error ");
            e.printStackTrace();
        }
        //封装ResultBean
        packageResultBean(clusterSummary);
    }



    /**
     * 封装监控结果信息ResultBean
     */
    public void packageResultBean(ClusterSummary clusterSummary) {

        String[] ss = supervisors_hosts.split(",");
        String[] ns = nimbus_host.split(",");

        List<SupervisorSummary> supervisiorList =  clusterSummary.getSupervisors();
        List<NimbusSummary> nimbusList =  clusterSummary.getNimbuses();
        List<TopologySummary> toplogyList =  clusterSummary.getTopologies();


        //先判断toplogy
        if(toplogyList.size() == 0){
//            BigDataUtil.packageFaultResultBean(rb, mv,FaultEnum.F_1204064.id,FaultEnum.F_1204064.desc);
        }

        //判断nimbus
        if(nimbusList.size() == 0){
            for(int i = 0 ; i<ns.length ; i++){
//                BigDataUtil.packageFaultResultBean(rb, mv,FaultEnum.F_1204057.id,FaultEnum.F_1204057.desc+" nimbus_host:"+ns[i]);
            }
        }

        //再判断supervisior
        if(supervisiorList.size() == 0){
            for(int i = 0 ; i<ss.length ; i++){
//                BigDataUtil.packageFaultResultBean(rb, mv,FaultEnum.F_1204056.id,FaultEnum.F_1204056.desc+" supervisior_host:"+ss[i]);
            }
        }else{
            for(int i = 0 ; i<ss.length ; i++){
                String stormip = ss[i];
                for(int j = 0;j<supervisiorList.size();j++){
                    if(stormip.equals(supervisiorList.get(j).host)){
                        //TODO生成数据 生成性能事件
                        alivelist.add(supervisiorList.get(j).host);
                        if(capabilityFlag){
                            StormBean sai = new StormBean();
//                            sai.setEnterDate(rb.time);
//                            sai.setMonitorId(rb.monitorID);
                            sai.setToplogyName(toplogyList.get(0).name);
                            sai.setTopologyExecutors(toplogyList.get(0).num_executors);
                            sai.setTopologyStatus(toplogyList.get(0).status);
                            sai.setTopologyTasks(toplogyList.get(0).getNum_tasks());
                            sai.setTopologyWorkers(toplogyList.get(0).getNum_workers());
                            sai.setNimbusString(nimbusList.get(0).host);
                            sai.setSupervisiorString(supervisiorList.get(j).host);
                            sai.setUsedWorkerNum(supervisiorList.get(j).num_used_workers);
                            sai.setUsedMem(supervisiorList.get(j).used_mem);
                            logger.info("StormBean:"+sai.toString());
//                            rb.addInfo(sai);
                        }
                    }
                }
            }
        }

        supervisorshostslist = Arrays.asList(ss);

        //重新封装list  解决removeAll产生notsupport异常
        List<String> tempList = new ArrayList<String>();
        for(int i = 0;i<supervisorshostslist.size();i++){
            tempList.add(supervisorshostslist.get(i));
        }

        if(tempList.removeAll(alivelist)){
            for(int i = 0;i<tempList.size();i++){
//                BigDataUtil.packageFaultResultBean(rb,mv ,FaultEnum.F_1204056.id,FaultEnum.F_1204056.desc+" supervisior_host:"+tempList.get(i));
            }
        }
    }
}
