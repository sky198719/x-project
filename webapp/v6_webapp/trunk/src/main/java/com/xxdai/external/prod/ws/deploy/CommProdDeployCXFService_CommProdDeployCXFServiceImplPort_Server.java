
package com.xxdai.external.prod.ws.deploy;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2015-12-31T15:28:51.994+08:00
 * Generated source version: 2.7.18
 * 
 */
 
public class CommProdDeployCXFService_CommProdDeployCXFServiceImplPort_Server{

    protected CommProdDeployCXFService_CommProdDeployCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new CommProdDeployCXFServiceImpl();
        String address = "http://172.16.15.61:8080/v6_webservice/service/commProdDeployWebservice";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new CommProdDeployCXFService_CommProdDeployCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
