package com;


import com.bean.config.ElasticConfig;
import com.bean.config.ScheduleJob;
import com.bean.config.StormConfig;

import java.util.ArrayList;
import java.util.List;

public class ClientWorkContext {

    public static List<ScheduleJob> getAllJob() {
        List<ScheduleJob> list = new ArrayList<ScheduleJob>();
        StormConfig job0 = new StormConfig();
        job0.setSupervisors_hosts("SOC-12,S0C-13,SOC-14");
        job0.setNimbus_host("10.176.63.102");
        job0.setNimbus_port(6627);
        job0.setCapabilityFlag(true);
        job0.setJobId("10001");
        job0.setJobName("StormMonitor");
        job0.setJobGroup("Host");
        job0.setJobStatus("1");
        job0.setCronExpression("0/10 * * * * ?");
        job0.setDesc("StormMonitor监控器");
        job0.setMonitorType("STORM");
        list.add(job0);


        //MonitorVo初始化结果
        ElasticConfig job = new ElasticConfig();
        job.setNodeIps("10.176.63.102,10.176.63.103,10.176.63.104");
//        job.setNodeIps("10.176.63.106,10.176.36.22");
        job.setClusterName("SOC-15");
        job.setNodePort(9300);
        job.setCapabilityFlag(true);
        job.setJobId("10002");
        job.setJobName("ElasticMonitor");
        job.setJobGroup("Host");
        job.setJobStatus("1");
        job.setCronExpression("0/5 * * * * ?");
        job.setDesc("ElasticMonitor监控器");
        job.setMonitorType("ES");
        list.add(job);

        return list;
    }
}

//    /**
//     * 通过mv对象来初始化全局变量
//     * MonitorVo
//     * nimbushost：   commandPrompt
//     * nimbusport：   windowsDomain
//     * supervisors：   monitorDatabaseName
//     * capability：      trustName
//     */
//    public void initQuantity(){
//        nimbus_host = "10.176.63.102";
//        supervisors_hosts = "SOC-102,SOC-103,SOC-104"  ;
//        nimbus_port = 6627;
//        capabilityFlag = true;
//        //MonitorVo初始化结果
//        logger.info("StormMonitor initQuantity nimbus_host ="+nimbus_host+" supervisors_hosts ="+supervisors_hosts+" nimbus_port = "+nimbus_port);
//    }
