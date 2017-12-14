package com.jobs.elastic;

import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */
public class ElasticClient {

    private static Logger logger = Logger.getLogger(ElasticClient.class);

    private String clusterName;

    private int nodePort;

    private String nodeIps;


    private List<String> eshostlist = new ArrayList<String>();

    public  TransportClient elasticClient;


    public int getNodePort() {
        return nodePort;
    }

    public void setNodePort(int nodePort) {
        this.nodePort = nodePort;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setNodeIps(String nodeIps) {
        this.nodeIps = nodeIps;
    }

    public void getClient(){
        elasticClient = getTransportClient();
    }


    private TransportClient getTransportClient(){
        try {
            String[] sourceStrArray = nodeIps.split(",");
            for (int i = 0; i < sourceStrArray.length; i++) {
                eshostlist.add(sourceStrArray[i]);
            }

            Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).put("transport.tcp.compress", true).build();
            TransportAddress[] addressArr = new TransportAddress[eshostlist.size()];
            for (int i = 0; i < eshostlist.size(); i++) {
                try {
                    addressArr[i] = new InetSocketTransportAddress(InetAddress.getByName(eshostlist.get(i)), nodePort);
                } catch (UnknownHostException e) {
                    logger.error("ElasticMonitor getTransportClient :"+e.getMessage());
                    return null;
                }
            }
            TransportClient c = TransportClient.builder().settings(settings).build().addTransportAddresses(addressArr);
            return c;
        } catch (Exception e) {
            logger.error("ElasticMonitor getTransportClient :"+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
