
package com.xxdai.external.fadada.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.fadada.ws package. 
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

    private final static QName _GetInvestorZipPath_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getInvestorZipPath");
    private final static QName _GetCurBorrowPath_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getCurBorrowPath");
    private final static QName _DoSigningContractReturnResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doSigningContractReturnResponse");
    private final static QName _DoUploadContractAndSaveInfo_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doUploadContractAndSaveInfo");
    private final static QName _DoUserCA_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doUserCA");
    private final static QName _DoViewContract_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doViewContract");
    private final static QName _DoUploadContractAndSaveInfoResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doUploadContractAndSaveInfoResponse");
    private final static QName _GetFadadaSignStatusByborrowId_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getFadadaSignStatusByborrowId");
    private final static QName _DoArchiveContractResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doArchiveContractResponse");
    private final static QName _DoDownloadContract_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doDownloadContract");
    private final static QName _GetBorrowGuarantorByBorrowId_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getBorrowGuarantorByBorrowId");
    private final static QName _DoUserCAResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doUserCAResponse");
    private final static QName _GetExtBorrowGuarantorByBorrowId_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getExtBorrowGuarantorByBorrowId");
    private final static QName _DoSendSmgToGuarantorResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doSendSmgToGuarantorResponse");
    private final static QName _GetGuarantorSignStatusByborrowId_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getGuarantorSignStatusByborrowId");
    private final static QName _GetBorrowGuarantorByBorrowIdResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getBorrowGuarantorByBorrowIdResponse");
    private final static QName _DoSendSmgToGuarantor_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doSendSmgToGuarantor");
    private final static QName _GetCompanyInfoByBorrowId_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getCompanyInfoByBorrowId");
    private final static QName _GetInvestorZipPathResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getInvestorZipPathResponse");
    private final static QName _DoGuarantorCA_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doGuarantorCA");
    private final static QName _GetExtBorrowGuarantorByBorrowIdResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getExtBorrowGuarantorByBorrowIdResponse");
    private final static QName _GetContractSavePathResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getContractSavePathResponse");
    private final static QName _GetBorrowerZipPathResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getBorrowerZipPathResponse");
    private final static QName _CheckBorrowContractZipIsExist_QNAME = new QName("http://webservice.fadada.xxdai.com/", "checkBorrowContractZipIsExist");
    private final static QName _CheckSZRongContractZipIsExistResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "checkSZRongContractZipIsExistResponse");
    private final static QName _GetBorrowCreditInfoByBorrowId_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getBorrowCreditInfoByBorrowId");
    private final static QName _DoSigningContractResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doSigningContractResponse");
    private final static QName _GetOriginDocContractPath_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getOriginDocContractPath");
    private final static QName _DoApCA_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doApCA");
    private final static QName _GetContractSavePath_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getContractSavePath");
    private final static QName _GetContractArchiveAndDownloadStatusResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getContractArchiveAndDownloadStatusResponse");
    private final static QName _DoExtGuarantorCAResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doExtGuarantorCAResponse");
    private final static QName _DoSigningContractReturn_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doSigningContractReturn");
    private final static QName _DoAutoSigningContractResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doAutoSigningContractResponse");
    private final static QName _GetContractCodeByborrowIdResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getContractCodeByborrowIdResponse");
    private final static QName _GetOriginDocContractPathResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getOriginDocContractPathResponse");
    private final static QName _GetCurBorrowPathResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getCurBorrowPathResponse");
    private final static QName _GetBorrowCreditInfoByBorrowIdResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getBorrowCreditInfoByBorrowIdResponse");
    private final static QName _DoExtGuarantorCA_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doExtGuarantorCA");
    private final static QName _DoGuarantorCAResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doGuarantorCAResponse");
    private final static QName _DoDownloadContractResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doDownloadContractResponse");
    private final static QName _GetGuarantorSignStatusByborrowIdResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getGuarantorSignStatusByborrowIdResponse");
    private final static QName _DoViewContractResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doViewContractResponse");
    private final static QName _CheckBorrowContractZipIsExistResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "checkBorrowContractZipIsExistResponse");
    private final static QName _GetFadadaSignStatusByborrowIdResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getFadadaSignStatusByborrowIdResponse");
    private final static QName _DoApCAResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doApCAResponse");
    private final static QName _GetContractArchiveAndDownloadStatus_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getContractArchiveAndDownloadStatus");
    private final static QName _GetBorrowerZipPath_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getBorrowerZipPath");
    private final static QName _DoSigningContract_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doSigningContract");
    private final static QName _GetCompanyInfoByBorrowIdResponse_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getCompanyInfoByBorrowIdResponse");
    private final static QName _DoAutoSigningContract_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doAutoSigningContract");
    private final static QName _GetContractCodeByborrowId_QNAME = new QName("http://webservice.fadada.xxdai.com/", "getContractCodeByborrowId");
    private final static QName _CheckSZRongContractZipIsExist_QNAME = new QName("http://webservice.fadada.xxdai.com/", "checkSZRongContractZipIsExist");
    private final static QName _DoArchiveContract_QNAME = new QName("http://webservice.fadada.xxdai.com/", "doArchiveContract");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.fadada.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetContractArchiveAndDownloadStatus }
     * 
     */
    public GetContractArchiveAndDownloadStatus createGetContractArchiveAndDownloadStatus() {
        return new GetContractArchiveAndDownloadStatus();
    }

    /**
     * Create an instance of {@link DoApCAResponse }
     * 
     */
    public DoApCAResponse createDoApCAResponse() {
        return new DoApCAResponse();
    }

    /**
     * Create an instance of {@link DoSigningContract }
     * 
     */
    public DoSigningContract createDoSigningContract() {
        return new DoSigningContract();
    }

    /**
     * Create an instance of {@link GetCompanyInfoByBorrowIdResponse }
     * 
     */
    public GetCompanyInfoByBorrowIdResponse createGetCompanyInfoByBorrowIdResponse() {
        return new GetCompanyInfoByBorrowIdResponse();
    }

    /**
     * Create an instance of {@link GetBorrowerZipPath }
     * 
     */
    public GetBorrowerZipPath createGetBorrowerZipPath() {
        return new GetBorrowerZipPath();
    }

    /**
     * Create an instance of {@link DoAutoSigningContract }
     * 
     */
    public DoAutoSigningContract createDoAutoSigningContract() {
        return new DoAutoSigningContract();
    }

    /**
     * Create an instance of {@link DoArchiveContract }
     * 
     */
    public DoArchiveContract createDoArchiveContract() {
        return new DoArchiveContract();
    }

    /**
     * Create an instance of {@link CheckSZRongContractZipIsExist }
     * 
     */
    public CheckSZRongContractZipIsExist createCheckSZRongContractZipIsExist() {
        return new CheckSZRongContractZipIsExist();
    }

    /**
     * Create an instance of {@link GetContractCodeByborrowId }
     * 
     */
    public GetContractCodeByborrowId createGetContractCodeByborrowId() {
        return new GetContractCodeByborrowId();
    }

    /**
     * Create an instance of {@link DoSigningContractReturn }
     * 
     */
    public DoSigningContractReturn createDoSigningContractReturn() {
        return new DoSigningContractReturn();
    }

    /**
     * Create an instance of {@link DoExtGuarantorCAResponse }
     * 
     */
    public DoExtGuarantorCAResponse createDoExtGuarantorCAResponse() {
        return new DoExtGuarantorCAResponse();
    }

    /**
     * Create an instance of {@link GetCurBorrowPathResponse }
     * 
     */
    public GetCurBorrowPathResponse createGetCurBorrowPathResponse() {
        return new GetCurBorrowPathResponse();
    }

    /**
     * Create an instance of {@link GetBorrowCreditInfoByBorrowIdResponse }
     * 
     */
    public GetBorrowCreditInfoByBorrowIdResponse createGetBorrowCreditInfoByBorrowIdResponse() {
        return new GetBorrowCreditInfoByBorrowIdResponse();
    }

    /**
     * Create an instance of {@link DoAutoSigningContractResponse }
     * 
     */
    public DoAutoSigningContractResponse createDoAutoSigningContractResponse() {
        return new DoAutoSigningContractResponse();
    }

    /**
     * Create an instance of {@link GetOriginDocContractPathResponse }
     * 
     */
    public GetOriginDocContractPathResponse createGetOriginDocContractPathResponse() {
        return new GetOriginDocContractPathResponse();
    }

    /**
     * Create an instance of {@link GetContractCodeByborrowIdResponse }
     * 
     */
    public GetContractCodeByborrowIdResponse createGetContractCodeByborrowIdResponse() {
        return new GetContractCodeByborrowIdResponse();
    }

    /**
     * Create an instance of {@link DoDownloadContractResponse }
     * 
     */
    public DoDownloadContractResponse createDoDownloadContractResponse() {
        return new DoDownloadContractResponse();
    }

    /**
     * Create an instance of {@link GetGuarantorSignStatusByborrowIdResponse }
     * 
     */
    public GetGuarantorSignStatusByborrowIdResponse createGetGuarantorSignStatusByborrowIdResponse() {
        return new GetGuarantorSignStatusByborrowIdResponse();
    }

    /**
     * Create an instance of {@link DoExtGuarantorCA }
     * 
     */
    public DoExtGuarantorCA createDoExtGuarantorCA() {
        return new DoExtGuarantorCA();
    }

    /**
     * Create an instance of {@link DoGuarantorCAResponse }
     * 
     */
    public DoGuarantorCAResponse createDoGuarantorCAResponse() {
        return new DoGuarantorCAResponse();
    }

    /**
     * Create an instance of {@link GetFadadaSignStatusByborrowIdResponse }
     * 
     */
    public GetFadadaSignStatusByborrowIdResponse createGetFadadaSignStatusByborrowIdResponse() {
        return new GetFadadaSignStatusByborrowIdResponse();
    }

    /**
     * Create an instance of {@link DoViewContractResponse }
     * 
     */
    public DoViewContractResponse createDoViewContractResponse() {
        return new DoViewContractResponse();
    }

    /**
     * Create an instance of {@link CheckBorrowContractZipIsExistResponse }
     * 
     */
    public CheckBorrowContractZipIsExistResponse createCheckBorrowContractZipIsExistResponse() {
        return new CheckBorrowContractZipIsExistResponse();
    }

    /**
     * Create an instance of {@link GetContractSavePathResponse }
     * 
     */
    public GetContractSavePathResponse createGetContractSavePathResponse() {
        return new GetContractSavePathResponse();
    }

    /**
     * Create an instance of {@link DoGuarantorCA }
     * 
     */
    public DoGuarantorCA createDoGuarantorCA() {
        return new DoGuarantorCA();
    }

    /**
     * Create an instance of {@link GetExtBorrowGuarantorByBorrowIdResponse }
     * 
     */
    public GetExtBorrowGuarantorByBorrowIdResponse createGetExtBorrowGuarantorByBorrowIdResponse() {
        return new GetExtBorrowGuarantorByBorrowIdResponse();
    }

    /**
     * Create an instance of {@link CheckSZRongContractZipIsExistResponse }
     * 
     */
    public CheckSZRongContractZipIsExistResponse createCheckSZRongContractZipIsExistResponse() {
        return new CheckSZRongContractZipIsExistResponse();
    }

    /**
     * Create an instance of {@link GetBorrowCreditInfoByBorrowId }
     * 
     */
    public GetBorrowCreditInfoByBorrowId createGetBorrowCreditInfoByBorrowId() {
        return new GetBorrowCreditInfoByBorrowId();
    }

    /**
     * Create an instance of {@link CheckBorrowContractZipIsExist }
     * 
     */
    public CheckBorrowContractZipIsExist createCheckBorrowContractZipIsExist() {
        return new CheckBorrowContractZipIsExist();
    }

    /**
     * Create an instance of {@link GetBorrowerZipPathResponse }
     * 
     */
    public GetBorrowerZipPathResponse createGetBorrowerZipPathResponse() {
        return new GetBorrowerZipPathResponse();
    }

    /**
     * Create an instance of {@link GetContractArchiveAndDownloadStatusResponse }
     * 
     */
    public GetContractArchiveAndDownloadStatusResponse createGetContractArchiveAndDownloadStatusResponse() {
        return new GetContractArchiveAndDownloadStatusResponse();
    }

    /**
     * Create an instance of {@link GetOriginDocContractPath }
     * 
     */
    public GetOriginDocContractPath createGetOriginDocContractPath() {
        return new GetOriginDocContractPath();
    }

    /**
     * Create an instance of {@link DoApCA }
     * 
     */
    public DoApCA createDoApCA() {
        return new DoApCA();
    }

    /**
     * Create an instance of {@link DoSigningContractResponse }
     * 
     */
    public DoSigningContractResponse createDoSigningContractResponse() {
        return new DoSigningContractResponse();
    }

    /**
     * Create an instance of {@link GetContractSavePath }
     * 
     */
    public GetContractSavePath createGetContractSavePath() {
        return new GetContractSavePath();
    }

    /**
     * Create an instance of {@link DoUserCA }
     * 
     */
    public DoUserCA createDoUserCA() {
        return new DoUserCA();
    }

    /**
     * Create an instance of {@link DoViewContract }
     * 
     */
    public DoViewContract createDoViewContract() {
        return new DoViewContract();
    }

    /**
     * Create an instance of {@link DoUploadContractAndSaveInfo }
     * 
     */
    public DoUploadContractAndSaveInfo createDoUploadContractAndSaveInfo() {
        return new DoUploadContractAndSaveInfo();
    }

    /**
     * Create an instance of {@link GetFadadaSignStatusByborrowId }
     * 
     */
    public GetFadadaSignStatusByborrowId createGetFadadaSignStatusByborrowId() {
        return new GetFadadaSignStatusByborrowId();
    }

    /**
     * Create an instance of {@link DoUploadContractAndSaveInfoResponse }
     * 
     */
    public DoUploadContractAndSaveInfoResponse createDoUploadContractAndSaveInfoResponse() {
        return new DoUploadContractAndSaveInfoResponse();
    }

    /**
     * Create an instance of {@link DoSigningContractReturnResponse }
     * 
     */
    public DoSigningContractReturnResponse createDoSigningContractReturnResponse() {
        return new DoSigningContractReturnResponse();
    }

    /**
     * Create an instance of {@link GetInvestorZipPath }
     * 
     */
    public GetInvestorZipPath createGetInvestorZipPath() {
        return new GetInvestorZipPath();
    }

    /**
     * Create an instance of {@link GetCurBorrowPath }
     * 
     */
    public GetCurBorrowPath createGetCurBorrowPath() {
        return new GetCurBorrowPath();
    }

    /**
     * Create an instance of {@link DoDownloadContract }
     * 
     */
    public DoDownloadContract createDoDownloadContract() {
        return new DoDownloadContract();
    }

    /**
     * Create an instance of {@link GetBorrowGuarantorByBorrowId }
     * 
     */
    public GetBorrowGuarantorByBorrowId createGetBorrowGuarantorByBorrowId() {
        return new GetBorrowGuarantorByBorrowId();
    }

    /**
     * Create an instance of {@link DoUserCAResponse }
     * 
     */
    public DoUserCAResponse createDoUserCAResponse() {
        return new DoUserCAResponse();
    }

    /**
     * Create an instance of {@link GetExtBorrowGuarantorByBorrowId }
     * 
     */
    public GetExtBorrowGuarantorByBorrowId createGetExtBorrowGuarantorByBorrowId() {
        return new GetExtBorrowGuarantorByBorrowId();
    }

    /**
     * Create an instance of {@link DoArchiveContractResponse }
     * 
     */
    public DoArchiveContractResponse createDoArchiveContractResponse() {
        return new DoArchiveContractResponse();
    }

    /**
     * Create an instance of {@link DoSendSmgToGuarantor }
     * 
     */
    public DoSendSmgToGuarantor createDoSendSmgToGuarantor() {
        return new DoSendSmgToGuarantor();
    }

    /**
     * Create an instance of {@link GetBorrowGuarantorByBorrowIdResponse }
     * 
     */
    public GetBorrowGuarantorByBorrowIdResponse createGetBorrowGuarantorByBorrowIdResponse() {
        return new GetBorrowGuarantorByBorrowIdResponse();
    }

    /**
     * Create an instance of {@link GetGuarantorSignStatusByborrowId }
     * 
     */
    public GetGuarantorSignStatusByborrowId createGetGuarantorSignStatusByborrowId() {
        return new GetGuarantorSignStatusByborrowId();
    }

    /**
     * Create an instance of {@link DoSendSmgToGuarantorResponse }
     * 
     */
    public DoSendSmgToGuarantorResponse createDoSendSmgToGuarantorResponse() {
        return new DoSendSmgToGuarantorResponse();
    }

    /**
     * Create an instance of {@link GetInvestorZipPathResponse }
     * 
     */
    public GetInvestorZipPathResponse createGetInvestorZipPathResponse() {
        return new GetInvestorZipPathResponse();
    }

    /**
     * Create an instance of {@link GetCompanyInfoByBorrowId }
     * 
     */
    public GetCompanyInfoByBorrowId createGetCompanyInfoByBorrowId() {
        return new GetCompanyInfoByBorrowId();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInvestorZipPath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getInvestorZipPath")
    public JAXBElement<GetInvestorZipPath> createGetInvestorZipPath(GetInvestorZipPath value) {
        return new JAXBElement<GetInvestorZipPath>(_GetInvestorZipPath_QNAME, GetInvestorZipPath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurBorrowPath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getCurBorrowPath")
    public JAXBElement<GetCurBorrowPath> createGetCurBorrowPath(GetCurBorrowPath value) {
        return new JAXBElement<GetCurBorrowPath>(_GetCurBorrowPath_QNAME, GetCurBorrowPath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSigningContractReturnResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doSigningContractReturnResponse")
    public JAXBElement<DoSigningContractReturnResponse> createDoSigningContractReturnResponse(DoSigningContractReturnResponse value) {
        return new JAXBElement<DoSigningContractReturnResponse>(_DoSigningContractReturnResponse_QNAME, DoSigningContractReturnResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoUploadContractAndSaveInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doUploadContractAndSaveInfo")
    public JAXBElement<DoUploadContractAndSaveInfo> createDoUploadContractAndSaveInfo(DoUploadContractAndSaveInfo value) {
        return new JAXBElement<DoUploadContractAndSaveInfo>(_DoUploadContractAndSaveInfo_QNAME, DoUploadContractAndSaveInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoUserCA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doUserCA")
    public JAXBElement<DoUserCA> createDoUserCA(DoUserCA value) {
        return new JAXBElement<DoUserCA>(_DoUserCA_QNAME, DoUserCA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoViewContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doViewContract")
    public JAXBElement<DoViewContract> createDoViewContract(DoViewContract value) {
        return new JAXBElement<DoViewContract>(_DoViewContract_QNAME, DoViewContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoUploadContractAndSaveInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doUploadContractAndSaveInfoResponse")
    public JAXBElement<DoUploadContractAndSaveInfoResponse> createDoUploadContractAndSaveInfoResponse(DoUploadContractAndSaveInfoResponse value) {
        return new JAXBElement<DoUploadContractAndSaveInfoResponse>(_DoUploadContractAndSaveInfoResponse_QNAME, DoUploadContractAndSaveInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFadadaSignStatusByborrowId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getFadadaSignStatusByborrowId")
    public JAXBElement<GetFadadaSignStatusByborrowId> createGetFadadaSignStatusByborrowId(GetFadadaSignStatusByborrowId value) {
        return new JAXBElement<GetFadadaSignStatusByborrowId>(_GetFadadaSignStatusByborrowId_QNAME, GetFadadaSignStatusByborrowId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoArchiveContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doArchiveContractResponse")
    public JAXBElement<DoArchiveContractResponse> createDoArchiveContractResponse(DoArchiveContractResponse value) {
        return new JAXBElement<DoArchiveContractResponse>(_DoArchiveContractResponse_QNAME, DoArchiveContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoDownloadContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doDownloadContract")
    public JAXBElement<DoDownloadContract> createDoDownloadContract(DoDownloadContract value) {
        return new JAXBElement<DoDownloadContract>(_DoDownloadContract_QNAME, DoDownloadContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBorrowGuarantorByBorrowId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getBorrowGuarantorByBorrowId")
    public JAXBElement<GetBorrowGuarantorByBorrowId> createGetBorrowGuarantorByBorrowId(GetBorrowGuarantorByBorrowId value) {
        return new JAXBElement<GetBorrowGuarantorByBorrowId>(_GetBorrowGuarantorByBorrowId_QNAME, GetBorrowGuarantorByBorrowId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoUserCAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doUserCAResponse")
    public JAXBElement<DoUserCAResponse> createDoUserCAResponse(DoUserCAResponse value) {
        return new JAXBElement<DoUserCAResponse>(_DoUserCAResponse_QNAME, DoUserCAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetExtBorrowGuarantorByBorrowId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getExtBorrowGuarantorByBorrowId")
    public JAXBElement<GetExtBorrowGuarantorByBorrowId> createGetExtBorrowGuarantorByBorrowId(GetExtBorrowGuarantorByBorrowId value) {
        return new JAXBElement<GetExtBorrowGuarantorByBorrowId>(_GetExtBorrowGuarantorByBorrowId_QNAME, GetExtBorrowGuarantorByBorrowId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSendSmgToGuarantorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doSendSmgToGuarantorResponse")
    public JAXBElement<DoSendSmgToGuarantorResponse> createDoSendSmgToGuarantorResponse(DoSendSmgToGuarantorResponse value) {
        return new JAXBElement<DoSendSmgToGuarantorResponse>(_DoSendSmgToGuarantorResponse_QNAME, DoSendSmgToGuarantorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGuarantorSignStatusByborrowId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getGuarantorSignStatusByborrowId")
    public JAXBElement<GetGuarantorSignStatusByborrowId> createGetGuarantorSignStatusByborrowId(GetGuarantorSignStatusByborrowId value) {
        return new JAXBElement<GetGuarantorSignStatusByborrowId>(_GetGuarantorSignStatusByborrowId_QNAME, GetGuarantorSignStatusByborrowId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBorrowGuarantorByBorrowIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getBorrowGuarantorByBorrowIdResponse")
    public JAXBElement<GetBorrowGuarantorByBorrowIdResponse> createGetBorrowGuarantorByBorrowIdResponse(GetBorrowGuarantorByBorrowIdResponse value) {
        return new JAXBElement<GetBorrowGuarantorByBorrowIdResponse>(_GetBorrowGuarantorByBorrowIdResponse_QNAME, GetBorrowGuarantorByBorrowIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSendSmgToGuarantor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doSendSmgToGuarantor")
    public JAXBElement<DoSendSmgToGuarantor> createDoSendSmgToGuarantor(DoSendSmgToGuarantor value) {
        return new JAXBElement<DoSendSmgToGuarantor>(_DoSendSmgToGuarantor_QNAME, DoSendSmgToGuarantor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompanyInfoByBorrowId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getCompanyInfoByBorrowId")
    public JAXBElement<GetCompanyInfoByBorrowId> createGetCompanyInfoByBorrowId(GetCompanyInfoByBorrowId value) {
        return new JAXBElement<GetCompanyInfoByBorrowId>(_GetCompanyInfoByBorrowId_QNAME, GetCompanyInfoByBorrowId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInvestorZipPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getInvestorZipPathResponse")
    public JAXBElement<GetInvestorZipPathResponse> createGetInvestorZipPathResponse(GetInvestorZipPathResponse value) {
        return new JAXBElement<GetInvestorZipPathResponse>(_GetInvestorZipPathResponse_QNAME, GetInvestorZipPathResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoGuarantorCA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doGuarantorCA")
    public JAXBElement<DoGuarantorCA> createDoGuarantorCA(DoGuarantorCA value) {
        return new JAXBElement<DoGuarantorCA>(_DoGuarantorCA_QNAME, DoGuarantorCA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetExtBorrowGuarantorByBorrowIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getExtBorrowGuarantorByBorrowIdResponse")
    public JAXBElement<GetExtBorrowGuarantorByBorrowIdResponse> createGetExtBorrowGuarantorByBorrowIdResponse(GetExtBorrowGuarantorByBorrowIdResponse value) {
        return new JAXBElement<GetExtBorrowGuarantorByBorrowIdResponse>(_GetExtBorrowGuarantorByBorrowIdResponse_QNAME, GetExtBorrowGuarantorByBorrowIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractSavePathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getContractSavePathResponse")
    public JAXBElement<GetContractSavePathResponse> createGetContractSavePathResponse(GetContractSavePathResponse value) {
        return new JAXBElement<GetContractSavePathResponse>(_GetContractSavePathResponse_QNAME, GetContractSavePathResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBorrowerZipPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getBorrowerZipPathResponse")
    public JAXBElement<GetBorrowerZipPathResponse> createGetBorrowerZipPathResponse(GetBorrowerZipPathResponse value) {
        return new JAXBElement<GetBorrowerZipPathResponse>(_GetBorrowerZipPathResponse_QNAME, GetBorrowerZipPathResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckBorrowContractZipIsExist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "checkBorrowContractZipIsExist")
    public JAXBElement<CheckBorrowContractZipIsExist> createCheckBorrowContractZipIsExist(CheckBorrowContractZipIsExist value) {
        return new JAXBElement<CheckBorrowContractZipIsExist>(_CheckBorrowContractZipIsExist_QNAME, CheckBorrowContractZipIsExist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckSZRongContractZipIsExistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "checkSZRongContractZipIsExistResponse")
    public JAXBElement<CheckSZRongContractZipIsExistResponse> createCheckSZRongContractZipIsExistResponse(CheckSZRongContractZipIsExistResponse value) {
        return new JAXBElement<CheckSZRongContractZipIsExistResponse>(_CheckSZRongContractZipIsExistResponse_QNAME, CheckSZRongContractZipIsExistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBorrowCreditInfoByBorrowId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getBorrowCreditInfoByBorrowId")
    public JAXBElement<GetBorrowCreditInfoByBorrowId> createGetBorrowCreditInfoByBorrowId(GetBorrowCreditInfoByBorrowId value) {
        return new JAXBElement<GetBorrowCreditInfoByBorrowId>(_GetBorrowCreditInfoByBorrowId_QNAME, GetBorrowCreditInfoByBorrowId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSigningContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doSigningContractResponse")
    public JAXBElement<DoSigningContractResponse> createDoSigningContractResponse(DoSigningContractResponse value) {
        return new JAXBElement<DoSigningContractResponse>(_DoSigningContractResponse_QNAME, DoSigningContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOriginDocContractPath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getOriginDocContractPath")
    public JAXBElement<GetOriginDocContractPath> createGetOriginDocContractPath(GetOriginDocContractPath value) {
        return new JAXBElement<GetOriginDocContractPath>(_GetOriginDocContractPath_QNAME, GetOriginDocContractPath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoApCA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doApCA")
    public JAXBElement<DoApCA> createDoApCA(DoApCA value) {
        return new JAXBElement<DoApCA>(_DoApCA_QNAME, DoApCA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractSavePath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getContractSavePath")
    public JAXBElement<GetContractSavePath> createGetContractSavePath(GetContractSavePath value) {
        return new JAXBElement<GetContractSavePath>(_GetContractSavePath_QNAME, GetContractSavePath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractArchiveAndDownloadStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getContractArchiveAndDownloadStatusResponse")
    public JAXBElement<GetContractArchiveAndDownloadStatusResponse> createGetContractArchiveAndDownloadStatusResponse(GetContractArchiveAndDownloadStatusResponse value) {
        return new JAXBElement<GetContractArchiveAndDownloadStatusResponse>(_GetContractArchiveAndDownloadStatusResponse_QNAME, GetContractArchiveAndDownloadStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoExtGuarantorCAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doExtGuarantorCAResponse")
    public JAXBElement<DoExtGuarantorCAResponse> createDoExtGuarantorCAResponse(DoExtGuarantorCAResponse value) {
        return new JAXBElement<DoExtGuarantorCAResponse>(_DoExtGuarantorCAResponse_QNAME, DoExtGuarantorCAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSigningContractReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doSigningContractReturn")
    public JAXBElement<DoSigningContractReturn> createDoSigningContractReturn(DoSigningContractReturn value) {
        return new JAXBElement<DoSigningContractReturn>(_DoSigningContractReturn_QNAME, DoSigningContractReturn.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoAutoSigningContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doAutoSigningContractResponse")
    public JAXBElement<DoAutoSigningContractResponse> createDoAutoSigningContractResponse(DoAutoSigningContractResponse value) {
        return new JAXBElement<DoAutoSigningContractResponse>(_DoAutoSigningContractResponse_QNAME, DoAutoSigningContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractCodeByborrowIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getContractCodeByborrowIdResponse")
    public JAXBElement<GetContractCodeByborrowIdResponse> createGetContractCodeByborrowIdResponse(GetContractCodeByborrowIdResponse value) {
        return new JAXBElement<GetContractCodeByborrowIdResponse>(_GetContractCodeByborrowIdResponse_QNAME, GetContractCodeByborrowIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOriginDocContractPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getOriginDocContractPathResponse")
    public JAXBElement<GetOriginDocContractPathResponse> createGetOriginDocContractPathResponse(GetOriginDocContractPathResponse value) {
        return new JAXBElement<GetOriginDocContractPathResponse>(_GetOriginDocContractPathResponse_QNAME, GetOriginDocContractPathResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurBorrowPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getCurBorrowPathResponse")
    public JAXBElement<GetCurBorrowPathResponse> createGetCurBorrowPathResponse(GetCurBorrowPathResponse value) {
        return new JAXBElement<GetCurBorrowPathResponse>(_GetCurBorrowPathResponse_QNAME, GetCurBorrowPathResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBorrowCreditInfoByBorrowIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getBorrowCreditInfoByBorrowIdResponse")
    public JAXBElement<GetBorrowCreditInfoByBorrowIdResponse> createGetBorrowCreditInfoByBorrowIdResponse(GetBorrowCreditInfoByBorrowIdResponse value) {
        return new JAXBElement<GetBorrowCreditInfoByBorrowIdResponse>(_GetBorrowCreditInfoByBorrowIdResponse_QNAME, GetBorrowCreditInfoByBorrowIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoExtGuarantorCA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doExtGuarantorCA")
    public JAXBElement<DoExtGuarantorCA> createDoExtGuarantorCA(DoExtGuarantorCA value) {
        return new JAXBElement<DoExtGuarantorCA>(_DoExtGuarantorCA_QNAME, DoExtGuarantorCA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoGuarantorCAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doGuarantorCAResponse")
    public JAXBElement<DoGuarantorCAResponse> createDoGuarantorCAResponse(DoGuarantorCAResponse value) {
        return new JAXBElement<DoGuarantorCAResponse>(_DoGuarantorCAResponse_QNAME, DoGuarantorCAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoDownloadContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doDownloadContractResponse")
    public JAXBElement<DoDownloadContractResponse> createDoDownloadContractResponse(DoDownloadContractResponse value) {
        return new JAXBElement<DoDownloadContractResponse>(_DoDownloadContractResponse_QNAME, DoDownloadContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGuarantorSignStatusByborrowIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getGuarantorSignStatusByborrowIdResponse")
    public JAXBElement<GetGuarantorSignStatusByborrowIdResponse> createGetGuarantorSignStatusByborrowIdResponse(GetGuarantorSignStatusByborrowIdResponse value) {
        return new JAXBElement<GetGuarantorSignStatusByborrowIdResponse>(_GetGuarantorSignStatusByborrowIdResponse_QNAME, GetGuarantorSignStatusByborrowIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoViewContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doViewContractResponse")
    public JAXBElement<DoViewContractResponse> createDoViewContractResponse(DoViewContractResponse value) {
        return new JAXBElement<DoViewContractResponse>(_DoViewContractResponse_QNAME, DoViewContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckBorrowContractZipIsExistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "checkBorrowContractZipIsExistResponse")
    public JAXBElement<CheckBorrowContractZipIsExistResponse> createCheckBorrowContractZipIsExistResponse(CheckBorrowContractZipIsExistResponse value) {
        return new JAXBElement<CheckBorrowContractZipIsExistResponse>(_CheckBorrowContractZipIsExistResponse_QNAME, CheckBorrowContractZipIsExistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFadadaSignStatusByborrowIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getFadadaSignStatusByborrowIdResponse")
    public JAXBElement<GetFadadaSignStatusByborrowIdResponse> createGetFadadaSignStatusByborrowIdResponse(GetFadadaSignStatusByborrowIdResponse value) {
        return new JAXBElement<GetFadadaSignStatusByborrowIdResponse>(_GetFadadaSignStatusByborrowIdResponse_QNAME, GetFadadaSignStatusByborrowIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoApCAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doApCAResponse")
    public JAXBElement<DoApCAResponse> createDoApCAResponse(DoApCAResponse value) {
        return new JAXBElement<DoApCAResponse>(_DoApCAResponse_QNAME, DoApCAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractArchiveAndDownloadStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getContractArchiveAndDownloadStatus")
    public JAXBElement<GetContractArchiveAndDownloadStatus> createGetContractArchiveAndDownloadStatus(GetContractArchiveAndDownloadStatus value) {
        return new JAXBElement<GetContractArchiveAndDownloadStatus>(_GetContractArchiveAndDownloadStatus_QNAME, GetContractArchiveAndDownloadStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBorrowerZipPath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getBorrowerZipPath")
    public JAXBElement<GetBorrowerZipPath> createGetBorrowerZipPath(GetBorrowerZipPath value) {
        return new JAXBElement<GetBorrowerZipPath>(_GetBorrowerZipPath_QNAME, GetBorrowerZipPath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSigningContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doSigningContract")
    public JAXBElement<DoSigningContract> createDoSigningContract(DoSigningContract value) {
        return new JAXBElement<DoSigningContract>(_DoSigningContract_QNAME, DoSigningContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompanyInfoByBorrowIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getCompanyInfoByBorrowIdResponse")
    public JAXBElement<GetCompanyInfoByBorrowIdResponse> createGetCompanyInfoByBorrowIdResponse(GetCompanyInfoByBorrowIdResponse value) {
        return new JAXBElement<GetCompanyInfoByBorrowIdResponse>(_GetCompanyInfoByBorrowIdResponse_QNAME, GetCompanyInfoByBorrowIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoAutoSigningContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doAutoSigningContract")
    public JAXBElement<DoAutoSigningContract> createDoAutoSigningContract(DoAutoSigningContract value) {
        return new JAXBElement<DoAutoSigningContract>(_DoAutoSigningContract_QNAME, DoAutoSigningContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractCodeByborrowId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "getContractCodeByborrowId")
    public JAXBElement<GetContractCodeByborrowId> createGetContractCodeByborrowId(GetContractCodeByborrowId value) {
        return new JAXBElement<GetContractCodeByborrowId>(_GetContractCodeByborrowId_QNAME, GetContractCodeByborrowId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckSZRongContractZipIsExist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "checkSZRongContractZipIsExist")
    public JAXBElement<CheckSZRongContractZipIsExist> createCheckSZRongContractZipIsExist(CheckSZRongContractZipIsExist value) {
        return new JAXBElement<CheckSZRongContractZipIsExist>(_CheckSZRongContractZipIsExist_QNAME, CheckSZRongContractZipIsExist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoArchiveContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.fadada.xxdai.com/", name = "doArchiveContract")
    public JAXBElement<DoArchiveContract> createDoArchiveContract(DoArchiveContract value) {
        return new JAXBElement<DoArchiveContract>(_DoArchiveContract_QNAME, DoArchiveContract.class, null, value);
    }

}
