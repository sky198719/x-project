
package com.xxdai.external.eurocup.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-06-30T11:33:59.083+08:00
 * Generated source version: 2.7.18
 * 
 */
 
public class EuroCupCXFService_EuroCupCXFServiceImplPort_Server{

    protected EuroCupCXFService_EuroCupCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new EuroCupCXFServiceImpl();
        String address = "http://192.168.38.152/v6_webservice/service/euroCup";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new EuroCupCXFService_EuroCupCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}