
package com.xxdai.external.borrowFee.ws;

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
 * 2017-04-14T14:55:42.770+08:00
 * Generated source version: 2.7.12
 * 
 */
public final class BorrowFeeCXFService_BorrowFeeCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.borrow.xxdai.com/", "BorrowFeeCXFServiceImplService");

    private BorrowFeeCXFService_BorrowFeeCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = BorrowFeeCXFServiceImplService.WSDL_LOCATION;
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
      
        BorrowFeeCXFServiceImplService ss = new BorrowFeeCXFServiceImplService(wsdlURL, SERVICE_NAME);
        BorrowFeeCXFService port = ss.getBorrowFeeCXFServiceImplPort();  
        
        {
        System.out.println("Invoking selectBorrowFeesMapByBorrowId...");
        java.lang.String _selectBorrowFeesMapByBorrowId_json = "";
        try {
            java.lang.String _selectBorrowFeesMapByBorrowId__return = port.selectBorrowFeesMapByBorrowId(_selectBorrowFeesMapByBorrowId_json);
            System.out.println("selectBorrowFeesMapByBorrowId.result=" + _selectBorrowFeesMapByBorrowId__return);

        } catch (DaoException_Exception e) { 
            System.out.println("Expected exception: DaoException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
