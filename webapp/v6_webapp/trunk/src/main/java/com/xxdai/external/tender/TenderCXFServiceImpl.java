
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.xxdai.external.tender;

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
 * 2016-10-08T16:32:46.860+08:00
 * Generated source version: 2.7.18
 * 
 */

@javax.jws.WebService(
                      serviceName = "TenderCXFServiceImplService",
                      portName = "TenderCXFServiceImplPort",
                      targetNamespace = "http://impl.webservice.account.xxdai.com/",
                      wsdlLocation = "http://172.16.14.52:8080/v6_webservice/service/tenderWebService?wsdl",
                      endpointInterface = "com.xxdai.external.tender.TenderCXFService")
                      
public class TenderCXFServiceImpl implements TenderCXFService {

    private static final Logger LOG = Logger.getLogger(TenderCXFServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see com.xxdai.external.tender.TenderCXFService#queryBorrowTenderbyid(java.lang.String  borrowid )*
     */
    public java.lang.String queryBorrowTenderbyid(java.lang.String borrowid) { 
        LOG.info("Executing operation queryBorrowTenderbyid");
        System.out.println(borrowid);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.tender.TenderCXFService#queryTenderDuein(java.lang.String  param )*
     */
    public java.lang.String queryTenderDuein(java.lang.String param) { 
        LOG.info("Executing operation queryTenderDuein");
        System.out.println(param);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.tender.TenderCXFService#saveBorrowAuto(java.lang.String  param )*
     */
    public java.lang.String saveBorrowAuto(java.lang.String param) { 
        LOG.info("Executing operation saveBorrowAuto");
        System.out.println(param);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.tender.TenderCXFService#selectTopBorrowRecord(java.lang.String  param )*
     */
    public java.lang.String selectTopBorrowRecord(java.lang.String param) { 
        LOG.info("Executing operation selectTopBorrowRecord");
        System.out.println(param);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.tender.TenderCXFService#queryTenderQueryNew(java.lang.String  param )*
     */
    public java.lang.String queryTenderQueryNew(java.lang.String param) { 
        LOG.info("Executing operation queryTenderQueryNew");
        System.out.println(param);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.tender.TenderCXFService#queryTenderDueinV2(java.lang.String  param )*
     */
    public java.lang.String queryTenderDueinV2(java.lang.String param) { 
        LOG.info("Executing operation queryTenderDueinV2");
        System.out.println(param);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.tender.TenderCXFService#selecBorrowAutoConf(java.lang.String  userId )*
     */
    public java.lang.String selecBorrowAutoConf(java.lang.String userId) { 
        LOG.info("Executing operation selecBorrowAutoConf");
        System.out.println(userId);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.tender.TenderCXFService#selectCollectInterest(java.lang.String  param )*
     */
    public java.lang.String selectCollectInterest(java.lang.String param) { 
        LOG.info("Executing operation selectCollectInterest");
        System.out.println(param);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.tender.TenderCXFService#queryBorrowAuto(java.lang.String  userId )*
     */
    public java.lang.String queryBorrowAuto(java.lang.String userId) { 
        LOG.info("Executing operation queryBorrowAuto");
        System.out.println(userId);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
