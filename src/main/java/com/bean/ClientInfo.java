package com.bean;

import org.apache.storm.generated.Nimbus;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

public class ClientInfo {  
    private TSocket tsocket;  
    private TFramedTransport tTransport;  
    private TBinaryProtocol tBinaryProtocol;  
    private Nimbus.Client client;  
  
    public TSocket getTsocket() {  
        return tsocket;  
    }  
  
    public void setTsocket(TSocket tsocket) {  
        this.tsocket = tsocket;  
    }  
  
    public TFramedTransport gettTransport() {  
        return tTransport;  
    }  
  
    public void settTransport(TFramedTransport tTransport) {  
        this.tTransport = tTransport;  
    }  
  
    public TBinaryProtocol gettBinaryProtocol() {  
        return tBinaryProtocol;  
    }  
  
    public void settBinaryProtocol(TBinaryProtocol tBinaryProtocol) {  
        this.tBinaryProtocol = tBinaryProtocol;  
    }  
  
    public Nimbus.Client getClient() {  
        return client;  
    }  
  
    public void setClient(Nimbus.Client client) {  
        this.client = client;  
    }  
}  