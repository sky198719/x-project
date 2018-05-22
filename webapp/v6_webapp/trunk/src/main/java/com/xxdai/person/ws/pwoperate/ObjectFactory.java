
package com.xxdai.person.ws.pwoperate;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.person.ws.pwoperate package. 
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

    private final static QName _UpdateUserPwdProResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "updateUserPwdProResponse");
    private final static QName _QueryUserResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryUserResponse");
    private final static QName _UpdateUserPwdPro_QNAME = new QName("http://interfaces.webService.xxdai.com/", "updateUserPwdPro");
    private final static QName _QueryUser_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryUser");
    private final static QName _UpdatePW_QNAME = new QName("http://interfaces.webService.xxdai.com/", "updatePW");
    private final static QName _UpdatePWResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "updatePWResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.person.ws.pwoperate
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdatePWResponse }
     * 
     */
    public UpdatePWResponse createUpdatePWResponse() {
        return new UpdatePWResponse();
    }

    /**
     * Create an instance of {@link UpdatePW }
     * 
     */
    public UpdatePW createUpdatePW() {
        return new UpdatePW();
    }

    /**
     * Create an instance of {@link QueryUser }
     * 
     */
    public QueryUser createQueryUser() {
        return new QueryUser();
    }

    /**
     * Create an instance of {@link UpdateUserPwdPro }
     * 
     */
    public UpdateUserPwdPro createUpdateUserPwdPro() {
        return new UpdateUserPwdPro();
    }

    /**
     * Create an instance of {@link QueryUserResponse }
     * 
     */
    public QueryUserResponse createQueryUserResponse() {
        return new QueryUserResponse();
    }

    /**
     * Create an instance of {@link UpdateUserPwdProResponse }
     * 
     */
    public UpdateUserPwdProResponse createUpdateUserPwdProResponse() {
        return new UpdateUserPwdProResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserPwdProResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "updateUserPwdProResponse")
    public JAXBElement<UpdateUserPwdProResponse> createUpdateUserPwdProResponse(UpdateUserPwdProResponse value) {
        return new JAXBElement<UpdateUserPwdProResponse>(_UpdateUserPwdProResponse_QNAME, UpdateUserPwdProResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryUserResponse")
    public JAXBElement<QueryUserResponse> createQueryUserResponse(QueryUserResponse value) {
        return new JAXBElement<QueryUserResponse>(_QueryUserResponse_QNAME, QueryUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserPwdPro }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "updateUserPwdPro")
    public JAXBElement<UpdateUserPwdPro> createUpdateUserPwdPro(UpdateUserPwdPro value) {
        return new JAXBElement<UpdateUserPwdPro>(_UpdateUserPwdPro_QNAME, UpdateUserPwdPro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryUser")
    public JAXBElement<QueryUser> createQueryUser(QueryUser value) {
        return new JAXBElement<QueryUser>(_QueryUser_QNAME, QueryUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePW }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "updatePW")
    public JAXBElement<UpdatePW> createUpdatePW(UpdatePW value) {
        return new JAXBElement<UpdatePW>(_UpdatePW_QNAME, UpdatePW.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePWResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "updatePWResponse")
    public JAXBElement<UpdatePWResponse> createUpdatePWResponse(UpdatePWResponse value) {
        return new JAXBElement<UpdatePWResponse>(_UpdatePWResponse_QNAME, UpdatePWResponse.class, null, value);
    }

}
