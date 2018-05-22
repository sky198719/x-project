
package com.xxdai.external.lottery.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.lottery.ws package. 
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

    private final static QName _GetSumUserTenderByDate_QNAME = new QName("http://webservice.lottery.xxdai.com/", "getSumUserTenderByDate");
    private final static QName _GetSumUserTenderByDateResponse_QNAME = new QName("http://webservice.lottery.xxdai.com/", "getSumUserTenderByDateResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.lottery.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSumUserTenderByDateResponse }
     * 
     */
    public GetSumUserTenderByDateResponse createGetSumUserTenderByDateResponse() {
        return new GetSumUserTenderByDateResponse();
    }

    /**
     * Create an instance of {@link GetSumUserTenderByDate }
     * 
     */
    public GetSumUserTenderByDate createGetSumUserTenderByDate() {
        return new GetSumUserTenderByDate();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSumUserTenderByDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lottery.xxdai.com/", name = "getSumUserTenderByDate")
    public JAXBElement<GetSumUserTenderByDate> createGetSumUserTenderByDate(GetSumUserTenderByDate value) {
        return new JAXBElement<GetSumUserTenderByDate>(_GetSumUserTenderByDate_QNAME, GetSumUserTenderByDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSumUserTenderByDateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lottery.xxdai.com/", name = "getSumUserTenderByDateResponse")
    public JAXBElement<GetSumUserTenderByDateResponse> createGetSumUserTenderByDateResponse(GetSumUserTenderByDateResponse value) {
        return new JAXBElement<GetSumUserTenderByDateResponse>(_GetSumUserTenderByDateResponse_QNAME, GetSumUserTenderByDateResponse.class, null, value);
    }

}
