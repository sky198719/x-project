
package com.xxdai.external.systemHoliday.ws;

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
 * 2015-09-17T22:38:48.033+08:00
 * Generated source version: 2.7.12
 * 
 */
public final class SystemHolidayCXFService_SystemHolidayCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.fund.xxdai.com/", "SystemHolidayCXFServiceImplService");

    private SystemHolidayCXFService_SystemHolidayCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SystemHolidayCXFServiceImplService.WSDL_LOCATION;
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
      
        SystemHolidayCXFServiceImplService ss = new SystemHolidayCXFServiceImplService(wsdlURL, SERVICE_NAME);
        SystemHolidayCXFService port = ss.getSystemHolidayCXFServiceImplPort();  
        
        {
        System.out.println("Invoking getValueDate...");
        java.lang.String _getValueDate_jsonstring = "";
        java.lang.String _getValueDate__return = port.getValueDate(_getValueDate_jsonstring);
        System.out.println("getValueDate.result=" + _getValueDate__return);


        }

        System.exit(0);
    }

}
