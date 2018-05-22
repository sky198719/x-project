package com.xxdai.external.redis.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2015-03-16T11:40:47.369+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebServiceClient(name = "RedisMsgCXFServiceImplService", 
                  wsdlLocation = "http://localhost/v6_webservice/service/redisMsgWebService?wsdl",
                  targetNamespace = "http://impl.webservice.redis.xxdai.com/") 
public class RedisMsgCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webservice.redis.xxdai.com/", "RedisMsgCXFServiceImplService");
    public final static QName RedisMsgCXFServiceImplPort = new QName("http://impl.webservice.redis.xxdai.com/", "RedisMsgCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost/v6_webservice/service/redisMsgWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(RedisMsgCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost/v6_webservice/service/redisMsgWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public RedisMsgCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public RedisMsgCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RedisMsgCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public RedisMsgCXFServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public RedisMsgCXFServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public RedisMsgCXFServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns RedisMsgCXFService
     */
    @WebEndpoint(name = "RedisMsgCXFServiceImplPort")
    public RedisMsgCXFService getRedisMsgCXFServiceImplPort() {
        return super.getPort(RedisMsgCXFServiceImplPort, RedisMsgCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RedisMsgCXFService
     */
    @WebEndpoint(name = "RedisMsgCXFServiceImplPort")
    public RedisMsgCXFService getRedisMsgCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(RedisMsgCXFServiceImplPort, RedisMsgCXFService.class, features);
    }

}