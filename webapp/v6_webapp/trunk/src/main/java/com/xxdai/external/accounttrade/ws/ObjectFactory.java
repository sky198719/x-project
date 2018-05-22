
package com.xxdai.external.accounttrade.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.accounttrade.ws package. 
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

    private final static QName _InitialAccountResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "initialAccountResponse");
    private final static QName _InitialAccount_QNAME = new QName("http://webservice.account.xxdai.com/", "initialAccount");
    private final static QName _TransferToXplanAccount_QNAME = new QName("http://webservice.account.xxdai.com/", "transferToXplanAccount");
    private final static QName _TransferToXplanAccountResponse_QNAME = new QName("http://webservice.account.xxdai.com/", "transferToXplanAccountResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.accounttrade.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InitialAccountResponse }
     * 
     */
    public InitialAccountResponse createInitialAccountResponse() {
        return new InitialAccountResponse();
    }

    /**
     * Create an instance of {@link TransferToXplanAccountResponse }
     * 
     */
    public TransferToXplanAccountResponse createTransferToXplanAccountResponse() {
        return new TransferToXplanAccountResponse();
    }

    /**
     * Create an instance of {@link TransferToXplanAccount }
     * 
     */
    public TransferToXplanAccount createTransferToXplanAccount() {
        return new TransferToXplanAccount();
    }

    /**
     * Create an instance of {@link InitialAccount }
     * 
     */
    public InitialAccount createInitialAccount() {
        return new InitialAccount();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitialAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "initialAccountResponse")
    public JAXBElement<InitialAccountResponse> createInitialAccountResponse(InitialAccountResponse value) {
        return new JAXBElement<InitialAccountResponse>(_InitialAccountResponse_QNAME, InitialAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitialAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "initialAccount")
    public JAXBElement<InitialAccount> createInitialAccount(InitialAccount value) {
        return new JAXBElement<InitialAccount>(_InitialAccount_QNAME, InitialAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferToXplanAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "transferToXplanAccount")
    public JAXBElement<TransferToXplanAccount> createTransferToXplanAccount(TransferToXplanAccount value) {
        return new JAXBElement<TransferToXplanAccount>(_TransferToXplanAccount_QNAME, TransferToXplanAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferToXplanAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.account.xxdai.com/", name = "transferToXplanAccountResponse")
    public JAXBElement<TransferToXplanAccountResponse> createTransferToXplanAccountResponse(TransferToXplanAccountResponse value) {
        return new JAXBElement<TransferToXplanAccountResponse>(_TransferToXplanAccountResponse_QNAME, TransferToXplanAccountResponse.class, null, value);
    }

}
