package com.xxdai.system.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2014-10-15T18:24:23.475+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebServiceClient(name = "DicCXFServiceImplService", 
                  wsdlLocation = "http://localhost:8080/v6_webservice/service/dicWebService?wsdl",
                  targetNamespace = "http://webservice.system.xxdai.com/") 
public class DicCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://webservice.system.xxdai.com/", "DicCXFServiceImplService");
    public final static QName DicCXFServiceImplPort = new QName("http://webservice.system.xxdai.com/", "DicCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/v6_webservice/service/dicWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(DicCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/v6_webservice/service/dicWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public DicCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public DicCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DicCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns DicCXFService
     */
    @WebEndpoint(name = "DicCXFServiceImplPort")
    public DicCXFService getDicCXFServiceImplPort() {
        return super.getPort(DicCXFServiceImplPort, DicCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DicCXFService
     */
    @WebEndpoint(name = "DicCXFServiceImplPort")
    public DicCXFService getDicCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(DicCXFServiceImplPort, DicCXFService.class, features);
    }

}