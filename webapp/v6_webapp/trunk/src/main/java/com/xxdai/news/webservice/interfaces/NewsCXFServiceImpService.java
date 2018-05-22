package com.xxdai.news.webservice.interfaces;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-10-17T17:39:04.476+08:00
 * Generated source version: 2.7.18
 * 
 */
@WebServiceClient(name = "NewsCXFServiceImpService", 
                  wsdlLocation = "http://test.xxd.com/yx_service/service/newsWebService?wsdl",
                  targetNamespace = "http://impl.webService.news.xxdai.com/") 
public class NewsCXFServiceImpService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webService.news.xxdai.com/", "NewsCXFServiceImpService");
    public final static QName NewsCXFServiceImpPort = new QName("http://impl.webService.news.xxdai.com/", "NewsCXFServiceImpPort");
    static {
        URL url = null;
        try {
            url = new URL("http://test.xxd.com/yx_service/service/newsWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(NewsCXFServiceImpService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://test.xxd.com/yx_service/service/newsWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public NewsCXFServiceImpService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public NewsCXFServiceImpService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NewsCXFServiceImpService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public NewsCXFServiceImpService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public NewsCXFServiceImpService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public NewsCXFServiceImpService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns NewsCXFService
     */
    @WebEndpoint(name = "NewsCXFServiceImpPort")
    public NewsCXFService getNewsCXFServiceImpPort() {
        return super.getPort(NewsCXFServiceImpPort, NewsCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NewsCXFService
     */
    @WebEndpoint(name = "NewsCXFServiceImpPort")
    public NewsCXFService getNewsCXFServiceImpPort(WebServiceFeature... features) {
        return super.getPort(NewsCXFServiceImpPort, NewsCXFService.class, features);
    }

}
