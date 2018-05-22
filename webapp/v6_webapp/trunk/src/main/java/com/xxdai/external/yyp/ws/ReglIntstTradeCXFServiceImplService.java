package com.xxdai.external.yyp.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-12-01T15:10:43.438+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebServiceClient(name = "ReglIntstTradeCXFServiceImplService", 
                  wsdlLocation = "http://test.xxd.com/yx_service/service/yypTradeCXFService?wsdl",
                  targetNamespace = "http://impl.webservice.yyp.xxdai.com/") 
public class ReglIntstTradeCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webservice.yyp.xxdai.com/", "ReglIntstTradeCXFServiceImplService");
    public final static QName ReglIntstTradeCXFServiceImplPort = new QName("http://impl.webservice.yyp.xxdai.com/", "ReglIntstTradeCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://test.xxd.com/yx_service/service/yypTradeCXFService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ReglIntstTradeCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://test.xxd.com/yx_service/service/yypTradeCXFService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ReglIntstTradeCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ReglIntstTradeCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ReglIntstTradeCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ReglIntstTradeCXFServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ReglIntstTradeCXFServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ReglIntstTradeCXFServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns ReglIntstTradeCXFService
     */
    @WebEndpoint(name = "ReglIntstTradeCXFServiceImplPort")
    public ReglIntstTradeCXFService getReglIntstTradeCXFServiceImplPort() {
        return super.getPort(ReglIntstTradeCXFServiceImplPort, ReglIntstTradeCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ReglIntstTradeCXFService
     */
    @WebEndpoint(name = "ReglIntstTradeCXFServiceImplPort")
    public ReglIntstTradeCXFService getReglIntstTradeCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(ReglIntstTradeCXFServiceImplPort, ReglIntstTradeCXFService.class, features);
    }

}
