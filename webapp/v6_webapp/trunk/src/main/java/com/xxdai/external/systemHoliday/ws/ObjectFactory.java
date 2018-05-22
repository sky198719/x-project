
package com.xxdai.external.systemHoliday.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.systemHoliday.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetValueDate_QNAME = new QName("http://webservice.fund.xxdai.com/", "getValueDate");
    private final static QName _GetValueDateResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "getValueDateResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.systemHoliday.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetValueDate }
     * 
     */
    public GetValueDate createGetValueDate() {
        return new GetValueDate();
    }

    /**
     * Create an instance of {@link GetValueDateResponse }
     * 
     */
    public GetValueDateResponse createGetValueDateResponse() {
        return new GetValueDateResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetValueDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getValueDate")
    public JAXBElement<GetValueDate> createGetValueDate(GetValueDate value) {
        return new JAXBElement<GetValueDate>(_GetValueDate_QNAME, GetValueDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetValueDateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getValueDateResponse")
    public JAXBElement<GetValueDateResponse> createGetValueDateResponse(GetValueDateResponse value) {
        return new JAXBElement<GetValueDateResponse>(_GetValueDateResponse_QNAME, GetValueDateResponse.class, null, value);
    }

}
