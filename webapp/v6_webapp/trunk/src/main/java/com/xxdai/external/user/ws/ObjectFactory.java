
package com.xxdai.external.user.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.user.ws package. 
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

    private final static QName _GetUserById_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserById");
    private final static QName _SaveCjdaoUser_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "saveCjdaoUser");
    private final static QName _RegistMobile_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registMobile");
    private final static QName _UniqueUserNameResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueUserNameResponse");
    private final static QName _CheckChangePasswordResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "CheckChangePasswordResponse");
    private final static QName _LoginResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "loginResponse");
    private final static QName _RegistV2_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registV2");
    private final static QName _RegistV3_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registV3");
    private final static QName _QueryUserByName_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "queryUserByName");
    private final static QName _UniqueEmailResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueEmailResponse");
    private final static QName _UpdateUserStatus_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "updateUserStatus");
    private final static QName _GetUserByEmailResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserByEmailResponse");
    private final static QName _QueryUserByNameResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "queryUserByNameResponse");
    private final static QName _UniqueEmail_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueEmail");
    private final static QName _UniqueNickNameResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueNickNameResponse");
    private final static QName _UniqueUserName_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueUserName");
    private final static QName _SaveCjdaoUserResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "saveCjdaoUserResponse");
    private final static QName _AddPayPassword_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "addPayPassword");
    private final static QName _UpdateUserStatusResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "updateUserStatusResponse");
    private final static QName _LoginUnlockResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "LoginUnlockResponse");
    private final static QName _UniqueNickName_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "uniqueNickName");
    private final static QName _SendEmailForPasswordResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "SendEmailForPasswordResponse");
    private final static QName _Regist_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "regist");
    private final static QName _IsXxdUser_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "isXxdUser");
    private final static QName _CheckLoginPasswordResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "checkLoginPasswordResponse");
    private final static QName _SelectUserLevelsResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "selectUserLevelsResponse");
    private final static QName _GetUserByEmail_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserByEmail");
    private final static QName _CheckLoginPassword_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "checkLoginPassword");
    private final static QName _SyncCjdRegUserInfoResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "syncCjdRegUserInfoResponse");
    private final static QName _SendEmailForLogin_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "SendEmailForLogin");
    private final static QName _LoginUnlock_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "LoginUnlock");
    private final static QName _SendEmailForPassword_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "SendEmailForPassword");
    private final static QName _GetUserByMobileNo_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserByMobileNo");
    private final static QName _CheckPayPassword_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "checkPayPassword");
    private final static QName _GetUserLoginSearch_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserLoginSearch");
    private final static QName _GetUserIntegralList_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserIntegralList");
    private final static QName _GetUserByIdResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserByIdResponse");
    private final static QName _QueryCjdaoUserResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "queryCjdaoUserResponse");
    private final static QName _UpdateUserLevelsResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "updateUserLevelsResponse");
    private final static QName _CheckChangePassword_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "CheckChangePassword");
    private final static QName _RegistResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registResponse");
    private final static QName _AddPayPasswordResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "addPayPasswordResponse");
    private final static QName _CheckLogin_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "CheckLogin");
    private final static QName _CheckLoginResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "CheckLoginResponse");
    private final static QName _UpdateCjdaoUserResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "updateCjdaoUserResponse");
    private final static QName _CheckPayPasswordResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "checkPayPasswordResponse");
    private final static QName _IsXxdUserResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "isXxdUserResponse");
    private final static QName _RegistV3Response_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registV3Response");
    private final static QName _RegistMobileResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registMobileResponse");
    private final static QName _CheckPswResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "CheckPswResponse");
    private final static QName _UpdateCjdaoUser_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "updateCjdaoUser");
    private final static QName _GetCurrentUserLevelsResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getCurrentUserLevelsResponse");
    private final static QName _GetUserIntegralListResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserIntegralListResponse");
    private final static QName _GetUserLoginSearchResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserLoginSearchResponse");
    private final static QName _SelectUserLevels_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "selectUserLevels");
    private final static QName _Login_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "login");
    private final static QName _GetUserByMobileNoResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getUserByMobileNoResponse");
    private final static QName _GetCurrentUserLevels_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "getCurrentUserLevels");
    private final static QName _UpdateUserLevels_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "updateUserLevels");
    private final static QName _SyncCjdRegUserInfo_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "syncCjdRegUserInfo");
    private final static QName _SendEmailForLoginResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "SendEmailForLoginResponse");
    private final static QName _CheckPsw_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "CheckPsw");
    private final static QName _ChangPassword_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "changPassword");
    private final static QName _QueryCjdaoUser_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "queryCjdaoUser");
    private final static QName _ChangPasswordResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "changPasswordResponse");
    private final static QName _RegistV2Response_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "registV2Response");
    private final static QName _InsertOrUpdateUserInfoExt_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "insertOrUpdateUserInfoExt");
    private final static QName _InsertOrUpdateUserInfoExtResponse_QNAME = new QName("http://user.interfaces.webService.xxdai.com/", "insertOrUpdateUserInfoExtResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.user.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCurrentUserLevelsResponse }
     * 
     */
    public GetCurrentUserLevelsResponse createGetCurrentUserLevelsResponse() {
        return new GetCurrentUserLevelsResponse();
    }

    /**
     * Create an instance of {@link InsertOrUpdateUserInfoExtResponse }
     *
     */
    public InsertOrUpdateUserInfoExtResponse createInsertOrUpdateUserInfoExtResponse() {
        return new InsertOrUpdateUserInfoExtResponse();
    }

    /**
     * Create an instance of {@link InsertOrUpdateUserInfoExt }
     *
     */
    public InsertOrUpdateUserInfoExt createInsertOrUpdateUserInfoExt() {
        return new InsertOrUpdateUserInfoExt();
    }

    /**
     * Create an instance of {@link GetUserIntegralListResponse }
     * 
     */
    public GetUserIntegralListResponse createGetUserIntegralListResponse() {
        return new GetUserIntegralListResponse();
    }

    /**
     * Create an instance of {@link GetUserLoginSearchResponse }
     * 
     */
    public GetUserLoginSearchResponse createGetUserLoginSearchResponse() {
        return new GetUserLoginSearchResponse();
    }

    /**
     * Create an instance of {@link RegistMobileResponse }
     * 
     */
    public RegistMobileResponse createRegistMobileResponse() {
        return new RegistMobileResponse();
    }

    /**
     * Create an instance of {@link CheckPswResponse }
     * 
     */
    public CheckPswResponse createCheckPswResponse() {
        return new CheckPswResponse();
    }

    /**
     * Create an instance of {@link UpdateCjdaoUser }
     * 
     */
    public UpdateCjdaoUser createUpdateCjdaoUser() {
        return new UpdateCjdaoUser();
    }

    /**
     * Create an instance of {@link SyncCjdRegUserInfo }
     * 
     */
    public SyncCjdRegUserInfo createSyncCjdRegUserInfo() {
        return new SyncCjdRegUserInfo();
    }

    /**
     * Create an instance of {@link SendEmailForLoginResponse }
     * 
     */
    public SendEmailForLoginResponse createSendEmailForLoginResponse() {
        return new SendEmailForLoginResponse();
    }

    /**
     * Create an instance of {@link CheckPsw }
     * 
     */
    public CheckPsw createCheckPsw() {
        return new CheckPsw();
    }

    /**
     * Create an instance of {@link ChangPassword }
     * 
     */
    public ChangPassword createChangPassword() {
        return new ChangPassword();
    }

    /**
     * Create an instance of {@link QueryCjdaoUser }
     * 
     */
    public QueryCjdaoUser createQueryCjdaoUser() {
        return new QueryCjdaoUser();
    }

    /**
     * Create an instance of {@link ChangPasswordResponse }
     * 
     */
    public ChangPasswordResponse createChangPasswordResponse() {
        return new ChangPasswordResponse();
    }

    /**
     * Create an instance of {@link RegistV2Response }
     * 
     */
    public RegistV2Response createRegistV2Response() {
        return new RegistV2Response();
    }

    /**
     * Create an instance of {@link SelectUserLevels }
     * 
     */
    public SelectUserLevels createSelectUserLevels() {
        return new SelectUserLevels();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link GetCurrentUserLevels }
     * 
     */
    public GetCurrentUserLevels createGetCurrentUserLevels() {
        return new GetCurrentUserLevels();
    }

    /**
     * Create an instance of {@link GetUserByMobileNoResponse }
     * 
     */
    public GetUserByMobileNoResponse createGetUserByMobileNoResponse() {
        return new GetUserByMobileNoResponse();
    }

    /**
     * Create an instance of {@link UpdateUserLevels }
     * 
     */
    public UpdateUserLevels createUpdateUserLevels() {
        return new UpdateUserLevels();
    }

    /**
     * Create an instance of {@link GetUserByIdResponse }
     * 
     */
    public GetUserByIdResponse createGetUserByIdResponse() {
        return new GetUserByIdResponse();
    }

    /**
     * Create an instance of {@link GetUserIntegralList }
     * 
     */
    public GetUserIntegralList createGetUserIntegralList() {
        return new GetUserIntegralList();
    }

    /**
     * Create an instance of {@link UpdateUserLevelsResponse }
     * 
     */
    public UpdateUserLevelsResponse createUpdateUserLevelsResponse() {
        return new UpdateUserLevelsResponse();
    }

    /**
     * Create an instance of {@link QueryCjdaoUserResponse }
     * 
     */
    public QueryCjdaoUserResponse createQueryCjdaoUserResponse() {
        return new QueryCjdaoUserResponse();
    }

    /**
     * Create an instance of {@link CheckChangePassword }
     * 
     */
    public CheckChangePassword createCheckChangePassword() {
        return new CheckChangePassword();
    }

    /**
     * Create an instance of {@link RegistResponse }
     * 
     */
    public RegistResponse createRegistResponse() {
        return new RegistResponse();
    }

    /**
     * Create an instance of {@link CheckPayPassword }
     * 
     */
    public CheckPayPassword createCheckPayPassword() {
        return new CheckPayPassword();
    }

    /**
     * Create an instance of {@link GetUserLoginSearch }
     * 
     */
    public GetUserLoginSearch createGetUserLoginSearch() {
        return new GetUserLoginSearch();
    }

    /**
     * Create an instance of {@link RegistV3Response }
     * 
     */
    public RegistV3Response createRegistV3Response() {
        return new RegistV3Response();
    }

    /**
     * Create an instance of {@link AddPayPasswordResponse }
     * 
     */
    public AddPayPasswordResponse createAddPayPasswordResponse() {
        return new AddPayPasswordResponse();
    }

    /**
     * Create an instance of {@link CheckLogin }
     * 
     */
    public CheckLogin createCheckLogin() {
        return new CheckLogin();
    }

    /**
     * Create an instance of {@link CheckLoginResponse }
     * 
     */
    public CheckLoginResponse createCheckLoginResponse() {
        return new CheckLoginResponse();
    }

    /**
     * Create an instance of {@link UpdateCjdaoUserResponse }
     * 
     */
    public UpdateCjdaoUserResponse createUpdateCjdaoUserResponse() {
        return new UpdateCjdaoUserResponse();
    }

    /**
     * Create an instance of {@link CheckPayPasswordResponse }
     * 
     */
    public CheckPayPasswordResponse createCheckPayPasswordResponse() {
        return new CheckPayPasswordResponse();
    }

    /**
     * Create an instance of {@link IsXxdUserResponse }
     * 
     */
    public IsXxdUserResponse createIsXxdUserResponse() {
        return new IsXxdUserResponse();
    }

    /**
     * Create an instance of {@link UpdateUserStatusResponse }
     * 
     */
    public UpdateUserStatusResponse createUpdateUserStatusResponse() {
        return new UpdateUserStatusResponse();
    }

    /**
     * Create an instance of {@link LoginUnlockResponse }
     * 
     */
    public LoginUnlockResponse createLoginUnlockResponse() {
        return new LoginUnlockResponse();
    }

    /**
     * Create an instance of {@link UniqueNickName }
     * 
     */
    public UniqueNickName createUniqueNickName() {
        return new UniqueNickName();
    }

    /**
     * Create an instance of {@link SendEmailForPasswordResponse }
     * 
     */
    public SendEmailForPasswordResponse createSendEmailForPasswordResponse() {
        return new SendEmailForPasswordResponse();
    }

    /**
     * Create an instance of {@link SaveCjdaoUserResponse }
     * 
     */
    public SaveCjdaoUserResponse createSaveCjdaoUserResponse() {
        return new SaveCjdaoUserResponse();
    }

    /**
     * Create an instance of {@link AddPayPassword }
     * 
     */
    public AddPayPassword createAddPayPassword() {
        return new AddPayPassword();
    }

    /**
     * Create an instance of {@link CheckLoginPassword }
     * 
     */
    public CheckLoginPassword createCheckLoginPassword() {
        return new CheckLoginPassword();
    }

    /**
     * Create an instance of {@link SyncCjdRegUserInfoResponse }
     * 
     */
    public SyncCjdRegUserInfoResponse createSyncCjdRegUserInfoResponse() {
        return new SyncCjdRegUserInfoResponse();
    }

    /**
     * Create an instance of {@link SendEmailForLogin }
     * 
     */
    public SendEmailForLogin createSendEmailForLogin() {
        return new SendEmailForLogin();
    }

    /**
     * Create an instance of {@link LoginUnlock }
     * 
     */
    public LoginUnlock createLoginUnlock() {
        return new LoginUnlock();
    }

    /**
     * Create an instance of {@link SendEmailForPassword }
     * 
     */
    public SendEmailForPassword createSendEmailForPassword() {
        return new SendEmailForPassword();
    }

    /**
     * Create an instance of {@link GetUserByMobileNo }
     * 
     */
    public GetUserByMobileNo createGetUserByMobileNo() {
        return new GetUserByMobileNo();
    }

    /**
     * Create an instance of {@link Regist }
     * 
     */
    public Regist createRegist() {
        return new Regist();
    }

    /**
     * Create an instance of {@link IsXxdUser }
     * 
     */
    public IsXxdUser createIsXxdUser() {
        return new IsXxdUser();
    }

    /**
     * Create an instance of {@link SelectUserLevelsResponse }
     * 
     */
    public SelectUserLevelsResponse createSelectUserLevelsResponse() {
        return new SelectUserLevelsResponse();
    }

    /**
     * Create an instance of {@link GetUserByEmail }
     * 
     */
    public GetUserByEmail createGetUserByEmail() {
        return new GetUserByEmail();
    }

    /**
     * Create an instance of {@link CheckLoginPasswordResponse }
     * 
     */
    public CheckLoginPasswordResponse createCheckLoginPasswordResponse() {
        return new CheckLoginPasswordResponse();
    }

    /**
     * Create an instance of {@link CheckChangePasswordResponse }
     * 
     */
    public CheckChangePasswordResponse createCheckChangePasswordResponse() {
        return new CheckChangePasswordResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link RegistV2 }
     * 
     */
    public RegistV2 createRegistV2() {
        return new RegistV2();
    }

    /**
     * Create an instance of {@link UniqueEmailResponse }
     * 
     */
    public UniqueEmailResponse createUniqueEmailResponse() {
        return new UniqueEmailResponse();
    }

    /**
     * Create an instance of {@link QueryUserByName }
     * 
     */
    public QueryUserByName createQueryUserByName() {
        return new QueryUserByName();
    }

    /**
     * Create an instance of {@link RegistV3 }
     * 
     */
    public RegistV3 createRegistV3() {
        return new RegistV3();
    }

    /**
     * Create an instance of {@link GetUserById }
     * 
     */
    public GetUserById createGetUserById() {
        return new GetUserById();
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
     * Create an instance of {@link SaveCjdaoUser }
     * 
     */
    public SaveCjdaoUser createSaveCjdaoUser() {
        return new SaveCjdaoUser();
    }

    /**
     * Create an instance of {@link UniqueEmail }
     * 
     */
    public UniqueEmail createUniqueEmail() {
        return new UniqueEmail();
    }

    /**
     * Create an instance of {@link UniqueNickNameResponse }
     * 
     */
    public UniqueNickNameResponse createUniqueNickNameResponse() {
        return new UniqueNickNameResponse();
    }

    /**
     * Create an instance of {@link UniqueUserName }
     * 
     */
    public UniqueUserName createUniqueUserName() {
        return new UniqueUserName();
    }

    /**
     * Create an instance of {@link UpdateUserStatus }
     * 
     */
    public UpdateUserStatus createUpdateUserStatus() {
        return new UpdateUserStatus();
    }

    /**
     * Create an instance of {@link GetUserByEmailResponse }
     * 
     */
    public GetUserByEmailResponse createGetUserByEmailResponse() {
        return new GetUserByEmailResponse();
    }

    /**
     * Create an instance of {@link QueryUserByNameResponse }
     * 
     */
    public QueryUserByNameResponse createQueryUserByNameResponse() {
        return new QueryUserByNameResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserById")
    public JAXBElement<GetUserById> createGetUserById(GetUserById value) {
        return new JAXBElement<GetUserById>(_GetUserById_QNAME, GetUserById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveCjdaoUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "saveCjdaoUser")
    public JAXBElement<SaveCjdaoUser> createSaveCjdaoUser(SaveCjdaoUser value) {
        return new JAXBElement<SaveCjdaoUser>(_SaveCjdaoUser_QNAME, SaveCjdaoUser.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckChangePasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "CheckChangePasswordResponse")
    public JAXBElement<CheckChangePasswordResponse> createCheckChangePasswordResponse(CheckChangePasswordResponse value) {
        return new JAXBElement<CheckChangePasswordResponse>(_CheckChangePasswordResponse_QNAME, CheckChangePasswordResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "registV2")
    public JAXBElement<RegistV2> createRegistV2(RegistV2 value) {
        return new JAXBElement<RegistV2>(_RegistV2_QNAME, RegistV2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistV3 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "registV3")
    public JAXBElement<RegistV3> createRegistV3(RegistV3 value) {
        return new JAXBElement<RegistV3>(_RegistV3_QNAME, RegistV3 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserByName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "queryUserByName")
    public JAXBElement<QueryUserByName> createQueryUserByName(QueryUserByName value) {
        return new JAXBElement<QueryUserByName>(_QueryUserByName_QNAME, QueryUserByName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UniqueEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "uniqueEmailResponse")
    public JAXBElement<UniqueEmailResponse> createUniqueEmailResponse(UniqueEmailResponse value) {
        return new JAXBElement<UniqueEmailResponse>(_UniqueEmailResponse_QNAME, UniqueEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "updateUserStatus")
    public JAXBElement<UpdateUserStatus> createUpdateUserStatus(UpdateUserStatus value) {
        return new JAXBElement<UpdateUserStatus>(_UpdateUserStatus_QNAME, UpdateUserStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserByEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserByEmailResponse")
    public JAXBElement<GetUserByEmailResponse> createGetUserByEmailResponse(GetUserByEmailResponse value) {
        return new JAXBElement<GetUserByEmailResponse>(_GetUserByEmailResponse_QNAME, GetUserByEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserByNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "queryUserByNameResponse")
    public JAXBElement<QueryUserByNameResponse> createQueryUserByNameResponse(QueryUserByNameResponse value) {
        return new JAXBElement<QueryUserByNameResponse>(_QueryUserByNameResponse_QNAME, QueryUserByNameResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UniqueNickNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "uniqueNickNameResponse")
    public JAXBElement<UniqueNickNameResponse> createUniqueNickNameResponse(UniqueNickNameResponse value) {
        return new JAXBElement<UniqueNickNameResponse>(_UniqueNickNameResponse_QNAME, UniqueNickNameResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveCjdaoUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "saveCjdaoUserResponse")
    public JAXBElement<SaveCjdaoUserResponse> createSaveCjdaoUserResponse(SaveCjdaoUserResponse value) {
        return new JAXBElement<SaveCjdaoUserResponse>(_SaveCjdaoUserResponse_QNAME, SaveCjdaoUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPayPassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "addPayPassword")
    public JAXBElement<AddPayPassword> createAddPayPassword(AddPayPassword value) {
        return new JAXBElement<AddPayPassword>(_AddPayPassword_QNAME, AddPayPassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "updateUserStatusResponse")
    public JAXBElement<UpdateUserStatusResponse> createUpdateUserStatusResponse(UpdateUserStatusResponse value) {
        return new JAXBElement<UpdateUserStatusResponse>(_UpdateUserStatusResponse_QNAME, UpdateUserStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginUnlockResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "LoginUnlockResponse")
    public JAXBElement<LoginUnlockResponse> createLoginUnlockResponse(LoginUnlockResponse value) {
        return new JAXBElement<LoginUnlockResponse>(_LoginUnlockResponse_QNAME, LoginUnlockResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UniqueNickName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "uniqueNickName")
    public JAXBElement<UniqueNickName> createUniqueNickName(UniqueNickName value) {
        return new JAXBElement<UniqueNickName>(_UniqueNickName_QNAME, UniqueNickName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendEmailForPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "SendEmailForPasswordResponse")
    public JAXBElement<SendEmailForPasswordResponse> createSendEmailForPasswordResponse(SendEmailForPasswordResponse value) {
        return new JAXBElement<SendEmailForPasswordResponse>(_SendEmailForPasswordResponse_QNAME, SendEmailForPasswordResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link IsXxdUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "isXxdUser")
    public JAXBElement<IsXxdUser> createIsXxdUser(IsXxdUser value) {
        return new JAXBElement<IsXxdUser>(_IsXxdUser_QNAME, IsXxdUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckLoginPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "checkLoginPasswordResponse")
    public JAXBElement<CheckLoginPasswordResponse> createCheckLoginPasswordResponse(CheckLoginPasswordResponse value) {
        return new JAXBElement<CheckLoginPasswordResponse>(_CheckLoginPasswordResponse_QNAME, CheckLoginPasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectUserLevelsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "selectUserLevelsResponse")
    public JAXBElement<SelectUserLevelsResponse> createSelectUserLevelsResponse(SelectUserLevelsResponse value) {
        return new JAXBElement<SelectUserLevelsResponse>(_SelectUserLevelsResponse_QNAME, SelectUserLevelsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserByEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserByEmail")
    public JAXBElement<GetUserByEmail> createGetUserByEmail(GetUserByEmail value) {
        return new JAXBElement<GetUserByEmail>(_GetUserByEmail_QNAME, GetUserByEmail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckLoginPassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "checkLoginPassword")
    public JAXBElement<CheckLoginPassword> createCheckLoginPassword(CheckLoginPassword value) {
        return new JAXBElement<CheckLoginPassword>(_CheckLoginPassword_QNAME, CheckLoginPassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SyncCjdRegUserInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "syncCjdRegUserInfoResponse")
    public JAXBElement<SyncCjdRegUserInfoResponse> createSyncCjdRegUserInfoResponse(SyncCjdRegUserInfoResponse value) {
        return new JAXBElement<SyncCjdRegUserInfoResponse>(_SyncCjdRegUserInfoResponse_QNAME, SyncCjdRegUserInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendEmailForLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "SendEmailForLogin")
    public JAXBElement<SendEmailForLogin> createSendEmailForLogin(SendEmailForLogin value) {
        return new JAXBElement<SendEmailForLogin>(_SendEmailForLogin_QNAME, SendEmailForLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginUnlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "LoginUnlock")
    public JAXBElement<LoginUnlock> createLoginUnlock(LoginUnlock value) {
        return new JAXBElement<LoginUnlock>(_LoginUnlock_QNAME, LoginUnlock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendEmailForPassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "SendEmailForPassword")
    public JAXBElement<SendEmailForPassword> createSendEmailForPassword(SendEmailForPassword value) {
        return new JAXBElement<SendEmailForPassword>(_SendEmailForPassword_QNAME, SendEmailForPassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserByMobileNo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserByMobileNo")
    public JAXBElement<GetUserByMobileNo> createGetUserByMobileNo(GetUserByMobileNo value) {
        return new JAXBElement<GetUserByMobileNo>(_GetUserByMobileNo_QNAME, GetUserByMobileNo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckPayPassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "checkPayPassword")
    public JAXBElement<CheckPayPassword> createCheckPayPassword(CheckPayPassword value) {
        return new JAXBElement<CheckPayPassword>(_CheckPayPassword_QNAME, CheckPayPassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserLoginSearch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserLoginSearch")
    public JAXBElement<GetUserLoginSearch> createGetUserLoginSearch(GetUserLoginSearch value) {
        return new JAXBElement<GetUserLoginSearch>(_GetUserLoginSearch_QNAME, GetUserLoginSearch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserIntegralList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserIntegralList")
    public JAXBElement<GetUserIntegralList> createGetUserIntegralList(GetUserIntegralList value) {
        return new JAXBElement<GetUserIntegralList>(_GetUserIntegralList_QNAME, GetUserIntegralList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserByIdResponse")
    public JAXBElement<GetUserByIdResponse> createGetUserByIdResponse(GetUserByIdResponse value) {
        return new JAXBElement<GetUserByIdResponse>(_GetUserByIdResponse_QNAME, GetUserByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCjdaoUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "queryCjdaoUserResponse")
    public JAXBElement<QueryCjdaoUserResponse> createQueryCjdaoUserResponse(QueryCjdaoUserResponse value) {
        return new JAXBElement<QueryCjdaoUserResponse>(_QueryCjdaoUserResponse_QNAME, QueryCjdaoUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserLevelsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "updateUserLevelsResponse")
    public JAXBElement<UpdateUserLevelsResponse> createUpdateUserLevelsResponse(UpdateUserLevelsResponse value) {
        return new JAXBElement<UpdateUserLevelsResponse>(_UpdateUserLevelsResponse_QNAME, UpdateUserLevelsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckChangePassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "CheckChangePassword")
    public JAXBElement<CheckChangePassword> createCheckChangePassword(CheckChangePassword value) {
        return new JAXBElement<CheckChangePassword>(_CheckChangePassword_QNAME, CheckChangePassword.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPayPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "addPayPasswordResponse")
    public JAXBElement<AddPayPasswordResponse> createAddPayPasswordResponse(AddPayPasswordResponse value) {
        return new JAXBElement<AddPayPasswordResponse>(_AddPayPasswordResponse_QNAME, AddPayPasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "CheckLogin")
    public JAXBElement<CheckLogin> createCheckLogin(CheckLogin value) {
        return new JAXBElement<CheckLogin>(_CheckLogin_QNAME, CheckLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "CheckLoginResponse")
    public JAXBElement<CheckLoginResponse> createCheckLoginResponse(CheckLoginResponse value) {
        return new JAXBElement<CheckLoginResponse>(_CheckLoginResponse_QNAME, CheckLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCjdaoUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "updateCjdaoUserResponse")
    public JAXBElement<UpdateCjdaoUserResponse> createUpdateCjdaoUserResponse(UpdateCjdaoUserResponse value) {
        return new JAXBElement<UpdateCjdaoUserResponse>(_UpdateCjdaoUserResponse_QNAME, UpdateCjdaoUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckPayPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "checkPayPasswordResponse")
    public JAXBElement<CheckPayPasswordResponse> createCheckPayPasswordResponse(CheckPayPasswordResponse value) {
        return new JAXBElement<CheckPayPasswordResponse>(_CheckPayPasswordResponse_QNAME, CheckPayPasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsXxdUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "isXxdUserResponse")
    public JAXBElement<IsXxdUserResponse> createIsXxdUserResponse(IsXxdUserResponse value) {
        return new JAXBElement<IsXxdUserResponse>(_IsXxdUserResponse_QNAME, IsXxdUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistV3Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "registV3Response")
    public JAXBElement<RegistV3Response> createRegistV3Response(RegistV3Response value) {
        return new JAXBElement<RegistV3Response>(_RegistV3Response_QNAME, RegistV3Response.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckPswResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "CheckPswResponse")
    public JAXBElement<CheckPswResponse> createCheckPswResponse(CheckPswResponse value) {
        return new JAXBElement<CheckPswResponse>(_CheckPswResponse_QNAME, CheckPswResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCjdaoUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "updateCjdaoUser")
    public JAXBElement<UpdateCjdaoUser> createUpdateCjdaoUser(UpdateCjdaoUser value) {
        return new JAXBElement<UpdateCjdaoUser>(_UpdateCjdaoUser_QNAME, UpdateCjdaoUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentUserLevelsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getCurrentUserLevelsResponse")
    public JAXBElement<GetCurrentUserLevelsResponse> createGetCurrentUserLevelsResponse(GetCurrentUserLevelsResponse value) {
        return new JAXBElement<GetCurrentUserLevelsResponse>(_GetCurrentUserLevelsResponse_QNAME, GetCurrentUserLevelsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserIntegralListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserIntegralListResponse")
    public JAXBElement<GetUserIntegralListResponse> createGetUserIntegralListResponse(GetUserIntegralListResponse value) {
        return new JAXBElement<GetUserIntegralListResponse>(_GetUserIntegralListResponse_QNAME, GetUserIntegralListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserLoginSearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserLoginSearchResponse")
    public JAXBElement<GetUserLoginSearchResponse> createGetUserLoginSearchResponse(GetUserLoginSearchResponse value) {
        return new JAXBElement<GetUserLoginSearchResponse>(_GetUserLoginSearchResponse_QNAME, GetUserLoginSearchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectUserLevels }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "selectUserLevels")
    public JAXBElement<SelectUserLevels> createSelectUserLevels(SelectUserLevels value) {
        return new JAXBElement<SelectUserLevels>(_SelectUserLevels_QNAME, SelectUserLevels.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserByMobileNoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getUserByMobileNoResponse")
    public JAXBElement<GetUserByMobileNoResponse> createGetUserByMobileNoResponse(GetUserByMobileNoResponse value) {
        return new JAXBElement<GetUserByMobileNoResponse>(_GetUserByMobileNoResponse_QNAME, GetUserByMobileNoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentUserLevels }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "getCurrentUserLevels")
    public JAXBElement<GetCurrentUserLevels> createGetCurrentUserLevels(GetCurrentUserLevels value) {
        return new JAXBElement<GetCurrentUserLevels>(_GetCurrentUserLevels_QNAME, GetCurrentUserLevels.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserLevels }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "updateUserLevels")
    public JAXBElement<UpdateUserLevels> createUpdateUserLevels(UpdateUserLevels value) {
        return new JAXBElement<UpdateUserLevels>(_UpdateUserLevels_QNAME, UpdateUserLevels.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SyncCjdRegUserInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "syncCjdRegUserInfo")
    public JAXBElement<SyncCjdRegUserInfo> createSyncCjdRegUserInfo(SyncCjdRegUserInfo value) {
        return new JAXBElement<SyncCjdRegUserInfo>(_SyncCjdRegUserInfo_QNAME, SyncCjdRegUserInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendEmailForLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "SendEmailForLoginResponse")
    public JAXBElement<SendEmailForLoginResponse> createSendEmailForLoginResponse(SendEmailForLoginResponse value) {
        return new JAXBElement<SendEmailForLoginResponse>(_SendEmailForLoginResponse_QNAME, SendEmailForLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckPsw }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "CheckPsw")
    public JAXBElement<CheckPsw> createCheckPsw(CheckPsw value) {
        return new JAXBElement<CheckPsw>(_CheckPsw_QNAME, CheckPsw.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangPassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "changPassword")
    public JAXBElement<ChangPassword> createChangPassword(ChangPassword value) {
        return new JAXBElement<ChangPassword>(_ChangPassword_QNAME, ChangPassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCjdaoUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "queryCjdaoUser")
    public JAXBElement<QueryCjdaoUser> createQueryCjdaoUser(QueryCjdaoUser value) {
        return new JAXBElement<QueryCjdaoUser>(_QueryCjdaoUser_QNAME, QueryCjdaoUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "changPasswordResponse")
    public JAXBElement<ChangPasswordResponse> createChangPasswordResponse(ChangPasswordResponse value) {
        return new JAXBElement<ChangPasswordResponse>(_ChangPasswordResponse_QNAME, ChangPasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "registV2Response")
    public JAXBElement<RegistV2Response> createRegistV2Response(RegistV2Response value) {
        return new JAXBElement<RegistV2Response>(_RegistV2Response_QNAME, RegistV2Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertOrUpdateUserInfoExt }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "insertOrUpdateUserInfoExt")
    public JAXBElement<InsertOrUpdateUserInfoExt> createInsertOrUpdateUserInfoExt(InsertOrUpdateUserInfoExt value) {
        return new JAXBElement<InsertOrUpdateUserInfoExt>(_InsertOrUpdateUserInfoExt_QNAME, InsertOrUpdateUserInfoExt.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertOrUpdateUserInfoExtResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://user.interfaces.webService.xxdai.com/", name = "insertOrUpdateUserInfoExtResponse")
    public JAXBElement<InsertOrUpdateUserInfoExtResponse> createInsertOrUpdateUserInfoExtResponse(InsertOrUpdateUserInfoExtResponse value) {
        return new JAXBElement<InsertOrUpdateUserInfoExtResponse>(_InsertOrUpdateUserInfoExtResponse_QNAME, InsertOrUpdateUserInfoExtResponse.class, null, value);
    }

}
