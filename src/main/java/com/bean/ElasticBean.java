package com.bean;

import java.io.Serializable;

public class ElasticBean implements Serializable {

    private static final long serialVersionUID = -1670104403789066243L;
    
    // MONITOR_ID VARCHAR2(100)
    public String monitorId;
    // ENTER_DATE DATE
    public java.sql.Timestamp enterDate;
    //es��Ⱥ����
    public String clusterName;
    //es�ڵ�����
    public String nodeName;
    //�Ƿ���master
    public boolean masterFlag;
    //���ڴ�ʹ����
    public int heapUsedPercent;
    //ϵͳcpuʹ����
    public int osCupPercent;
    //ϵͳ�ڴ�
    public int osMen;
    //��ǰ�߳���
    public int currentThread;
    //���߳���
    public int totalThread;
    //�ĵ���
    public double docsTotal;
    
    
    
    
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


    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getNodeName() {
        return nodeName;
    }


    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }


    public boolean isMasterFlag() {
        return masterFlag;
    }


    public void setMasterFlag(boolean masterFlag) {
        this.masterFlag = masterFlag;
    }


    public int getHeapUsedPercent() {
        return heapUsedPercent;
    }


    public void setHeapUsedPercent(int heapUsedPercent) {
        this.heapUsedPercent = heapUsedPercent;
    }


    public int getOsCupPercent() {
        return osCupPercent;
    }




    public void setOsCupPercent(int osCupPercent) {
        this.osCupPercent = osCupPercent;
    }




    public int getOsMen() {
        return osMen;
    }




    public void setOsMen(int osMen) {
        this.osMen = osMen;
    }




    public int getCurrentThread() {
        return currentThread;
    }




    public void setCurrentThread(int currentThread) {
        this.currentThread = currentThread;
    }




    public int getTotalThread() {
        return totalThread;
    }




    public void setTotalThread(int totalThread) {
        this.totalThread = totalThread;
    }




    public double getDocsTotal() {
        return docsTotal;
    }




    public void setDocsTotal(double docsTotal) {
        this.docsTotal = docsTotal;
    }


    @Override
    public String toString() {
        return "ElasticBean [monitorId=" + monitorId + ", enterDate="
            + enterDate + ", clusterName=" + clusterName + ", nodeName="
            + nodeName + ", masterFlag=" + masterFlag + ", heapUsedPercent="
            + heapUsedPercent + ", osCupPercent=" + osCupPercent + ", osMen="
            + osMen + ", currentThread=" + currentThread + ", totalThread="
            + totalThread + ", docsTotal=" + docsTotal + "]";
    }
    
}
