package com.xxdai.external.employee.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2016-08-02T18:11:57.548+08:00
 * Generated source version: 2.7.12
 * 
 */
@WebServiceClient(name = "EmployeeCXFServiceImplService", 
                  wsdlLocation = "http://192.168.31.34/yx_service/service/employeeWebService?wsdl",
                  targetNamespace = "http://webservice.employee.xxdai.com/") 
public class EmployeeCXFServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://webservice.employee.xxdai.com/", "EmployeeCXFServiceImplService");
    public final static QName EmployeeCXFServiceImplPort = new QName("http://webservice.employee.xxdai.com/", "EmployeeCXFServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.31.34/yx_service/service/employeeWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(EmployeeCXFServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.31.34/yx_service/service/employeeWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public EmployeeCXFServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EmployeeCXFServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EmployeeCXFServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public EmployeeCXFServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public EmployeeCXFServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public EmployeeCXFServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns EmployeeCXFService
     */
    @WebEndpoint(name = "EmployeeCXFServiceImplPort")
    public EmployeeCXFService getEmployeeCXFServiceImplPort() {
        return super.getPort(EmployeeCXFServiceImplPort, EmployeeCXFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EmployeeCXFService
     */
    @WebEndpoint(name = "EmployeeCXFServiceImplPort")
    public EmployeeCXFService getEmployeeCXFServiceImplPort(WebServiceFeature... features) {
        return super.getPort(EmployeeCXFServiceImplPort, EmployeeCXFService.class, features);
    }

}