
package com.xxdai.external.inforDiscloure.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.inforDiscloure.ws package. 
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

    private final static QName _GetInforDiscList_QNAME = new QName("http://webservice.statistics.xxdai.com/", "getInforDiscList");
    private final static QName _QueryInforList_QNAME = new QName("http://webservice.statistics.xxdai.com/", "queryInforList");
    private final static QName _UpdateInforStatus_QNAME = new QName("http://webservice.statistics.xxdai.com/", "updateInforStatus");
    private final static QName _QueryInforListResponse_QNAME = new QName("http://webservice.statistics.xxdai.com/", "queryInforListResponse");
    private final static QName _GetInforDiscloureResponse_QNAME = new QName("http://webservice.statistics.xxdai.com/", "getInforDiscloureResponse");
    private final static QName _GetInforDiscloure_QNAME = new QName("http://webservice.statistics.xxdai.com/", "getInforDiscloure");
    private final static QName _AddInforDiscloure_QNAME = new QName("http://webservice.statistics.xxdai.com/", "addInforDiscloure");
    private final static QName _AddInforDiscloureResponse_QNAME = new QName("http://webservice.statistics.xxdai.com/", "addInforDiscloureResponse");
    private final static QName _UpdateInforStatusResponse_QNAME = new QName("http://webservice.statistics.xxdai.com/", "updateInforStatusResponse");
    private final static QName _UpdateInforDiscloureResponse_QNAME = new QName("http://webservice.statistics.xxdai.com/", "updateInforDiscloureResponse");
    private final static QName _UpdateInforDiscloure_QNAME = new QName("http://webservice.statistics.xxdai.com/", "updateInforDiscloure");
    private final static QName _GetInforDiscListResponse_QNAME = new QName("http://webservice.statistics.xxdai.com/", "getInforDiscListResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.inforDiscloure.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryInforListResponse }
     * 
     */
    public QueryInforListResponse createQueryInforListResponse() {
        return new QueryInforListResponse();
    }

    /**
     * Create an instance of {@link UpdateInforStatus }
     * 
     */
    public UpdateInforStatus createUpdateInforStatus() {
        return new UpdateInforStatus();
    }

    /**
     * Create an instance of {@link QueryInforList }
     * 
     */
    public QueryInforList createQueryInforList() {
        return new QueryInforList();
    }

    /**
     * Create an instance of {@link GetInforDiscList }
     * 
     */
    public GetInforDiscList createGetInforDiscList() {
        return new GetInforDiscList();
    }

    /**
     * Create an instance of {@link GetInforDiscloure }
     * 
     */
    public GetInforDiscloure createGetInforDiscloure() {
        return new GetInforDiscloure();
    }

    /**
     * Create an instance of {@link GetInforDiscloureResponse }
     * 
     */
    public GetInforDiscloureResponse createGetInforDiscloureResponse() {
        return new GetInforDiscloureResponse();
    }

    /**
     * Create an instance of {@link UpdateInforDiscloureResponse }
     * 
     */
    public UpdateInforDiscloureResponse createUpdateInforDiscloureResponse() {
        return new UpdateInforDiscloureResponse();
    }

    /**
     * Create an instance of {@link UpdateInforStatusResponse }
     * 
     */
    public UpdateInforStatusResponse createUpdateInforStatusResponse() {
        return new UpdateInforStatusResponse();
    }

    /**
     * Create an instance of {@link AddInforDiscloureResponse }
     * 
     */
    public AddInforDiscloureResponse createAddInforDiscloureResponse() {
        return new AddInforDiscloureResponse();
    }

    /**
     * Create an instance of {@link AddInforDiscloure }
     * 
     */
    public AddInforDiscloure createAddInforDiscloure() {
        return new AddInforDiscloure();
    }

    /**
     * Create an instance of {@link GetInforDiscListResponse }
     * 
     */
    public GetInforDiscListResponse createGetInforDiscListResponse() {
        return new GetInforDiscListResponse();
    }

    /**
     * Create an instance of {@link UpdateInforDiscloure }
     * 
     */
    public UpdateInforDiscloure createUpdateInforDiscloure() {
        return new UpdateInforDiscloure();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInforDiscList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "getInforDiscList")
    public JAXBElement<GetInforDiscList> createGetInforDiscList(GetInforDiscList value) {
        return new JAXBElement<GetInforDiscList>(_GetInforDiscList_QNAME, GetInforDiscList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryInforList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "queryInforList")
    public JAXBElement<QueryInforList> createQueryInforList(QueryInforList value) {
        return new JAXBElement<QueryInforList>(_QueryInforList_QNAME, QueryInforList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateInforStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "updateInforStatus")
    public JAXBElement<UpdateInforStatus> createUpdateInforStatus(UpdateInforStatus value) {
        return new JAXBElement<UpdateInforStatus>(_UpdateInforStatus_QNAME, UpdateInforStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryInforListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "queryInforListResponse")
    public JAXBElement<QueryInforListResponse> createQueryInforListResponse(QueryInforListResponse value) {
        return new JAXBElement<QueryInforListResponse>(_QueryInforListResponse_QNAME, QueryInforListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInforDiscloureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "getInforDiscloureResponse")
    public JAXBElement<GetInforDiscloureResponse> createGetInforDiscloureResponse(GetInforDiscloureResponse value) {
        return new JAXBElement<GetInforDiscloureResponse>(_GetInforDiscloureResponse_QNAME, GetInforDiscloureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInforDiscloure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "getInforDiscloure")
    public JAXBElement<GetInforDiscloure> createGetInforDiscloure(GetInforDiscloure value) {
        return new JAXBElement<GetInforDiscloure>(_GetInforDiscloure_QNAME, GetInforDiscloure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInforDiscloure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "addInforDiscloure")
    public JAXBElement<AddInforDiscloure> createAddInforDiscloure(AddInforDiscloure value) {
        return new JAXBElement<AddInforDiscloure>(_AddInforDiscloure_QNAME, AddInforDiscloure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInforDiscloureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "addInforDiscloureResponse")
    public JAXBElement<AddInforDiscloureResponse> createAddInforDiscloureResponse(AddInforDiscloureResponse value) {
        return new JAXBElement<AddInforDiscloureResponse>(_AddInforDiscloureResponse_QNAME, AddInforDiscloureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateInforStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "updateInforStatusResponse")
    public JAXBElement<UpdateInforStatusResponse> createUpdateInforStatusResponse(UpdateInforStatusResponse value) {
        return new JAXBElement<UpdateInforStatusResponse>(_UpdateInforStatusResponse_QNAME, UpdateInforStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateInforDiscloureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "updateInforDiscloureResponse")
    public JAXBElement<UpdateInforDiscloureResponse> createUpdateInforDiscloureResponse(UpdateInforDiscloureResponse value) {
        return new JAXBElement<UpdateInforDiscloureResponse>(_UpdateInforDiscloureResponse_QNAME, UpdateInforDiscloureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateInforDiscloure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "updateInforDiscloure")
    public JAXBElement<UpdateInforDiscloure> createUpdateInforDiscloure(UpdateInforDiscloure value) {
        return new JAXBElement<UpdateInforDiscloure>(_UpdateInforDiscloure_QNAME, UpdateInforDiscloure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInforDiscListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.statistics.xxdai.com/", name = "getInforDiscListResponse")
    public JAXBElement<GetInforDiscListResponse> createGetInforDiscListResponse(GetInforDiscListResponse value) {
        return new JAXBElement<GetInforDiscListResponse>(_GetInforDiscListResponse_QNAME, GetInforDiscListResponse.class, null, value);
    }

}
