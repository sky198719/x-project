package com.xxdai.external.fundTrade.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2015-09-05T17:15:00.278+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebServiceClient(name = "FundTradeCXFServiceImplService", 
                  wsdlLocation = "http://localhost:8080/v6_tradews/service/fundTradeWebService?wsdl",
                  targetNamespace = "http://impl.webservice.fund.xxdai.com/") 
public class FundTradeCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webservice.fund.xxdai.com/", "FundTradeCXFServiceImplService");
    public final static QName FundTradeCXFServiceImplPort = new QName("http://impl.webservice.fund.xxdai.com/", "FundTradeCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/v6_tradews/service/fundTradeWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(FundTradeCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/v6_tradews/service/fundTradeWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public FundTradeCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public FundTradeCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FundTradeCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public FundTradeCXFServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public FundTradeCXFServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public FundTradeCXFServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns FundTradeCXFService
     */
    @WebEndpoint(name = "FundTradeCXFServiceImplPort")
    public FundTradeCXFService getFundTradeCXFServiceImplPort() {
        return super.getPort(FundTradeCXFServiceImplPort, FundTradeCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FundTradeCXFService
     */
    @WebEndpoint(name = "FundTradeCXFServiceImplPort")
    public FundTradeCXFService getFundTradeCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(FundTradeCXFServiceImplPort, FundTradeCXFService.class, features);
    }

}
