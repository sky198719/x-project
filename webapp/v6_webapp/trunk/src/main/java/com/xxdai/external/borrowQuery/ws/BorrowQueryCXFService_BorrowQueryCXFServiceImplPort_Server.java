
package com.xxdai.external.borrowQuery.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2017-04-20T00:58:55.065+08:00
 * Generated source version: 2.7.12
 * 
 */
 
public class BorrowQueryCXFService_BorrowQueryCXFServiceImplPort_Server{

    protected BorrowQueryCXFService_BorrowQueryCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new BorrowQueryCXFServiceImpl();
        String address = "http://test.xxd.com/yx_service/service/borrowQueryWebService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new BorrowQueryCXFService_BorrowQueryCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}