
package com.xxdai.external.yyp.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-12-01T15:10:24.468+08:00
 * Generated source version: 2.7.12
 * 
 */
 
public class ReglIntstCXFService_ReglIntstCXFServiceImplPort_Server{

    protected ReglIntstCXFService_ReglIntstCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new ReglIntstCXFServiceImpl();
        String address = "http://test.xxd.com/yx_service/service/yypCXFService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new ReglIntstCXFService_ReglIntstCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}