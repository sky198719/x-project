
package com.xxdai.external.employee.ws;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-08-02T18:11:57.546+08:00
 * Generated source version: 2.7.12
 * 
 */
 
public class EmployeeCXFService_EmployeeCXFServiceImplPort_Server{

    protected EmployeeCXFService_EmployeeCXFServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new EmployeeCXFServiceImpl();
        String address = "http://192.168.31.34/yx_service/service/employeeWebService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new EmployeeCXFService_EmployeeCXFServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}