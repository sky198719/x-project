
package com.xxdai.external.fundTrade.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2015-09-05T17:15:00.275+08:00
 * Generated source version: 2.7.12
 * 
 */
 
public class FundTradeCXFService_FundTradeCXFServiceImplPort_Server{

    protected FundTradeCXFService_FundTradeCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new FundTradeCXFServiceImpl();
        String address = "http://localhost:8080/v6_tradews/service/fundTradeWebService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new FundTradeCXFService_FundTradeCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
