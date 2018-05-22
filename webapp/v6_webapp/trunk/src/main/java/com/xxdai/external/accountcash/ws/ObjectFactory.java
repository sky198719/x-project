
package com.xxdai.external.accountcash.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.accountcash.ws package. 
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

    private final static QName _GetDrawMoneyCountMonthly_QNAME = new QName("http://webservice.account.xxdai.com/", "getDrawMoneyCountMonthly");
    private final static QName _GetAccountCost_QNAME = new QName("http://webservice.account.xxdai.com/", "getAccountCost");
    private final static QName _SelectAccountCashByIdResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountCashByIdResponse");
    private final static QName _SelectRemitAccountCashWithPaging_QNAME = new QName("http://webservice.account.xxdai.com/", "selectRemitAccountCashWithPaging");
    private final static QName _RemitMultiAccountCash_QNAME = new QName("http://webservice.account.xxdai.com/", "remitMultiAccountCash");
    private final static QName _CanGetCashResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "canGetCashResponse");
    private final static QName _SelectApprovedAccountCashs_QNAME = new QName("http://webservice.account.xxdai.com/", "selectApprovedAccountCashs");
    private final static QName _CashAccountResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "cashAccountResponse");
    private final static QName _RemitAccountCash_QNAME = new QName("http://webservice.account.xxdai.com/", "remitAccountCash");
    private final static QName _GetSumcashAmountResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "getSumcashAmountResponse");
    private final static QName _SelectVerifyAccountCashWithPaging_QNAME = new QName("http://webservice.account.xxdai.com/", "selectVerifyAccountCashWithPaging");
    private final static QName _VerifyMultiAccountCash_QNAME = new QName("http://webservice.account.xxdai.com/", "verifyMultiAccountCash");
    private final static QName _SelectVerifyAccountCashWithPagingResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectVerifyAccountCashWithPagingResponse");
    private final static QName _GetMaxcashAmount_QNAME = new QName("http://webservice.account.xxdai.com/", "getMaxcashAmount");
    private final static QName _RemitAccountCashResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "remitAccountCashResponse");
    private final static QName _SelectAccountCashByUserResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountCashByUserResponse");
    private final static QName _SelectFailedAccountCashWithPaging_QNAME = new QName("http://webservice.account.xxdai.com/", "selectFailedAccountCashWithPaging");
    private final static QName _VerifyAccountCash_QNAME = new QName("http://webservice.account.xxdai.com/", "verifyAccountCash");
    private final static QName _RefundCash_QNAME = new QName("http://webservice.account.xxdai.com/", "refundCash");
    private final static QName _SelectAccountCashById_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountCashById");
    private final static QName _GetMaxcashAmountResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "getMaxcashAmountResponse");
    private final static QName _SelectApprovedAccountCashsResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectApprovedAccountCashsResponse");
    private final static QName _SelectFailedAccountCashWithPagingResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectFailedAccountCashWithPagingResponse");
    private final static QName _SelectRemitAccountCashWithPagingResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectRemitAccountCashWithPagingResponse");
    private final static QName _RefundCashResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "refundCashResponse");
    private final static QName _SelectRefundCashById_QNAME = new QName("http://webservice.account.xxdai.com/", "selectRefundCashById");
    private final static QName _CanGetCash_QNAME = new QName("http://webservice.account.xxdai.com/", "canGetCash");
    private final static QName _GetSumcashAmount_QNAME = new QName("http://webservice.account.xxdai.com/", "getSumcashAmount");
    private final static QName _VerifyMultiAccountCashResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "verifyMultiAccountCashResponse");
    private final static QName _SelectAccountCashByUser_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountCashByUser");
    private final static QName _RemitMultiAccountCashResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "remitMultiAccountCashResponse");
    private final static QName _SelectRefundCashByIdResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectRefundCashByIdResponse");
    private final static QName _GetAccountCostResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "getAccountCostResponse");
    private final static QName _VerifyAccountCashResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "verifyAccountCashResponse");
    private final static QName _CashAccount_QNAME = new QName("http://webservice.account.xxdai.com/", "cashAccount");
    private final static QName _GetDrawMoneyCountMonthlyResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "getDrawMoneyCountMonthlyResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.accountcash.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectRemitAccountCashWithPaging }
     * 
     */
    public SelectRemitAccountCashWithPaging createSelectRemitAccountCashWithPaging() {
        return new SelectRemitAccountCashWithPaging();
    }

    /**
     * Create an instance of {@link SelectAccountCashByIdResponse }
     * 
     */
    public SelectAccountCashByIdResponse createSelectAccountCashByIdResponse() {
        return new SelectAccountCashByIdResponse();
    }

    /**
     * Create an instance of {@link GetDrawMoneyCountMonthly }
     * 
     */
    public GetDrawMoneyCountMonthly createGetDrawMoneyCountMonthly() {
        return new GetDrawMoneyCountMonthly();
    }

    /**
     * Create an instance of {@link GetAccountCost }
     * 
     */
    public GetAccountCost createGetAccountCost() {
        return new GetAccountCost();
    }

    /**
     * Create an instance of {@link CanGetCashResponse }
     * 
     */
    public CanGetCashResponse createCanGetCashResponse() {
        return new CanGetCashResponse();
    }

    /**
     * Create an instance of {@link RemitMultiAccountCash }
     * 
     */
    public RemitMultiAccountCash createRemitMultiAccountCash() {
        return new RemitMultiAccountCash();
    }

    /**
     * Create an instance of {@link VerifyMultiAccountCash }
     * 
     */
    public VerifyMultiAccountCash createVerifyMultiAccountCash() {
        return new VerifyMultiAccountCash();
    }

    /**
     * Create an instance of {@link SelectVerifyAccountCashWithPaging }
     * 
     */
    public SelectVerifyAccountCashWithPaging createSelectVerifyAccountCashWithPaging() {
        return new SelectVerifyAccountCashWithPaging();
    }

    /**
     * Create an instance of {@link SelectVerifyAccountCashWithPagingResponse }
     * 
     */
    public SelectVerifyAccountCashWithPagingResponse createSelectVerifyAccountCashWithPagingResponse() {
        return new SelectVerifyAccountCashWithPagingResponse();
    }

    /**
     * Create an instance of {@link GetMaxcashAmount }
     * 
     */
    public GetMaxcashAmount createGetMaxcashAmount() {
        return new GetMaxcashAmount();
    }

    /**
     * Create an instance of {@link SelectApprovedAccountCashs }
     * 
     */
    public SelectApprovedAccountCashs createSelectApprovedAccountCashs() {
        return new SelectApprovedAccountCashs();
    }

    /**
     * Create an instance of {@link CashAccountResponse }
     * 
     */
    public CashAccountResponse createCashAccountResponse() {
        return new CashAccountResponse();
    }

    /**
     * Create an instance of {@link RemitAccountCash }
     * 
     */
    public RemitAccountCash createRemitAccountCash() {
        return new RemitAccountCash();
    }

    /**
     * Create an instance of {@link GetSumcashAmountResponse }
     * 
     */
    public GetSumcashAmountResponse createGetSumcashAmountResponse() {
        return new GetSumcashAmountResponse();
    }

    /**
     * Create an instance of {@link SelectAccountCashByUserResponse }
     * 
     */
    public SelectAccountCashByUserResponse createSelectAccountCashByUserResponse() {
        return new SelectAccountCashByUserResponse();
    }

    /**
     * Create an instance of {@link RemitAccountCashResponse }
     * 
     */
    public RemitAccountCashResponse createRemitAccountCashResponse() {
        return new RemitAccountCashResponse();
    }

    /**
     * Create an instance of {@link GetMaxcashAmountResponse }
     * 
     */
    public GetMaxcashAmountResponse createGetMaxcashAmountResponse() {
        return new GetMaxcashAmountResponse();
    }

    /**
     * Create an instance of {@link SelectApprovedAccountCashsResponse }
     * 
     */
    public SelectApprovedAccountCashsResponse createSelectApprovedAccountCashsResponse() {
        return new SelectApprovedAccountCashsResponse();
    }

    /**
     * Create an instance of {@link SelectFailedAccountCashWithPaging }
     * 
     */
    public SelectFailedAccountCashWithPaging createSelectFailedAccountCashWithPaging() {
        return new SelectFailedAccountCashWithPaging();
    }

    /**
     * Create an instance of {@link VerifyAccountCash }
     * 
     */
    public VerifyAccountCash createVerifyAccountCash() {
        return new VerifyAccountCash();
    }

    /**
     * Create an instance of {@link RefundCash }
     * 
     */
    public RefundCash createRefundCash() {
        return new RefundCash();
    }

    /**
     * Create an instance of {@link SelectAccountCashById }
     * 
     */
    public SelectAccountCashById createSelectAccountCashById() {
        return new SelectAccountCashById();
    }

    /**
     * Create an instance of {@link SelectRemitAccountCashWithPagingResponse }
     * 
     */
    public SelectRemitAccountCashWithPagingResponse createSelectRemitAccountCashWithPagingResponse() {
        return new SelectRemitAccountCashWithPagingResponse();
    }

    /**
     * Create an instance of {@link SelectFailedAccountCashWithPagingResponse }
     * 
     */
    public SelectFailedAccountCashWithPagingResponse createSelectFailedAccountCashWithPagingResponse() {
        return new SelectFailedAccountCashWithPagingResponse();
    }

    /**
     * Create an instance of {@link SelectRefundCashById }
     * 
     */
    public SelectRefundCashById createSelectRefundCashById() {
        return new SelectRefundCashById();
    }

    /**
     * Create an instance of {@link VerifyMultiAccountCashResponse }
     * 
     */
    public VerifyMultiAccountCashResponse createVerifyMultiAccountCashResponse() {
        return new VerifyMultiAccountCashResponse();
    }

    /**
     * Create an instance of {@link GetSumcashAmount }
     * 
     */
    public GetSumcashAmount createGetSumcashAmount() {
        return new GetSumcashAmount();
    }

    /**
     * Create an instance of {@link CanGetCash }
     * 
     */
    public CanGetCash createCanGetCash() {
        return new CanGetCash();
    }

    /**
     * Create an instance of {@link SelectRefundCashByIdResponse }
     * 
     */
    public SelectRefundCashByIdResponse createSelectRefundCashByIdResponse() {
        return new SelectRefundCashByIdResponse();
    }

    /**
     * Create an instance of {@link RemitMultiAccountCashResponse }
     * 
     */
    public RemitMultiAccountCashResponse createRemitMultiAccountCashResponse() {
        return new RemitMultiAccountCashResponse();
    }

    /**
     * Create an instance of {@link SelectAccountCashByUser }
     * 
     */
    public SelectAccountCashByUser createSelectAccountCashByUser() {
        return new SelectAccountCashByUser();
    }

    /**
     * Create an instance of {@link RefundCashResponse }
     * 
     */
    public RefundCashResponse createRefundCashResponse() {
        return new RefundCashResponse();
    }

    /**
     * Create an instance of {@link GetDrawMoneyCountMonthlyResponse }
     * 
     */
    public GetDrawMoneyCountMonthlyResponse createGetDrawMoneyCountMonthlyResponse() {
        return new GetDrawMoneyCountMonthlyResponse();
    }

    /**
     * Create an instance of {@link GetAccountCostResponse }
     * 
     */
    public GetAccountCostResponse createGetAccountCostResponse() {
        return new GetAccountCostResponse();
    }

    /**
     * Create an instance of {@link VerifyAccountCashResponse }
     * 
     */
    public VerifyAccountCashResponse createVerifyAccountCashResponse() {
        return new VerifyAccountCashResponse();
    }

    /**
     * Create an instance of {@link CashAccount }
     * 
     */
    public CashAccount createCashAccount() {
        return new CashAccount();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDrawMoneyCountMonthly }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getDrawMoneyCountMonthly")
    public JAXBElement<GetDrawMoneyCountMonthly> createGetDrawMoneyCountMonthly(GetDrawMoneyCountMonthly value) {
        return new JAXBElement<GetDrawMoneyCountMonthly>(_GetDrawMoneyCountMonthly_QNAME, GetDrawMoneyCountMonthly.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountCost }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getAccountCost")
    public JAXBElement<GetAccountCost> createGetAccountCost(GetAccountCost value) {
        return new JAXBElement<GetAccountCost>(_GetAccountCost_QNAME, GetAccountCost.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountCashByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountCashByIdResponse")
    public JAXBElement<SelectAccountCashByIdResponse> createSelectAccountCashByIdResponse(SelectAccountCashByIdResponse value) {
        return new JAXBElement<SelectAccountCashByIdResponse>(_SelectAccountCashByIdResponse_QNAME, SelectAccountCashByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectRemitAccountCashWithPaging }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectRemitAccountCashWithPaging")
    public JAXBElement<SelectRemitAccountCashWithPaging> createSelectRemitAccountCashWithPaging(SelectRemitAccountCashWithPaging value) {
        return new JAXBElement<SelectRemitAccountCashWithPaging>(_SelectRemitAccountCashWithPaging_QNAME, SelectRemitAccountCashWithPaging.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemitMultiAccountCash }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "remitMultiAccountCash")
    public JAXBElement<RemitMultiAccountCash> createRemitMultiAccountCash(RemitMultiAccountCash value) {
        return new JAXBElement<RemitMultiAccountCash>(_RemitMultiAccountCash_QNAME, RemitMultiAccountCash.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CanGetCashResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "canGetCashResponse")
    public JAXBElement<CanGetCashResponse> createCanGetCashResponse(CanGetCashResponse value) {
        return new JAXBElement<CanGetCashResponse>(_CanGetCashResponse_QNAME, CanGetCashResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectApprovedAccountCashs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectApprovedAccountCashs")
    public JAXBElement<SelectApprovedAccountCashs> createSelectApprovedAccountCashs(SelectApprovedAccountCashs value) {
        return new JAXBElement<SelectApprovedAccountCashs>(_SelectApprovedAccountCashs_QNAME, SelectApprovedAccountCashs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CashAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "cashAccountResponse")
    public JAXBElement<CashAccountResponse> createCashAccountResponse(CashAccountResponse value) {
        return new JAXBElement<CashAccountResponse>(_CashAccountResponse_QNAME, CashAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemitAccountCash }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "remitAccountCash")
    public JAXBElement<RemitAccountCash> createRemitAccountCash(RemitAccountCash value) {
        return new JAXBElement<RemitAccountCash>(_RemitAccountCash_QNAME, RemitAccountCash.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSumcashAmountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getSumcashAmountResponse")
    public JAXBElement<GetSumcashAmountResponse> createGetSumcashAmountResponse(GetSumcashAmountResponse value) {
        return new JAXBElement<GetSumcashAmountResponse>(_GetSumcashAmountResponse_QNAME, GetSumcashAmountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectVerifyAccountCashWithPaging }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectVerifyAccountCashWithPaging")
    public JAXBElement<SelectVerifyAccountCashWithPaging> createSelectVerifyAccountCashWithPaging(SelectVerifyAccountCashWithPaging value) {
        return new JAXBElement<SelectVerifyAccountCashWithPaging>(_SelectVerifyAccountCashWithPaging_QNAME, SelectVerifyAccountCashWithPaging.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyMultiAccountCash }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "verifyMultiAccountCash")
    public JAXBElement<VerifyMultiAccountCash> createVerifyMultiAccountCash(VerifyMultiAccountCash value) {
        return new JAXBElement<VerifyMultiAccountCash>(_VerifyMultiAccountCash_QNAME, VerifyMultiAccountCash.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectVerifyAccountCashWithPagingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectVerifyAccountCashWithPagingResponse")
    public JAXBElement<SelectVerifyAccountCashWithPagingResponse> createSelectVerifyAccountCashWithPagingResponse(SelectVerifyAccountCashWithPagingResponse value) {
        return new JAXBElement<SelectVerifyAccountCashWithPagingResponse>(_SelectVerifyAccountCashWithPagingResponse_QNAME, SelectVerifyAccountCashWithPagingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMaxcashAmount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getMaxcashAmount")
    public JAXBElement<GetMaxcashAmount> createGetMaxcashAmount(GetMaxcashAmount value) {
        return new JAXBElement<GetMaxcashAmount>(_GetMaxcashAmount_QNAME, GetMaxcashAmount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemitAccountCashResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "remitAccountCashResponse")
    public JAXBElement<RemitAccountCashResponse> createRemitAccountCashResponse(RemitAccountCashResponse value) {
        return new JAXBElement<RemitAccountCashResponse>(_RemitAccountCashResponse_QNAME, RemitAccountCashResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountCashByUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountCashByUserResponse")
    public JAXBElement<SelectAccountCashByUserResponse> createSelectAccountCashByUserResponse(SelectAccountCashByUserResponse value) {
        return new JAXBElement<SelectAccountCashByUserResponse>(_SelectAccountCashByUserResponse_QNAME, SelectAccountCashByUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectFailedAccountCashWithPaging }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectFailedAccountCashWithPaging")
    public JAXBElement<SelectFailedAccountCashWithPaging> createSelectFailedAccountCashWithPaging(SelectFailedAccountCashWithPaging value) {
        return new JAXBElement<SelectFailedAccountCashWithPaging>(_SelectFailedAccountCashWithPaging_QNAME, SelectFailedAccountCashWithPaging.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyAccountCash }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "verifyAccountCash")
    public JAXBElement<VerifyAccountCash> createVerifyAccountCash(VerifyAccountCash value) {
        return new JAXBElement<VerifyAccountCash>(_VerifyAccountCash_QNAME, VerifyAccountCash.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefundCash }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "refundCash")
    public JAXBElement<RefundCash> createRefundCash(RefundCash value) {
        return new JAXBElement<RefundCash>(_RefundCash_QNAME, RefundCash.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountCashById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountCashById")
    public JAXBElement<SelectAccountCashById> createSelectAccountCashById(SelectAccountCashById value) {
        return new JAXBElement<SelectAccountCashById>(_SelectAccountCashById_QNAME, SelectAccountCashById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMaxcashAmountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getMaxcashAmountResponse")
    public JAXBElement<GetMaxcashAmountResponse> createGetMaxcashAmountResponse(GetMaxcashAmountResponse value) {
        return new JAXBElement<GetMaxcashAmountResponse>(_GetMaxcashAmountResponse_QNAME, GetMaxcashAmountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectApprovedAccountCashsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectApprovedAccountCashsResponse")
    public JAXBElement<SelectApprovedAccountCashsResponse> createSelectApprovedAccountCashsResponse(SelectApprovedAccountCashsResponse value) {
        return new JAXBElement<SelectApprovedAccountCashsResponse>(_SelectApprovedAccountCashsResponse_QNAME, SelectApprovedAccountCashsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectFailedAccountCashWithPagingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectFailedAccountCashWithPagingResponse")
    public JAXBElement<SelectFailedAccountCashWithPagingResponse> createSelectFailedAccountCashWithPagingResponse(SelectFailedAccountCashWithPagingResponse value) {
        return new JAXBElement<SelectFailedAccountCashWithPagingResponse>(_SelectFailedAccountCashWithPagingResponse_QNAME, SelectFailedAccountCashWithPagingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectRemitAccountCashWithPagingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectRemitAccountCashWithPagingResponse")
    public JAXBElement<SelectRemitAccountCashWithPagingResponse> createSelectRemitAccountCashWithPagingResponse(SelectRemitAccountCashWithPagingResponse value) {
        return new JAXBElement<SelectRemitAccountCashWithPagingResponse>(_SelectRemitAccountCashWithPagingResponse_QNAME, SelectRemitAccountCashWithPagingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefundCashResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "refundCashResponse")
    public JAXBElement<RefundCashResponse> createRefundCashResponse(RefundCashResponse value) {
        return new JAXBElement<RefundCashResponse>(_RefundCashResponse_QNAME, RefundCashResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectRefundCashById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectRefundCashById")
    public JAXBElement<SelectRefundCashById> createSelectRefundCashById(SelectRefundCashById value) {
        return new JAXBElement<SelectRefundCashById>(_SelectRefundCashById_QNAME, SelectRefundCashById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CanGetCash }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "canGetCash")
    public JAXBElement<CanGetCash> createCanGetCash(CanGetCash value) {
        return new JAXBElement<CanGetCash>(_CanGetCash_QNAME, CanGetCash.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSumcashAmount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getSumcashAmount")
    public JAXBElement<GetSumcashAmount> createGetSumcashAmount(GetSumcashAmount value) {
        return new JAXBElement<GetSumcashAmount>(_GetSumcashAmount_QNAME, GetSumcashAmount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyMultiAccountCashResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "verifyMultiAccountCashResponse")
    public JAXBElement<VerifyMultiAccountCashResponse> createVerifyMultiAccountCashResponse(VerifyMultiAccountCashResponse value) {
        return new JAXBElement<VerifyMultiAccountCashResponse>(_VerifyMultiAccountCashResponse_QNAME, VerifyMultiAccountCashResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAccountCashByUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountCashByUser")
    public JAXBElement<SelectAccountCashByUser> createSelectAccountCashByUser(SelectAccountCashByUser value) {
        return new JAXBElement<SelectAccountCashByUser>(_SelectAccountCashByUser_QNAME, SelectAccountCashByUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemitMultiAccountCashResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "remitMultiAccountCashResponse")
    public JAXBElement<RemitMultiAccountCashResponse> createRemitMultiAccountCashResponse(RemitMultiAccountCashResponse value) {
        return new JAXBElement<RemitMultiAccountCashResponse>(_RemitMultiAccountCashResponse_QNAME, RemitMultiAccountCashResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectRefundCashByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectRefundCashByIdResponse")
    public JAXBElement<SelectRefundCashByIdResponse> createSelectRefundCashByIdResponse(SelectRefundCashByIdResponse value) {
        return new JAXBElement<SelectRefundCashByIdResponse>(_SelectRefundCashByIdResponse_QNAME, SelectRefundCashByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountCostResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getAccountCostResponse")
    public JAXBElement<GetAccountCostResponse> createGetAccountCostResponse(GetAccountCostResponse value) {
        return new JAXBElement<GetAccountCostResponse>(_GetAccountCostResponse_QNAME, GetAccountCostResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyAccountCashResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "verifyAccountCashResponse")
    public JAXBElement<VerifyAccountCashResponse> createVerifyAccountCashResponse(VerifyAccountCashResponse value) {
        return new JAXBElement<VerifyAccountCashResponse>(_VerifyAccountCashResponse_QNAME, VerifyAccountCashResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CashAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "cashAccount")
    public JAXBElement<CashAccount> createCashAccount(CashAccount value) {
        return new JAXBElement<CashAccount>(_CashAccount_QNAME, CashAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDrawMoneyCountMonthlyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getDrawMoneyCountMonthlyResponse")
    public JAXBElement<GetDrawMoneyCountMonthlyResponse> createGetDrawMoneyCountMonthlyResponse(GetDrawMoneyCountMonthlyResponse value) {
        return new JAXBElement<GetDrawMoneyCountMonthlyResponse>(_GetDrawMoneyCountMonthlyResponse_QNAME, GetDrawMoneyCountMonthlyResponse.class, null, value);
    }

}
