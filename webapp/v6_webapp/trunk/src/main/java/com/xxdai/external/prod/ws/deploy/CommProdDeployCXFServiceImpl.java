
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.xxdai.external.prod.ws.deploy;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2015-12-31T15:28:51.973+08:00
 * Generated source version: 2.7.18
 * 
 */

@javax.jws.WebService(
                      serviceName = "CommProdDeployCXFServiceImplService",
                      portName = "CommProdDeployCXFServiceImplPort",
                      targetNamespace = "http://impl.webservice.prod.xxdai.com/",
                      wsdlLocation = "http://172.16.15.61:8080/v6_webservice/service/commProdDeployWebservice?wsdl",
                      endpointInterface = "com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService")
                      
public class CommProdDeployCXFServiceImpl implements CommProdDeployCXFService {

    private static final Logger LOG = Logger.getLogger(CommProdDeployCXFServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#saveCommProdDeploy(java.lang.String  saveParam )*
     */
    public java.lang.String saveCommProdDeploy(java.lang.String saveParam) { 
        LOG.info("Executing operation saveCommProdDeploy");
        System.out.println(saveParam);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#queryCommProdDeployListFront(java.lang.String  queryParam )*
     */
    public java.lang.String queryCommProdDeployListFront(java.lang.String queryParam) { 
        LOG.info("Executing operation queryCommProdDeployListFront");
        System.out.println(queryParam);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#queryCommProdDeployFrontInfo(java.lang.String  queryParam )*
     */
    public java.lang.String queryCommProdDeployFrontInfo(java.lang.String queryParam) { 
        LOG.info("Executing operation queryCommProdDeployFrontInfo");
        System.out.println(queryParam);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#releaseCommProdDeploy(java.lang.String  releaseParam )*
     */
    public java.lang.String releaseCommProdDeploy(java.lang.String releaseParam) { 
        LOG.info("Executing operation releaseCommProdDeploy");
        System.out.println(releaseParam);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#queryPageList(java.lang.String  queryParam )*
     */
    public java.lang.String queryPageList(java.lang.String queryParam) { 
        LOG.info("Executing operation queryPageList");
        System.out.println(queryParam);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#queryCommProdDeployById(java.lang.String  paramId )*
     */
    public java.lang.String queryCommProdDeployById(java.lang.String paramId) { 
        LOG.info("Executing operation queryCommProdDeployById");
        System.out.println(paramId);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#queryCommProdBorrowTender(java.lang.String  queryTender )*
     */
    public java.lang.String queryCommProdBorrowTender(java.lang.String queryTender) { 
        LOG.info("Executing operation queryCommProdBorrowTender");
        System.out.println(queryTender);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#queryMyCommProdTender(java.lang.String  queryParam )*
     */
    public java.lang.String queryMyCommProdTender(java.lang.String queryParam) { 
        LOG.info("Executing operation queryMyCommProdTender");
        System.out.println(queryParam);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#cancelCommProdDeploy(java.lang.String  cancelParam )*
     */
    public java.lang.String cancelCommProdDeploy(java.lang.String cancelParam) { 
        LOG.info("Executing operation cancelCommProdDeploy");
        System.out.println(cancelParam);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#updateCommProdDeploy(java.lang.String  updateParam )*
     */
    public java.lang.String updateCommProdDeploy(java.lang.String updateParam) { 
        LOG.info("Executing operation updateCommProdDeploy");
        System.out.println(updateParam);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.prod.ws.deploy.CommProdDeployCXFService#queryLastCommProdDeployFrontInfo(java.lang.String  queryParam )*
     */
    public java.lang.String queryLastCommProdDeployFrontInfo(java.lang.String queryParam) { 
        LOG.info("Executing operation queryLastCommProdDeployFrontInfo");
        System.out.println(queryParam);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
