
package com.xxdai.external.weixin.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2015-03-20T16:27:32.233+08:00
 * Generated source version: 2.7.12
 * 
 */
 
public class WeixinCXFService_WeixinCXFServiceImplPort_Server{

    protected WeixinCXFService_WeixinCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new WeixinCXFServiceImpl();
        String address = "http://192.168.31.34/yx_service/service/weixinWebService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new WeixinCXFService_WeixinCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
