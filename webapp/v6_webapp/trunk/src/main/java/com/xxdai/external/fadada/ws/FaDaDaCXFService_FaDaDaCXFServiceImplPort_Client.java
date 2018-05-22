
package com.xxdai.external.fadada.ws;

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
 * 2017-10-23T16:04:33.613+08:00
 * Generated source version: 2.7.12
 * 
 */
public final class FaDaDaCXFService_FaDaDaCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.fadada.xxdai.com/", "FaDaDaCXFServiceImplService");

    private FaDaDaCXFService_FaDaDaCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = FaDaDaCXFServiceImplService.WSDL_LOCATION;
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
      
        FaDaDaCXFServiceImplService ss = new FaDaDaCXFServiceImplService(wsdlURL, SERVICE_NAME);
        FaDaDaCXFService port = ss.getFaDaDaCXFServiceImplPort();  
        
        {
        System.out.println("Invoking doDownloadContract...");
        java.lang.String _doDownloadContract_params = "";
        java.lang.String _doDownloadContract__return = port.doDownloadContract(_doDownloadContract_params);
        System.out.println("doDownloadContract.result=" + _doDownloadContract__return);


        }
        {
        System.out.println("Invoking doAutoSigningContract...");
        java.lang.String _doAutoSigningContract_params = "";
        java.lang.String _doAutoSigningContract__return = port.doAutoSigningContract(_doAutoSigningContract_params);
        System.out.println("doAutoSigningContract.result=" + _doAutoSigningContract__return);


        }
        {
        System.out.println("Invoking getBorrowCreditInfoByBorrowId...");
        java.lang.String _getBorrowCreditInfoByBorrowId_params = "";
        java.lang.String _getBorrowCreditInfoByBorrowId__return = port.getBorrowCreditInfoByBorrowId(_getBorrowCreditInfoByBorrowId_params);
        System.out.println("getBorrowCreditInfoByBorrowId.result=" + _getBorrowCreditInfoByBorrowId__return);


        }
        {
        System.out.println("Invoking getBorrowGuarantorByBorrowId...");
        java.lang.String _getBorrowGuarantorByBorrowId_params = "";
        java.lang.String _getBorrowGuarantorByBorrowId__return = port.getBorrowGuarantorByBorrowId(_getBorrowGuarantorByBorrowId_params);
        System.out.println("getBorrowGuarantorByBorrowId.result=" + _getBorrowGuarantorByBorrowId__return);


        }
        {
        System.out.println("Invoking getFadadaSignStatusByborrowId...");
        java.lang.String _getFadadaSignStatusByborrowId_params = "";
        java.lang.String _getFadadaSignStatusByborrowId__return = port.getFadadaSignStatusByborrowId(_getFadadaSignStatusByborrowId_params);
        System.out.println("getFadadaSignStatusByborrowId.result=" + _getFadadaSignStatusByborrowId__return);


        }
        {
        System.out.println("Invoking getOriginDocContractPath...");
        java.lang.String _getOriginDocContractPath_params = "";
        java.lang.String _getOriginDocContractPath__return = port.getOriginDocContractPath(_getOriginDocContractPath_params);
        System.out.println("getOriginDocContractPath.result=" + _getOriginDocContractPath__return);


        }
        {
        System.out.println("Invoking getContractCodeByborrowId...");
        java.lang.String _getContractCodeByborrowId_params = "";
        java.lang.String _getContractCodeByborrowId__return = port.getContractCodeByborrowId(_getContractCodeByborrowId_params);
        System.out.println("getContractCodeByborrowId.result=" + _getContractCodeByborrowId__return);


        }
        {
        System.out.println("Invoking doSendSmgToGuarantor...");
        java.lang.String _doSendSmgToGuarantor_params = "";
        java.lang.String _doSendSmgToGuarantor__return = port.doSendSmgToGuarantor(_doSendSmgToGuarantor_params);
        System.out.println("doSendSmgToGuarantor.result=" + _doSendSmgToGuarantor__return);


        }
        {
        System.out.println("Invoking doExtGuarantorCA...");
        java.lang.String _doExtGuarantorCA_params = "";
        java.lang.String _doExtGuarantorCA__return = port.doExtGuarantorCA(_doExtGuarantorCA_params);
        System.out.println("doExtGuarantorCA.result=" + _doExtGuarantorCA__return);


        }
        {
        System.out.println("Invoking getContractSavePath...");
        java.lang.String _getContractSavePath__return = port.getContractSavePath();
        System.out.println("getContractSavePath.result=" + _getContractSavePath__return);


        }
        {
        System.out.println("Invoking getGuarantorSignStatusByborrowId...");
        java.lang.String _getGuarantorSignStatusByborrowId_params = "";
        java.lang.String _getGuarantorSignStatusByborrowId__return = port.getGuarantorSignStatusByborrowId(_getGuarantorSignStatusByborrowId_params);
        System.out.println("getGuarantorSignStatusByborrowId.result=" + _getGuarantorSignStatusByborrowId__return);


        }
        {
        System.out.println("Invoking doViewContract...");
        java.lang.String _doViewContract_params = "";
        java.lang.String _doViewContract__return = port.doViewContract(_doViewContract_params);
        System.out.println("doViewContract.result=" + _doViewContract__return);


        }
        {
        System.out.println("Invoking getContractArchiveAndDownloadStatus...");
        java.lang.String _getContractArchiveAndDownloadStatus_params = "";
        java.lang.String _getContractArchiveAndDownloadStatus__return = port.getContractArchiveAndDownloadStatus(_getContractArchiveAndDownloadStatus_params);
        System.out.println("getContractArchiveAndDownloadStatus.result=" + _getContractArchiveAndDownloadStatus__return);


        }
        {
        System.out.println("Invoking doApCA...");
        java.lang.String _doApCA_params = "";
        java.lang.String _doApCA__return = port.doApCA(_doApCA_params);
        System.out.println("doApCA.result=" + _doApCA__return);


        }
        {
        System.out.println("Invoking getExtBorrowGuarantorByBorrowId...");
        java.lang.String _getExtBorrowGuarantorByBorrowId_params = "";
        java.lang.String _getExtBorrowGuarantorByBorrowId__return = port.getExtBorrowGuarantorByBorrowId(_getExtBorrowGuarantorByBorrowId_params);
        System.out.println("getExtBorrowGuarantorByBorrowId.result=" + _getExtBorrowGuarantorByBorrowId__return);


        }
        {
        System.out.println("Invoking checkSZRongContractZipIsExist...");
        java.lang.String _checkSZRongContractZipIsExist_params = "";
        java.lang.String _checkSZRongContractZipIsExist__return = port.checkSZRongContractZipIsExist(_checkSZRongContractZipIsExist_params);
        System.out.println("checkSZRongContractZipIsExist.result=" + _checkSZRongContractZipIsExist__return);


        }
        {
        System.out.println("Invoking getBorrowerZipPath...");
        java.lang.String _getBorrowerZipPath_params = "";
        java.lang.String _getBorrowerZipPath__return = port.getBorrowerZipPath(_getBorrowerZipPath_params);
        System.out.println("getBorrowerZipPath.result=" + _getBorrowerZipPath__return);


        }
        {
        System.out.println("Invoking getCompanyInfoByBorrowId...");
        java.lang.String _getCompanyInfoByBorrowId_params = "";
        java.lang.String _getCompanyInfoByBorrowId__return = port.getCompanyInfoByBorrowId(_getCompanyInfoByBorrowId_params);
        System.out.println("getCompanyInfoByBorrowId.result=" + _getCompanyInfoByBorrowId__return);


        }
        {
        System.out.println("Invoking doUserCA...");
        java.lang.String _doUserCA_params = "";
        java.lang.String _doUserCA__return = port.doUserCA(_doUserCA_params);
        System.out.println("doUserCA.result=" + _doUserCA__return);


        }
        {
        System.out.println("Invoking doUploadContractAndSaveInfo...");
        java.lang.String _doUploadContractAndSaveInfo_params = "";
        java.lang.String _doUploadContractAndSaveInfo__return = port.doUploadContractAndSaveInfo(_doUploadContractAndSaveInfo_params);
        System.out.println("doUploadContractAndSaveInfo.result=" + _doUploadContractAndSaveInfo__return);


        }
        {
        System.out.println("Invoking checkBorrowContractZipIsExist...");
        java.lang.String _checkBorrowContractZipIsExist_params = "";
        java.lang.String _checkBorrowContractZipIsExist__return = port.checkBorrowContractZipIsExist(_checkBorrowContractZipIsExist_params);
        System.out.println("checkBorrowContractZipIsExist.result=" + _checkBorrowContractZipIsExist__return);


        }
        {
        System.out.println("Invoking doArchiveContract...");
        java.lang.String _doArchiveContract_params = "";
        java.lang.String _doArchiveContract__return = port.doArchiveContract(_doArchiveContract_params);
        System.out.println("doArchiveContract.result=" + _doArchiveContract__return);


        }
        {
        System.out.println("Invoking doSigningContract...");
        java.lang.String _doSigningContract_params = "";
        java.lang.String _doSigningContract__return = port.doSigningContract(_doSigningContract_params);
        System.out.println("doSigningContract.result=" + _doSigningContract__return);


        }
        {
        System.out.println("Invoking getInvestorZipPath...");
        java.lang.String _getInvestorZipPath_params = "";
        java.lang.String _getInvestorZipPath__return = port.getInvestorZipPath(_getInvestorZipPath_params);
        System.out.println("getInvestorZipPath.result=" + _getInvestorZipPath__return);


        }
        {
        System.out.println("Invoking getCurBorrowPath...");
        java.lang.String _getCurBorrowPath_params = "";
        java.lang.String _getCurBorrowPath__return = port.getCurBorrowPath(_getCurBorrowPath_params);
        System.out.println("getCurBorrowPath.result=" + _getCurBorrowPath__return);


        }
        {
        System.out.println("Invoking doGuarantorCA...");
        java.lang.String _doGuarantorCA_params = "";
        java.lang.String _doGuarantorCA__return = port.doGuarantorCA(_doGuarantorCA_params);
        System.out.println("doGuarantorCA.result=" + _doGuarantorCA__return);


        }
        {
        System.out.println("Invoking doSigningContractReturn...");
        java.lang.String _doSigningContractReturn_params = "";
        java.lang.String _doSigningContractReturn__return = port.doSigningContractReturn(_doSigningContractReturn_params);
        System.out.println("doSigningContractReturn.result=" + _doSigningContractReturn__return);


        }

        System.exit(0);
    }

}
