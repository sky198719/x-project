
package com.xxdai.external.prod.ws.deploy;

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
 * 2015-12-31T15:28:51.949+08:00
 * Generated source version: 2.7.18
 * 
 */
public final class CommProdDeployCXFService_CommProdDeployCXFServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.webservice.prod.xxdai.com/", "CommProdDeployCXFServiceImplService");

    private CommProdDeployCXFService_CommProdDeployCXFServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = CommProdDeployCXFServiceImplService.WSDL_LOCATION;
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
      
        CommProdDeployCXFServiceImplService ss = new CommProdDeployCXFServiceImplService(wsdlURL, SERVICE_NAME);
        CommProdDeployCXFService port = ss.getCommProdDeployCXFServiceImplPort();  
        
        {
        System.out.println("Invoking saveCommProdDeploy...");
        java.lang.String _saveCommProdDeploy_saveParam = "";
        java.lang.String _saveCommProdDeploy__return = port.saveCommProdDeploy(_saveCommProdDeploy_saveParam);
        System.out.println("saveCommProdDeploy.result=" + _saveCommProdDeploy__return);


        }
        {
        System.out.println("Invoking queryCommProdDeployListFront...");
        java.lang.String _queryCommProdDeployListFront_queryParam = "";
        java.lang.String _queryCommProdDeployListFront__return = port.queryCommProdDeployListFront(_queryCommProdDeployListFront_queryParam);
        System.out.println("queryCommProdDeployListFront.result=" + _queryCommProdDeployListFront__return);


        }
        {
        System.out.println("Invoking queryCommProdDeployFrontInfo...");
        java.lang.String _queryCommProdDeployFrontInfo_queryParam = "";
        java.lang.String _queryCommProdDeployFrontInfo__return = port.queryCommProdDeployFrontInfo(_queryCommProdDeployFrontInfo_queryParam);
        System.out.println("queryCommProdDeployFrontInfo.result=" + _queryCommProdDeployFrontInfo__return);


        }
        {
        System.out.println("Invoking releaseCommProdDeploy...");
        java.lang.String _releaseCommProdDeploy_releaseParam = "";
        java.lang.String _releaseCommProdDeploy__return = port.releaseCommProdDeploy(_releaseCommProdDeploy_releaseParam);
        System.out.println("releaseCommProdDeploy.result=" + _releaseCommProdDeploy__return);


        }
        {
        System.out.println("Invoking queryPageList...");
        java.lang.String _queryPageList_queryParam = "";
        java.lang.String _queryPageList__return = port.queryPageList(_queryPageList_queryParam);
        System.out.println("queryPageList.result=" + _queryPageList__return);


        }
        {
        System.out.println("Invoking queryCommProdDeployById...");
        java.lang.String _queryCommProdDeployById_paramId = "";
        java.lang.String _queryCommProdDeployById__return = port.queryCommProdDeployById(_queryCommProdDeployById_paramId);
        System.out.println("queryCommProdDeployById.result=" + _queryCommProdDeployById__return);


        }
        {
        System.out.println("Invoking queryCommProdBorrowTender...");
        java.lang.String _queryCommProdBorrowTender_queryTender = "";
        java.lang.String _queryCommProdBorrowTender__return = port.queryCommProdBorrowTender(_queryCommProdBorrowTender_queryTender);
        System.out.println("queryCommProdBorrowTender.result=" + _queryCommProdBorrowTender__return);


        }
        {
        System.out.println("Invoking queryMyCommProdTender...");
        java.lang.String _queryMyCommProdTender_queryParam = "";
        java.lang.String _queryMyCommProdTender__return = port.queryMyCommProdTender(_queryMyCommProdTender_queryParam);
        System.out.println("queryMyCommProdTender.result=" + _queryMyCommProdTender__return);


        }
        {
        System.out.println("Invoking cancelCommProdDeploy...");
        java.lang.String _cancelCommProdDeploy_cancelParam = "";
        java.lang.String _cancelCommProdDeploy__return = port.cancelCommProdDeploy(_cancelCommProdDeploy_cancelParam);
        System.out.println("cancelCommProdDeploy.result=" + _cancelCommProdDeploy__return);


        }
        {
        System.out.println("Invoking updateCommProdDeploy...");
        java.lang.String _updateCommProdDeploy_updateParam = "";
        java.lang.String _updateCommProdDeploy__return = port.updateCommProdDeploy(_updateCommProdDeploy_updateParam);
        System.out.println("updateCommProdDeploy.result=" + _updateCommProdDeploy__return);


        }
        {
        System.out.println("Invoking queryLastCommProdDeployFrontInfo...");
        java.lang.String _queryLastCommProdDeployFrontInfo_queryParam = "";
        java.lang.String _queryLastCommProdDeployFrontInfo__return = port.queryLastCommProdDeployFrontInfo(_queryLastCommProdDeployFrontInfo_queryParam);
        System.out.println("queryLastCommProdDeployFrontInfo.result=" + _queryLastCommProdDeployFrontInfo__return);


        }

        System.exit(0);
    }

}
