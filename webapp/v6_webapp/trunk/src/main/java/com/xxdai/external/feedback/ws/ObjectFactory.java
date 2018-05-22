
package com.xxdai.external.feedback.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.feedback.ws package. 
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

    private final static QName _AddFeedback_QNAME = new QName("http://webservice.feedback.xxdai.com/", "addFeedback");
    private final static QName _GetFeedbackSearch_QNAME = new QName("http://webservice.feedback.xxdai.com/", "getFeedbackSearch");
    private final static QName _QueryFeedbackValueResponse_QNAME = new QName("http://webservice.feedback.xxdai.com/", "queryFeedbackValueResponse");
    private final static QName _AddFeedbackResponse_QNAME = new QName("http://webservice.feedback.xxdai.com/", "addFeedbackResponse");
    private final static QName _GetFeedbackSearchResponse_QNAME = new QName("http://webservice.feedback.xxdai.com/", "getFeedbackSearchResponse");
    private final static QName _QueryFeedbackValue_QNAME = new QName("http://webservice.feedback.xxdai.com/", "queryFeedbackValue");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.feedback.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetFeedbackSearch }
     * 
     */
    public GetFeedbackSearch createGetFeedbackSearch() {
        return new GetFeedbackSearch();
    }

    /**
     * Create an instance of {@link AddFeedback }
     * 
     */
    public AddFeedback createAddFeedback() {
        return new AddFeedback();
    }

    /**
     * Create an instance of {@link GetFeedbackSearchResponse }
     * 
     */
    public GetFeedbackSearchResponse createGetFeedbackSearchResponse() {
        return new GetFeedbackSearchResponse();
    }

    /**
     * Create an instance of {@link AddFeedbackResponse }
     * 
     */
    public AddFeedbackResponse createAddFeedbackResponse() {
        return new AddFeedbackResponse();
    }

    /**
     * Create an instance of {@link QueryFeedbackValueResponse }
     * 
     */
    public QueryFeedbackValueResponse createQueryFeedbackValueResponse() {
        return new QueryFeedbackValueResponse();
    }

    /**
     * Create an instance of {@link QueryFeedbackValue }
     * 
     */
    public QueryFeedbackValue createQueryFeedbackValue() {
        return new QueryFeedbackValue();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddFeedback }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.feedback.xxdai.com/", name = "addFeedback")
    public JAXBElement<AddFeedback> createAddFeedback(AddFeedback value) {
        return new JAXBElement<AddFeedback>(_AddFeedback_QNAME, AddFeedback.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFeedbackSearch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.feedback.xxdai.com/", name = "getFeedbackSearch")
    public JAXBElement<GetFeedbackSearch> createGetFeedbackSearch(GetFeedbackSearch value) {
        return new JAXBElement<GetFeedbackSearch>(_GetFeedbackSearch_QNAME, GetFeedbackSearch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFeedbackValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.feedback.xxdai.com/", name = "queryFeedbackValueResponse")
    public JAXBElement<QueryFeedbackValueResponse> createQueryFeedbackValueResponse(QueryFeedbackValueResponse value) {
        return new JAXBElement<QueryFeedbackValueResponse>(_QueryFeedbackValueResponse_QNAME, QueryFeedbackValueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddFeedbackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.feedback.xxdai.com/", name = "addFeedbackResponse")
    public JAXBElement<AddFeedbackResponse> createAddFeedbackResponse(AddFeedbackResponse value) {
        return new JAXBElement<AddFeedbackResponse>(_AddFeedbackResponse_QNAME, AddFeedbackResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFeedbackSearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.feedback.xxdai.com/", name = "getFeedbackSearchResponse")
    public JAXBElement<GetFeedbackSearchResponse> createGetFeedbackSearchResponse(GetFeedbackSearchResponse value) {
        return new JAXBElement<GetFeedbackSearchResponse>(_GetFeedbackSearchResponse_QNAME, GetFeedbackSearchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFeedbackValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.feedback.xxdai.com/", name = "queryFeedbackValue")
    public JAXBElement<QueryFeedbackValue> createQueryFeedbackValue(QueryFeedbackValue value) {
        return new JAXBElement<QueryFeedbackValue>(_QueryFeedbackValue_QNAME, QueryFeedbackValue.class, null, value);
    }

}
