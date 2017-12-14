package com.jobs.storm;

import com.bean.ClientInfo;
import org.apache.log4j.Logger;
import org.apache.storm.generated.Nimbus;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by Administrator on 2017/12/12.
 */
public class StormClient {

    private static Logger logger = Logger.getLogger(StormClient.class);

    public  ClientInfo stormClient;

    private String nimbus_host = "";

    private int nimbus_port = 0;

    public String getNimbus_host() {
        return nimbus_host;
    }

    public void setNimbus_host(String nimbus_host) {
        this.nimbus_host = nimbus_host;
    }

    public int getNimbus_port() {
        return nimbus_port;
    }

    public void setNimbus_port(int nimbus_port) {
        this.nimbus_port = nimbus_port;
    }

    public void getClient(){
        try {
            stormClient = getStormClient();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public ClientInfo getStormClient() throws TTransportException {
        logger.info("StormMonitor getClient nimbusHost:"+nimbus_host+" nimbusPort:"+nimbus_port);
        ClientInfo client = new ClientInfo();
        TSocket tsocket = new TSocket(nimbus_host, nimbus_port);
        TFramedTransport tTransport = new TFramedTransport(tsocket);
        TBinaryProtocol tBinaryProtocol = new TBinaryProtocol(tTransport);
        Nimbus.Client c = new Nimbus.Client(tBinaryProtocol);
        tTransport.open();
        client.setTsocket(tsocket);
        client.settTransport(tTransport);
        client.settBinaryProtocol(tBinaryProtocol);
        client.setClient(c);
        return client;
    }

    /**
     * StormMonitor 关闭client
     */
    public  void closeClient(ClientInfo client) {
        if (null == client) {
            return;
        }

        if (null != client.gettTransport()) {
            client.gettTransport().close();
        }

        if (null != client.getTsocket()) {
            client.getTsocket().close();
        }
    }
}
