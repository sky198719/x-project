
package com.xxdai.external.account.ws.account;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.account.ws.account package. 
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

    private final static QName _SelectCashWhiteListResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectCashWhiteListResponse");
    private final static QName _SelectCashWhiteListLog_QNAME = new QName("http://webservice.account.xxdai.com/", "selectCashWhiteListLog");
    private final static QName _CheckCashWhiteListResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "checkCashWhiteListResponse");
    private final static QName _UpdateCashWhiteListResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "updateCashWhiteListResponse");
    private final static QName _CheckCashWhiteList_QNAME = new QName("http://webservice.account.xxdai.com/", "checkCashWhiteList");
    private final static QName _UpdateCashWhiteList_QNAME = new QName("http://webservice.account.xxdai.com/", "updateCashWhiteList");
    private final static QName _SelectCashWhiteListLogResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "selectCashWhiteListLogResponse");
    private final static QName _SelectCashWhiteList_QNAME = new QName("http://webservice.account.xxdai.com/", "selectCashWhiteList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.account.ws.account
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectCashWhiteListResponse }
     * 
     */
    public SelectCashWhiteListResponse createSelectCashWhiteListResponse() {
        return new SelectCashWhiteListResponse();
    }

    /**
     * Create an instance of {@link SelectCashWhiteListLog }
     * 
     */
    public SelectCashWhiteListLog createSelectCashWhiteListLog() {
        return new SelectCashWhiteListLog();
    }

    /**
     * Create an instance of {@link CheckCashWhiteListResponse }
     * 
     */
    public CheckCashWhiteListResponse createCheckCashWhiteListResponse() {
        return new CheckCashWhiteListResponse();
    }

    /**
     * Create an instance of {@link SelectCashWhiteList }
     * 
     */
    public SelectCashWhiteList createSelectCashWhiteList() {
        return new SelectCashWhiteList();
    }

    /**
     * Create an instance of {@link UpdateCashWhiteListResponse }
     * 
     */
    public UpdateCashWhiteListResponse createUpdateCashWhiteListResponse() {
        return new UpdateCashWhiteListResponse();
    }

    /**
     * Create an instance of {@link UpdateCashWhiteList }
     * 
     */
    public UpdateCashWhiteList createUpdateCashWhiteList() {
        return new UpdateCashWhiteList();
    }

    /**
     * Create an instance of {@link CheckCashWhiteList }
     * 
     */
    public CheckCashWhiteList createCheckCashWhiteList() {
        return new CheckCashWhiteList();
    }

    /**
     * Create an instance of {@link SelectCashWhiteListLogResponse }
     * 
     */
    public SelectCashWhiteListLogResponse createSelectCashWhiteListLogResponse() {
        return new SelectCashWhiteListLogResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectCashWhiteListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectCashWhiteListResponse")
    public JAXBElement<SelectCashWhiteListResponse> createSelectCashWhiteListResponse(SelectCashWhiteListResponse value) {
        return new JAXBElement<SelectCashWhiteListResponse>(_SelectCashWhiteListResponse_QNAME, SelectCashWhiteListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectCashWhiteListLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectCashWhiteListLog")
    public JAXBElement<SelectCashWhiteListLog> createSelectCashWhiteListLog(SelectCashWhiteListLog value) {
        return new JAXBElement<SelectCashWhiteListLog>(_SelectCashWhiteListLog_QNAME, SelectCashWhiteListLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckCashWhiteListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "checkCashWhiteListResponse")
    public JAXBElement<CheckCashWhiteListResponse> createCheckCashWhiteListResponse(CheckCashWhiteListResponse value) {
        return new JAXBElement<CheckCashWhiteListResponse>(_CheckCashWhiteListResponse_QNAME, CheckCashWhiteListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCashWhiteListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "updateCashWhiteListResponse")
    public JAXBElement<UpdateCashWhiteListResponse> createUpdateCashWhiteListResponse(UpdateCashWhiteListResponse value) {
        return new JAXBElement<UpdateCashWhiteListResponse>(_UpdateCashWhiteListResponse_QNAME, UpdateCashWhiteListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckCashWhiteList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "checkCashWhiteList")
    public JAXBElement<CheckCashWhiteList> createCheckCashWhiteList(CheckCashWhiteList value) {
        return new JAXBElement<CheckCashWhiteList>(_CheckCashWhiteList_QNAME, CheckCashWhiteList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCashWhiteList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "updateCashWhiteList")
    public JAXBElement<UpdateCashWhiteList> createUpdateCashWhiteList(UpdateCashWhiteList value) {
        return new JAXBElement<UpdateCashWhiteList>(_UpdateCashWhiteList_QNAME, UpdateCashWhiteList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectCashWhiteListLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectCashWhiteListLogResponse")
    public JAXBElement<SelectCashWhiteListLogResponse> createSelectCashWhiteListLogResponse(SelectCashWhiteListLogResponse value) {
        return new JAXBElement<SelectCashWhiteListLogResponse>(_SelectCashWhiteListLogResponse_QNAME, SelectCashWhiteListLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectCashWhiteList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "selectCashWhiteList")
    public JAXBElement<SelectCashWhiteList> createSelectCashWhiteList(SelectCashWhiteList value) {
        return new JAXBElement<SelectCashWhiteList>(_SelectCashWhiteList_QNAME, SelectCashWhiteList.class, null, value);
    }

}
