
package com.xxdai.external.accounttrade.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2014-11-09T17:09:31.948+08:00
 * Generated source version: 2.7.12
 * 
 */
 
public class AccountTradeCXFService_AccountTradeCXFServiceImplPort_Server{

    protected AccountTradeCXFService_AccountTradeCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new AccountTradeCXFServiceImpl();
        String address = "http://localhost:8080/v6_tradews/service/accountTradeWebService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new AccountTradeCXFService_AccountTradeCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
