
package com.xxdai.person.ws.user;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.person.ws.user package. 
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

    private final static QName _Regist_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "regist");
    private final static QName _RegistMobileResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registMobileResponse");
    private final static QName _Login_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "login");
    private final static QName _RegistMobile_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registMobile");
    private final static QName _UniqueUserNameResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueUserNameResponse");
    private final static QName _UniqueEmail_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueEmail");
    private final static QName _UniqueUserName_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueUserName");
    private final static QName _LoginResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "loginResponse");
    private final static QName _RegistResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registResponse");
    private final static QName _UniqueEmailResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueEmailResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.person.ws.user
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UniqueEmail }
     * 
     */
    public UniqueEmail createUniqueEmail() {
        return new UniqueEmail();
    }

    /**
     * Create an instance of {@link UniqueUserName }
     * 
     */
    public UniqueUserName createUniqueUserName() {
        return new UniqueUserName();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link UniqueEmailResponse }
     * 
     */
    public UniqueEmailResponse createUniqueEmailResponse() {
        return new UniqueEmailResponse();
    }

    /**
     * Create an instance of {@link RegistResponse }
     * 
     */
    public RegistResponse createRegistResponse() {
        return new RegistResponse();
    }

    /**
     * Create an instance of {@link RegistMobileResponse }
     * 
     */
    public RegistMobileResponse createRegistMobileResponse() {
        return new RegistMobileResponse();
    }

    /**
     * Create an instance of {@link Regist }
     * 
     */
    public Regist createRegist() {
        return new Regist();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link UniqueUserNameResponse }
     * 
     */
    public UniqueUserNameResponse createUniqueUserNameResponse() {
        return new UniqueUserNameResponse();
    }

    /**
     * Create an instance of {@link RegistMobile }
     * 
     */
    public RegistMobile createRegistMobile() {
        return new RegistMobile();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Regist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "regist")
    public JAXBElement<Regist> createRegist(Regist value) {
        return new JAXBElement<Regist>(_Regist_QNAME, Regist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistMobileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "registMobileResponse")
    public JAXBElement<RegistMobileResponse> createRegistMobileResponse(RegistMobileResponse value) {
        return new JAXBElement<RegistMobileResponse>(_RegistMobileResponse_QNAME, RegistMobileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistMobile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "registMobile")
    public JAXBElement<RegistMobile> createRegistMobile(RegistMobile value) {
        return new JAXBElement<RegistMobile>(_RegistMobile_QNAME, RegistMobile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UniqueUserNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "uniqueUserNameResponse")
    public JAXBElement<UniqueUserNameResponse> createUniqueUserNameResponse(UniqueUserNameResponse value) {
        return new JAXBElement<UniqueUserNameResponse>(_UniqueUserNameResponse_QNAME, UniqueUserNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UniqueEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "uniqueEmail")
    public JAXBElement<UniqueEmail> createUniqueEmail(UniqueEmail value) {
        return new JAXBElement<UniqueEmail>(_UniqueEmail_QNAME, UniqueEmail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UniqueUserName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "uniqueUserName")
    public JAXBElement<UniqueUserName> createUniqueUserName(UniqueUserName value) {
        return new JAXBElement<UniqueUserName>(_UniqueUserName_QNAME, UniqueUserName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "registResponse")
    public JAXBElement<RegistResponse> createRegistResponse(RegistResponse value) {
        return new JAXBElement<RegistResponse>(_RegistResponse_QNAME, RegistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UniqueEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "uniqueEmailResponse")
    public JAXBElement<UniqueEmailResponse> createUniqueEmailResponse(UniqueEmailResponse value) {
        return new JAXBElement<UniqueEmailResponse>(_UniqueEmailResponse_QNAME, UniqueEmailResponse.class, null, value);
    }

}
