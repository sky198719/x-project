
package com.xxdai.external.lendInfo.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.lendInfo.ws package. 
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

    private final static QName _SearchPersonInfo_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "searchPersonInfo");
    private final static QName _SearchPersonInfoResponse_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "searchPersonInfoResponse");
    private final static QName _InvestBankContractResponse_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "investBankContractResponse");
    private final static QName _QueryAllLenderInfoResponse_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "queryAllLenderInfoResponse");
    private final static QName _InvestBorrowGuarantorContractResponse_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "investBorrowGuarantorContractResponse");
    private final static QName _PrintContract_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "printContract");
    private final static QName _InvestBorrowGuarantorContract_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "investBorrowGuarantorContract");
    private final static QName _QueryLoanUserInfo_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "queryLoanUserInfo");
    private final static QName _PrintContractResponse_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "printContractResponse");
    private final static QName _InvestBankContract_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "investBankContract");
    private final static QName _QueryAllLenderInfo_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "queryAllLenderInfo");
    private final static QName _QueryLoanUserInfoResponse_QNAME = new QName("http://webservice.lendInfo.xxdai.com/", "queryLoanUserInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.lendInfo.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PrintContractResponse }
     * 
     */
    public PrintContractResponse createPrintContractResponse() {
        return new PrintContractResponse();
    }

    /**
     * Create an instance of {@link QueryLoanUserInfo }
     * 
     */
    public QueryLoanUserInfo createQueryLoanUserInfo() {
        return new QueryLoanUserInfo();
    }

    /**
     * Create an instance of {@link InvestBorrowGuarantorContract }
     * 
     */
    public InvestBorrowGuarantorContract createInvestBorrowGuarantorContract() {
        return new InvestBorrowGuarantorContract();
    }

    /**
     * Create an instance of {@link PrintContract }
     * 
     */
    public PrintContract createPrintContract() {
        return new PrintContract();
    }

    /**
     * Create an instance of {@link InvestBorrowGuarantorContractResponse }
     * 
     */
    public InvestBorrowGuarantorContractResponse createInvestBorrowGuarantorContractResponse() {
        return new InvestBorrowGuarantorContractResponse();
    }

    /**
     * Create an instance of {@link QueryLoanUserInfoResponse }
     * 
     */
    public QueryLoanUserInfoResponse createQueryLoanUserInfoResponse() {
        return new QueryLoanUserInfoResponse();
    }

    /**
     * Create an instance of {@link QueryAllLenderInfo }
     * 
     */
    public QueryAllLenderInfo createQueryAllLenderInfo() {
        return new QueryAllLenderInfo();
    }

    /**
     * Create an instance of {@link InvestBankContract }
     * 
     */
    public InvestBankContract createInvestBankContract() {
        return new InvestBankContract();
    }

    /**
     * Create an instance of {@link QueryAllLenderInfoResponse }
     * 
     */
    public QueryAllLenderInfoResponse createQueryAllLenderInfoResponse() {
        return new QueryAllLenderInfoResponse();
    }

    /**
     * Create an instance of {@link InvestBankContractResponse }
     * 
     */
    public InvestBankContractResponse createInvestBankContractResponse() {
        return new InvestBankContractResponse();
    }

    /**
     * Create an instance of {@link SearchPersonInfoResponse }
     * 
     */
    public SearchPersonInfoResponse createSearchPersonInfoResponse() {
        return new SearchPersonInfoResponse();
    }

    /**
     * Create an instance of {@link SearchPersonInfo }
     * 
     */
    public SearchPersonInfo createSearchPersonInfo() {
        return new SearchPersonInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersonInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "searchPersonInfo")
    public JAXBElement<SearchPersonInfo> createSearchPersonInfo(SearchPersonInfo value) {
        return new JAXBElement<SearchPersonInfo>(_SearchPersonInfo_QNAME, SearchPersonInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchPersonInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "searchPersonInfoResponse")
    public JAXBElement<SearchPersonInfoResponse> createSearchPersonInfoResponse(SearchPersonInfoResponse value) {
        return new JAXBElement<SearchPersonInfoResponse>(_SearchPersonInfoResponse_QNAME, SearchPersonInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvestBankContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "investBankContractResponse")
    public JAXBElement<InvestBankContractResponse> createInvestBankContractResponse(InvestBankContractResponse value) {
        return new JAXBElement<InvestBankContractResponse>(_InvestBankContractResponse_QNAME, InvestBankContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryAllLenderInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "queryAllLenderInfoResponse")
    public JAXBElement<QueryAllLenderInfoResponse> createQueryAllLenderInfoResponse(QueryAllLenderInfoResponse value) {
        return new JAXBElement<QueryAllLenderInfoResponse>(_QueryAllLenderInfoResponse_QNAME, QueryAllLenderInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvestBorrowGuarantorContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "investBorrowGuarantorContractResponse")
    public JAXBElement<InvestBorrowGuarantorContractResponse> createInvestBorrowGuarantorContractResponse(InvestBorrowGuarantorContractResponse value) {
        return new JAXBElement<InvestBorrowGuarantorContractResponse>(_InvestBorrowGuarantorContractResponse_QNAME, InvestBorrowGuarantorContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrintContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "printContract")
    public JAXBElement<PrintContract> createPrintContract(PrintContract value) {
        return new JAXBElement<PrintContract>(_PrintContract_QNAME, PrintContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvestBorrowGuarantorContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "investBorrowGuarantorContract")
    public JAXBElement<InvestBorrowGuarantorContract> createInvestBorrowGuarantorContract(InvestBorrowGuarantorContract value) {
        return new JAXBElement<InvestBorrowGuarantorContract>(_InvestBorrowGuarantorContract_QNAME, InvestBorrowGuarantorContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLoanUserInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "queryLoanUserInfo")
    public JAXBElement<QueryLoanUserInfo> createQueryLoanUserInfo(QueryLoanUserInfo value) {
        return new JAXBElement<QueryLoanUserInfo>(_QueryLoanUserInfo_QNAME, QueryLoanUserInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrintContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "printContractResponse")
    public JAXBElement<PrintContractResponse> createPrintContractResponse(PrintContractResponse value) {
        return new JAXBElement<PrintContractResponse>(_PrintContractResponse_QNAME, PrintContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvestBankContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "investBankContract")
    public JAXBElement<InvestBankContract> createInvestBankContract(InvestBankContract value) {
        return new JAXBElement<InvestBankContract>(_InvestBankContract_QNAME, InvestBankContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryAllLenderInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "queryAllLenderInfo")
    public JAXBElement<QueryAllLenderInfo> createQueryAllLenderInfo(QueryAllLenderInfo value) {
        return new JAXBElement<QueryAllLenderInfo>(_QueryAllLenderInfo_QNAME, QueryAllLenderInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLoanUserInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.lendInfo.xxdai.com/", name = "queryLoanUserInfoResponse")
    public JAXBElement<QueryLoanUserInfoResponse> createQueryLoanUserInfoResponse(QueryLoanUserInfoResponse value) {
        return new JAXBElement<QueryLoanUserInfoResponse>(_QueryLoanUserInfoResponse_QNAME, QueryLoanUserInfoResponse.class, null, value);
    }

}
