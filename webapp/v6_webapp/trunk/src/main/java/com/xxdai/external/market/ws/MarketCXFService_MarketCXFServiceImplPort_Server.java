
package com.xxdai.external.market.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-07-22T16:23:08.731+08:00
 * Generated source version: 2.7.18
 * 
 */
 
public class MarketCXFService_MarketCXFServiceImplPort_Server{

    protected MarketCXFService_MarketCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new MarketCXFServiceImpl();
        String address = "http://172.16.14.45:8080/v6_webservice/service/marketWebService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new MarketCXFService_MarketCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}