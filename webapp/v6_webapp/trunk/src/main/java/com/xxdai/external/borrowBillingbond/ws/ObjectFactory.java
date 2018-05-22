
package com.xxdai.external.borrowBillingbond.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.borrowBillingbond.ws package. 
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

    private final static QName _GetBorrowBillingbondResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "getBorrowBillingbondResponse");
    private final static QName _GetBorrowBillingbond_QNAME = new QName("http://webservice.borrow.xxdai.com/", "getBorrowBillingbond");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.borrowBillingbond.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetBorrowBillingbondResponse }
     * 
     */
    public GetBorrowBillingbondResponse createGetBorrowBillingbondResponse() {
        return new GetBorrowBillingbondResponse();
    }

    /**
     * Create an instance of {@link GetBorrowBillingbond }
     * 
     */
    public GetBorrowBillingbond createGetBorrowBillingbond() {
        return new GetBorrowBillingbond();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBorrowBillingbondResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "getBorrowBillingbondResponse")
    public JAXBElement<GetBorrowBillingbondResponse> createGetBorrowBillingbondResponse(GetBorrowBillingbondResponse value) {
        return new JAXBElement<GetBorrowBillingbondResponse>(_GetBorrowBillingbondResponse_QNAME, GetBorrowBillingbondResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBorrowBillingbond }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "getBorrowBillingbond")
    public JAXBElement<GetBorrowBillingbond> createGetBorrowBillingbond(GetBorrowBillingbond value) {
        return new JAXBElement<GetBorrowBillingbond>(_GetBorrowBillingbond_QNAME, GetBorrowBillingbond.class, null, value);
    }

}
