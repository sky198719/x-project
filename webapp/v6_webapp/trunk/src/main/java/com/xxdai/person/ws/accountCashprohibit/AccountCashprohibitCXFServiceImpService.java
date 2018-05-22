package com.xxdai.person.ws.accountCashprohibit;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-09-27T10:17:43.439+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebServiceClient(name = "AccountCashprohibitCXFServiceImpService", 
                  wsdlLocation = "http://test.xxd.com/v6_webservice/service/accountCashprohibitWebService?wsdl",
                  targetNamespace = "http://impl.webService.xxdai.com/") 
public class AccountCashprohibitCXFServiceImpService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.webService.xxdai.com/", "AccountCashprohibitCXFServiceImpService");
    public final static QName AccountCashprohibitCXFServiceImpPort = new QName("http://impl.webService.xxdai.com/", "AccountCashprohibitCXFServiceImpPort");
    static {
        URL url = null;
        try {
            url = new URL("http://test.xxd.com/v6_webservice/service/accountCashprohibitWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(AccountCashprohibitCXFServiceImpService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://test.xxd.com/v6_webservice/service/accountCashprohibitWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public AccountCashprohibitCXFServiceImpService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AccountCashprohibitCXFServiceImpService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AccountCashprohibitCXFServiceImpService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AccountCashprohibitCXFServiceImpService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AccountCashprohibitCXFServiceImpService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AccountCashprohibitCXFServiceImpService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns AccountCashprohibitCXFService
     */
    @WebEndpoint(name = "AccountCashprohibitCXFServiceImpPort")
    public AccountCashprohibitCXFService getAccountCashprohibitCXFServiceImpPort() {
        return super.getPort(AccountCashprohibitCXFServiceImpPort, AccountCashprohibitCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AccountCashprohibitCXFService
     */
    @WebEndpoint(name = "AccountCashprohibitCXFServiceImpPort")
    public AccountCashprohibitCXFService getAccountCashprohibitCXFServiceImpPort(WebServiceFeature... features) {
        return super.getPort(AccountCashprohibitCXFServiceImpPort, AccountCashprohibitCXFService.class, features);
    }

}
