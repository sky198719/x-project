
package com.xxdai.system.ws;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-10-17T11:40:36.885+08:00
 * Generated source version: 2.7.12
 * 
 */
public final class SysConfigCXFService_SysConfigCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.system.xxdai.com/", "SysConfigCXFServiceImplService");

    private SysConfigCXFService_SysConfigCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SysConfigCXFServiceImplService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        SysConfigCXFServiceImplService ss = new SysConfigCXFServiceImplService(wsdlURL, SERVICE_NAME);
        SysConfigCXFService port = ss.getSysConfigCXFServiceImplPort();  
        
        {
        System.out.println("Invoking addSConfig...");
        java.lang.String _addSConfig_addSysConfig = "";
        java.lang.String _addSConfig__return = port.addSConfig(_addSConfig_addSysConfig);
        System.out.println("addSConfig.result=" + _addSConfig__return);


        }
        {
        System.out.println("Invoking updateSysConfig...");
        java.lang.String _updateSysConfig_syskey = "";
        java.lang.String _updateSysConfig__return = port.updateSysConfig(_updateSysConfig_syskey);
        System.out.println("updateSysConfig.result=" + _updateSysConfig__return);


        }
        {
        System.out.println("Invoking clearSysConfigAllCache...");
        java.lang.String _clearSysConfigAllCache__return = port.clearSysConfigAllCache();
        System.out.println("clearSysConfigAllCache.result=" + _clearSysConfigAllCache__return);


        }
        {
        System.out.println("Invoking clearSysConfigCache...");
        java.lang.String _clearSysConfigCache_syskey = "";
        java.lang.String _clearSysConfigCache__return = port.clearSysConfigCache(_clearSysConfigCache_syskey);
        System.out.println("clearSysConfigCache.result=" + _clearSysConfigCache__return);


        }
        {
        System.out.println("Invoking queryObjectById...");
        java.lang.String _queryObjectById_queryId = "";
        java.lang.String _queryObjectById__return = port.queryObjectById(_queryObjectById_queryId);
        System.out.println("queryObjectById.result=" + _queryObjectById__return);


        }
        {
        System.out.println("Invoking querySysConfigByKey...");
        java.lang.String _querySysConfigByKey_syskey = "";
        java.lang.String _querySysConfigByKey__return = port.querySysConfigByKey(_querySysConfigByKey_syskey);
        System.out.println("querySysConfigByKey.result=" + _querySysConfigByKey__return);


        }
        {
        System.out.println("Invoking queryPageSysConfig...");
        java.lang.String _queryPageSysConfig_syskey = "";
        java.lang.String _queryPageSysConfig__return = port.queryPageSysConfig(_queryPageSysConfig_syskey);
        System.out.println("queryPageSysConfig.result=" + _queryPageSysConfig__return);


        }
        {
        System.out.println("Invoking updateSConfig...");
        java.lang.String _updateSConfig_updateSysConfig = "";
        java.lang.String _updateSConfig__return = port.updateSConfig(_updateSConfig_updateSysConfig);
        System.out.println("updateSConfig.result=" + _updateSConfig__return);


        }

        System.exit(0);
    }

}