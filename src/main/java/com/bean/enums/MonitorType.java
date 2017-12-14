package com.bean.enums;


import com.jobs.elastic.ElasticMonitorJob;
import com.jobs.storm.StormMonitorJob;

/**
 * MonitorType TestTpye
 */
public enum MonitorType {

    ES(ElasticMonitorJob.class,"TCP"),
    STORM(StormMonitorJob.class,"TCP");

    Class clazz;
    String testType;

    private MonitorType(Class clazz,String testType){
        this.clazz = clazz;
        this.testType = testType;
    }
    public Class getClazz() {
        return clazz;
    }
    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }
}