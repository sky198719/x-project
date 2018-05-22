
package com.xxdai.external.prod.ws.notice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.prod.ws.notice package. 
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

    private final static QName _QueryPageList_QNAME = new QName("http://webservice.prod.xxdai.com/", "queryPageList");
    private final static QName _SaveCommProdNoticeResponse_QNAME = new QName("http://webservice.prod.xxdai.com/", "saveCommProdNoticeResponse");
    private final static QName _ReleaseCommProdNotice_QNAME = new QName("http://webservice.prod.xxdai.com/", "releaseCommProdNotice");
    private final static QName _QueryCommProdNoticeById_QNAME = new QName("http://webservice.prod.xxdai.com/", "queryCommProdNoticeById");
    private final static QName _QueryCommProdNoticeByIdResponse_QNAME = new QName("http://webservice.prod.xxdai.com/", "queryCommProdNoticeByIdResponse");
    private final static QName _UpdateCommProdNotice_QNAME = new QName("http://webservice.prod.xxdai.com/", "updateCommProdNotice");
    private final static QName _SaveCommProdNotice_QNAME = new QName("http://webservice.prod.xxdai.com/", "saveCommProdNotice");
    private final static QName _UpdateCommProdNoticeResponse_QNAME = new QName("http://webservice.prod.xxdai.com/", "updateCommProdNoticeResponse");
    private final static QName _CancelCommProdNotice_QNAME = new QName("http://webservice.prod.xxdai.com/", "cancelCommProdNotice");
    private final static QName _CancelCommProdNoticeResponse_QNAME = new QName("http://webservice.prod.xxdai.com/", "cancelCommProdNoticeResponse");
    private final static QName _QueryCommProdNoticeByCommProdIdResponse_QNAME = new QName("http://webservice.prod.xxdai.com/", "queryCommProdNoticeByCommProdIdResponse");
    private final static QName _QueryPageListResponse_QNAME = new QName("http://webservice.prod.xxdai.com/", "queryPageListResponse");
    private final static QName _ReleaseCommProdNoticeResponse_QNAME = new QName("http://webservice.prod.xxdai.com/", "releaseCommProdNoticeResponse");
    private final static QName _QueryCommProdNoticeByCommProdId_QNAME = new QName("http://webservice.prod.xxdai.com/", "queryCommProdNoticeByCommProdId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.prod.ws.notice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateCommProdNoticeResponse }
     * 
     */
    public UpdateCommProdNoticeResponse createUpdateCommProdNoticeResponse() {
        return new UpdateCommProdNoticeResponse();
    }

    /**
     * Create an instance of {@link CancelCommProdNotice }
     * 
     */
    public CancelCommProdNotice createCancelCommProdNotice() {
        return new CancelCommProdNotice();
    }

    /**
     * Create an instance of {@link QueryCommProdNoticeByCommProdId }
     * 
     */
    public QueryCommProdNoticeByCommProdId createQueryCommProdNoticeByCommProdId() {
        return new QueryCommProdNoticeByCommProdId();
    }

    /**
     * Create an instance of {@link QueryCommProdNoticeByCommProdIdResponse }
     * 
     */
    public QueryCommProdNoticeByCommProdIdResponse createQueryCommProdNoticeByCommProdIdResponse() {
        return new QueryCommProdNoticeByCommProdIdResponse();
    }

    /**
     * Create an instance of {@link QueryPageListResponse }
     * 
     */
    public QueryPageListResponse createQueryPageListResponse() {
        return new QueryPageListResponse();
    }

    /**
     * Create an instance of {@link ReleaseCommProdNoticeResponse }
     * 
     */
    public ReleaseCommProdNoticeResponse createReleaseCommProdNoticeResponse() {
        return new ReleaseCommProdNoticeResponse();
    }

    /**
     * Create an instance of {@link CancelCommProdNoticeResponse }
     * 
     */
    public CancelCommProdNoticeResponse createCancelCommProdNoticeResponse() {
        return new CancelCommProdNoticeResponse();
    }

    /**
     * Create an instance of {@link QueryCommProdNoticeByIdResponse }
     * 
     */
    public QueryCommProdNoticeByIdResponse createQueryCommProdNoticeByIdResponse() {
        return new QueryCommProdNoticeByIdResponse();
    }

    /**
     * Create an instance of {@link UpdateCommProdNotice }
     * 
     */
    public UpdateCommProdNotice createUpdateCommProdNotice() {
        return new UpdateCommProdNotice();
    }

    /**
     * Create an instance of {@link QueryCommProdNoticeById }
     * 
     */
    public QueryCommProdNoticeById createQueryCommProdNoticeById() {
        return new QueryCommProdNoticeById();
    }

    /**
     * Create an instance of {@link SaveCommProdNoticeResponse }
     * 
     */
    public SaveCommProdNoticeResponse createSaveCommProdNoticeResponse() {
        return new SaveCommProdNoticeResponse();
    }

    /**
     * Create an instance of {@link ReleaseCommProdNotice }
     * 
     */
    public ReleaseCommProdNotice createReleaseCommProdNotice() {
        return new ReleaseCommProdNotice();
    }

    /**
     * Create an instance of {@link QueryPageList }
     * 
     */
    public QueryPageList createQueryPageList() {
        return new QueryPageList();
    }

    /**
     * Create an instance of {@link SaveCommProdNotice }
     * 
     */
    public SaveCommProdNotice createSaveCommProdNotice() {
        return new SaveCommProdNotice();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPageList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "queryPageList")
    public JAXBElement<QueryPageList> createQueryPageList(QueryPageList value) {
        return new JAXBElement<QueryPageList>(_QueryPageList_QNAME, QueryPageList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveCommProdNoticeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "saveCommProdNoticeResponse")
    public JAXBElement<SaveCommProdNoticeResponse> createSaveCommProdNoticeResponse(SaveCommProdNoticeResponse value) {
        return new JAXBElement<SaveCommProdNoticeResponse>(_SaveCommProdNoticeResponse_QNAME, SaveCommProdNoticeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseCommProdNotice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "releaseCommProdNotice")
    public JAXBElement<ReleaseCommProdNotice> createReleaseCommProdNotice(ReleaseCommProdNotice value) {
        return new JAXBElement<ReleaseCommProdNotice>(_ReleaseCommProdNotice_QNAME, ReleaseCommProdNotice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCommProdNoticeById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "queryCommProdNoticeById")
    public JAXBElement<QueryCommProdNoticeById> createQueryCommProdNoticeById(QueryCommProdNoticeById value) {
        return new JAXBElement<QueryCommProdNoticeById>(_QueryCommProdNoticeById_QNAME, QueryCommProdNoticeById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCommProdNoticeByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "queryCommProdNoticeByIdResponse")
    public JAXBElement<QueryCommProdNoticeByIdResponse> createQueryCommProdNoticeByIdResponse(QueryCommProdNoticeByIdResponse value) {
        return new JAXBElement<QueryCommProdNoticeByIdResponse>(_QueryCommProdNoticeByIdResponse_QNAME, QueryCommProdNoticeByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCommProdNotice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "updateCommProdNotice")
    public JAXBElement<UpdateCommProdNotice> createUpdateCommProdNotice(UpdateCommProdNotice value) {
        return new JAXBElement<UpdateCommProdNotice>(_UpdateCommProdNotice_QNAME, UpdateCommProdNotice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveCommProdNotice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "saveCommProdNotice")
    public JAXBElement<SaveCommProdNotice> createSaveCommProdNotice(SaveCommProdNotice value) {
        return new JAXBElement<SaveCommProdNotice>(_SaveCommProdNotice_QNAME, SaveCommProdNotice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCommProdNoticeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "updateCommProdNoticeResponse")
    public JAXBElement<UpdateCommProdNoticeResponse> createUpdateCommProdNoticeResponse(UpdateCommProdNoticeResponse value) {
        return new JAXBElement<UpdateCommProdNoticeResponse>(_UpdateCommProdNoticeResponse_QNAME, UpdateCommProdNoticeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelCommProdNotice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "cancelCommProdNotice")
    public JAXBElement<CancelCommProdNotice> createCancelCommProdNotice(CancelCommProdNotice value) {
        return new JAXBElement<CancelCommProdNotice>(_CancelCommProdNotice_QNAME, CancelCommProdNotice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelCommProdNoticeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "cancelCommProdNoticeResponse")
    public JAXBElement<CancelCommProdNoticeResponse> createCancelCommProdNoticeResponse(CancelCommProdNoticeResponse value) {
        return new JAXBElement<CancelCommProdNoticeResponse>(_CancelCommProdNoticeResponse_QNAME, CancelCommProdNoticeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCommProdNoticeByCommProdIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "queryCommProdNoticeByCommProdIdResponse")
    public JAXBElement<QueryCommProdNoticeByCommProdIdResponse> createQueryCommProdNoticeByCommProdIdResponse(QueryCommProdNoticeByCommProdIdResponse value) {
        return new JAXBElement<QueryCommProdNoticeByCommProdIdResponse>(_QueryCommProdNoticeByCommProdIdResponse_QNAME, QueryCommProdNoticeByCommProdIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPageListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "queryPageListResponse")
    public JAXBElement<QueryPageListResponse> createQueryPageListResponse(QueryPageListResponse value) {
        return new JAXBElement<QueryPageListResponse>(_QueryPageListResponse_QNAME, QueryPageListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseCommProdNoticeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "releaseCommProdNoticeResponse")
    public JAXBElement<ReleaseCommProdNoticeResponse> createReleaseCommProdNoticeResponse(ReleaseCommProdNoticeResponse value) {
        return new JAXBElement<ReleaseCommProdNoticeResponse>(_ReleaseCommProdNoticeResponse_QNAME, ReleaseCommProdNoticeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCommProdNoticeByCommProdId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.prod.xxdai.com/", name = "queryCommProdNoticeByCommProdId")
    public JAXBElement<QueryCommProdNoticeByCommProdId> createQueryCommProdNoticeByCommProdId(QueryCommProdNoticeByCommProdId value) {
        return new JAXBElement<QueryCommProdNoticeByCommProdId>(_QueryCommProdNoticeByCommProdId_QNAME, QueryCommProdNoticeByCommProdId.class, null, value);
    }

}
