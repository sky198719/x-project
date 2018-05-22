
package com.xxdai.external.accquery.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.accquery.ws package. 
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

    private final static QName _SelectAccountRecharge_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountRecharge");
    private final static QName _GetNetWorthRepayResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "getNetWorthRepayResponse");
    private final static QName _SelectAccountList_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountList");
    private final static QName _SelectLevelsByUserId_QNAME = new QName("http://webservice.account.xxdai.com/", "selectLevelsByUserId");
    private final static QName _SelectAccountRechargeByOrderNo_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountRechargeByOrderNo");
    private final static QName _SelectAccountRechargeByOrderNoResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountRechargeByOrderNoResponse");
    private final static QName _SelectLevelLogsByUserIdResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectLevelLogsByUserIdResponse");
    private final static QName _SelectAccountRechargeResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountRechargeResponse");
    private final static QName _SelectExchangeRecord_QNAME = new QName("http://webservice.account.xxdai.com/", "selectExchangeRecord");
    private final static QName _SelectExchangeRecordResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectExchangeRecordResponse");
    private final static QName _SelectAccountCashRecordResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountCashRecordResponse");
    private final static QName _SelectAccountListResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountListResponse");
    private final static QName _SelectLevelsByUserIdResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectLevelsByUserIdResponse");
    private final static QName _SelectLevelLogsByUserId_QNAME = new QName("http://webservice.account.xxdai.com/", "selectLevelLogsByUserId");
    private final static QName _SelectAccountCashRecord_QNAME = new QName("http://webservice.account.xxdai.com/", "selectAccountCashRecord");
    private final static QName _GetNetWorthRepay_QNAME = new QName("http://webservice.account.xxdai.com/", "getNetWorthRepay");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.accquery.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectAccountRecharge }
     * 
     */
    public SelectAccountRecharge createSelectAccountRecharge() {
        return new SelectAccountRecharge();
    }

    /**
     * Create an instance of {@link SelectLevelsByUserId }
     * 
     */
    public SelectLevelsByUserId createSelectLevelsByUserId() {
        return new SelectLevelsByUserId();
    }

    /**
     * Create an instance of {@link GetNetWorthRepayResponse }
     * 
     */
    public GetNetWorthRepayResponse createGetNetWorthRepayResponse() {
        return new GetNetWorthRepayResponse();
    }

    /**
     * Create an instance of {@link SelectAccountList }
     * 
     */
    public SelectAccountList createSelectAccountList() {
        return new SelectAccountList();
    }

    /**
     * Create an instance of {@link SelectExchangeRecord }
     * 
     */
    public SelectExchangeRecord createSelectExchangeRecord() {
        return new SelectExchangeRecord();
    }

    /**
     * Create an instance of {@link SelectExchangeRecordResponse }
     * 
     */
    public SelectExchangeRecordResponse createSelectExchangeRecordResponse() {
        return new SelectExchangeRecordResponse();
    }

    /**
     * Create an instance of {@link SelectAccountCashRecordResponse }
     * 
     */
    public SelectAccountCashRecordResponse createSelectAccountCashRecordResponse() {
        return new SelectAccountCashRecordResponse();
    }

    /**
     * Create an instance of {@link SelectAccountListResponse }
     * 
     */
    public SelectAccountListResponse createSelectAccountListResponse() {
        return new SelectAccountListResponse();
    }

    /**
     * Create an instance of {@link SelectAccountRechargeByOrderNo }
     * 
     */
    public SelectAccountRechargeByOrderNo createSelectAccountRechargeByOrderNo() {
        return new SelectAccountRechargeByOrderNo();
    }

    /**
     * Create an instance of {@link SelectAccountRechargeByOrderNoResponse }
     * 
     */
    public SelectAccountRechargeByOrderNoResponse createSelectAccountRechargeByOrderNoResponse() {
        return new SelectAccountRechargeByOrderNoResponse();
    }

    /**
     * Create an instance of {@link SelectLevelLogsByUserIdResponse }
     * 
     */
    public SelectLevelLogsByUserIdResponse createSelectLevelLogsByUserIdResponse() {
        return new SelectLevelLogsByUserIdResponse();
    }

    /**
     * Create an instance of {@link SelectAccountRechargeResponse }
     * 
     */
    public SelectAccountRechargeResponse createSelectAccountRechargeResponse() {
        return new SelectAccountRechargeResponse();
    }

    /**
     * Create an instance of {@link GetNetWorthRepay }
     * 
     */
    public GetNetWorthRepay createGetNetWorthRepay() {
        return new GetNetWorthRepay();
    }

    /**
     * Create an instance of {@link SelectLevelsByUserIdResponse }
     * 
     */
    public SelectLevelsByUserIdResponse createSelectLevelsByUserIdResponse() {
        return new SelectLevelsByUserIdResponse();
    }

    /**
     * Create an instance of {@link SelectLevelLogsByUserId }
     * 
     */
    public SelectLevelLogsByUserId createSelectLevelLogsByUserId() {
        return new SelectLevelLogsByUserId();
    }

    /**
     * Create an instance of {@link SelectAccountCashRecord }
     * 
     */
    public SelectAccountCashRecord createSelectAccountCashRecord() {
        return new SelectAccountCashRecord();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectAccountRecharge }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountRecharge")
    public JAXBElement<SelectAccountRecharge> createSelectAccountRecharge(SelectAccountRecharge value) {
        return new JAXBElement<SelectAccountRecharge>(_SelectAccountRecharge_QNAME, SelectAccountRecharge.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link GetNetWorthRepayResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getNetWorthRepayResponse")
    public JAXBElement<GetNetWorthRepayResponse> createGetNetWorthRepayResponse(GetNetWorthRepayResponse value) {
        return new JAXBElement<GetNetWorthRepayResponse>(_GetNetWorthRepayResponse_QNAME, GetNetWorthRepayResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectAccountList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountList")
    public JAXBElement<SelectAccountList> createSelectAccountList(SelectAccountList value) {
        return new JAXBElement<SelectAccountList>(_SelectAccountList_QNAME, SelectAccountList.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectLevelsByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectLevelsByUserId")
    public JAXBElement<SelectLevelsByUserId> createSelectLevelsByUserId(SelectLevelsByUserId value) {
        return new JAXBElement<SelectLevelsByUserId>(_SelectLevelsByUserId_QNAME, SelectLevelsByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectAccountRechargeByOrderNo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountRechargeByOrderNo")
    public JAXBElement<SelectAccountRechargeByOrderNo> createSelectAccountRechargeByOrderNo(SelectAccountRechargeByOrderNo value) {
        return new JAXBElement<SelectAccountRechargeByOrderNo>(_SelectAccountRechargeByOrderNo_QNAME, SelectAccountRechargeByOrderNo.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectAccountRechargeByOrderNoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountRechargeByOrderNoResponse")
    public JAXBElement<SelectAccountRechargeByOrderNoResponse> createSelectAccountRechargeByOrderNoResponse(SelectAccountRechargeByOrderNoResponse value) {
        return new JAXBElement<SelectAccountRechargeByOrderNoResponse>(_SelectAccountRechargeByOrderNoResponse_QNAME, SelectAccountRechargeByOrderNoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectLevelLogsByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectLevelLogsByUserIdResponse")
    public JAXBElement<SelectLevelLogsByUserIdResponse> createSelectLevelLogsByUserIdResponse(SelectLevelLogsByUserIdResponse value) {
        return new JAXBElement<SelectLevelLogsByUserIdResponse>(_SelectLevelLogsByUserIdResponse_QNAME, SelectLevelLogsByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectAccountRechargeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountRechargeResponse")
    public JAXBElement<SelectAccountRechargeResponse> createSelectAccountRechargeResponse(SelectAccountRechargeResponse value) {
        return new JAXBElement<SelectAccountRechargeResponse>(_SelectAccountRechargeResponse_QNAME, SelectAccountRechargeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectExchangeRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectExchangeRecord")
    public JAXBElement<SelectExchangeRecord> createSelectExchangeRecord(SelectExchangeRecord value) {
        return new JAXBElement<SelectExchangeRecord>(_SelectExchangeRecord_QNAME, SelectExchangeRecord.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectExchangeRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectExchangeRecordResponse")
    public JAXBElement<SelectExchangeRecordResponse> createSelectExchangeRecordResponse(SelectExchangeRecordResponse value) {
        return new JAXBElement<SelectExchangeRecordResponse>(_SelectExchangeRecordResponse_QNAME, SelectExchangeRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectAccountCashRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountCashRecordResponse")
    public JAXBElement<SelectAccountCashRecordResponse> createSelectAccountCashRecordResponse(SelectAccountCashRecordResponse value) {
        return new JAXBElement<SelectAccountCashRecordResponse>(_SelectAccountCashRecordResponse_QNAME, SelectAccountCashRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectAccountListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountListResponse")
    public JAXBElement<SelectAccountListResponse> createSelectAccountListResponse(SelectAccountListResponse value) {
        return new JAXBElement<SelectAccountListResponse>(_SelectAccountListResponse_QNAME, SelectAccountListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectLevelsByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectLevelsByUserIdResponse")
    public JAXBElement<SelectLevelsByUserIdResponse> createSelectLevelsByUserIdResponse(SelectLevelsByUserIdResponse value) {
        return new JAXBElement<SelectLevelsByUserIdResponse>(_SelectLevelsByUserIdResponse_QNAME, SelectLevelsByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectLevelLogsByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectLevelLogsByUserId")
    public JAXBElement<SelectLevelLogsByUserId> createSelectLevelLogsByUserId(SelectLevelLogsByUserId value) {
        return new JAXBElement<SelectLevelLogsByUserId>(_SelectLevelLogsByUserId_QNAME, SelectLevelLogsByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link SelectAccountCashRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectAccountCashRecord")
    public JAXBElement<SelectAccountCashRecord> createSelectAccountCashRecord(SelectAccountCashRecord value) {
        return new JAXBElement<SelectAccountCashRecord>(_SelectAccountCashRecord_QNAME, SelectAccountCashRecord.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link GetNetWorthRepay }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "getNetWorthRepay")
    public JAXBElement<GetNetWorthRepay> createGetNetWorthRepay(GetNetWorthRepay value) {
        return new JAXBElement<GetNetWorthRepay>(_GetNetWorthRepay_QNAME, GetNetWorthRepay.class, null, value);
    }

}
