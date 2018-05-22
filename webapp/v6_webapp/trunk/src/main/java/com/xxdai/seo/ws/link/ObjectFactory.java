
package com.xxdai.seo.ws.link;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.seo.ws.link package. 
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

    private final static QName _DeleteLinkByIdResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "deleteLinkByIdResponse");
    private final static QName _GetLinkExchangeList_QNAME = new QName("http://webservice.seo.xxdai.com/", "getLinkExchangeList");
    private final static QName _GetlinkListByCateTypeResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "getlinkListByCateTypeResponse");
    private final static QName _GetLinkExchangeById_QNAME = new QName("http://webservice.seo.xxdai.com/", "getLinkExchangeById");
    private final static QName _GetlinkListByCateType_QNAME = new QName("http://webservice.seo.xxdai.com/", "getlinkListByCateType");
    private final static QName _SaveOrUpdateLinkResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "saveOrUpdateLinkResponse");
    private final static QName _DeleteLinkById_QNAME = new QName("http://webservice.seo.xxdai.com/", "deleteLinkById");
    private final static QName _GetLinkExchangeListResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "getLinkExchangeListResponse");
    private final static QName _GetLinkExchangeByIdResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "getLinkExchangeByIdResponse");
    private final static QName _SaveOrUpdateLink_QNAME = new QName("http://webservice.seo.xxdai.com/", "saveOrUpdateLink");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.seo.ws.link
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SaveOrUpdateLink }
     * 
     */
    public SaveOrUpdateLink createSaveOrUpdateLink() {
        return new SaveOrUpdateLink();
    }

    /**
     * Create an instance of {@link GetLinkExchangeByIdResponse }
     * 
     */
    public GetLinkExchangeByIdResponse createGetLinkExchangeByIdResponse() {
        return new GetLinkExchangeByIdResponse();
    }

    /**
     * Create an instance of {@link GetLinkExchangeListResponse }
     * 
     */
    public GetLinkExchangeListResponse createGetLinkExchangeListResponse() {
        return new GetLinkExchangeListResponse();
    }

    /**
     * Create an instance of {@link DeleteLinkById }
     * 
     */
    public DeleteLinkById createDeleteLinkById() {
        return new DeleteLinkById();
    }

    /**
     * Create an instance of {@link SaveOrUpdateLinkResponse }
     * 
     */
    public SaveOrUpdateLinkResponse createSaveOrUpdateLinkResponse() {
        return new SaveOrUpdateLinkResponse();
    }

    /**
     * Create an instance of {@link GetlinkListByCateType }
     * 
     */
    public GetlinkListByCateType createGetlinkListByCateType() {
        return new GetlinkListByCateType();
    }

    /**
     * Create an instance of {@link GetLinkExchangeById }
     * 
     */
    public GetLinkExchangeById createGetLinkExchangeById() {
        return new GetLinkExchangeById();
    }

    /**
     * Create an instance of {@link GetlinkListByCateTypeResponse }
     * 
     */
    public GetlinkListByCateTypeResponse createGetlinkListByCateTypeResponse() {
        return new GetlinkListByCateTypeResponse();
    }

    /**
     * Create an instance of {@link GetLinkExchangeList }
     * 
     */
    public GetLinkExchangeList createGetLinkExchangeList() {
        return new GetLinkExchangeList();
    }

    /**
     * Create an instance of {@link DeleteLinkByIdResponse }
     * 
     */
    public DeleteLinkByIdResponse createDeleteLinkByIdResponse() {
        return new DeleteLinkByIdResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteLinkByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "deleteLinkByIdResponse")
    public JAXBElement<DeleteLinkByIdResponse> createDeleteLinkByIdResponse(DeleteLinkByIdResponse value) {
        return new JAXBElement<DeleteLinkByIdResponse>(_DeleteLinkByIdResponse_QNAME, DeleteLinkByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLinkExchangeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "getLinkExchangeList")
    public JAXBElement<GetLinkExchangeList> createGetLinkExchangeList(GetLinkExchangeList value) {
        return new JAXBElement<GetLinkExchangeList>(_GetLinkExchangeList_QNAME, GetLinkExchangeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetlinkListByCateTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "getlinkListByCateTypeResponse")
    public JAXBElement<GetlinkListByCateTypeResponse> createGetlinkListByCateTypeResponse(GetlinkListByCateTypeResponse value) {
        return new JAXBElement<GetlinkListByCateTypeResponse>(_GetlinkListByCateTypeResponse_QNAME, GetlinkListByCateTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLinkExchangeById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "getLinkExchangeById")
    public JAXBElement<GetLinkExchangeById> createGetLinkExchangeById(GetLinkExchangeById value) {
        return new JAXBElement<GetLinkExchangeById>(_GetLinkExchangeById_QNAME, GetLinkExchangeById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetlinkListByCateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "getlinkListByCateType")
    public JAXBElement<GetlinkListByCateType> createGetlinkListByCateType(GetlinkListByCateType value) {
        return new JAXBElement<GetlinkListByCateType>(_GetlinkListByCateType_QNAME, GetlinkListByCateType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveOrUpdateLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "saveOrUpdateLinkResponse")
    public JAXBElement<SaveOrUpdateLinkResponse> createSaveOrUpdateLinkResponse(SaveOrUpdateLinkResponse value) {
        return new JAXBElement<SaveOrUpdateLinkResponse>(_SaveOrUpdateLinkResponse_QNAME, SaveOrUpdateLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteLinkById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "deleteLinkById")
    public JAXBElement<DeleteLinkById> createDeleteLinkById(DeleteLinkById value) {
        return new JAXBElement<DeleteLinkById>(_DeleteLinkById_QNAME, DeleteLinkById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLinkExchangeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "getLinkExchangeListResponse")
    public JAXBElement<GetLinkExchangeListResponse> createGetLinkExchangeListResponse(GetLinkExchangeListResponse value) {
        return new JAXBElement<GetLinkExchangeListResponse>(_GetLinkExchangeListResponse_QNAME, GetLinkExchangeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLinkExchangeByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "getLinkExchangeByIdResponse")
    public JAXBElement<GetLinkExchangeByIdResponse> createGetLinkExchangeByIdResponse(GetLinkExchangeByIdResponse value) {
        return new JAXBElement<GetLinkExchangeByIdResponse>(_GetLinkExchangeByIdResponse_QNAME, GetLinkExchangeByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveOrUpdateLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "saveOrUpdateLink")
    public JAXBElement<SaveOrUpdateLink> createSaveOrUpdateLink(SaveOrUpdateLink value) {
        return new JAXBElement<SaveOrUpdateLink>(_SaveOrUpdateLink_QNAME, SaveOrUpdateLink.class, null, value);
    }

}
