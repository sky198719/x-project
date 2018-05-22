
package com.xxdai.external.tender;

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
 * 2016-10-08T16:32:46.822+08:00
 * Generated source version: 2.7.18
 * 
 */
public final class TenderCXFService_TenderCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.account.xxdai.com/", "TenderCXFServiceImplService");

    private TenderCXFService_TenderCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = TenderCXFServiceImplService.WSDL_LOCATION;
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
      
        TenderCXFServiceImplService ss = new TenderCXFServiceImplService(wsdlURL, SERVICE_NAME);
        TenderCXFService port = ss.getTenderCXFServiceImplPort();  
        
        {
        System.out.println("Invoking queryBorrowTenderbyid...");
        java.lang.String _queryBorrowTenderbyid_borrowid = "";
        java.lang.String _queryBorrowTenderbyid__return = port.queryBorrowTenderbyid(_queryBorrowTenderbyid_borrowid);
        System.out.println("queryBorrowTenderbyid.result=" + _queryBorrowTenderbyid__return);


        }
        {
        System.out.println("Invoking queryTenderDuein...");
        java.lang.String _queryTenderDuein_param = "";
        java.lang.String _queryTenderDuein__return = port.queryTenderDuein(_queryTenderDuein_param);
        System.out.println("queryTenderDuein.result=" + _queryTenderDuein__return);


        }
        {
        System.out.println("Invoking saveBorrowAuto...");
        java.lang.String _saveBorrowAuto_param = "";
        java.lang.String _saveBorrowAuto__return = port.saveBorrowAuto(_saveBorrowAuto_param);
        System.out.println("saveBorrowAuto.result=" + _saveBorrowAuto__return);


        }
        {
        System.out.println("Invoking selectTopBorrowRecord...");
        java.lang.String _selectTopBorrowRecord_param = "";
        java.lang.String _selectTopBorrowRecord__return = port.selectTopBorrowRecord(_selectTopBorrowRecord_param);
        System.out.println("selectTopBorrowRecord.result=" + _selectTopBorrowRecord__return);


        }
        {
        System.out.println("Invoking queryTenderQueryNew...");
        java.lang.String _queryTenderQueryNew_param = "";
        java.lang.String _queryTenderQueryNew__return = port.queryTenderQueryNew(_queryTenderQueryNew_param);
        System.out.println("queryTenderQueryNew.result=" + _queryTenderQueryNew__return);


        }
        {
        System.out.println("Invoking queryTenderDueinV2...");
        java.lang.String _queryTenderDueinV2_param = "";
        java.lang.String _queryTenderDueinV2__return = port.queryTenderDueinV2(_queryTenderDueinV2_param);
        System.out.println("queryTenderDueinV2.result=" + _queryTenderDueinV2__return);


        }
        {
        System.out.println("Invoking selecBorrowAutoConf...");
        java.lang.String _selecBorrowAutoConf_userId = "";
        java.lang.String _selecBorrowAutoConf__return = port.selecBorrowAutoConf(_selecBorrowAutoConf_userId);
        System.out.println("selecBorrowAutoConf.result=" + _selecBorrowAutoConf__return);


        }
        {
        System.out.println("Invoking selectCollectInterest...");
        java.lang.String _selectCollectInterest_param = "";
        java.lang.String _selectCollectInterest__return = port.selectCollectInterest(_selectCollectInterest_param);
        System.out.println("selectCollectInterest.result=" + _selectCollectInterest__return);


        }
        {
        System.out.println("Invoking queryBorrowAuto...");
        java.lang.String _queryBorrowAuto_userId = "";
        java.lang.String _queryBorrowAuto__return = port.queryBorrowAuto(_queryBorrowAuto_userId);
        System.out.println("queryBorrowAuto.result=" + _queryBorrowAuto__return);


        }

        System.exit(0);
    }

}