package com.xxdai.external.market.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-07-22T16:23:08.734+08:00
 * Generated source version: 2.7.18
 * 
 */
@WebServiceClient(name = "MarketCXFServiceImplService", 
                  wsdlLocation = "http://172.16.14.45:8080/v6_webservice/service/marketWebService?wsdl",
                  targetNamespace = "http://impl.webservice.market.xxdai.com/") 
public class MarketCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webservice.market.xxdai.com/", "MarketCXFServiceImplService");
    public final static QName MarketCXFServiceImplPort = new QName("http://impl.webservice.market.xxdai.com/", "MarketCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://172.16.14.45:8080/v6_webservice/service/marketWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(MarketCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://172.16.14.45:8080/v6_webservice/service/marketWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public MarketCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MarketCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MarketCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public MarketCXFServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public MarketCXFServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public MarketCXFServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns MarketCXFService
     */
    @WebEndpoint(name = "MarketCXFServiceImplPort")
    public MarketCXFService getMarketCXFServiceImplPort() {
        return super.getPort(MarketCXFServiceImplPort, MarketCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MarketCXFService
     */
    @WebEndpoint(name = "MarketCXFServiceImplPort")
    public MarketCXFService getMarketCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(MarketCXFServiceImplPort, MarketCXFService.class, features);
    }

}