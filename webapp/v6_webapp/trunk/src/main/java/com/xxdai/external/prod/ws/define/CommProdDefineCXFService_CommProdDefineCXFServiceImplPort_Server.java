
package com.xxdai.external.prod.ws.define;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-06-12T19:13:53.419+08:00
 * Generated source version: 2.7.12
 * 
 */
 
public class CommProdDefineCXFService_CommProdDefineCXFServiceImplPort_Server{

    protected CommProdDefineCXFService_CommProdDefineCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new CommProdDefineCXFServiceImpl();
        String address = "http://192.168.38.152/v6_webservice/service/commProdDefineWebservice";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new CommProdDefineCXFService_CommProdDefineCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}