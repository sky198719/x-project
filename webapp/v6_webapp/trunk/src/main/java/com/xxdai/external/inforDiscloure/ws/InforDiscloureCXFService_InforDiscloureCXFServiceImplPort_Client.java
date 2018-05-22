
package com.xxdai.external.inforDiscloure.ws;

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
 * 2016-06-03T09:50:25.360+08:00
 * Generated source version: 2.7.12
 * 
 */
public final class InforDiscloureCXFService_InforDiscloureCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.statistics.xxdai.com/", "InforDiscloureCXFServiceImplService");

    private InforDiscloureCXFService_InforDiscloureCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = InforDiscloureCXFServiceImplService.WSDL_LOCATION;
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
      
        InforDiscloureCXFServiceImplService ss = new InforDiscloureCXFServiceImplService(wsdlURL, SERVICE_NAME);
        InforDiscloureCXFService port = ss.getInforDiscloureCXFServiceImplPort();  
        
        {
        System.out.println("Invoking queryInforList...");
        java.lang.String _queryInforList__return = port.queryInforList();
        System.out.println("queryInforList.result=" + _queryInforList__return);


        }
        {
        System.out.println("Invoking updateInforStatus...");
        java.lang.String _updateInforStatus_updInforDiscStatusStr = "";
        java.lang.String _updateInforStatus__return = port.updateInforStatus(_updateInforStatus_updInforDiscStatusStr);
        System.out.println("updateInforStatus.result=" + _updateInforStatus__return);


        }
        {
        System.out.println("Invoking addInforDiscloure...");
        java.lang.String _addInforDiscloure_addInforDiscloureStr = "";
        java.lang.String _addInforDiscloure__return = port.addInforDiscloure(_addInforDiscloure_addInforDiscloureStr);
        System.out.println("addInforDiscloure.result=" + _addInforDiscloure__return);


        }
        {
        System.out.println("Invoking getInforDiscList...");
        java.lang.String _getInforDiscList_jsonstring = "";
        java.lang.String _getInforDiscList__return = port.getInforDiscList(_getInforDiscList_jsonstring);
        System.out.println("getInforDiscList.result=" + _getInforDiscList__return);


        }
        {
        System.out.println("Invoking updateInforDiscloure...");
        java.lang.String _updateInforDiscloure_updateInforDiscloure = "";
        java.lang.String _updateInforDiscloure__return = port.updateInforDiscloure(_updateInforDiscloure_updateInforDiscloure);
        System.out.println("updateInforDiscloure.result=" + _updateInforDiscloure__return);


        }
        {
        System.out.println("Invoking getInforDiscloure...");
        java.lang.String _getInforDiscloure_jsonstring = "";
        java.lang.String _getInforDiscloure__return = port.getInforDiscloure(_getInforDiscloure_jsonstring);
        System.out.println("getInforDiscloure.result=" + _getInforDiscloure__return);


        }

        System.exit(0);
    }

}