
package com.xxdai.external.redpacket.ws;

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
 * This class was generated by Apache CXF 2.7.18
 * 2016-08-15T10:40:31.042+08:00
 * Generated source version: 2.7.18
 * 
 */
public final class RedpacketCXFService_RedpacketCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.redpacket.xxdai.com/", "RedpacketCXFServiceImplService");

    private RedpacketCXFService_RedpacketCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RedpacketCXFServiceImplService.WSDL_LOCATION;
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
      
        RedpacketCXFServiceImplService ss = new RedpacketCXFServiceImplService(wsdlURL, SERVICE_NAME);
        RedpacketCXFService port = ss.getRedpacketCXFServiceImplPort();  
        
        {
        System.out.println("Invoking getRedpacketListByUseCodition...");
        java.lang.String _getRedpacketListByUseCodition_redpacketJson = "";
        java.lang.String _getRedpacketListByUseCodition__return = port.getRedpacketListByUseCodition(_getRedpacketListByUseCodition_redpacketJson);
        System.out.println("getRedpacketListByUseCodition.result=" + _getRedpacketListByUseCodition__return);


        }
        {
        System.out.println("Invoking getRedpacketTotal...");
        java.lang.String _getRedpacketTotal_redpacketJson = "";
        java.lang.String _getRedpacketTotal__return = port.getRedpacketTotal(_getRedpacketTotal_redpacketJson);
        System.out.println("getRedpacketTotal.result=" + _getRedpacketTotal__return);


        }
        {
        System.out.println("Invoking queryRedPacketRecord...");
        java.lang.String _queryRedPacketRecord__return = port.queryRedPacketRecord();
        System.out.println("queryRedPacketRecord.result=" + _queryRedPacketRecord__return);


        }
        {
        System.out.println("Invoking getRedpacketList...");
        java.lang.String _getRedpacketList_redpacketJson = "";
        java.lang.String _getRedpacketList__return = port.getRedpacketList(_getRedpacketList_redpacketJson);
        System.out.println("getRedpacketList.result=" + _getRedpacketList__return);


        }

        System.exit(0);
    }

}