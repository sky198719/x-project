package com.xxdai.system.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.1
 * 2014-11-20T18:49:22.657+08:00
 * Generated source version: 3.0.1
 * 
 */
@WebServiceClient(name = "AreaQueryCXFServiceImplService", 
                  wsdlLocation = "http://127.0.0.1:8080/v6_webservice/service/areaQueryWebService?wsdl",
                  targetNamespace = "http://impl.webservice.system.xxdai.com/") 
public class AreaQueryCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webservice.system.xxdai.com/", "AreaQueryCXFServiceImplService");
    public final static QName AreaQueryCXFServiceImplPort = new QName("http://impl.webservice.system.xxdai.com/", "AreaQueryCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:8080/v6_webservice/service/areaQueryWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(AreaQueryCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://127.0.0.1:8080/v6_webservice/service/areaQueryWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public AreaQueryCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AreaQueryCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AreaQueryCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AreaQueryCXFServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AreaQueryCXFServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AreaQueryCXFServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }    

    /**
     *
     * @return
     *     returns AreaQueryCXFService
     */
    @WebEndpoint(name = "AreaQueryCXFServiceImplPort")
    public AreaQueryCXFService getAreaQueryCXFServiceImplPort() {
        return super.getPort(AreaQueryCXFServiceImplPort, AreaQueryCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AreaQueryCXFService
     */
    @WebEndpoint(name = "AreaQueryCXFServiceImplPort")
    public AreaQueryCXFService getAreaQueryCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(AreaQueryCXFServiceImplPort, AreaQueryCXFService.class, features);
    }

}