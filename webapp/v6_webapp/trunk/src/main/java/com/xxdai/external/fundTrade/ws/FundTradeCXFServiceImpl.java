
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.xxdai.external.fundTrade.ws;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2015-09-05T17:15:00.240+08:00
 * Generated source version: 2.7.12
 * 
 */

@javax.jws.WebService(
                      serviceName = "FundTradeCXFServiceImplService",
                      portName = "FundTradeCXFServiceImplPort",
                      targetNamespace = "http://impl.webservice.fund.xxdai.com/",
                      wsdlLocation = "http://localhost:8080/v6_tradews/service/fundTradeWebService?wsdl",
                      endpointInterface = "com.xxdai.external.fundTrade.ws.FundTradeCXFService")
                      
public class FundTradeCXFServiceImpl implements FundTradeCXFService {

    private static final Logger LOG = Logger.getLogger(FundTradeCXFServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see com.xxdai.external.fundTrade.ws.FundTradeCXFService#calculateEarnings(java.lang.String  params )*
     */
    public java.lang.String calculateEarnings(java.lang.String params) { 
        LOG.info("Executing operation calculateEarnings");
        System.out.println(params);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.xxdai.external.fundTrade.ws.FundTradeCXFService#fundTrade(java.lang.String  param )*
     */
    public java.lang.String fundTrade(java.lang.String param) { 
        LOG.info("Executing operation fundTrade");
        System.out.println(param);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}