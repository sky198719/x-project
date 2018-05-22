
package com.xxdai.external.partner.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.partner.ws package. 
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

    private final static QName _QueryPartnerStorePromotionTotalResponse_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerStorePromotionTotalResponse");
    private final static QName _AddPartnerAndUserRelate_QNAME = new QName("http://webservice.partners.xxdai.com/", "addPartnerAndUserRelate");
    private final static QName _QueryPartnerStoreListResponse_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerStoreListResponse");
    private final static QName _DelUserExchange_QNAME = new QName("http://webservice.partners.xxdai.com/", "delUserExchange");
    private final static QName _QueryPartnerPromotionStatisticsInfo_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerPromotionStatisticsInfo");
    private final static QName _QueryPartnerStorePromotionInfo_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerStorePromotionInfo");
    private final static QName _QueryPartnerStoreDetail_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerStoreDetail");
    private final static QName _DelUserExchangeResponse_QNAME = new QName("http://webservice.partners.xxdai.com/", "delUserExchangeResponse");
    private final static QName _QueryPartnerStorePromotionTotal_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerStorePromotionTotal");
    private final static QName _QueryPartnerStorePromotionInfoResponse_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerStorePromotionInfoResponse");
    private final static QName _QueryPartnerStoreList_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerStoreList");
    private final static QName _QueryPartnerPromotionStatisticsInfoResponse_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerPromotionStatisticsInfoResponse");
    private final static QName _QueryPartnerStoreDetailResponse_QNAME = new QName("http://webservice.partners.xxdai.com/", "queryPartnerStoreDetailResponse");
    private final static QName _AddPartnerAndUserRelateResponse_QNAME = new QName("http://webservice.partners.xxdai.com/", "addPartnerAndUserRelateResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.partner.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DelUserExchange }
     * 
     */
    public DelUserExchange createDelUserExchange() {
        return new DelUserExchange();
    }

    /**
     * Create an instance of {@link QueryPartnerPromotionStatisticsInfo }
     * 
     */
    public QueryPartnerPromotionStatisticsInfo createQueryPartnerPromotionStatisticsInfo() {
        return new QueryPartnerPromotionStatisticsInfo();
    }

    /**
     * Create an instance of {@link QueryPartnerStorePromotionInfo }
     * 
     */
    public QueryPartnerStorePromotionInfo createQueryPartnerStorePromotionInfo() {
        return new QueryPartnerStorePromotionInfo();
    }

    /**
     * Create an instance of {@link QueryPartnerStorePromotionTotalResponse }
     * 
     */
    public QueryPartnerStorePromotionTotalResponse createQueryPartnerStorePromotionTotalResponse() {
        return new QueryPartnerStorePromotionTotalResponse();
    }

    /**
     * Create an instance of {@link AddPartnerAndUserRelate }
     * 
     */
    public AddPartnerAndUserRelate createAddPartnerAndUserRelate() {
        return new AddPartnerAndUserRelate();
    }

    /**
     * Create an instance of {@link QueryPartnerStoreListResponse }
     * 
     */
    public QueryPartnerStoreListResponse createQueryPartnerStoreListResponse() {
        return new QueryPartnerStoreListResponse();
    }

    /**
     * Create an instance of {@link QueryPartnerStoreDetail }
     * 
     */
    public QueryPartnerStoreDetail createQueryPartnerStoreDetail() {
        return new QueryPartnerStoreDetail();
    }

    /**
     * Create an instance of {@link QueryPartnerStorePromotionTotal }
     * 
     */
    public QueryPartnerStorePromotionTotal createQueryPartnerStorePromotionTotal() {
        return new QueryPartnerStorePromotionTotal();
    }

    /**
     * Create an instance of {@link QueryPartnerStorePromotionInfoResponse }
     * 
     */
    public QueryPartnerStorePromotionInfoResponse createQueryPartnerStorePromotionInfoResponse() {
        return new QueryPartnerStorePromotionInfoResponse();
    }

    /**
     * Create an instance of {@link DelUserExchangeResponse }
     * 
     */
    public DelUserExchangeResponse createDelUserExchangeResponse() {
        return new DelUserExchangeResponse();
    }

    /**
     * Create an instance of {@link QueryPartnerPromotionStatisticsInfoResponse }
     * 
     */
    public QueryPartnerPromotionStatisticsInfoResponse createQueryPartnerPromotionStatisticsInfoResponse() {
        return new QueryPartnerPromotionStatisticsInfoResponse();
    }

    /**
     * Create an instance of {@link QueryPartnerStoreDetailResponse }
     * 
     */
    public QueryPartnerStoreDetailResponse createQueryPartnerStoreDetailResponse() {
        return new QueryPartnerStoreDetailResponse();
    }

    /**
     * Create an instance of {@link QueryPartnerStoreList }
     * 
     */
    public QueryPartnerStoreList createQueryPartnerStoreList() {
        return new QueryPartnerStoreList();
    }

    /**
     * Create an instance of {@link AddPartnerAndUserRelateResponse }
     * 
     */
    public AddPartnerAndUserRelateResponse createAddPartnerAndUserRelateResponse() {
        return new AddPartnerAndUserRelateResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerStorePromotionTotalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerStorePromotionTotalResponse")
    public JAXBElement<QueryPartnerStorePromotionTotalResponse> createQueryPartnerStorePromotionTotalResponse(QueryPartnerStorePromotionTotalResponse value) {
        return new JAXBElement<QueryPartnerStorePromotionTotalResponse>(_QueryPartnerStorePromotionTotalResponse_QNAME, QueryPartnerStorePromotionTotalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPartnerAndUserRelate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "addPartnerAndUserRelate")
    public JAXBElement<AddPartnerAndUserRelate> createAddPartnerAndUserRelate(AddPartnerAndUserRelate value) {
        return new JAXBElement<AddPartnerAndUserRelate>(_AddPartnerAndUserRelate_QNAME, AddPartnerAndUserRelate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerStoreListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerStoreListResponse")
    public JAXBElement<QueryPartnerStoreListResponse> createQueryPartnerStoreListResponse(QueryPartnerStoreListResponse value) {
        return new JAXBElement<QueryPartnerStoreListResponse>(_QueryPartnerStoreListResponse_QNAME, QueryPartnerStoreListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DelUserExchange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "delUserExchange")
    public JAXBElement<DelUserExchange> createDelUserExchange(DelUserExchange value) {
        return new JAXBElement<DelUserExchange>(_DelUserExchange_QNAME, DelUserExchange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerPromotionStatisticsInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerPromotionStatisticsInfo")
    public JAXBElement<QueryPartnerPromotionStatisticsInfo> createQueryPartnerPromotionStatisticsInfo(QueryPartnerPromotionStatisticsInfo value) {
        return new JAXBElement<QueryPartnerPromotionStatisticsInfo>(_QueryPartnerPromotionStatisticsInfo_QNAME, QueryPartnerPromotionStatisticsInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerStorePromotionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerStorePromotionInfo")
    public JAXBElement<QueryPartnerStorePromotionInfo> createQueryPartnerStorePromotionInfo(QueryPartnerStorePromotionInfo value) {
        return new JAXBElement<QueryPartnerStorePromotionInfo>(_QueryPartnerStorePromotionInfo_QNAME, QueryPartnerStorePromotionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerStoreDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerStoreDetail")
    public JAXBElement<QueryPartnerStoreDetail> createQueryPartnerStoreDetail(QueryPartnerStoreDetail value) {
        return new JAXBElement<QueryPartnerStoreDetail>(_QueryPartnerStoreDetail_QNAME, QueryPartnerStoreDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DelUserExchangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "delUserExchangeResponse")
    public JAXBElement<DelUserExchangeResponse> createDelUserExchangeResponse(DelUserExchangeResponse value) {
        return new JAXBElement<DelUserExchangeResponse>(_DelUserExchangeResponse_QNAME, DelUserExchangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerStorePromotionTotal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerStorePromotionTotal")
    public JAXBElement<QueryPartnerStorePromotionTotal> createQueryPartnerStorePromotionTotal(QueryPartnerStorePromotionTotal value) {
        return new JAXBElement<QueryPartnerStorePromotionTotal>(_QueryPartnerStorePromotionTotal_QNAME, QueryPartnerStorePromotionTotal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerStorePromotionInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerStorePromotionInfoResponse")
    public JAXBElement<QueryPartnerStorePromotionInfoResponse> createQueryPartnerStorePromotionInfoResponse(QueryPartnerStorePromotionInfoResponse value) {
        return new JAXBElement<QueryPartnerStorePromotionInfoResponse>(_QueryPartnerStorePromotionInfoResponse_QNAME, QueryPartnerStorePromotionInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerStoreList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerStoreList")
    public JAXBElement<QueryPartnerStoreList> createQueryPartnerStoreList(QueryPartnerStoreList value) {
        return new JAXBElement<QueryPartnerStoreList>(_QueryPartnerStoreList_QNAME, QueryPartnerStoreList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerPromotionStatisticsInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerPromotionStatisticsInfoResponse")
    public JAXBElement<QueryPartnerPromotionStatisticsInfoResponse> createQueryPartnerPromotionStatisticsInfoResponse(QueryPartnerPromotionStatisticsInfoResponse value) {
        return new JAXBElement<QueryPartnerPromotionStatisticsInfoResponse>(_QueryPartnerPromotionStatisticsInfoResponse_QNAME, QueryPartnerPromotionStatisticsInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPartnerStoreDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "queryPartnerStoreDetailResponse")
    public JAXBElement<QueryPartnerStoreDetailResponse> createQueryPartnerStoreDetailResponse(QueryPartnerStoreDetailResponse value) {
        return new JAXBElement<QueryPartnerStoreDetailResponse>(_QueryPartnerStoreDetailResponse_QNAME, QueryPartnerStoreDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPartnerAndUserRelateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.partners.xxdai.com/", name = "addPartnerAndUserRelateResponse")
    public JAXBElement<AddPartnerAndUserRelateResponse> createAddPartnerAndUserRelateResponse(AddPartnerAndUserRelateResponse value) {
        return new JAXBElement<AddPartnerAndUserRelateResponse>(_AddPartnerAndUserRelateResponse_QNAME, AddPartnerAndUserRelateResponse.class, null, value);
    }

}
