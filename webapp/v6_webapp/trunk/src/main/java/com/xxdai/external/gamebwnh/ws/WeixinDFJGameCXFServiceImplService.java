package com.xxdai.external.gamebwnh.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-07-29T13:58:21.921+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebServiceClient(name = "WeixinDFJGameCXFServiceImplService", 
                  wsdlLocation = "http://192.168.31.34/yx_service/service/weixinGameWebService?wsdl",
                  targetNamespace = "http://impl.webservice.weixin.xxdai.com/") 
public class WeixinDFJGameCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webservice.weixin.xxdai.com/", "WeixinDFJGameCXFServiceImplService");
    public final static QName WeixinDFJGameCXFServiceImplPort = new QName("http://impl.webservice.weixin.xxdai.com/", "WeixinDFJGameCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.31.34/yx_service/service/weixinGameWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(WeixinDFJGameCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.31.34/yx_service/service/weixinGameWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public WeixinDFJGameCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public WeixinDFJGameCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WeixinDFJGameCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public WeixinDFJGameCXFServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public WeixinDFJGameCXFServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public WeixinDFJGameCXFServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns WeixinDFJGameCXFService
     */
    @WebEndpoint(name = "WeixinDFJGameCXFServiceImplPort")
    public WeixinDFJGameCXFService getWeixinDFJGameCXFServiceImplPort() {
        return super.getPort(WeixinDFJGameCXFServiceImplPort, WeixinDFJGameCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WeixinDFJGameCXFService
     */
    @WebEndpoint(name = "WeixinDFJGameCXFServiceImplPort")
    public WeixinDFJGameCXFService getWeixinDFJGameCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(WeixinDFJGameCXFServiceImplPort, WeixinDFJGameCXFService.class, features);
    }

}
