
package com.xxdai.external.appro.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-03-11T15:47:07.826+08:00
 * Generated source version: 2.7.18
 * 
 */
 
public class ApproCXFService_ApproCXFServiceImplPort_Server{

    protected ApproCXFService_ApproCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new ApproCXFServiceImpl();
        String address = "http://172.16.14.21:8080/v6_webservice/service/approWebService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new ApproCXFService_ApproCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
