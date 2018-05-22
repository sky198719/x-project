
package com.xxdai.external.fundTrade.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.fundTrade.ws package. 
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

    private final static QName _SelectFundApr_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectFundApr");
    private final static QName _SelectFundByStatus_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectFundByStatus");
    private final static QName _SelectTotalTradeNumResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectTotalTradeNumResponse");
    private final static QName _IsInvestedResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "isInvestedResponse");
    private final static QName _SelectTotalTradeNum_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectTotalTradeNum");
    private final static QName _IsInvested_QNAME = new QName("http://webservice.fund.xxdai.com/", "isInvested");
    private final static QName _GetFundTradeInfoResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "getFundTradeInfoResponse");
    private final static QName _SelectYesterdayEarningsResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectYesterdayEarningsResponse");
    private final static QName _GetFundTradeList_QNAME = new QName("http://webservice.fund.xxdai.com/", "getFundTradeList");
    private final static QName _SelectFundAprResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectFundAprResponse");
    private final static QName _SelectInvestAmount_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectInvestAmount");
    private final static QName _SelectFundByStatusResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectFundByStatusResponse");
    private final static QName _GetFundTradeListResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "getFundTradeListResponse");
    private final static QName _SelectYesterdayEarnings_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectYesterdayEarnings");
    private final static QName _GetFundTradeInfo_QNAME = new QName("http://webservice.fund.xxdai.com/", "getFundTradeInfo");
    private final static QName _SelectInvestAmountResponse_QNAME = new QName("http://webservice.fund.xxdai.com/", "selectInvestAmountResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.fundTrade.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectFundByStatusResponse }
     * 
     */
    public SelectFundByStatusResponse createSelectFundByStatusResponse() {
        return new SelectFundByStatusResponse();
    }

    /**
     * Create an instance of {@link GetFundTradeListResponse }
     * 
     */
    public GetFundTradeListResponse createGetFundTradeListResponse() {
        return new GetFundTradeListResponse();
    }

    /**
     * Create an instance of {@link SelectYesterdayEarnings }
     * 
     */
    public SelectYesterdayEarnings createSelectYesterdayEarnings() {
        return new SelectYesterdayEarnings();
    }

    /**
     * Create an instance of {@link GetFundTradeInfo }
     * 
     */
    public GetFundTradeInfo createGetFundTradeInfo() {
        return new GetFundTradeInfo();
    }

    /**
     * Create an instance of {@link SelectInvestAmountResponse }
     * 
     */
    public SelectInvestAmountResponse createSelectInvestAmountResponse() {
        return new SelectInvestAmountResponse();
    }

    /**
     * Create an instance of {@link SelectYesterdayEarningsResponse }
     * 
     */
    public SelectYesterdayEarningsResponse createSelectYesterdayEarningsResponse() {
        return new SelectYesterdayEarningsResponse();
    }

    /**
     * Create an instance of {@link GetFundTradeList }
     * 
     */
    public GetFundTradeList createGetFundTradeList() {
        return new GetFundTradeList();
    }

    /**
     * Create an instance of {@link SelectFundAprResponse }
     * 
     */
    public SelectFundAprResponse createSelectFundAprResponse() {
        return new SelectFundAprResponse();
    }

    /**
     * Create an instance of {@link SelectInvestAmount }
     * 
     */
    public SelectInvestAmount createSelectInvestAmount() {
        return new SelectInvestAmount();
    }

    /**
     * Create an instance of {@link SelectFundByStatus }
     * 
     */
    public SelectFundByStatus createSelectFundByStatus() {
        return new SelectFundByStatus();
    }

    /**
     * Create an instance of {@link SelectTotalTradeNum }
     * 
     */
    public SelectTotalTradeNum createSelectTotalTradeNum() {
        return new SelectTotalTradeNum();
    }

    /**
     * Create an instance of {@link SelectTotalTradeNumResponse }
     * 
     */
    public SelectTotalTradeNumResponse createSelectTotalTradeNumResponse() {
        return new SelectTotalTradeNumResponse();
    }

    /**
     * Create an instance of {@link IsInvestedResponse }
     * 
     */
    public IsInvestedResponse createIsInvestedResponse() {
        return new IsInvestedResponse();
    }

    /**
     * Create an instance of {@link GetFundTradeInfoResponse }
     * 
     */
    public GetFundTradeInfoResponse createGetFundTradeInfoResponse() {
        return new GetFundTradeInfoResponse();
    }

    /**
     * Create an instance of {@link IsInvested }
     * 
     */
    public IsInvested createIsInvested() {
        return new IsInvested();
    }

    /**
     * Create an instance of {@link SelectFundApr }
     * 
     */
    public SelectFundApr createSelectFundApr() {
        return new SelectFundApr();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectFundApr }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectFundApr")
    public JAXBElement<SelectFundApr> createSelectFundApr(SelectFundApr value) {
        return new JAXBElement<SelectFundApr>(_SelectFundApr_QNAME, SelectFundApr.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectFundByStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectFundByStatus")
    public JAXBElement<SelectFundByStatus> createSelectFundByStatus(SelectFundByStatus value) {
        return new JAXBElement<SelectFundByStatus>(_SelectFundByStatus_QNAME, SelectFundByStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectTotalTradeNumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectTotalTradeNumResponse")
    public JAXBElement<SelectTotalTradeNumResponse> createSelectTotalTradeNumResponse(SelectTotalTradeNumResponse value) {
        return new JAXBElement<SelectTotalTradeNumResponse>(_SelectTotalTradeNumResponse_QNAME, SelectTotalTradeNumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsInvestedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "isInvestedResponse")
    public JAXBElement<IsInvestedResponse> createIsInvestedResponse(IsInvestedResponse value) {
        return new JAXBElement<IsInvestedResponse>(_IsInvestedResponse_QNAME, IsInvestedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectTotalTradeNum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectTotalTradeNum")
    public JAXBElement<SelectTotalTradeNum> createSelectTotalTradeNum(SelectTotalTradeNum value) {
        return new JAXBElement<SelectTotalTradeNum>(_SelectTotalTradeNum_QNAME, SelectTotalTradeNum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsInvested }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "isInvested")
    public JAXBElement<IsInvested> createIsInvested(IsInvested value) {
        return new JAXBElement<IsInvested>(_IsInvested_QNAME, IsInvested.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundTradeInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getFundTradeInfoResponse")
    public JAXBElement<GetFundTradeInfoResponse> createGetFundTradeInfoResponse(GetFundTradeInfoResponse value) {
        return new JAXBElement<GetFundTradeInfoResponse>(_GetFundTradeInfoResponse_QNAME, GetFundTradeInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectYesterdayEarningsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectYesterdayEarningsResponse")
    public JAXBElement<SelectYesterdayEarningsResponse> createSelectYesterdayEarningsResponse(SelectYesterdayEarningsResponse value) {
        return new JAXBElement<SelectYesterdayEarningsResponse>(_SelectYesterdayEarningsResponse_QNAME, SelectYesterdayEarningsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundTradeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getFundTradeList")
    public JAXBElement<GetFundTradeList> createGetFundTradeList(GetFundTradeList value) {
        return new JAXBElement<GetFundTradeList>(_GetFundTradeList_QNAME, GetFundTradeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectFundAprResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectFundAprResponse")
    public JAXBElement<SelectFundAprResponse> createSelectFundAprResponse(SelectFundAprResponse value) {
        return new JAXBElement<SelectFundAprResponse>(_SelectFundAprResponse_QNAME, SelectFundAprResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectInvestAmount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectInvestAmount")
    public JAXBElement<SelectInvestAmount> createSelectInvestAmount(SelectInvestAmount value) {
        return new JAXBElement<SelectInvestAmount>(_SelectInvestAmount_QNAME, SelectInvestAmount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectFundByStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectFundByStatusResponse")
    public JAXBElement<SelectFundByStatusResponse> createSelectFundByStatusResponse(SelectFundByStatusResponse value) {
        return new JAXBElement<SelectFundByStatusResponse>(_SelectFundByStatusResponse_QNAME, SelectFundByStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundTradeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getFundTradeListResponse")
    public JAXBElement<GetFundTradeListResponse> createGetFundTradeListResponse(GetFundTradeListResponse value) {
        return new JAXBElement<GetFundTradeListResponse>(_GetFundTradeListResponse_QNAME, GetFundTradeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectYesterdayEarnings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectYesterdayEarnings")
    public JAXBElement<SelectYesterdayEarnings> createSelectYesterdayEarnings(SelectYesterdayEarnings value) {
        return new JAXBElement<SelectYesterdayEarnings>(_SelectYesterdayEarnings_QNAME, SelectYesterdayEarnings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundTradeInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "getFundTradeInfo")
    public JAXBElement<GetFundTradeInfo> createGetFundTradeInfo(GetFundTradeInfo value) {
        return new JAXBElement<GetFundTradeInfo>(_GetFundTradeInfo_QNAME, GetFundTradeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectInvestAmountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fund.xxdai.com/", name = "selectInvestAmountResponse")
    public JAXBElement<SelectInvestAmountResponse> createSelectInvestAmountResponse(SelectInvestAmountResponse value) {
        return new JAXBElement<SelectInvestAmountResponse>(_SelectInvestAmountResponse_QNAME, SelectInvestAmountResponse.class, null, value);
    }

}
