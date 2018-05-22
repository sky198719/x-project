
package com.xxdai.external.yyp.ws;

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
 * 2016-12-01T15:10:24.421+08:00
 * Generated source version: 2.7.12
 * 
 */
public final class ReglIntstCXFService_ReglIntstCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.yyp.xxdai.com/", "ReglIntstCXFServiceImplService");

    private ReglIntstCXFService_ReglIntstCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = ReglIntstCXFServiceImplService.WSDL_LOCATION;
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
      
        ReglIntstCXFServiceImplService ss = new ReglIntstCXFServiceImplService(wsdlURL, SERVICE_NAME);
        ReglIntstCXFService port = ss.getReglIntstCXFServiceImplPort();  
        
        {
        System.out.println("Invoking queryYypJoinList...");
        java.lang.String _queryYypJoinList_jsonParam = "";
        java.lang.String _queryYypJoinList__return = port.queryYypJoinList(_queryYypJoinList_jsonParam);
        System.out.println("queryYypJoinList.result=" + _queryYypJoinList__return);


        }
        {
        System.out.println("Invoking queryReglintstStaticInfo...");
        java.lang.String _queryReglintstStaticInfo__return = port.queryReglintstStaticInfo();
        System.out.println("queryReglintstStaticInfo.result=" + _queryReglintstStaticInfo__return);


        }
        {
        System.out.println("Invoking updateReglintst...");
        java.lang.String _updateReglintst_params = "";
        java.lang.String _updateReglintst__return = port.updateReglintst(_updateReglintst_params);
        System.out.println("updateReglintst.result=" + _updateReglintst__return);


        }
        {
        System.out.println("Invoking getReglintstById...");
        java.lang.String _getReglintstById_params = "";
        java.lang.String _getReglintstById__return = port.getReglintstById(_getReglintstById_params);
        System.out.println("getReglintstById.result=" + _getReglintstById__return);


        }
        {
        System.out.println("Invoking queryYYPLicaiLogs...");
        java.lang.String _queryYYPLicaiLogs_params = "";
        java.lang.String _queryYYPLicaiLogs__return = port.queryYYPLicaiLogs(_queryYYPLicaiLogs_params);
        System.out.println("queryYYPLicaiLogs.result=" + _queryYYPLicaiLogs__return);


        }
        {
        System.out.println("Invoking repealReglintst...");
        java.lang.String _repealReglintst_params = "";
        java.lang.String _repealReglintst__return = port.repealReglintst(_repealReglintst_params);
        System.out.println("repealReglintst.result=" + _repealReglintst__return);


        }
        {
        System.out.println("Invoking getReglintstAccount...");
        java.lang.String _getReglintstAccount__return = port.getReglintstAccount();
        System.out.println("getReglintstAccount.result=" + _getReglintstAccount__return);


        }
        {
        System.out.println("Invoking queryInvestInfo...");
        java.lang.String _queryInvestInfo_jsonParam = "";
        java.lang.String _queryInvestInfo__return = port.queryInvestInfo(_queryInvestInfo_jsonParam);
        System.out.println("queryInvestInfo.result=" + _queryInvestInfo__return);


        }
        {
        System.out.println("Invoking queryReglintstTerms...");
        java.lang.String _queryReglintstTerms__return = port.queryReglintstTerms();
        System.out.println("queryReglintstTerms.result=" + _queryReglintstTerms__return);


        }
        {
        System.out.println("Invoking queryReglintstTotalPersonTime...");
        java.lang.String _queryReglintstTotalPersonTime_jsonParam = "";
        java.lang.String _queryReglintstTotalPersonTime__return = port.queryReglintstTotalPersonTime(_queryReglintstTotalPersonTime_jsonParam);
        System.out.println("queryReglintstTotalPersonTime.result=" + _queryReglintstTotalPersonTime__return);


        }
        {
        System.out.println("Invoking addReglintst...");
        java.lang.String _addReglintst_params = "";
        java.lang.String _addReglintst__return = port.addReglintst(_addReglintst_params);
        System.out.println("addReglintst.result=" + _addReglintst__return);


        }
        {
        System.out.println("Invoking queryInterestList...");
        java.lang.String _queryInterestList_jsonParam = "";
        java.lang.String _queryInterestList__return = port.queryInterestList(_queryInterestList_jsonParam);
        System.out.println("queryInterestList.result=" + _queryInterestList__return);


        }
        {
        System.out.println("Invoking getReglintstJoinRecordList...");
        java.lang.String _getReglintstJoinRecordList_params = "";
        java.lang.String _getReglintstJoinRecordList__return = port.getReglintstJoinRecordList(_getReglintstJoinRecordList_params);
        System.out.println("getReglintstJoinRecordList.result=" + _getReglintstJoinRecordList__return);


        }
        {
        System.out.println("Invoking getMyYypList...");
        java.lang.String _getMyYypList_jsonParam = "";
        java.lang.String _getMyYypList__return = port.getMyYypList(_getMyYypList_jsonParam);
        System.out.println("getMyYypList.result=" + _getMyYypList__return);


        }
        {
        System.out.println("Invoking getYYPGAData...");
        java.lang.String _getYYPGAData_params = "";
        java.lang.String _getYYPGAData__return = port.getYYPGAData(_getYYPGAData_params);
        System.out.println("getYYPGAData.result=" + _getYYPGAData__return);


        }
        {
        System.out.println("Invoking selectReglintstList...");
        java.lang.String _selectReglintstList_params = "";
        java.lang.String _selectReglintstList__return = port.selectReglintstList(_selectReglintstList_params);
        System.out.println("selectReglintstList.result=" + _selectReglintstList__return);


        }
        {
        System.out.println("Invoking queryInterestInfoByJoinId...");
        java.lang.String _queryInterestInfoByJoinId_jsonParam = "";
        java.lang.String _queryInterestInfoByJoinId__return = port.queryInterestInfoByJoinId(_queryInterestInfoByJoinId_jsonParam);
        System.out.println("queryInterestInfoByJoinId.result=" + _queryInterestInfoByJoinId__return);


        }
        {
        System.out.println("Invoking queryJoinInfoByUserIdAndJoinId...");
        java.lang.String _queryJoinInfoByUserIdAndJoinId_jsonParam = "";
        java.lang.String _queryJoinInfoByUserIdAndJoinId__return = port.queryJoinInfoByUserIdAndJoinId(_queryJoinInfoByUserIdAndJoinId_jsonParam);
        System.out.println("queryJoinInfoByUserIdAndJoinId.result=" + _queryJoinInfoByUserIdAndJoinId__return);


        }
        {
        System.out.println("Invoking queryReglIntsById...");
        java.lang.String _queryReglIntsById_jsonParam = "";
        java.lang.String _queryReglIntsById__return = port.queryReglIntsById(_queryReglIntsById_jsonParam);
        System.out.println("queryReglIntsById.result=" + _queryReglIntsById__return);


        }
        {
        System.out.println("Invoking queryReglintstInfoById...");
        java.lang.String _queryReglintstInfoById_jsonParam = "";
        java.lang.String _queryReglintstInfoById__return = port.queryReglintstInfoById(_queryReglintstInfoById_jsonParam);
        System.out.println("queryReglintstInfoById.result=" + _queryReglintstInfoById__return);


        }
        {
        System.out.println("Invoking queryReglintstJoinInfo...");
        java.lang.String _queryReglintstJoinInfo_jsonParam = "";
        java.lang.String _queryReglintstJoinInfo__return = port.queryReglintstJoinInfo(_queryReglintstJoinInfo_jsonParam);
        System.out.println("queryReglintstJoinInfo.result=" + _queryReglintstJoinInfo__return);


        }

        System.exit(0);
    }

}
