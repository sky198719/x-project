package com.xxdai.seo.ws.category;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2015-04-21T15:55:57.755+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebServiceClient(name = "SeoCategoryCXFServiceImplService", 
                  wsdlLocation = "http://localhost:8080/v6_webservice/service/seoCategoryWebService?wsdl",
                  targetNamespace = "http://impl.webservice.seo.xxdai.com/") 
public class SeoCategoryCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webservice.seo.xxdai.com/", "SeoCategoryCXFServiceImplService");
    public final static QName SeoCategoryCXFServiceImplPort = new QName("http://impl.webservice.seo.xxdai.com/", "SeoCategoryCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/v6_webservice/service/seoCategoryWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SeoCategoryCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/v6_webservice/service/seoCategoryWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SeoCategoryCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SeoCategoryCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SeoCategoryCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns SeoCategoryCXFService
     */
    @WebEndpoint(name = "SeoCategoryCXFServiceImplPort")
    public SeoCategoryCXFService getSeoCategoryCXFServiceImplPort() {
        return super.getPort(SeoCategoryCXFServiceImplPort, SeoCategoryCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SeoCategoryCXFService
     */
    @WebEndpoint(name = "SeoCategoryCXFServiceImplPort")
    public SeoCategoryCXFService getSeoCategoryCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(SeoCategoryCXFServiceImplPort, SeoCategoryCXFService.class, features);
    }

}