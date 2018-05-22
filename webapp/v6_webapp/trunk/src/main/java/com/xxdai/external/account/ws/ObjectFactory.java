
package com.xxdai.external.account.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.account.ws package. 
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

    private final static QName _CoinExchangeResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "coinExchangeResponse");
    private final static QName _CounttMoneyRecordResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "counttMoneyRecordResponse");
    private final static QName _SelectAccountLogByUserId_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountLogByUserId");
    private final static QName _SelectAccountByIdAndTypeResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountByIdAndTypeResponse");
    private final static QName _QueryLevelLogListResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "queryLevelLogListResponse");
    private final static QName _SelectSixAccountLogByUserId_QNAME = new QName("http://webservice.account.xxdai.com/", "selectSixAccountLogByUserId");
    private final static QName _SelectIsUserOverdue_QNAME = new QName("http://webservice.account.xxdai.com/", "selectIsUserOverdue");
    private final static QName _SelectUserAccountAndCoupon_QNAME = new QName("http://webservice.account.xxdai.com/", "selectUserAccountAndCoupon");
    private final static QName _SelectAccountLog2ByIdAndType_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountLog2ByIdAndType");
    private final static QName _SelectAccountLog2ByIdAndTypeResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountLog2ByIdAndTypeResponse");
    private final static QName _GetAccountSumResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "getAccountSumResponse");
    private final static QName _QueryLevelLogList_QNAME = new QName("http://webservice.account.xxdai.com/", "queryLevelLogList");
    private final static QName _SelectMoneyRecordResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectMoneyRecordResponse");
    private final static QName _IncreaseAccountUsable_QNAME = new QName("http://webservice.account.xxdai.com/", "increaseAccountUsable");
    private final static QName _SelectPageAccountMoneyRecord_QNAME = new QName("http://webservice.account.xxdai.com/", "selectPageAccountMoneyRecord");
    private final static QName _SelectOverdueRepaymentByUserIdResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectOverdueRepaymentByUserIdResponse");
    private final static QName _SelectOverdueRepaymentByUserId_QNAME = new QName("http://webservice.account.xxdai.com/", "selectOverdueRepaymentByUserId");
    private final static QName _SelectUserAccountAndCouponResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectUserAccountAndCouponResponse");
    private final static QName _SelectMoneyRecord_QNAME = new QName("http://webservice.account.xxdai.com/", "selectMoneyRecord");
    private final static QName _SelectIsUserOverdueResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectIsUserOverdueResponse");
    private final static QName _SelectSixAccountLogByUserIdResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectSixAccountLogByUserIdResponse");
    private final static QName _SelectPageAccountMoneyRecordResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectPageAccountMoneyRecordResponse");
    private final static QName _GetAccountLogSumResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "getAccountLogSumResponse");
    private final static QName _GetAccountLogSum_QNAME = new QName("http://webservice.account.xxdai.com/", "getAccountLogSum");
    private final static QName _SaveAccountLog_QNAME = new QName("http://webservice.account.xxdai.com/", "saveAccountLog");
    private final static QName _SelectAccountLogByUserIdResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountLogByUserIdResponse");
    private final static QName _CounttMoneyRecord_QNAME = new QName("http://webservice.account.xxdai.com/", "counttMoneyRecord");
    private final static QName _FreezeAccount_QNAME = new QName("http://webservice.account.xxdai.com/", "freezeAccount");
    private final static QName _CoinExchange_QNAME = new QName("http://webservice.account.xxdai.com/", "coinExchange");
    private final static QName _FreezeAccountResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "freezeAccountResponse");
    private final static QName _SelectAccountByIdAndType_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountByIdAndType");
    private final static QName _IncreaseAccountUsableResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "increaseAccountUsableResponse");
    private final static QName _SaveAccountLogResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "saveAccountLogResponse");
    private final static QName _GetAccountSum_QNAME = new QName("http://webservice.account.xxdai.com/", "getAccountSum");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.account.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CounttMoneyRecordResponse }
     * 
     */
    public CounttMoneyRecordResponse createCounttMoneyRecordResponse() {
        return new CounttMoneyRecordResponse();
    }

    /**
     * Create an instance of {@link SelectAccountLogByUserId }
     * 
     */
    public SelectAccountLogByUserId createSelectAccountLogByUserId() {
        return new SelectAccountLogByUserId();
    }

    /**
     * Create an instance of {@link SelectSixAccountLogByUserId }
     * 
     */
    public SelectSixAccountLogByUserId createSelectSixAccountLogByUserId() {
        return new SelectSixAccountLogByUserId();
    }

    /**
     * Create an instance of {@link QueryLevelLogListResponse }
     * 
     */
    public QueryLevelLogListResponse createQueryLevelLogListResponse() {
        return new QueryLevelLogListResponse();
    }

    /**
     * Create an instance of {@link SelectAccountByIdAndTypeResponse }
     * 
     */
    public SelectAccountByIdAndTypeResponse createSelectAccountByIdAndTypeResponse() {
        return new SelectAccountByIdAndTypeResponse();
    }

    /**
     * Create an instance of {@link CoinExchangeResponse }
     * 
     */
    public CoinExchangeResponse createCoinExchangeResponse() {
        return new CoinExchangeResponse();
    }

    /**
     * Create an instance of {@link SelectAccountLog2ByIdAndType }
     * 
     */
    public SelectAccountLog2ByIdAndType createSelectAccountLog2ByIdAndType() {
        return new SelectAccountLog2ByIdAndType();
    }

    /**
     * Create an instance of {@link SelectAccountLog2ByIdAndTypeResponse }
     * 
     */
    public SelectAccountLog2ByIdAndTypeResponse createSelectAccountLog2ByIdAndTypeResponse() {
        return new SelectAccountLog2ByIdAndTypeResponse();
    }

    /**
     * Create an instance of {@link SelectIsUserOverdue }
     * 
     */
    public SelectIsUserOverdue createSelectIsUserOverdue() {
        return new SelectIsUserOverdue();
    }

    /**
     * Create an instance of {@link SelectUserAccountAndCoupon }
     * 
     */
    public SelectUserAccountAndCoupon createSelectUserAccountAndCoupon() {
        return new SelectUserAccountAndCoupon();
    }

    /**
     * Create an instance of {@link QueryLevelLogList }
     * 
     */
    public QueryLevelLogList createQueryLevelLogList() {
        return new QueryLevelLogList();
    }

    /**
     * Create an instance of {@link SelectMoneyRecordResponse }
     * 
     */
    public SelectMoneyRecordResponse createSelectMoneyRecordResponse() {
        return new SelectMoneyRecordResponse();
    }

    /**
     * Create an instance of {@link GetAccountSumResponse }
     * 
     */
    public GetAccountSumResponse createGetAccountSumResponse() {
        return new GetAccountSumResponse();
    }

    /**
     * Create an instance of {@link SelectOverdueRepaymentByUserId }
     * 
     */
    public SelectOverdueRepaymentByUserId createSelectOverdueRepaymentByUserId() {
        return new SelectOverdueRepaymentByUserId();
    }

    /**
     * Create an instance of {@link SelectUserAccountAndCouponResponse }
     * 
     */
    public SelectUserAccountAndCouponResponse createSelectUserAccountAndCouponResponse() {
        return new SelectUserAccountAndCouponResponse();
    }

    /**
     * Create an instance of {@link SelectMoneyRecord }
     * 
     */
    public SelectMoneyRecord createSelectMoneyRecord() {
        return new SelectMoneyRecord();
    }

    /**
     * Create an instance of {@link IncreaseAccountUsable }
     * 
     */
    public IncreaseAccountUsable createIncreaseAccountUsable() {
        return new IncreaseAccountUsable();
    }

    /**
     * Create an instance of {@link SelectPageAccountMoneyRecord }
     * 
     */
    public SelectPageAccountMoneyRecord createSelectPageAccountMoneyRecord() {
        return new SelectPageAccountMoneyRecord();
    }

    /**
     * Create an instance of {@link SelectOverdueRepaymentByUserIdResponse }
     * 
     */
    public SelectOverdueRepaymentByUserIdResponse createSelectOverdueRepaymentByUserIdResponse() {
        return new SelectOverdueRepaymentByUserIdResponse();
    }

    /**
     * Create an instance of {@link SaveAccountLog }
     * 
     */
    public SaveAccountLog createSaveAccountLog() {
        return new SaveAccountLog();
    }

    /**
     * Create an instance of {@link SelectAccountLogByUserIdResponse }
     * 
     */
    public SelectAccountLogByUserIdResponse createSelectAccountLogByUserIdResponse() {
        return new SelectAccountLogByUserIdResponse();
    }

    /**
     * Create an instance of {@link SelectIsUserOverdueResponse }
     * 
     */
    public SelectIsUserOverdueResponse createSelectIsUserOverdueResponse() {
        return new SelectIsUserOverdueResponse();
    }

    /**
     * Create an instance of {@link SelectSixAccountLogByUserIdResponse }
     * 
     */
    public SelectSixAccountLogByUserIdResponse createSelectSixAccountLogByUserIdResponse() {
        return new SelectSixAccountLogByUserIdResponse();
    }

    /**
     * Create an instance of {@link SelectPageAccountMoneyRecordResponse }
     * 
     */
    public SelectPageAccountMoneyRecordResponse createSelectPageAccountMoneyRecordResponse() {
        return new SelectPageAccountMoneyRecordResponse();
    }

    /**
     * Create an instance of {@link GetAccountLogSumResponse }
     * 
     */
    public GetAccountLogSumResponse createGetAccountLogSumResponse() {
        return new GetAccountLogSumResponse();
    }

    /**
     * Create an instance of {@link GetAccountLogSum }
     * 
     */
    public GetAccountLogSum createGetAccountLogSum() {
        return new GetAccountLogSum();
    }

    /**
     * Create an instance of {@link FreezeAccount }
     * 
     */
    public FreezeAccount createFreezeAccount() {
        return new FreezeAccount();
    }

    /**
     * Create an instance of {@link CounttMoneyRecord }
     * 
     */
    public CounttMoneyRecord createCounttMoneyRecord() {
        return new CounttMoneyRecord();
    }

    /**
     * Create an instance of {@link FreezeAccountResponse }
     * 
     */
    public FreezeAccountResponse createFreezeAccountResponse() {
        return new FreezeAccountResponse();
    }

    /**
     * Create an instance of {@link CoinExchange }
     * 
     */
    public CoinExchange createCoinExchange() {
        return new CoinExchange();
    }

    /**
     * Create an instance of {@link GetAccountSum }
     * 
     */
    public GetAccountSum createGetAccountSum() {
        return new GetAccountSum();
    }

    /**
     * Create an instance of {@link SaveAccountLogResponse }
     * 
     */
    public SaveAccountLogResponse createSaveAccountLogResponse() {
        return new SaveAccountLogResponse();
    }

    /**
     * Create an instance of {@link SelectAccountByIdAndType }
     * 
     */
    public SelectAccountByIdAndType createSelectAccountByIdAndType() {
        return new SelectAccountByIdAndType();
    }

    /**
     * Create an instance of {@link IncreaseAccountUsableResponse }
     * 
     */
    public IncreaseAccountUsableResponse createIncreaseAccountUsableResponse() {
        return new IncreaseAccountUsableResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CoinExchangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "coinExchangeResponse")
    public JAXBElement<CoinExchangeResponse> createCoinExchangeResponse(CoinExchangeResponse value) {
        return new JAXBElement<CoinExchangeResponse>(_CoinExchangeResponse_QNAME, CoinExchangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CounttMoneyRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "counttMoneyRecordResponse")
    public JAXBElement<CounttMoneyRecordResponse> createCounttMoneyRecordResponse(CounttMoneyRecordResponse value) {
        return new JAXBElement<CounttMoneyRecordResponse>(_CounttMoneyRecordResponse_QNAME, CounttMoneyRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountLogByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountLogByUserId")
    public JAXBElement<SelectAccountLogByUserId> createSelectAccountLogByUserId(SelectAccountLogByUserId value) {
        return new JAXBElement<SelectAccountLogByUserId>(_SelectAccountLogByUserId_QNAME, SelectAccountLogByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountByIdAndTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountByIdAndTypeResponse")
    public JAXBElement<SelectAccountByIdAndTypeResponse> createSelectAccountByIdAndTypeResponse(SelectAccountByIdAndTypeResponse value) {
        return new JAXBElement<SelectAccountByIdAndTypeResponse>(_SelectAccountByIdAndTypeResponse_QNAME, SelectAccountByIdAndTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLevelLogListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "queryLevelLogListResponse")
    public JAXBElement<QueryLevelLogListResponse> createQueryLevelLogListResponse(QueryLevelLogListResponse value) {
        return new JAXBElement<QueryLevelLogListResponse>(_QueryLevelLogListResponse_QNAME, QueryLevelLogListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectSixAccountLogByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectSixAccountLogByUserId")
    public JAXBElement<SelectSixAccountLogByUserId> createSelectSixAccountLogByUserId(SelectSixAccountLogByUserId value) {
        return new JAXBElement<SelectSixAccountLogByUserId>(_SelectSixAccountLogByUserId_QNAME, SelectSixAccountLogByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectIsUserOverdue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectIsUserOverdue")
    public JAXBElement<SelectIsUserOverdue> createSelectIsUserOverdue(SelectIsUserOverdue value) {
        return new JAXBElement<SelectIsUserOverdue>(_SelectIsUserOverdue_QNAME, SelectIsUserOverdue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectUserAccountAndCoupon }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectUserAccountAndCoupon")
    public JAXBElement<SelectUserAccountAndCoupon> createSelectUserAccountAndCoupon(SelectUserAccountAndCoupon value) {
        return new JAXBElement<SelectUserAccountAndCoupon>(_SelectUserAccountAndCoupon_QNAME, SelectUserAccountAndCoupon.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountLog2ByIdAndType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountLog2ByIdAndType")
    public JAXBElement<SelectAccountLog2ByIdAndType> createSelectAccountLog2ByIdAndType(SelectAccountLog2ByIdAndType value) {
        return new JAXBElement<SelectAccountLog2ByIdAndType>(_SelectAccountLog2ByIdAndType_QNAME, SelectAccountLog2ByIdAndType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountLog2ByIdAndTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountLog2ByIdAndTypeResponse")
    public JAXBElement<SelectAccountLog2ByIdAndTypeResponse> createSelectAccountLog2ByIdAndTypeResponse(SelectAccountLog2ByIdAndTypeResponse value) {
        return new JAXBElement<SelectAccountLog2ByIdAndTypeResponse>(_SelectAccountLog2ByIdAndTypeResponse_QNAME, SelectAccountLog2ByIdAndTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getAccountSumResponse")
    public JAXBElement<GetAccountSumResponse> createGetAccountSumResponse(GetAccountSumResponse value) {
        return new JAXBElement<GetAccountSumResponse>(_GetAccountSumResponse_QNAME, GetAccountSumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLevelLogList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "queryLevelLogList")
    public JAXBElement<QueryLevelLogList> createQueryLevelLogList(QueryLevelLogList value) {
        return new JAXBElement<QueryLevelLogList>(_QueryLevelLogList_QNAME, QueryLevelLogList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectMoneyRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectMoneyRecordResponse")
    public JAXBElement<SelectMoneyRecordResponse> createSelectMoneyRecordResponse(SelectMoneyRecordResponse value) {
        return new JAXBElement<SelectMoneyRecordResponse>(_SelectMoneyRecordResponse_QNAME, SelectMoneyRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IncreaseAccountUsable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "increaseAccountUsable")
    public JAXBElement<IncreaseAccountUsable> createIncreaseAccountUsable(IncreaseAccountUsable value) {
        return new JAXBElement<IncreaseAccountUsable>(_IncreaseAccountUsable_QNAME, IncreaseAccountUsable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectPageAccountMoneyRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectPageAccountMoneyRecord")
    public JAXBElement<SelectPageAccountMoneyRecord> createSelectPageAccountMoneyRecord(SelectPageAccountMoneyRecord value) {
        return new JAXBElement<SelectPageAccountMoneyRecord>(_SelectPageAccountMoneyRecord_QNAME, SelectPageAccountMoneyRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectOverdueRepaymentByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectOverdueRepaymentByUserIdResponse")
    public JAXBElement<SelectOverdueRepaymentByUserIdResponse> createSelectOverdueRepaymentByUserIdResponse(SelectOverdueRepaymentByUserIdResponse value) {
        return new JAXBElement<SelectOverdueRepaymentByUserIdResponse>(_SelectOverdueRepaymentByUserIdResponse_QNAME, SelectOverdueRepaymentByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectOverdueRepaymentByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectOverdueRepaymentByUserId")
    public JAXBElement<SelectOverdueRepaymentByUserId> createSelectOverdueRepaymentByUserId(SelectOverdueRepaymentByUserId value) {
        return new JAXBElement<SelectOverdueRepaymentByUserId>(_SelectOverdueRepaymentByUserId_QNAME, SelectOverdueRepaymentByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectUserAccountAndCouponResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectUserAccountAndCouponResponse")
    public JAXBElement<SelectUserAccountAndCouponResponse> createSelectUserAccountAndCouponResponse(SelectUserAccountAndCouponResponse value) {
        return new JAXBElement<SelectUserAccountAndCouponResponse>(_SelectUserAccountAndCouponResponse_QNAME, SelectUserAccountAndCouponResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectMoneyRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectMoneyRecord")
    public JAXBElement<SelectMoneyRecord> createSelectMoneyRecord(SelectMoneyRecord value) {
        return new JAXBElement<SelectMoneyRecord>(_SelectMoneyRecord_QNAME, SelectMoneyRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectIsUserOverdueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectIsUserOverdueResponse")
    public JAXBElement<SelectIsUserOverdueResponse> createSelectIsUserOverdueResponse(SelectIsUserOverdueResponse value) {
        return new JAXBElement<SelectIsUserOverdueResponse>(_SelectIsUserOverdueResponse_QNAME, SelectIsUserOverdueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectSixAccountLogByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectSixAccountLogByUserIdResponse")
    public JAXBElement<SelectSixAccountLogByUserIdResponse> createSelectSixAccountLogByUserIdResponse(SelectSixAccountLogByUserIdResponse value) {
        return new JAXBElement<SelectSixAccountLogByUserIdResponse>(_SelectSixAccountLogByUserIdResponse_QNAME, SelectSixAccountLogByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectPageAccountMoneyRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectPageAccountMoneyRecordResponse")
    public JAXBElement<SelectPageAccountMoneyRecordResponse> createSelectPageAccountMoneyRecordResponse(SelectPageAccountMoneyRecordResponse value) {
        return new JAXBElement<SelectPageAccountMoneyRecordResponse>(_SelectPageAccountMoneyRecordResponse_QNAME, SelectPageAccountMoneyRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountLogSumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getAccountLogSumResponse")
    public JAXBElement<GetAccountLogSumResponse> createGetAccountLogSumResponse(GetAccountLogSumResponse value) {
        return new JAXBElement<GetAccountLogSumResponse>(_GetAccountLogSumResponse_QNAME, GetAccountLogSumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountLogSum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getAccountLogSum")
    public JAXBElement<GetAccountLogSum> createGetAccountLogSum(GetAccountLogSum value) {
        return new JAXBElement<GetAccountLogSum>(_GetAccountLogSum_QNAME, GetAccountLogSum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveAccountLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "saveAccountLog")
    public JAXBElement<SaveAccountLog> createSaveAccountLog(SaveAccountLog value) {
        return new JAXBElement<SaveAccountLog>(_SaveAccountLog_QNAME, SaveAccountLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountLogByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountLogByUserIdResponse")
    public JAXBElement<SelectAccountLogByUserIdResponse> createSelectAccountLogByUserIdResponse(SelectAccountLogByUserIdResponse value) {
        return new JAXBElement<SelectAccountLogByUserIdResponse>(_SelectAccountLogByUserIdResponse_QNAME, SelectAccountLogByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CounttMoneyRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "counttMoneyRecord")
    public JAXBElement<CounttMoneyRecord> createCounttMoneyRecord(CounttMoneyRecord value) {
        return new JAXBElement<CounttMoneyRecord>(_CounttMoneyRecord_QNAME, CounttMoneyRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FreezeAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "freezeAccount")
    public JAXBElement<FreezeAccount> createFreezeAccount(FreezeAccount value) {
        return new JAXBElement<FreezeAccount>(_FreezeAccount_QNAME, FreezeAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CoinExchange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "coinExchange")
    public JAXBElement<CoinExchange> createCoinExchange(CoinExchange value) {
        return new JAXBElement<CoinExchange>(_CoinExchange_QNAME, CoinExchange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FreezeAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "freezeAccountResponse")
    public JAXBElement<FreezeAccountResponse> createFreezeAccountResponse(FreezeAccountResponse value) {
        return new JAXBElement<FreezeAccountResponse>(_FreezeAccountResponse_QNAME, FreezeAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountByIdAndType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountByIdAndType")
    public JAXBElement<SelectAccountByIdAndType> createSelectAccountByIdAndType(SelectAccountByIdAndType value) {
        return new JAXBElement<SelectAccountByIdAndType>(_SelectAccountByIdAndType_QNAME, SelectAccountByIdAndType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IncreaseAccountUsableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "increaseAccountUsableResponse")
    public JAXBElement<IncreaseAccountUsableResponse> createIncreaseAccountUsableResponse(IncreaseAccountUsableResponse value) {
        return new JAXBElement<IncreaseAccountUsableResponse>(_IncreaseAccountUsableResponse_QNAME, IncreaseAccountUsableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveAccountLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "saveAccountLogResponse")
    public JAXBElement<SaveAccountLogResponse> createSaveAccountLogResponse(SaveAccountLogResponse value) {
        return new JAXBElement<SaveAccountLogResponse>(_SaveAccountLogResponse_QNAME, SaveAccountLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getAccountSum")
    public JAXBElement<GetAccountSum> createGetAccountSum(GetAccountSum value) {
        return new JAXBElement<GetAccountSum>(_GetAccountSum_QNAME, GetAccountSum.class, null, value);
    }

}
