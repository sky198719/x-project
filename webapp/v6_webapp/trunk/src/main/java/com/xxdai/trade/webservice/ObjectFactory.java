
package com.xxdai.trade.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.trade.webservice package. 
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

    private final static QName _CheckTradeTransfer_QNAME = new QName("http://webservice.trade.xxdai.com/", "checkTradeTransfer");
    private final static QName _QueryTradeRequest_QNAME = new QName("http://webservice.trade.xxdai.com/", "queryTradeRequest");
    private final static QName _GetAccountUsableResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getAccountUsableResponse");
    private final static QName _GetTotalTradeCountResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTotalTradeCountResponse");
    private final static QName _GetTradeDetail_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTradeDetail");
    private final static QName _UpdateTradeRequestAprAndRateResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "updateTradeRequestAprAndRateResponse");
    private final static QName _GetTradeRequestDetailResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTradeRequestDetailResponse");
    private final static QName _SearchTradeTransfer_QNAME = new QName("http://webservice.trade.xxdai.com/", "searchTradeTransfer");
    private final static QName _BuyTradePack_QNAME = new QName("http://webservice.trade.xxdai.com/", "buyTradePack");
    private final static QName _GetTransferContract_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTransferContract");
    private final static QName _GetTotalTradeCount_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTotalTradeCount");
    private final static QName _GetTradeRequestDetail_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTradeRequestDetail");
    private final static QName _GetTotalProfit_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTotalProfit");
    private final static QName _GetTransferingCountResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTransferingCountResponse");
    private final static QName _CheckTradeTransferResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "checkTradeTransferResponse");
    private final static QName _GetTradeDetailResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTradeDetailResponse");
    private final static QName _GetTransferOutCount_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTransferOutCount");
    private final static QName _GetTotalProfitResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTotalProfitResponse");
    private final static QName _SearchTradeTransferResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "searchTradeTransferResponse");
    private final static QName _SendSmsMessage_QNAME = new QName("http://webservice.trade.xxdai.com/", "sendSmsMessage");
    private final static QName _GetCurrMonthTradeCountResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getCurrMonthTradeCountResponse");
    private final static QName _UpdateTradeRequestAprAndRate_QNAME = new QName("http://webservice.trade.xxdai.com/", "updateTradeRequestAprAndRate");
    private final static QName _GetCurrMonthTradeCount_QNAME = new QName("http://webservice.trade.xxdai.com/", "getCurrMonthTradeCount");
    private final static QName _GetTransferingCount_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTransferingCount");
    private final static QName _GetTransferOutCountResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTransferOutCountResponse");
    private final static QName _SearchTradeRequest_QNAME = new QName("http://webservice.trade.xxdai.com/", "searchTradeRequest");
    private final static QName _RollbackRadeRequestResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "rollbackRadeRequestResponse");
    private final static QName _SaveTradeRequest_QNAME = new QName("http://webservice.trade.xxdai.com/", "saveTradeRequest");
    private final static QName _GetAccountUsable_QNAME = new QName("http://webservice.trade.xxdai.com/", "getAccountUsable");
    private final static QName _GetTransferContractResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTransferContractResponse");
    private final static QName _RollbackRadeRequest_QNAME = new QName("http://webservice.trade.xxdai.com/", "rollbackRadeRequest");
    private final static QName _SendSmsMessageResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "sendSmsMessageResponse");
    private final static QName _SaveTradeRequestResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "saveTradeRequestResponse");
    private final static QName _BuyTradePackResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "buyTradePackResponse");
    private final static QName _GetTotalTradeAmount_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTotalTradeAmount");
    private final static QName _QueryTradeRequestResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "queryTradeRequestResponse");
    private final static QName _SearchTradeRequestResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "searchTradeRequestResponse");
    private final static QName _GetTotalTradeAmountResponse_QNAME = new QName("http://webservice.trade.xxdai.com/", "getTotalTradeAmountResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.trade.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchTradeRequest }
     * 
     */
    public SearchTradeRequest createSearchTradeRequest() {
        return new SearchTradeRequest();
    }

    /**
     * Create an instance of {@link GetTransferOutCountResponse }
     * 
     */
    public GetTransferOutCountResponse createGetTransferOutCountResponse() {
        return new GetTransferOutCountResponse();
    }

    /**
     * Create an instance of {@link RollbackRadeRequestResponse }
     * 
     */
    public RollbackRadeRequestResponse createRollbackRadeRequestResponse() {
        return new RollbackRadeRequestResponse();
    }

    /**
     * Create an instance of {@link SaveTradeRequest }
     * 
     */
    public SaveTradeRequest createSaveTradeRequest() {
        return new SaveTradeRequest();
    }

    /**
     * Create an instance of {@link GetAccountUsable }
     * 
     */
    public GetAccountUsable createGetAccountUsable() {
        return new GetAccountUsable();
    }

    /**
     * Create an instance of {@link GetTransferContractResponse }
     * 
     */
    public GetTransferContractResponse createGetTransferContractResponse() {
        return new GetTransferContractResponse();
    }

    /**
     * Create an instance of {@link RollbackRadeRequest }
     * 
     */
    public RollbackRadeRequest createRollbackRadeRequest() {
        return new RollbackRadeRequest();
    }

    /**
     * Create an instance of {@link SendSmsMessageResponse }
     * 
     */
    public SendSmsMessageResponse createSendSmsMessageResponse() {
        return new SendSmsMessageResponse();
    }

    /**
     * Create an instance of {@link SaveTradeRequestResponse }
     * 
     */
    public SaveTradeRequestResponse createSaveTradeRequestResponse() {
        return new SaveTradeRequestResponse();
    }

    /**
     * Create an instance of {@link BuyTradePackResponse }
     * 
     */
    public BuyTradePackResponse createBuyTradePackResponse() {
        return new BuyTradePackResponse();
    }

    /**
     * Create an instance of {@link QueryTradeRequestResponse }
     * 
     */
    public QueryTradeRequestResponse createQueryTradeRequestResponse() {
        return new QueryTradeRequestResponse();
    }

    /**
     * Create an instance of {@link GetTotalTradeAmount }
     * 
     */
    public GetTotalTradeAmount createGetTotalTradeAmount() {
        return new GetTotalTradeAmount();
    }

    /**
     * Create an instance of {@link SearchTradeRequestResponse }
     * 
     */
    public SearchTradeRequestResponse createSearchTradeRequestResponse() {
        return new SearchTradeRequestResponse();
    }

    /**
     * Create an instance of {@link GetTotalTradeAmountResponse }
     * 
     */
    public GetTotalTradeAmountResponse createGetTotalTradeAmountResponse() {
        return new GetTotalTradeAmountResponse();
    }

    /**
     * Create an instance of {@link CheckTradeTransfer }
     * 
     */
    public CheckTradeTransfer createCheckTradeTransfer() {
        return new CheckTradeTransfer();
    }

    /**
     * Create an instance of {@link QueryTradeRequest }
     * 
     */
    public QueryTradeRequest createQueryTradeRequest() {
        return new QueryTradeRequest();
    }

    /**
     * Create an instance of {@link GetAccountUsableResponse }
     * 
     */
    public GetAccountUsableResponse createGetAccountUsableResponse() {
        return new GetAccountUsableResponse();
    }

    /**
     * Create an instance of {@link GetTotalTradeCountResponse }
     * 
     */
    public GetTotalTradeCountResponse createGetTotalTradeCountResponse() {
        return new GetTotalTradeCountResponse();
    }

    /**
     * Create an instance of {@link GetTradeDetail }
     * 
     */
    public GetTradeDetail createGetTradeDetail() {
        return new GetTradeDetail();
    }

    /**
     * Create an instance of {@link UpdateTradeRequestAprAndRateResponse }
     * 
     */
    public UpdateTradeRequestAprAndRateResponse createUpdateTradeRequestAprAndRateResponse() {
        return new UpdateTradeRequestAprAndRateResponse();
    }

    /**
     * Create an instance of {@link GetTradeRequestDetailResponse }
     * 
     */
    public GetTradeRequestDetailResponse createGetTradeRequestDetailResponse() {
        return new GetTradeRequestDetailResponse();
    }

    /**
     * Create an instance of {@link SearchTradeTransfer }
     * 
     */
    public SearchTradeTransfer createSearchTradeTransfer() {
        return new SearchTradeTransfer();
    }

    /**
     * Create an instance of {@link BuyTradePack }
     * 
     */
    public BuyTradePack createBuyTradePack() {
        return new BuyTradePack();
    }

    /**
     * Create an instance of {@link GetTransferContract }
     * 
     */
    public GetTransferContract createGetTransferContract() {
        return new GetTransferContract();
    }

    /**
     * Create an instance of {@link GetTotalTradeCount }
     * 
     */
    public GetTotalTradeCount createGetTotalTradeCount() {
        return new GetTotalTradeCount();
    }

    /**
     * Create an instance of {@link GetTotalProfit }
     * 
     */
    public GetTotalProfit createGetTotalProfit() {
        return new GetTotalProfit();
    }

    /**
     * Create an instance of {@link GetTradeRequestDetail }
     * 
     */
    public GetTradeRequestDetail createGetTradeRequestDetail() {
        return new GetTradeRequestDetail();
    }

    /**
     * Create an instance of {@link GetTransferingCountResponse }
     * 
     */
    public GetTransferingCountResponse createGetTransferingCountResponse() {
        return new GetTransferingCountResponse();
    }

    /**
     * Create an instance of {@link CheckTradeTransferResponse }
     * 
     */
    public CheckTradeTransferResponse createCheckTradeTransferResponse() {
        return new CheckTradeTransferResponse();
    }

    /**
     * Create an instance of {@link GetTradeDetailResponse }
     * 
     */
    public GetTradeDetailResponse createGetTradeDetailResponse() {
        return new GetTradeDetailResponse();
    }

    /**
     * Create an instance of {@link GetTransferOutCount }
     * 
     */
    public GetTransferOutCount createGetTransferOutCount() {
        return new GetTransferOutCount();
    }

    /**
     * Create an instance of {@link GetTotalProfitResponse }
     * 
     */
    public GetTotalProfitResponse createGetTotalProfitResponse() {
        return new GetTotalProfitResponse();
    }

    /**
     * Create an instance of {@link SendSmsMessage }
     * 
     */
    public SendSmsMessage createSendSmsMessage() {
        return new SendSmsMessage();
    }

    /**
     * Create an instance of {@link SearchTradeTransferResponse }
     * 
     */
    public SearchTradeTransferResponse createSearchTradeTransferResponse() {
        return new SearchTradeTransferResponse();
    }

    /**
     * Create an instance of {@link GetCurrMonthTradeCountResponse }
     * 
     */
    public GetCurrMonthTradeCountResponse createGetCurrMonthTradeCountResponse() {
        return new GetCurrMonthTradeCountResponse();
    }

    /**
     * Create an instance of {@link UpdateTradeRequestAprAndRate }
     * 
     */
    public UpdateTradeRequestAprAndRate createUpdateTradeRequestAprAndRate() {
        return new UpdateTradeRequestAprAndRate();
    }

    /**
     * Create an instance of {@link GetCurrMonthTradeCount }
     * 
     */
    public GetCurrMonthTradeCount createGetCurrMonthTradeCount() {
        return new GetCurrMonthTradeCount();
    }

    /**
     * Create an instance of {@link GetTransferingCount }
     * 
     */
    public GetTransferingCount createGetTransferingCount() {
        return new GetTransferingCount();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckTradeTransfer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "checkTradeTransfer")
    public JAXBElement<CheckTradeTransfer> createCheckTradeTransfer(CheckTradeTransfer value) {
        return new JAXBElement<CheckTradeTransfer>(_CheckTradeTransfer_QNAME, CheckTradeTransfer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTradeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "queryTradeRequest")
    public JAXBElement<QueryTradeRequest> createQueryTradeRequest(QueryTradeRequest value) {
        return new JAXBElement<QueryTradeRequest>(_QueryTradeRequest_QNAME, QueryTradeRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountUsableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getAccountUsableResponse")
    public JAXBElement<GetAccountUsableResponse> createGetAccountUsableResponse(GetAccountUsableResponse value) {
        return new JAXBElement<GetAccountUsableResponse>(_GetAccountUsableResponse_QNAME, GetAccountUsableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalTradeCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTotalTradeCountResponse")
    public JAXBElement<GetTotalTradeCountResponse> createGetTotalTradeCountResponse(GetTotalTradeCountResponse value) {
        return new JAXBElement<GetTotalTradeCountResponse>(_GetTotalTradeCountResponse_QNAME, GetTotalTradeCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTradeDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTradeDetail")
    public JAXBElement<GetTradeDetail> createGetTradeDetail(GetTradeDetail value) {
        return new JAXBElement<GetTradeDetail>(_GetTradeDetail_QNAME, GetTradeDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTradeRequestAprAndRateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "updateTradeRequestAprAndRateResponse")
    public JAXBElement<UpdateTradeRequestAprAndRateResponse> createUpdateTradeRequestAprAndRateResponse(UpdateTradeRequestAprAndRateResponse value) {
        return new JAXBElement<UpdateTradeRequestAprAndRateResponse>(_UpdateTradeRequestAprAndRateResponse_QNAME, UpdateTradeRequestAprAndRateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTradeRequestDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTradeRequestDetailResponse")
    public JAXBElement<GetTradeRequestDetailResponse> createGetTradeRequestDetailResponse(GetTradeRequestDetailResponse value) {
        return new JAXBElement<GetTradeRequestDetailResponse>(_GetTradeRequestDetailResponse_QNAME, GetTradeRequestDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchTradeTransfer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "searchTradeTransfer")
    public JAXBElement<SearchTradeTransfer> createSearchTradeTransfer(SearchTradeTransfer value) {
        return new JAXBElement<SearchTradeTransfer>(_SearchTradeTransfer_QNAME, SearchTradeTransfer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyTradePack }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "buyTradePack")
    public JAXBElement<BuyTradePack> createBuyTradePack(BuyTradePack value) {
        return new JAXBElement<BuyTradePack>(_BuyTradePack_QNAME, BuyTradePack.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransferContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTransferContract")
    public JAXBElement<GetTransferContract> createGetTransferContract(GetTransferContract value) {
        return new JAXBElement<GetTransferContract>(_GetTransferContract_QNAME, GetTransferContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalTradeCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTotalTradeCount")
    public JAXBElement<GetTotalTradeCount> createGetTotalTradeCount(GetTotalTradeCount value) {
        return new JAXBElement<GetTotalTradeCount>(_GetTotalTradeCount_QNAME, GetTotalTradeCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTradeRequestDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTradeRequestDetail")
    public JAXBElement<GetTradeRequestDetail> createGetTradeRequestDetail(GetTradeRequestDetail value) {
        return new JAXBElement<GetTradeRequestDetail>(_GetTradeRequestDetail_QNAME, GetTradeRequestDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalProfit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTotalProfit")
    public JAXBElement<GetTotalProfit> createGetTotalProfit(GetTotalProfit value) {
        return new JAXBElement<GetTotalProfit>(_GetTotalProfit_QNAME, GetTotalProfit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransferingCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTransferingCountResponse")
    public JAXBElement<GetTransferingCountResponse> createGetTransferingCountResponse(GetTransferingCountResponse value) {
        return new JAXBElement<GetTransferingCountResponse>(_GetTransferingCountResponse_QNAME, GetTransferingCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckTradeTransferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "checkTradeTransferResponse")
    public JAXBElement<CheckTradeTransferResponse> createCheckTradeTransferResponse(CheckTradeTransferResponse value) {
        return new JAXBElement<CheckTradeTransferResponse>(_CheckTradeTransferResponse_QNAME, CheckTradeTransferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTradeDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTradeDetailResponse")
    public JAXBElement<GetTradeDetailResponse> createGetTradeDetailResponse(GetTradeDetailResponse value) {
        return new JAXBElement<GetTradeDetailResponse>(_GetTradeDetailResponse_QNAME, GetTradeDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransferOutCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTransferOutCount")
    public JAXBElement<GetTransferOutCount> createGetTransferOutCount(GetTransferOutCount value) {
        return new JAXBElement<GetTransferOutCount>(_GetTransferOutCount_QNAME, GetTransferOutCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalProfitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTotalProfitResponse")
    public JAXBElement<GetTotalProfitResponse> createGetTotalProfitResponse(GetTotalProfitResponse value) {
        return new JAXBElement<GetTotalProfitResponse>(_GetTotalProfitResponse_QNAME, GetTotalProfitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchTradeTransferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "searchTradeTransferResponse")
    public JAXBElement<SearchTradeTransferResponse> createSearchTradeTransferResponse(SearchTradeTransferResponse value) {
        return new JAXBElement<SearchTradeTransferResponse>(_SearchTradeTransferResponse_QNAME, SearchTradeTransferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSmsMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "sendSmsMessage")
    public JAXBElement<SendSmsMessage> createSendSmsMessage(SendSmsMessage value) {
        return new JAXBElement<SendSmsMessage>(_SendSmsMessage_QNAME, SendSmsMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrMonthTradeCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getCurrMonthTradeCountResponse")
    public JAXBElement<GetCurrMonthTradeCountResponse> createGetCurrMonthTradeCountResponse(GetCurrMonthTradeCountResponse value) {
        return new JAXBElement<GetCurrMonthTradeCountResponse>(_GetCurrMonthTradeCountResponse_QNAME, GetCurrMonthTradeCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTradeRequestAprAndRate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "updateTradeRequestAprAndRate")
    public JAXBElement<UpdateTradeRequestAprAndRate> createUpdateTradeRequestAprAndRate(UpdateTradeRequestAprAndRate value) {
        return new JAXBElement<UpdateTradeRequestAprAndRate>(_UpdateTradeRequestAprAndRate_QNAME, UpdateTradeRequestAprAndRate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrMonthTradeCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getCurrMonthTradeCount")
    public JAXBElement<GetCurrMonthTradeCount> createGetCurrMonthTradeCount(GetCurrMonthTradeCount value) {
        return new JAXBElement<GetCurrMonthTradeCount>(_GetCurrMonthTradeCount_QNAME, GetCurrMonthTradeCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransferingCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTransferingCount")
    public JAXBElement<GetTransferingCount> createGetTransferingCount(GetTransferingCount value) {
        return new JAXBElement<GetTransferingCount>(_GetTransferingCount_QNAME, GetTransferingCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransferOutCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTransferOutCountResponse")
    public JAXBElement<GetTransferOutCountResponse> createGetTransferOutCountResponse(GetTransferOutCountResponse value) {
        return new JAXBElement<GetTransferOutCountResponse>(_GetTransferOutCountResponse_QNAME, GetTransferOutCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchTradeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "searchTradeRequest")
    public JAXBElement<SearchTradeRequest> createSearchTradeRequest(SearchTradeRequest value) {
        return new JAXBElement<SearchTradeRequest>(_SearchTradeRequest_QNAME, SearchTradeRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RollbackRadeRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "rollbackRadeRequestResponse")
    public JAXBElement<RollbackRadeRequestResponse> createRollbackRadeRequestResponse(RollbackRadeRequestResponse value) {
        return new JAXBElement<RollbackRadeRequestResponse>(_RollbackRadeRequestResponse_QNAME, RollbackRadeRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveTradeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "saveTradeRequest")
    public JAXBElement<SaveTradeRequest> createSaveTradeRequest(SaveTradeRequest value) {
        return new JAXBElement<SaveTradeRequest>(_SaveTradeRequest_QNAME, SaveTradeRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountUsable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getAccountUsable")
    public JAXBElement<GetAccountUsable> createGetAccountUsable(GetAccountUsable value) {
        return new JAXBElement<GetAccountUsable>(_GetAccountUsable_QNAME, GetAccountUsable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransferContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTransferContractResponse")
    public JAXBElement<GetTransferContractResponse> createGetTransferContractResponse(GetTransferContractResponse value) {
        return new JAXBElement<GetTransferContractResponse>(_GetTransferContractResponse_QNAME, GetTransferContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RollbackRadeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "rollbackRadeRequest")
    public JAXBElement<RollbackRadeRequest> createRollbackRadeRequest(RollbackRadeRequest value) {
        return new JAXBElement<RollbackRadeRequest>(_RollbackRadeRequest_QNAME, RollbackRadeRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSmsMessageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "sendSmsMessageResponse")
    public JAXBElement<SendSmsMessageResponse> createSendSmsMessageResponse(SendSmsMessageResponse value) {
        return new JAXBElement<SendSmsMessageResponse>(_SendSmsMessageResponse_QNAME, SendSmsMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveTradeRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "saveTradeRequestResponse")
    public JAXBElement<SaveTradeRequestResponse> createSaveTradeRequestResponse(SaveTradeRequestResponse value) {
        return new JAXBElement<SaveTradeRequestResponse>(_SaveTradeRequestResponse_QNAME, SaveTradeRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyTradePackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "buyTradePackResponse")
    public JAXBElement<BuyTradePackResponse> createBuyTradePackResponse(BuyTradePackResponse value) {
        return new JAXBElement<BuyTradePackResponse>(_BuyTradePackResponse_QNAME, BuyTradePackResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalTradeAmount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTotalTradeAmount")
    public JAXBElement<GetTotalTradeAmount> createGetTotalTradeAmount(GetTotalTradeAmount value) {
        return new JAXBElement<GetTotalTradeAmount>(_GetTotalTradeAmount_QNAME, GetTotalTradeAmount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTradeRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "queryTradeRequestResponse")
    public JAXBElement<QueryTradeRequestResponse> createQueryTradeRequestResponse(QueryTradeRequestResponse value) {
        return new JAXBElement<QueryTradeRequestResponse>(_QueryTradeRequestResponse_QNAME, QueryTradeRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchTradeRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "searchTradeRequestResponse")
    public JAXBElement<SearchTradeRequestResponse> createSearchTradeRequestResponse(SearchTradeRequestResponse value) {
        return new JAXBElement<SearchTradeRequestResponse>(_SearchTradeRequestResponse_QNAME, SearchTradeRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalTradeAmountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.trade.xxdai.com/", name = "getTotalTradeAmountResponse")
    public JAXBElement<GetTotalTradeAmountResponse> createGetTotalTradeAmountResponse(GetTotalTradeAmountResponse value) {
        return new JAXBElement<GetTotalTradeAmountResponse>(_GetTotalTradeAmountResponse_QNAME, GetTotalTradeAmountResponse.class, null, value);
    }

}
