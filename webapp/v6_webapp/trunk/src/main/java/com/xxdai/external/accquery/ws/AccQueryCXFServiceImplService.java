package com.xxdai.external.accquery.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.1
 * 2015-02-03T16:19:03.407+08:00
 * Generated source version: 3.0.1
 * 
 */
@WebServiceClient(name = "AccQueryCXFServiceImplService", 
                  wsdlLocation = "http://192.168.38.152/v6_webservice/service/accQueryWebService?wsdl",
                  targetNamespace = "http://impl.webservice.account.xxdai.com/") 
public class AccQueryCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webservice.account.xxdai.com/", "AccQueryCXFServiceImplService");
    public final static QName AccQueryCXFServiceImplPort = new QName("http://impl.webservice.account.xxdai.com/", "AccQueryCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.38.152/v6_webservice/service/accQueryWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(AccQueryCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.38.152/v6_webservice/service/accQueryWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public AccQueryCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AccQueryCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AccQueryCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns AccQueryCXFService
     */
    @WebEndpoint(name = "AccQueryCXFServiceImplPort")
    public AccQueryCXFService getAccQueryCXFServiceImplPort() {
        return super.getPort(AccQueryCXFServiceImplPort, AccQueryCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AccQueryCXFService
     */
    @WebEndpoint(name = "AccQueryCXFServiceImplPort")
    public AccQueryCXFService getAccQueryCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(AccQueryCXFServiceImplPort, AccQueryCXFService.class, features);
    }

}