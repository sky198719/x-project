
package com.xxdai.external.redpacket.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-08-15T10:40:31.103+08:00
 * Generated source version: 2.7.18
 * 
 */
 
public class RedpacketCXFService_RedpacketCXFServiceImplPort_Server{

    protected RedpacketCXFService_RedpacketCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new RedpacketCXFServiceImpl();
        String address = "http://172.16.14.52:8080/v6_webservice/service/redpacketWebService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new RedpacketCXFService_RedpacketCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}