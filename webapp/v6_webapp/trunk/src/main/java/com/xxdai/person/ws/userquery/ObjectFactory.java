
package com.xxdai.person.ws.userquery;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.person.ws.userquery package. 
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

    private final static QName _QueryUserByNameResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryUserByNameResponse");
    private final static QName _UpdateUserHeadImg_QNAME = new QName("http://interfaces.webService.xxdai.com/", "updateUserHeadImg");
    private final static QName _QueryBaseInfoResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryBaseInfoResponse");
    private final static QName _SelectUserByMobile_QNAME = new QName("http://interfaces.webService.xxdai.com/", "selectUserByMobile");
    private final static QName _QueryByUserIdOrpwValue_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryByUserIdOrpwValue");
    private final static QName _SelectUserByMobileResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "selectUserByMobileResponse");
    private final static QName _QueryByUserIdOrpwValueResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryByUserIdOrpwValueResponse");
    private final static QName _QueryByIdResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryByIdResponse");
    private final static QName _UpdateUserHeadImgResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "updateUserHeadImgResponse");
    private final static QName _QueryUserByName_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryUserByName");
    private final static QName _SelectUserByEmail_QNAME = new QName("http://interfaces.webService.xxdai.com/", "selectUserByEmail");
    private final static QName _QueryById_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryById");
    private final static QName _SelectUserByEmailResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "selectUserByEmailResponse");
    private final static QName _QueryBaseInfo_QNAME = new QName("http://interfaces.webService.xxdai.com/", "queryBaseInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.person.ws.userquery
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryBaseInfo }
     * 
     */
    public QueryBaseInfo createQueryBaseInfo() {
        return new QueryBaseInfo();
    }

    /**
     * Create an instance of {@link SelectUserByEmailResponse }
     * 
     */
    public SelectUserByEmailResponse createSelectUserByEmailResponse() {
        return new SelectUserByEmailResponse();
    }

    /**
     * Create an instance of {@link QueryById }
     * 
     */
    public QueryById createQueryById() {
        return new QueryById();
    }

    /**
     * Create an instance of {@link SelectUserByEmail }
     * 
     */
    public SelectUserByEmail createSelectUserByEmail() {
        return new SelectUserByEmail();
    }

    /**
     * Create an instance of {@link QueryUserByName }
     * 
     */
    public QueryUserByName createQueryUserByName() {
        return new QueryUserByName();
    }

    /**
     * Create an instance of {@link UpdateUserHeadImgResponse }
     * 
     */
    public UpdateUserHeadImgResponse createUpdateUserHeadImgResponse() {
        return new UpdateUserHeadImgResponse();
    }

    /**
     * Create an instance of {@link QueryByIdResponse }
     * 
     */
    public QueryByIdResponse createQueryByIdResponse() {
        return new QueryByIdResponse();
    }

    /**
     * Create an instance of {@link QueryByUserIdOrpwValueResponse }
     * 
     */
    public QueryByUserIdOrpwValueResponse createQueryByUserIdOrpwValueResponse() {
        return new QueryByUserIdOrpwValueResponse();
    }

    /**
     * Create an instance of {@link SelectUserByMobileResponse }
     * 
     */
    public SelectUserByMobileResponse createSelectUserByMobileResponse() {
        return new SelectUserByMobileResponse();
    }

    /**
     * Create an instance of {@link QueryByUserIdOrpwValue }
     * 
     */
    public QueryByUserIdOrpwValue createQueryByUserIdOrpwValue() {
        return new QueryByUserIdOrpwValue();
    }

    /**
     * Create an instance of {@link SelectUserByMobile }
     * 
     */
    public SelectUserByMobile createSelectUserByMobile() {
        return new SelectUserByMobile();
    }

    /**
     * Create an instance of {@link QueryBaseInfoResponse }
     * 
     */
    public QueryBaseInfoResponse createQueryBaseInfoResponse() {
        return new QueryBaseInfoResponse();
    }

    /**
     * Create an instance of {@link UpdateUserHeadImg }
     * 
     */
    public UpdateUserHeadImg createUpdateUserHeadImg() {
        return new UpdateUserHeadImg();
    }

    /**
     * Create an instance of {@link QueryUserByNameResponse }
     * 
     */
    public QueryUserByNameResponse createQueryUserByNameResponse() {
        return new QueryUserByNameResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserByNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryUserByNameResponse")
    public JAXBElement<QueryUserByNameResponse> createQueryUserByNameResponse(QueryUserByNameResponse value) {
        return new JAXBElement<QueryUserByNameResponse>(_QueryUserByNameResponse_QNAME, QueryUserByNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserHeadImg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "updateUserHeadImg")
    public JAXBElement<UpdateUserHeadImg> createUpdateUserHeadImg(UpdateUserHeadImg value) {
        return new JAXBElement<UpdateUserHeadImg>(_UpdateUserHeadImg_QNAME, UpdateUserHeadImg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryBaseInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryBaseInfoResponse")
    public JAXBElement<QueryBaseInfoResponse> createQueryBaseInfoResponse(QueryBaseInfoResponse value) {
        return new JAXBElement<QueryBaseInfoResponse>(_QueryBaseInfoResponse_QNAME, QueryBaseInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectUserByMobile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "selectUserByMobile")
    public JAXBElement<SelectUserByMobile> createSelectUserByMobile(SelectUserByMobile value) {
        return new JAXBElement<SelectUserByMobile>(_SelectUserByMobile_QNAME, SelectUserByMobile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryByUserIdOrpwValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryByUserIdOrpwValue")
    public JAXBElement<QueryByUserIdOrpwValue> createQueryByUserIdOrpwValue(QueryByUserIdOrpwValue value) {
        return new JAXBElement<QueryByUserIdOrpwValue>(_QueryByUserIdOrpwValue_QNAME, QueryByUserIdOrpwValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectUserByMobileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "selectUserByMobileResponse")
    public JAXBElement<SelectUserByMobileResponse> createSelectUserByMobileResponse(SelectUserByMobileResponse value) {
        return new JAXBElement<SelectUserByMobileResponse>(_SelectUserByMobileResponse_QNAME, SelectUserByMobileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryByUserIdOrpwValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryByUserIdOrpwValueResponse")
    public JAXBElement<QueryByUserIdOrpwValueResponse> createQueryByUserIdOrpwValueResponse(QueryByUserIdOrpwValueResponse value) {
        return new JAXBElement<QueryByUserIdOrpwValueResponse>(_QueryByUserIdOrpwValueResponse_QNAME, QueryByUserIdOrpwValueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryByIdResponse")
    public JAXBElement<QueryByIdResponse> createQueryByIdResponse(QueryByIdResponse value) {
        return new JAXBElement<QueryByIdResponse>(_QueryByIdResponse_QNAME, QueryByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserHeadImgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "updateUserHeadImgResponse")
    public JAXBElement<UpdateUserHeadImgResponse> createUpdateUserHeadImgResponse(UpdateUserHeadImgResponse value) {
        return new JAXBElement<UpdateUserHeadImgResponse>(_UpdateUserHeadImgResponse_QNAME, UpdateUserHeadImgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserByName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryUserByName")
    public JAXBElement<QueryUserByName> createQueryUserByName(QueryUserByName value) {
        return new JAXBElement<QueryUserByName>(_QueryUserByName_QNAME, QueryUserByName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectUserByEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "selectUserByEmail")
    public JAXBElement<SelectUserByEmail> createSelectUserByEmail(SelectUserByEmail value) {
        return new JAXBElement<SelectUserByEmail>(_SelectUserByEmail_QNAME, SelectUserByEmail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryById")
    public JAXBElement<QueryById> createQueryById(QueryById value) {
        return new JAXBElement<QueryById>(_QueryById_QNAME, QueryById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectUserByEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "selectUserByEmailResponse")
    public JAXBElement<SelectUserByEmailResponse> createSelectUserByEmailResponse(SelectUserByEmailResponse value) {
        return new JAXBElement<SelectUserByEmailResponse>(_SelectUserByEmailResponse_QNAME, SelectUserByEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryBaseInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "queryBaseInfo")
    public JAXBElement<QueryBaseInfo> createQueryBaseInfo(QueryBaseInfo value) {
        return new JAXBElement<QueryBaseInfo>(_QueryBaseInfo_QNAME, QueryBaseInfo.class, null, value);
    }

}
