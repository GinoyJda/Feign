package com.bean;

import java.io.Serializable;

public class StormBean implements Serializable {

    private static final long serialVersionUID = 2820563341755275750L;
    // MONITOR_ID VARCHAR2(100)
    public String monitorId;
    // ENTER_DATE DATE
    public java.sql.Timestamp enterDate;
    // regionServer���ڵ���Ϣ   VARCHAR2(100)
    public String nimbusString;
    // regionServer�����ڵ���Ϣ   VARCHAR2(100)
    public String supervisiorString;
    //���ڹ���worker����
    public int usedWorkerNum;
    //��ʹ���ڴ�
    public double usedMem;
    //toplogy����
    public String toplogyName;
    //toplogy״̬
    public String topologyStatus;
    //toplogy Executors����
    public int topologyExecutors;
    //topology Workers����
    public int topologyWorkers;
    //topology Tasks����
    public int topologyTasks;
    

    public String getToplogyName() {
        return toplogyName;
    }


    public void setToplogyName(String toplogyName) {
        this.toplogyName = toplogyName;
    }


    public String getTopologyStatus() {
        return topologyStatus;
    }


    public void setTopologyStatus(String topologyStatus) {
        this.topologyStatus = topologyStatus;
    }


    public int getTopologyExecutors() {
        return topologyExecutors;
    }


    public void setTopologyExecutors(int topologyExecutors) {
        this.topologyExecutors = topologyExecutors;
    }


    public int getTopologyWorkers() {
        return topologyWorkers;
    }


    public void setTopologyWorkers(int topologyWorkers) {
        this.topologyWorkers = topologyWorkers;
    }


    public int getTopologyTasks() {
        return topologyTasks;
    }


    public void setTopologyTasks(int topologyTasks) {
        this.topologyTasks = topologyTasks;
    }


    public String getMonitorId() {
        return monitorId;
    }


    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }


    public java.sql.Timestamp getEnterDate() {
        return enterDate;
    }


    public void setEnterDate(java.sql.Timestamp enterDate) {
        this.enterDate = enterDate;
    }


    public String getNimbusString() {
        return nimbusString;
    }


    public void setNimbusString(String nimbusString) {
        this.nimbusString = nimbusString;
    }


    public String getSupervisiorString() {
        return supervisiorString;
    }


    public void setSupervisiorString(String supervisiorString) {
        this.supervisiorString = supervisiorString;
    }


    public int getUsedWorkerNum() {
        return usedWorkerNum;
    }


    public void setUsedWorkerNum(int usedWorkerNum) {
        this.usedWorkerNum = usedWorkerNum;
    }


    public double getUsedMem() {
        return usedMem;
    }


    public void setUsedMem(double usedMem) {
        this.usedMem = usedMem;
    }

    @Override
    public String toString() {
        return "StormBean{" +
                "monitorId='" + monitorId + '\'' +
                ", enterDate=" + enterDate +
                ", nimbusString='" + nimbusString + '\'' +
                ", supervisiorString='" + supervisiorString + '\'' +
                ", usedWorkerNum=" + usedWorkerNum +
                ", usedMem=" + usedMem +
                ", toplogyName='" + toplogyName + '\'' +
                ", topologyStatus='" + topologyStatus + '\'' +
                ", topologyExecutors=" + topologyExecutors +
                ", topologyWorkers=" + topologyWorkers +
                ", topologyTasks=" + topologyTasks +
                '}';
    }
}
