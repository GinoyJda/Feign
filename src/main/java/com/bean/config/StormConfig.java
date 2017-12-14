package com.bean.config;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/12.
 */
public class StormConfig extends ScheduleJob implements Serializable {

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
    private int nimbus_port = 0;
    private boolean capabilityFlag = false;
    public String getNimbus_host() {
        return nimbus_host;
    }

    public void setNimbus_host(String nimbus_host) {
        this.nimbus_host = nimbus_host;
    }

    public String getSupervisors_hosts() {
        return supervisors_hosts;
    }

    public void setSupervisors_hosts(String supervisors_hosts) {
        this.supervisors_hosts = supervisors_hosts;
    }

    public int getNimbus_port() {
        return nimbus_port;
    }

    public void setNimbus_port(int nimbus_port) {
        this.nimbus_port = nimbus_port;
    }

    public boolean isCapabilityFlag() {
        return capabilityFlag;
    }

    public void setCapabilityFlag(boolean capabilityFlag) {
        this.capabilityFlag = capabilityFlag;
    }
}
