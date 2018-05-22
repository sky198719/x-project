
package com.xxdai.external.borrowTrade.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.borrowTrade.ws package. 
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

    private final static QName _QueryUserCanBorrowSum_QNAME = new QName("http://webservice.borrow.xxdai.com/", "queryUserCanBorrowSum");
    private final static QName _FlowBorrowResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "flowBorrowResponse");
    private final static QName _QueryUserBorrowTenderResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "queryUserBorrowTenderResponse");
    private final static QName _TenderCommProdBorrow_QNAME = new QName("http://webservice.borrow.xxdai.com/", "tenderCommProdBorrow");
    private final static QName _ReturnFrozenAccountForInstallmentResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "returnFrozenAccountForInstallmentResponse");
    private final static QName _TenderBorrow_QNAME = new QName("http://webservice.borrow.xxdai.com/", "tenderBorrow");
    private final static QName _FlowBorrow_QNAME = new QName("http://webservice.borrow.xxdai.com/", "flowBorrow");
    private final static QName _TenderCommProdBorrowResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "tenderCommProdBorrowResponse");
    private final static QName _TenderBorrowResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "tenderBorrowResponse");
    private final static QName _TransferMarketPrizeAccount_QNAME = new QName("http://webservice.borrow.xxdai.com/", "transferMarketPrizeAccount");
    private final static QName _QueryUserCanBorrowSumResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "queryUserCanBorrowSumResponse");
    private final static QName _TenderXxbaoBorrow_QNAME = new QName("http://webservice.borrow.xxdai.com/", "tenderXxbaoBorrow");
    private final static QName _QueryUserBorrowTender_QNAME = new QName("http://webservice.borrow.xxdai.com/", "queryUserBorrowTender");
    private final static QName _FrozenAccountForInstallmentResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "frozenAccountForInstallmentResponse");
    private final static QName _FrozenAccountForInstallment_QNAME = new QName("http://webservice.borrow.xxdai.com/", "frozenAccountForInstallment");
    private final static QName _QueryUserCommProdBorrowSum_QNAME = new QName("http://webservice.borrow.xxdai.com/", "queryUserCommProdBorrowSum");
    private final static QName _TenderXxbaoBorrowResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "tenderXxbaoBorrowResponse");
    private final static QName _QueryUserCommProdBorrowSumResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "queryUserCommProdBorrowSumResponse");
    private final static QName _TransferMarketPrizeAccountResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "transferMarketPrizeAccountResponse");
    private final static QName _ReturnFrozenAccountForInstallment_QNAME = new QName("http://webservice.borrow.xxdai.com/", "returnFrozenAccountForInstallment");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.borrowTrade.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TenderCommProdBorrowResponse }
     * 
     */
    public TenderCommProdBorrowResponse createTenderCommProdBorrowResponse() {
        return new TenderCommProdBorrowResponse();
    }

    /**
     * Create an instance of {@link FlowBorrow }
     * 
     */
    public FlowBorrow createFlowBorrow() {
        return new FlowBorrow();
    }

    /**
     * Create an instance of {@link TenderCommProdBorrow }
     * 
     */
    public TenderCommProdBorrow createTenderCommProdBorrow() {
        return new TenderCommProdBorrow();
    }

    /**
     * Create an instance of {@link ReturnFrozenAccountForInstallmentResponse }
     * 
     */
    public ReturnFrozenAccountForInstallmentResponse createReturnFrozenAccountForInstallmentResponse() {
        return new ReturnFrozenAccountForInstallmentResponse();
    }

    /**
     * Create an instance of {@link TenderBorrow }
     * 
     */
    public TenderBorrow createTenderBorrow() {
        return new TenderBorrow();
    }

    /**
     * Create an instance of {@link QueryUserBorrowTenderResponse }
     * 
     */
    public QueryUserBorrowTenderResponse createQueryUserBorrowTenderResponse() {
        return new QueryUserBorrowTenderResponse();
    }

    /**
     * Create an instance of {@link FlowBorrowResponse }
     * 
     */
    public FlowBorrowResponse createFlowBorrowResponse() {
        return new FlowBorrowResponse();
    }

    /**
     * Create an instance of {@link QueryUserCanBorrowSum }
     * 
     */
    public QueryUserCanBorrowSum createQueryUserCanBorrowSum() {
        return new QueryUserCanBorrowSum();
    }

    /**
     * Create an instance of {@link TransferMarketPrizeAccountResponse }
     * 
     */
    public TransferMarketPrizeAccountResponse createTransferMarketPrizeAccountResponse() {
        return new TransferMarketPrizeAccountResponse();
    }

    /**
     * Create an instance of {@link ReturnFrozenAccountForInstallment }
     * 
     */
    public ReturnFrozenAccountForInstallment createReturnFrozenAccountForInstallment() {
        return new ReturnFrozenAccountForInstallment();
    }

    /**
     * Create an instance of {@link TenderXxbaoBorrowResponse }
     * 
     */
    public TenderXxbaoBorrowResponse createTenderXxbaoBorrowResponse() {
        return new TenderXxbaoBorrowResponse();
    }

    /**
     * Create an instance of {@link QueryUserCommProdBorrowSumResponse }
     * 
     */
    public QueryUserCommProdBorrowSumResponse createQueryUserCommProdBorrowSumResponse() {
        return new QueryUserCommProdBorrowSumResponse();
    }

    /**
     * Create an instance of {@link FrozenAccountForInstallment }
     * 
     */
    public FrozenAccountForInstallment createFrozenAccountForInstallment() {
        return new FrozenAccountForInstallment();
    }

    /**
     * Create an instance of {@link QueryUserCommProdBorrowSum }
     * 
     */
    public QueryUserCommProdBorrowSum createQueryUserCommProdBorrowSum() {
        return new QueryUserCommProdBorrowSum();
    }

    /**
     * Create an instance of {@link FrozenAccountForInstallmentResponse }
     * 
     */
    public FrozenAccountForInstallmentResponse createFrozenAccountForInstallmentResponse() {
        return new FrozenAccountForInstallmentResponse();
    }

    /**
     * Create an instance of {@link QueryUserBorrowTender }
     * 
     */
    public QueryUserBorrowTender createQueryUserBorrowTender() {
        return new QueryUserBorrowTender();
    }

    /**
     * Create an instance of {@link TransferMarketPrizeAccount }
     * 
     */
    public TransferMarketPrizeAccount createTransferMarketPrizeAccount() {
        return new TransferMarketPrizeAccount();
    }

    /**
     * Create an instance of {@link TenderBorrowResponse }
     * 
     */
    public TenderBorrowResponse createTenderBorrowResponse() {
        return new TenderBorrowResponse();
    }

    /**
     * Create an instance of {@link TenderXxbaoBorrow }
     * 
     */
    public TenderXxbaoBorrow createTenderXxbaoBorrow() {
        return new TenderXxbaoBorrow();
    }

    /**
     * Create an instance of {@link QueryUserCanBorrowSumResponse }
     * 
     */
    public QueryUserCanBorrowSumResponse createQueryUserCanBorrowSumResponse() {
        return new QueryUserCanBorrowSumResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserCanBorrowSum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "queryUserCanBorrowSum")
    public JAXBElement<QueryUserCanBorrowSum> createQueryUserCanBorrowSum(QueryUserCanBorrowSum value) {
        return new JAXBElement<QueryUserCanBorrowSum>(_QueryUserCanBorrowSum_QNAME, QueryUserCanBorrowSum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlowBorrowResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "flowBorrowResponse")
    public JAXBElement<FlowBorrowResponse> createFlowBorrowResponse(FlowBorrowResponse value) {
        return new JAXBElement<FlowBorrowResponse>(_FlowBorrowResponse_QNAME, FlowBorrowResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserBorrowTenderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "queryUserBorrowTenderResponse")
    public JAXBElement<QueryUserBorrowTenderResponse> createQueryUserBorrowTenderResponse(QueryUserBorrowTenderResponse value) {
        return new JAXBElement<QueryUserBorrowTenderResponse>(_QueryUserBorrowTenderResponse_QNAME, QueryUserBorrowTenderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TenderCommProdBorrow }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "tenderCommProdBorrow")
    public JAXBElement<TenderCommProdBorrow> createTenderCommProdBorrow(TenderCommProdBorrow value) {
        return new JAXBElement<TenderCommProdBorrow>(_TenderCommProdBorrow_QNAME, TenderCommProdBorrow.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnFrozenAccountForInstallmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "returnFrozenAccountForInstallmentResponse")
    public JAXBElement<ReturnFrozenAccountForInstallmentResponse> createReturnFrozenAccountForInstallmentResponse(ReturnFrozenAccountForInstallmentResponse value) {
        return new JAXBElement<ReturnFrozenAccountForInstallmentResponse>(_ReturnFrozenAccountForInstallmentResponse_QNAME, ReturnFrozenAccountForInstallmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TenderBorrow }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "tenderBorrow")
    public JAXBElement<TenderBorrow> createTenderBorrow(TenderBorrow value) {
        return new JAXBElement<TenderBorrow>(_TenderBorrow_QNAME, TenderBorrow.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlowBorrow }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "flowBorrow")
    public JAXBElement<FlowBorrow> createFlowBorrow(FlowBorrow value) {
        return new JAXBElement<FlowBorrow>(_FlowBorrow_QNAME, FlowBorrow.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TenderCommProdBorrowResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "tenderCommProdBorrowResponse")
    public JAXBElement<TenderCommProdBorrowResponse> createTenderCommProdBorrowResponse(TenderCommProdBorrowResponse value) {
        return new JAXBElement<TenderCommProdBorrowResponse>(_TenderCommProdBorrowResponse_QNAME, TenderCommProdBorrowResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TenderBorrowResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "tenderBorrowResponse")
    public JAXBElement<TenderBorrowResponse> createTenderBorrowResponse(TenderBorrowResponse value) {
        return new JAXBElement<TenderBorrowResponse>(_TenderBorrowResponse_QNAME, TenderBorrowResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferMarketPrizeAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "transferMarketPrizeAccount")
    public JAXBElement<TransferMarketPrizeAccount> createTransferMarketPrizeAccount(TransferMarketPrizeAccount value) {
        return new JAXBElement<TransferMarketPrizeAccount>(_TransferMarketPrizeAccount_QNAME, TransferMarketPrizeAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserCanBorrowSumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "queryUserCanBorrowSumResponse")
    public JAXBElement<QueryUserCanBorrowSumResponse> createQueryUserCanBorrowSumResponse(QueryUserCanBorrowSumResponse value) {
        return new JAXBElement<QueryUserCanBorrowSumResponse>(_QueryUserCanBorrowSumResponse_QNAME, QueryUserCanBorrowSumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TenderXxbaoBorrow }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "tenderXxbaoBorrow")
    public JAXBElement<TenderXxbaoBorrow> createTenderXxbaoBorrow(TenderXxbaoBorrow value) {
        return new JAXBElement<TenderXxbaoBorrow>(_TenderXxbaoBorrow_QNAME, TenderXxbaoBorrow.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserBorrowTender }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "queryUserBorrowTender")
    public JAXBElement<QueryUserBorrowTender> createQueryUserBorrowTender(QueryUserBorrowTender value) {
        return new JAXBElement<QueryUserBorrowTender>(_QueryUserBorrowTender_QNAME, QueryUserBorrowTender.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FrozenAccountForInstallmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "frozenAccountForInstallmentResponse")
    public JAXBElement<FrozenAccountForInstallmentResponse> createFrozenAccountForInstallmentResponse(FrozenAccountForInstallmentResponse value) {
        return new JAXBElement<FrozenAccountForInstallmentResponse>(_FrozenAccountForInstallmentResponse_QNAME, FrozenAccountForInstallmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FrozenAccountForInstallment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "frozenAccountForInstallment")
    public JAXBElement<FrozenAccountForInstallment> createFrozenAccountForInstallment(FrozenAccountForInstallment value) {
        return new JAXBElement<FrozenAccountForInstallment>(_FrozenAccountForInstallment_QNAME, FrozenAccountForInstallment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserCommProdBorrowSum }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "queryUserCommProdBorrowSum")
    public JAXBElement<QueryUserCommProdBorrowSum> createQueryUserCommProdBorrowSum(QueryUserCommProdBorrowSum value) {
        return new JAXBElement<QueryUserCommProdBorrowSum>(_QueryUserCommProdBorrowSum_QNAME, QueryUserCommProdBorrowSum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TenderXxbaoBorrowResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "tenderXxbaoBorrowResponse")
    public JAXBElement<TenderXxbaoBorrowResponse> createTenderXxbaoBorrowResponse(TenderXxbaoBorrowResponse value) {
        return new JAXBElement<TenderXxbaoBorrowResponse>(_TenderXxbaoBorrowResponse_QNAME, TenderXxbaoBorrowResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserCommProdBorrowSumResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "queryUserCommProdBorrowSumResponse")
    public JAXBElement<QueryUserCommProdBorrowSumResponse> createQueryUserCommProdBorrowSumResponse(QueryUserCommProdBorrowSumResponse value) {
        return new JAXBElement<QueryUserCommProdBorrowSumResponse>(_QueryUserCommProdBorrowSumResponse_QNAME, QueryUserCommProdBorrowSumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferMarketPrizeAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "transferMarketPrizeAccountResponse")
    public JAXBElement<TransferMarketPrizeAccountResponse> createTransferMarketPrizeAccountResponse(TransferMarketPrizeAccountResponse value) {
        return new JAXBElement<TransferMarketPrizeAccountResponse>(_TransferMarketPrizeAccountResponse_QNAME, TransferMarketPrizeAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnFrozenAccountForInstallment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "returnFrozenAccountForInstallment")
    public JAXBElement<ReturnFrozenAccountForInstallment> createReturnFrozenAccountForInstallment(ReturnFrozenAccountForInstallment value) {
        return new JAXBElement<ReturnFrozenAccountForInstallment>(_ReturnFrozenAccountForInstallment_QNAME, ReturnFrozenAccountForInstallment.class, null, value);
    }

}
