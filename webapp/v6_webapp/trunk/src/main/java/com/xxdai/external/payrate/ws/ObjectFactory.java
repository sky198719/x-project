
package com.xxdai.external.payrate.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.payrate.ws package. 
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

    private final static QName _QueryPayRateListFromPage_QNAME = new QName("http://webservice.payrate.xxdai.com/", "queryPayRateListFromPage");
    private final static QName _DealAddPayRateResponse_QNAME = new QName("http://webservice.payrate.xxdai.com/", "dealAddPayRateResponse");
    private final static QName _GetMinPayFeeByBank_QNAME = new QName("http://webservice.payrate.xxdai.com/", "getMinPayFeeByBank");
    private final static QName _GetPayPartnerForApp_QNAME = new QName("http://webservice.payrate.xxdai.com/", "getPayPartnerForApp");
    private final static QName _DealUpdatePayRateResponse_QNAME = new QName("http://webservice.payrate.xxdai.com/", "dealUpdatePayRateResponse");
    private final static QName _QueryPayRateByPidAndPayCodeResponse_QNAME = new QName("http://webservice.payrate.xxdai.com/", "queryPayRateByPidAndPayCodeResponse");
    private final static QName _GetPayPartnerForAppResponse_QNAME = new QName("http://webservice.payrate.xxdai.com/", "getPayPartnerForAppResponse");
    private final static QName _DealUpdatePayRate_QNAME = new QName("http://webservice.payrate.xxdai.com/", "dealUpdatePayRate");
    private final static QName _QueryPayRateListFromPageResponse_QNAME = new QName("http://webservice.payrate.xxdai.com/", "queryPayRateListFromPageResponse");
    private final static QName _GetMinPayFeeByBankResponse_QNAME = new QName("http://webservice.payrate.xxdai.com/", "getMinPayFeeByBankResponse");
    private final static QName _QueryPayRateByPidAndPayCode_QNAME = new QName("http://webservice.payrate.xxdai.com/", "queryPayRateByPidAndPayCode");
    private final static QName _DealAddPayRate_QNAME = new QName("http://webservice.payrate.xxdai.com/", "dealAddPayRate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.payrate.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMinPayFeeByBank }
     * 
     */
    public GetMinPayFeeByBank createGetMinPayFeeByBank() {
        return new GetMinPayFeeByBank();
    }

    /**
     * Create an instance of {@link DealAddPayRateResponse }
     * 
     */
    public DealAddPayRateResponse createDealAddPayRateResponse() {
        return new DealAddPayRateResponse();
    }

    /**
     * Create an instance of {@link QueryPayRateListFromPage }
     * 
     */
    public QueryPayRateListFromPage createQueryPayRateListFromPage() {
        return new QueryPayRateListFromPage();
    }

    /**
     * Create an instance of {@link QueryPayRateByPidAndPayCodeResponse }
     * 
     */
    public QueryPayRateByPidAndPayCodeResponse createQueryPayRateByPidAndPayCodeResponse() {
        return new QueryPayRateByPidAndPayCodeResponse();
    }

    /**
     * Create an instance of {@link DealUpdatePayRateResponse }
     * 
     */
    public DealUpdatePayRateResponse createDealUpdatePayRateResponse() {
        return new DealUpdatePayRateResponse();
    }

    /**
     * Create an instance of {@link GetPayPartnerForApp }
     * 
     */
    public GetPayPartnerForApp createGetPayPartnerForApp() {
        return new GetPayPartnerForApp();
    }

    /**
     * Create an instance of {@link DealUpdatePayRate }
     * 
     */
    public DealUpdatePayRate createDealUpdatePayRate() {
        return new DealUpdatePayRate();
    }

    /**
     * Create an instance of {@link GetPayPartnerForAppResponse }
     * 
     */
    public GetPayPartnerForAppResponse createGetPayPartnerForAppResponse() {
        return new GetPayPartnerForAppResponse();
    }

    /**
     * Create an instance of {@link DealAddPayRate }
     * 
     */
    public DealAddPayRate createDealAddPayRate() {
        return new DealAddPayRate();
    }

    /**
     * Create an instance of {@link QueryPayRateByPidAndPayCode }
     * 
     */
    public QueryPayRateByPidAndPayCode createQueryPayRateByPidAndPayCode() {
        return new QueryPayRateByPidAndPayCode();
    }

    /**
     * Create an instance of {@link GetMinPayFeeByBankResponse }
     * 
     */
    public GetMinPayFeeByBankResponse createGetMinPayFeeByBankResponse() {
        return new GetMinPayFeeByBankResponse();
    }

    /**
     * Create an instance of {@link QueryPayRateListFromPageResponse }
     * 
     */
    public QueryPayRateListFromPageResponse createQueryPayRateListFromPageResponse() {
        return new QueryPayRateListFromPageResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPayRateListFromPage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "queryPayRateListFromPage")
    public JAXBElement<QueryPayRateListFromPage> createQueryPayRateListFromPage(QueryPayRateListFromPage value) {
        return new JAXBElement<QueryPayRateListFromPage>(_QueryPayRateListFromPage_QNAME, QueryPayRateListFromPage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DealAddPayRateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "dealAddPayRateResponse")
    public JAXBElement<DealAddPayRateResponse> createDealAddPayRateResponse(DealAddPayRateResponse value) {
        return new JAXBElement<DealAddPayRateResponse>(_DealAddPayRateResponse_QNAME, DealAddPayRateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMinPayFeeByBank }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "getMinPayFeeByBank")
    public JAXBElement<GetMinPayFeeByBank> createGetMinPayFeeByBank(GetMinPayFeeByBank value) {
        return new JAXBElement<GetMinPayFeeByBank>(_GetMinPayFeeByBank_QNAME, GetMinPayFeeByBank.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPayPartnerForApp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "getPayPartnerForApp")
    public JAXBElement<GetPayPartnerForApp> createGetPayPartnerForApp(GetPayPartnerForApp value) {
        return new JAXBElement<GetPayPartnerForApp>(_GetPayPartnerForApp_QNAME, GetPayPartnerForApp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DealUpdatePayRateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "dealUpdatePayRateResponse")
    public JAXBElement<DealUpdatePayRateResponse> createDealUpdatePayRateResponse(DealUpdatePayRateResponse value) {
        return new JAXBElement<DealUpdatePayRateResponse>(_DealUpdatePayRateResponse_QNAME, DealUpdatePayRateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPayRateByPidAndPayCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "queryPayRateByPidAndPayCodeResponse")
    public JAXBElement<QueryPayRateByPidAndPayCodeResponse> createQueryPayRateByPidAndPayCodeResponse(QueryPayRateByPidAndPayCodeResponse value) {
        return new JAXBElement<QueryPayRateByPidAndPayCodeResponse>(_QueryPayRateByPidAndPayCodeResponse_QNAME, QueryPayRateByPidAndPayCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPayPartnerForAppResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "getPayPartnerForAppResponse")
    public JAXBElement<GetPayPartnerForAppResponse> createGetPayPartnerForAppResponse(GetPayPartnerForAppResponse value) {
        return new JAXBElement<GetPayPartnerForAppResponse>(_GetPayPartnerForAppResponse_QNAME, GetPayPartnerForAppResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DealUpdatePayRate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "dealUpdatePayRate")
    public JAXBElement<DealUpdatePayRate> createDealUpdatePayRate(DealUpdatePayRate value) {
        return new JAXBElement<DealUpdatePayRate>(_DealUpdatePayRate_QNAME, DealUpdatePayRate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPayRateListFromPageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "queryPayRateListFromPageResponse")
    public JAXBElement<QueryPayRateListFromPageResponse> createQueryPayRateListFromPageResponse(QueryPayRateListFromPageResponse value) {
        return new JAXBElement<QueryPayRateListFromPageResponse>(_QueryPayRateListFromPageResponse_QNAME, QueryPayRateListFromPageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMinPayFeeByBankResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "getMinPayFeeByBankResponse")
    public JAXBElement<GetMinPayFeeByBankResponse> createGetMinPayFeeByBankResponse(GetMinPayFeeByBankResponse value) {
        return new JAXBElement<GetMinPayFeeByBankResponse>(_GetMinPayFeeByBankResponse_QNAME, GetMinPayFeeByBankResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPayRateByPidAndPayCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "queryPayRateByPidAndPayCode")
    public JAXBElement<QueryPayRateByPidAndPayCode> createQueryPayRateByPidAndPayCode(QueryPayRateByPidAndPayCode value) {
        return new JAXBElement<QueryPayRateByPidAndPayCode>(_QueryPayRateByPidAndPayCode_QNAME, QueryPayRateByPidAndPayCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DealAddPayRate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.payrate.xxdai.com/", name = "dealAddPayRate")
    public JAXBElement<DealAddPayRate> createDealAddPayRate(DealAddPayRate value) {
        return new JAXBElement<DealAddPayRate>(_DealAddPayRate_QNAME, DealAddPayRate.class, null, value);
    }

}
