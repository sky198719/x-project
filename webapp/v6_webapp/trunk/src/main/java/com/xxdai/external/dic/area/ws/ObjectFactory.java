
package com.xxdai.external.dic.area.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.dic.area.ws package. 
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

    private final static QName _QueryTypeAreaByCode_QNAME = new QName("http://webservice.system.xxdai.com/", "queryTypeAreaByCode");
    private final static QName _QueryAreaByCode_QNAME = new QName("http://webservice.system.xxdai.com/", "queryAreaByCode");
    private final static QName _QueryTypeAreaByCodeResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "queryTypeAreaByCodeResponse");
    private final static QName _QueryAreaByCodeResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "queryAreaByCodeResponse");
    private final static QName _QueryLowerAreaResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "queryLowerAreaResponse");
    private final static QName _QueryTypeAllChildrenAreaByPcodeResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "queryTypeAllChildrenAreaByPcodeResponse");
    private final static QName _QueryLowerArea_QNAME = new QName("http://webservice.system.xxdai.com/", "queryLowerArea");
    private final static QName _QueryTypeAllChildrenAreaByPcode_QNAME = new QName("http://webservice.system.xxdai.com/", "queryTypeAllChildrenAreaByPcode");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.dic.area.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryLowerAreaResponse }
     * 
     */
    public QueryLowerAreaResponse createQueryLowerAreaResponse() {
        return new QueryLowerAreaResponse();
    }

    /**
     * Create an instance of {@link QueryAreaByCodeResponse }
     * 
     */
    public QueryAreaByCodeResponse createQueryAreaByCodeResponse() {
        return new QueryAreaByCodeResponse();
    }

    /**
     * Create an instance of {@link QueryTypeAreaByCodeResponse }
     * 
     */
    public QueryTypeAreaByCodeResponse createQueryTypeAreaByCodeResponse() {
        return new QueryTypeAreaByCodeResponse();
    }

    /**
     * Create an instance of {@link QueryAreaByCode }
     * 
     */
    public QueryAreaByCode createQueryAreaByCode() {
        return new QueryAreaByCode();
    }

    /**
     * Create an instance of {@link QueryTypeAreaByCode }
     * 
     */
    public QueryTypeAreaByCode createQueryTypeAreaByCode() {
        return new QueryTypeAreaByCode();
    }

    /**
     * Create an instance of {@link QueryLowerArea }
     * 
     */
    public QueryLowerArea createQueryLowerArea() {
        return new QueryLowerArea();
    }

    /**
     * Create an instance of {@link QueryTypeAllChildrenAreaByPcode }
     * 
     */
    public QueryTypeAllChildrenAreaByPcode createQueryTypeAllChildrenAreaByPcode() {
        return new QueryTypeAllChildrenAreaByPcode();
    }

    /**
     * Create an instance of {@link QueryTypeAllChildrenAreaByPcodeResponse }
     * 
     */
    public QueryTypeAllChildrenAreaByPcodeResponse createQueryTypeAllChildrenAreaByPcodeResponse() {
        return new QueryTypeAllChildrenAreaByPcodeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTypeAreaByCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryTypeAreaByCode")
    public JAXBElement<QueryTypeAreaByCode> createQueryTypeAreaByCode(QueryTypeAreaByCode value) {
        return new JAXBElement<QueryTypeAreaByCode>(_QueryTypeAreaByCode_QNAME, QueryTypeAreaByCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryAreaByCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryAreaByCode")
    public JAXBElement<QueryAreaByCode> createQueryAreaByCode(QueryAreaByCode value) {
        return new JAXBElement<QueryAreaByCode>(_QueryAreaByCode_QNAME, QueryAreaByCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTypeAreaByCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryTypeAreaByCodeResponse")
    public JAXBElement<QueryTypeAreaByCodeResponse> createQueryTypeAreaByCodeResponse(QueryTypeAreaByCodeResponse value) {
        return new JAXBElement<QueryTypeAreaByCodeResponse>(_QueryTypeAreaByCodeResponse_QNAME, QueryTypeAreaByCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryAreaByCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryAreaByCodeResponse")
    public JAXBElement<QueryAreaByCodeResponse> createQueryAreaByCodeResponse(QueryAreaByCodeResponse value) {
        return new JAXBElement<QueryAreaByCodeResponse>(_QueryAreaByCodeResponse_QNAME, QueryAreaByCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLowerAreaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryLowerAreaResponse")
    public JAXBElement<QueryLowerAreaResponse> createQueryLowerAreaResponse(QueryLowerAreaResponse value) {
        return new JAXBElement<QueryLowerAreaResponse>(_QueryLowerAreaResponse_QNAME, QueryLowerAreaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTypeAllChildrenAreaByPcodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryTypeAllChildrenAreaByPcodeResponse")
    public JAXBElement<QueryTypeAllChildrenAreaByPcodeResponse> createQueryTypeAllChildrenAreaByPcodeResponse(QueryTypeAllChildrenAreaByPcodeResponse value) {
        return new JAXBElement<QueryTypeAllChildrenAreaByPcodeResponse>(_QueryTypeAllChildrenAreaByPcodeResponse_QNAME, QueryTypeAllChildrenAreaByPcodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLowerArea }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryLowerArea")
    public JAXBElement<QueryLowerArea> createQueryLowerArea(QueryLowerArea value) {
        return new JAXBElement<QueryLowerArea>(_QueryLowerArea_QNAME, QueryLowerArea.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTypeAllChildrenAreaByPcode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryTypeAllChildrenAreaByPcode")
    public JAXBElement<QueryTypeAllChildrenAreaByPcode> createQueryTypeAllChildrenAreaByPcode(QueryTypeAllChildrenAreaByPcode value) {
        return new JAXBElement<QueryTypeAllChildrenAreaByPcode>(_QueryTypeAllChildrenAreaByPcode_QNAME, QueryTypeAllChildrenAreaByPcode.class, null, value);
    }

}
