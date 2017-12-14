package com.bean.config;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/12.
 */
public class ElasticConfig extends ScheduleJob implements Serializable {
    /**
     * 前台配置项
     * Elastic监控配置
     * es.cluster.name=SOC-15
     * es.port=9300
     * nodes=10.2.4.15,10.2.4.42,10.2.4.43
     * capability=false
     *
     */
    private String nodeIps;

    private String clusterName;

    private int nodePort;

    private boolean capabilityFlag;

    public String getNodeIps() {
        return nodeIps;
    }

    public void setNodeIps(String nodeIps) {
        this.nodeIps = nodeIps;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public int getNodePort() {
        return nodePort;
    }

    public void setNodePort(int nodePort) {
        this.nodePort = nodePort;
    }

    public boolean isCapabilityFlag() {
        return capabilityFlag;
    }

    public void setCapabilityFlag(boolean capabilityFlag) {
        this.capabilityFlag = capabilityFlag;
    }
}
