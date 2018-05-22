
package com.xxdai.external.fund.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.fund.ws package. 
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

    private final static QName _GetFundListResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "getFundListResponse");
    private final static QName _QueryFundBuyRechargeDetailResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "queryFundBuyRechargeDetailResponse");
    private final static QName _GetFundGADataResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "getFundGADataResponse");
    private final static QName _QueryFundBuyRechargeDetail_QNAME = new QName("http://webservice.fund.xxdai.com/", "queryFundBuyRechargeDetail");
    private final static QName _QueryFundInfoByFcode_QNAME = new QName("http://webservice.fund.xxdai.com/", "queryFundInfoByFcode");
    private final static QName _UpdateFundInfoByFcodeResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "updateFundInfoByFcodeResponse");
    private final static QName _QueryFundInfoByFcodeResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "queryFundInfoByFcodeResponse");
    private final static QName _QueryFundJoinNumbers_QNAME = new QName("http://webservice.fund.xxdai.com/", "queryFundJoinNumbers");
    private final static QName _UpdateFundInfoByFcode_QNAME = new QName("http://webservice.fund.xxdai.com/", "updateFundInfoByFcode");
    private final static QName _GetFundGAData_QNAME = new QName("http://webservice.fund.xxdai.com/", "getFundGAData");
    private final static QName _GetFundList_QNAME = new QName("http://webservice.fund.xxdai.com/", "getFundList");
    private final static QName _QueryFundJoinNumbersResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "queryFundJoinNumbersResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.fund.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryFundJoinNumbers }
     * 
     */
    public QueryFundJoinNumbers createQueryFundJoinNumbers() {
        return new QueryFundJoinNumbers();
    }

    /**
     * Create an instance of {@link UpdateFundInfoByFcode }
     * 
     */
    public UpdateFundInfoByFcode createUpdateFundInfoByFcode() {
        return new UpdateFundInfoByFcode();
    }

    /**
     * Create an instance of {@link QueryFundJoinNumbersResponse }
     * 
     */
    public QueryFundJoinNumbersResponse createQueryFundJoinNumbersResponse() {
        return new QueryFundJoinNumbersResponse();
    }

    /**
     * Create an instance of {@link GetFundList }
     * 
     */
    public GetFundList createGetFundList() {
        return new GetFundList();
    }

    /**
     * Create an instance of {@link GetFundGAData }
     * 
     */
    public GetFundGAData createGetFundGAData() {
        return new GetFundGAData();
    }

    /**
     * Create an instance of {@link QueryFundBuyRechargeDetail }
     * 
     */
    public QueryFundBuyRechargeDetail createQueryFundBuyRechargeDetail() {
        return new QueryFundBuyRechargeDetail();
    }

    /**
     * Create an instance of {@link GetFundGADataResponse }
     * 
     */
    public GetFundGADataResponse createGetFundGADataResponse() {
        return new GetFundGADataResponse();
    }

    /**
     * Create an instance of {@link QueryFundBuyRechargeDetailResponse }
     * 
     */
    public QueryFundBuyRechargeDetailResponse createQueryFundBuyRechargeDetailResponse() {
        return new QueryFundBuyRechargeDetailResponse();
    }

    /**
     * Create an instance of {@link GetFundListResponse }
     * 
     */
    public GetFundListResponse createGetFundListResponse() {
        return new GetFundListResponse();
    }

    /**
     * Create an instance of {@link UpdateFundInfoByFcodeResponse }
     * 
     */
    public UpdateFundInfoByFcodeResponse createUpdateFundInfoByFcodeResponse() {
        return new UpdateFundInfoByFcodeResponse();
    }

    /**
     * Create an instance of {@link QueryFundInfoByFcode }
     * 
     */
    public QueryFundInfoByFcode createQueryFundInfoByFcode() {
        return new QueryFundInfoByFcode();
    }

    /**
     * Create an instance of {@link QueryFundInfoByFcodeResponse }
     * 
     */
    public QueryFundInfoByFcodeResponse createQueryFundInfoByFcodeResponse() {
        return new QueryFundInfoByFcodeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getFundListResponse")
    public JAXBElement<GetFundListResponse> createGetFundListResponse(GetFundListResponse value) {
        return new JAXBElement<GetFundListResponse>(_GetFundListResponse_QNAME, GetFundListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFundBuyRechargeDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "queryFundBuyRechargeDetailResponse")
    public JAXBElement<QueryFundBuyRechargeDetailResponse> createQueryFundBuyRechargeDetailResponse(QueryFundBuyRechargeDetailResponse value) {
        return new JAXBElement<QueryFundBuyRechargeDetailResponse>(_QueryFundBuyRechargeDetailResponse_QNAME, QueryFundBuyRechargeDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundGADataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getFundGADataResponse")
    public JAXBElement<GetFundGADataResponse> createGetFundGADataResponse(GetFundGADataResponse value) {
        return new JAXBElement<GetFundGADataResponse>(_GetFundGADataResponse_QNAME, GetFundGADataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFundBuyRechargeDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "queryFundBuyRechargeDetail")
    public JAXBElement<QueryFundBuyRechargeDetail> createQueryFundBuyRechargeDetail(QueryFundBuyRechargeDetail value) {
        return new JAXBElement<QueryFundBuyRechargeDetail>(_QueryFundBuyRechargeDetail_QNAME, QueryFundBuyRechargeDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFundInfoByFcode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "queryFundInfoByFcode")
    public JAXBElement<QueryFundInfoByFcode> createQueryFundInfoByFcode(QueryFundInfoByFcode value) {
        return new JAXBElement<QueryFundInfoByFcode>(_QueryFundInfoByFcode_QNAME, QueryFundInfoByFcode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateFundInfoByFcodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "updateFundInfoByFcodeResponse")
    public JAXBElement<UpdateFundInfoByFcodeResponse> createUpdateFundInfoByFcodeResponse(UpdateFundInfoByFcodeResponse value) {
        return new JAXBElement<UpdateFundInfoByFcodeResponse>(_UpdateFundInfoByFcodeResponse_QNAME, UpdateFundInfoByFcodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFundInfoByFcodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "queryFundInfoByFcodeResponse")
    public JAXBElement<QueryFundInfoByFcodeResponse> createQueryFundInfoByFcodeResponse(QueryFundInfoByFcodeResponse value) {
        return new JAXBElement<QueryFundInfoByFcodeResponse>(_QueryFundInfoByFcodeResponse_QNAME, QueryFundInfoByFcodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFundJoinNumbers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "queryFundJoinNumbers")
    public JAXBElement<QueryFundJoinNumbers> createQueryFundJoinNumbers(QueryFundJoinNumbers value) {
        return new JAXBElement<QueryFundJoinNumbers>(_QueryFundJoinNumbers_QNAME, QueryFundJoinNumbers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateFundInfoByFcode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "updateFundInfoByFcode")
    public JAXBElement<UpdateFundInfoByFcode> createUpdateFundInfoByFcode(UpdateFundInfoByFcode value) {
        return new JAXBElement<UpdateFundInfoByFcode>(_UpdateFundInfoByFcode_QNAME, UpdateFundInfoByFcode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundGAData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getFundGAData")
    public JAXBElement<GetFundGAData> createGetFundGAData(GetFundGAData value) {
        return new JAXBElement<GetFundGAData>(_GetFundGAData_QNAME, GetFundGAData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getFundList")
    public JAXBElement<GetFundList> createGetFundList(GetFundList value) {
        return new JAXBElement<GetFundList>(_GetFundList_QNAME, GetFundList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFundJoinNumbersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "queryFundJoinNumbersResponse")
    public JAXBElement<QueryFundJoinNumbersResponse> createQueryFundJoinNumbersResponse(QueryFundJoinNumbersResponse value) {
        return new JAXBElement<QueryFundJoinNumbersResponse>(_QueryFundJoinNumbersResponse_QNAME, QueryFundJoinNumbersResponse.class, null, value);
    }

}
