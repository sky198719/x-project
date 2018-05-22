
package com.xxdai.external.plan.ws;

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
 * 2016-12-01T15:07:54.659+08:00
 * Generated source version: 2.7.12
 * 
 */
public final class PlanCXFService_PlanCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.plan.xxdai.com/", "PlanCXFServiceImplService");

    private PlanCXFService_PlanCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = PlanCXFServiceImplService.WSDL_LOCATION;
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
      
        PlanCXFServiceImplService ss = new PlanCXFServiceImplService(wsdlURL, SERVICE_NAME);
        PlanCXFService port = ss.getPlanCXFServiceImplPort();  
        
        {
        System.out.println("Invoking generateNextPname...");
        java.lang.String _generateNextPname__return = port.generateNextPname();
        System.out.println("generateNextPname.result=" + _generateNextPname__return);


        }
        {
        System.out.println("Invoking repealScheme...");
        java.lang.String _repealScheme_params = "";
        java.lang.String _repealScheme__return = port.repealScheme(_repealScheme_params);
        System.out.println("repealScheme.result=" + _repealScheme__return);


        }
        {
        System.out.println("Invoking getAccounLogsOfMyScheme...");
        java.lang.String _getAccounLogsOfMyScheme_params = "";
        java.lang.String _getAccounLogsOfMyScheme__return = port.getAccounLogsOfMyScheme(_getAccounLogsOfMyScheme_params);
        System.out.println("getAccounLogsOfMyScheme.result=" + _getAccounLogsOfMyScheme__return);


        }
        {
        System.out.println("Invoking getPlanGAData...");
        java.lang.String _getPlanGAData_params = "";
        java.lang.String _getPlanGAData__return = port.getPlanGAData(_getPlanGAData_params);
        System.out.println("getPlanGAData.result=" + _getPlanGAData__return);


        }
        {
        System.out.println("Invoking getUserMoney...");
        java.lang.String _getUserMoney_params = "";
        java.lang.String _getUserMoney__return = port.getUserMoney(_getUserMoney_params);
        System.out.println("getUserMoney.result=" + _getUserMoney__return);


        }
        {
        System.out.println("Invoking quitScheme...");
        java.lang.String _quitScheme_params = "";
        java.lang.String _quitScheme__return = port.quitScheme(_quitScheme_params);
        System.out.println("quitScheme.result=" + _quitScheme__return);


        }
        {
        System.out.println("Invoking getSumUserTenderByDate...");
        java.lang.String _getSumUserTenderByDate_params = "";
        java.lang.String _getSumUserTenderByDate__return = port.getSumUserTenderByDate(_getSumUserTenderByDate_params);
        System.out.println("getSumUserTenderByDate.result=" + _getSumUserTenderByDate__return);


        }
        {
        System.out.println("Invoking getMySchemeList...");
        java.lang.String _getMySchemeList_params = "";
        java.lang.String _getMySchemeList__return = port.getMySchemeList(_getMySchemeList_params);
        System.out.println("getMySchemeList.result=" + _getMySchemeList__return);


        }
        {
        System.out.println("Invoking addScheme...");
        java.lang.String _addScheme_params = "";
        java.lang.String _addScheme__return = port.addScheme(_addScheme_params);
        System.out.println("addScheme.result=" + _addScheme__return);


        }
        {
        System.out.println("Invoking expireQuitScheme...");
        java.lang.String _expireQuitScheme_params = "";
        java.lang.String _expireQuitScheme__return = port.expireQuitScheme(_expireQuitScheme_params);
        System.out.println("expireQuitScheme.result=" + _expireQuitScheme__return);


        }
        {
        System.out.println("Invoking getTransferListOfMyScheme...");
        java.lang.String _getTransferListOfMyScheme_params = "";
        java.lang.String _getTransferListOfMyScheme__return = port.getTransferListOfMyScheme(_getTransferListOfMyScheme_params);
        System.out.println("getTransferListOfMyScheme.result=" + _getTransferListOfMyScheme__return);


        }
        {
        System.out.println("Invoking queryUserDetailPage...");
        java.lang.String _queryUserDetailPage_params = "";
        java.lang.String _queryUserDetailPage__return = port.queryUserDetailPage(_queryUserDetailPage_params);
        System.out.println("queryUserDetailPage.result=" + _queryUserDetailPage__return);


        }
        {
        System.out.println("Invoking buyScheme...");
        java.lang.String _buyScheme_params = "";
        java.lang.String _buyScheme__return = port.buyScheme(_buyScheme_params);
        System.out.println("buyScheme.result=" + _buyScheme__return);


        }
        {
        System.out.println("Invoking planExpireQuitProfitNotEnough...");
        java.lang.String _planExpireQuitProfitNotEnough_params = "";
        java.lang.String _planExpireQuitProfitNotEnough__return = port.planExpireQuitProfitNotEnough(_planExpireQuitProfitNotEnough_params);
        System.out.println("planExpireQuitProfitNotEnough.result=" + _planExpireQuitProfitNotEnough__return);


        }
        {
        System.out.println("Invoking getUserSchemeList...");
        java.lang.String _getUserSchemeList_params = "";
        java.lang.String _getUserSchemeList__return = port.getUserSchemeList(_getUserSchemeList_params);
        System.out.println("getUserSchemeList.result=" + _getUserSchemeList__return);


        }
        {
        System.out.println("Invoking selectUserSchemeByUserIdSchemeId...");
        java.lang.String _selectUserSchemeByUserIdSchemeId_params = "";
        java.lang.String _selectUserSchemeByUserIdSchemeId__return = port.selectUserSchemeByUserIdSchemeId(_selectUserSchemeByUserIdSchemeId_params);
        System.out.println("selectUserSchemeByUserIdSchemeId.result=" + _selectUserSchemeByUserIdSchemeId__return);


        }
        {
        System.out.println("Invoking getAccounLogsOfYyp...");
        java.lang.String _getAccounLogsOfYyp_params = "";
        java.lang.String _getAccounLogsOfYyp__return = port.getAccounLogsOfYyp(_getAccounLogsOfYyp_params);
        System.out.println("getAccounLogsOfYyp.result=" + _getAccounLogsOfYyp__return);


        }
        {
        System.out.println("Invoking getYypBorrowList...");
        java.lang.String _getYypBorrowList_params = "";
        java.lang.String _getYypBorrowList__return = port.getYypBorrowList(_getYypBorrowList_params);
        System.out.println("getYypBorrowList.result=" + _getYypBorrowList__return);


        }
        {
        System.out.println("Invoking queryUserDetailList...");
        java.lang.String _queryUserDetailList_params = "";
        java.lang.String _queryUserDetailList__return = port.queryUserDetailList(_queryUserDetailList_params);
        System.out.println("queryUserDetailList.result=" + _queryUserDetailList__return);


        }
        {
        System.out.println("Invoking updateScheme...");
        java.lang.String _updateScheme_params = "";
        java.lang.String _updateScheme__return = port.updateScheme(_updateScheme_params);
        System.out.println("updateScheme.result=" + _updateScheme__return);


        }
        {
        System.out.println("Invoking getUserSchemeInfo...");
        java.lang.String _getUserSchemeInfo_params = "";
        java.lang.String _getUserSchemeInfo__return = port.getUserSchemeInfo(_getUserSchemeInfo_params);
        System.out.println("getUserSchemeInfo.result=" + _getUserSchemeInfo__return);


        }
        {
        System.out.println("Invoking getTotalScheme...");
        java.lang.String _getTotalScheme_params = "";
        java.lang.String _getTotalScheme__return = port.getTotalScheme(_getTotalScheme_params);
        System.out.println("getTotalScheme.result=" + _getTotalScheme__return);


        }
        {
        System.out.println("Invoking queryAgreementVO...");
        java.lang.String _queryAgreementVO_params = "";
        java.lang.String _queryAgreementVO__return = port.queryAgreementVO(_queryAgreementVO_params);
        System.out.println("queryAgreementVO.result=" + _queryAgreementVO__return);


        }
        {
        System.out.println("Invoking getSchemeInfo...");
        java.lang.String _getSchemeInfo_params = "";
        java.lang.String _getSchemeInfo__return = port.getSchemeInfo(_getSchemeInfo_params);
        System.out.println("getSchemeInfo.result=" + _getSchemeInfo__return);


        }
        {
        System.out.println("Invoking getSchemeList...");
        java.lang.String _getSchemeList_params = "";
        java.lang.String _getSchemeList__return = port.getSchemeList(_getSchemeList_params);
        System.out.println("getSchemeList.result=" + _getSchemeList__return);


        }
        {
        System.out.println("Invoking getBorrowListOfMyScheme...");
        java.lang.String _getBorrowListOfMyScheme_params = "";
        java.lang.String _getBorrowListOfMyScheme__return = port.getBorrowListOfMyScheme(_getBorrowListOfMyScheme_params);
        System.out.println("getBorrowListOfMyScheme.result=" + _getBorrowListOfMyScheme__return);


        }
        {
        System.out.println("Invoking planJoinNums...");
        java.lang.String _planJoinNums__return = port.planJoinNums();
        System.out.println("planJoinNums.result=" + _planJoinNums__return);


        }

        System.exit(0);
    }

}