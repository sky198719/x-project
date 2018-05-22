
package com.xxdai.external.accountov.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.accountov.ws package. 
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

    private final static QName _QueryMobileByUserIdResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryMobileByUserIdResponse");
    private final static QName _QueryVIPByUserIdResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryVIPByUserIdResponse");
    private final static QName _QueryVIPByUserId_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryVIPByUserId");
    private final static QName _QueryRealNameByUserIdResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryRealNameByUserIdResponse");
    private final static QName _QueryRealNameByUserId_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryRealNameByUserId");
    private final static QName _QueryMobileByUserId_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryMobileByUserId");
    private final static QName _QueryEmailByUserId_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryEmailByUserId");
    private final static QName _QueryEmailByUserIdResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryEmailByUserIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.accountov.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryEmailByUserIdResponse }
     * 
     */
    public QueryEmailByUserIdResponse createQueryEmailByUserIdResponse() {
        return new QueryEmailByUserIdResponse();
    }

    /**
     * Create an instance of {@link QueryEmailByUserId }
     * 
     */
    public QueryEmailByUserId createQueryEmailByUserId() {
        return new QueryEmailByUserId();
    }

    /**
     * Create an instance of {@link QueryMobileByUserId }
     * 
     */
    public QueryMobileByUserId createQueryMobileByUserId() {
        return new QueryMobileByUserId();
    }

    /**
     * Create an instance of {@link QueryRealNameByUserId }
     * 
     */
    public QueryRealNameByUserId createQueryRealNameByUserId() {
        return new QueryRealNameByUserId();
    }

    /**
     * Create an instance of {@link QueryRealNameByUserIdResponse }
     * 
     */
    public QueryRealNameByUserIdResponse createQueryRealNameByUserIdResponse() {
        return new QueryRealNameByUserIdResponse();
    }

    /**
     * Create an instance of {@link QueryVIPByUserId }
     * 
     */
    public QueryVIPByUserId createQueryVIPByUserId() {
        return new QueryVIPByUserId();
    }

    /**
     * Create an instance of {@link QueryVIPByUserIdResponse }
     * 
     */
    public QueryVIPByUserIdResponse createQueryVIPByUserIdResponse() {
        return new QueryVIPByUserIdResponse();
    }

    /**
     * Create an instance of {@link QueryMobileByUserIdResponse }
     * 
     */
    public QueryMobileByUserIdResponse createQueryMobileByUserIdResponse() {
        return new QueryMobileByUserIdResponse();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link QueryMobileByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryMobileByUserIdResponse")
    public JAXBElement<QueryMobileByUserIdResponse> createQueryMobileByUserIdResponse(QueryMobileByUserIdResponse value) {
        return new JAXBElement<QueryMobileByUserIdResponse>(_QueryMobileByUserIdResponse_QNAME, QueryMobileByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link QueryVIPByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryVIPByUserIdResponse")
    public JAXBElement<QueryVIPByUserIdResponse> createQueryVIPByUserIdResponse(QueryVIPByUserIdResponse value) {
        return new JAXBElement<QueryVIPByUserIdResponse>(_QueryVIPByUserIdResponse_QNAME, QueryVIPByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link QueryVIPByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryVIPByUserId")
    public JAXBElement<QueryVIPByUserId> createQueryVIPByUserId(QueryVIPByUserId value) {
        return new JAXBElement<QueryVIPByUserId>(_QueryVIPByUserId_QNAME, QueryVIPByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link QueryRealNameByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryRealNameByUserIdResponse")
    public JAXBElement<QueryRealNameByUserIdResponse> createQueryRealNameByUserIdResponse(QueryRealNameByUserIdResponse value) {
        return new JAXBElement<QueryRealNameByUserIdResponse>(_QueryRealNameByUserIdResponse_QNAME, QueryRealNameByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link QueryRealNameByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryRealNameByUserId")
    public JAXBElement<QueryRealNameByUserId> createQueryRealNameByUserId(QueryRealNameByUserId value) {
        return new JAXBElement<QueryRealNameByUserId>(_QueryRealNameByUserId_QNAME, QueryRealNameByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link QueryMobileByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryMobileByUserId")
    public JAXBElement<QueryMobileByUserId> createQueryMobileByUserId(QueryMobileByUserId value) {
        return new JAXBElement<QueryMobileByUserId>(_QueryMobileByUserId_QNAME, QueryMobileByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link QueryEmailByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryEmailByUserId")
    public JAXBElement<QueryEmailByUserId> createQueryEmailByUserId(QueryEmailByUserId value) {
        return new JAXBElement<QueryEmailByUserId>(_QueryEmailByUserId_QNAME, QueryEmailByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link QueryEmailByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryEmailByUserIdResponse")
    public JAXBElement<QueryEmailByUserIdResponse> createQueryEmailByUserIdResponse(QueryEmailByUserIdResponse value) {
        return new JAXBElement<QueryEmailByUserIdResponse>(_QueryEmailByUserIdResponse_QNAME, QueryEmailByUserIdResponse.class, null, value);
    }

}
