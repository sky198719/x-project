
package com.xxdai.external.weixin.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.weixin.ws package. 
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

    private final static QName _CheckMobileResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "checkMobileResponse");
    private final static QName _GetWeixinUserBind_QNAME = new QName("http://webservice.weixin.xxdai.com/", "getWeixinUserBind");
    private final static QName _GetWeixinUserResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "getWeixinUserResponse");
    private final static QName _GetWeixinUserBindResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "getWeixinUserBindResponse");
    private final static QName _RefreshAccessTokenResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "refreshAccessTokenResponse");
    private final static QName _SubscribeWeixinServiceNo_QNAME = new QName("http://webservice.weixin.xxdai.com/", "subscribeWeixinServiceNo");
    private final static QName _SaveWeixinUserResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "saveWeixinUserResponse");
    private final static QName _GoAcquireGiftResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "goAcquireGiftResponse");
    private final static QName _UpdateWeixinUser_QNAME = new QName("http://webservice.weixin.xxdai.com/", "updateWeixinUser");
    private final static QName _WeixinUserBind_QNAME = new QName("http://webservice.weixin.xxdai.com/", "weixinUserBind");
    private final static QName _CheckMobile_QNAME = new QName("http://webservice.weixin.xxdai.com/", "checkMobile");
    private final static QName _GetAccessToken_QNAME = new QName("http://webservice.weixin.xxdai.com/", "getAccessToken");
    private final static QName _GetAccessTokenResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "getAccessTokenResponse");
    private final static QName _SubscribeWeixinServiceNoResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "subscribeWeixinServiceNoResponse");
    private final static QName _UnSubscribeWeixinServiceNo_QNAME = new QName("http://webservice.weixin.xxdai.com/", "unSubscribeWeixinServiceNo");
    private final static QName _UnSubscribeWeixinServiceNoResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "unSubscribeWeixinServiceNoResponse");
    private final static QName _GetWeixinAccoiuntResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "getWeixinAccoiuntResponse");
    private final static QName _GetWeixinUser_QNAME = new QName("http://webservice.weixin.xxdai.com/", "getWeixinUser");
    private final static QName _SaveWeixinUser_QNAME = new QName("http://webservice.weixin.xxdai.com/", "saveWeixinUser");
    private final static QName _WeixinUserBindResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "weixinUserBindResponse");
    private final static QName _RefreshAccessToken_QNAME = new QName("http://webservice.weixin.xxdai.com/", "refreshAccessToken");
    private final static QName _IsActivityEnd_QNAME = new QName("http://webservice.weixin.xxdai.com/", "isActivityEnd");
    private final static QName _IsFollPacksResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "isFollPacksResponse");
    private final static QName _GetWeixinAccoiunt_QNAME = new QName("http://webservice.weixin.xxdai.com/", "getWeixinAccoiunt");
    private final static QName _IsFollPacks_QNAME = new QName("http://webservice.weixin.xxdai.com/", "isFollPacks");
    private final static QName _IsActivityEndResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "isActivityEndResponse");
    private final static QName _UpdateWeixinUserResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "updateWeixinUserResponse");
    private final static QName _GoAcquireGift_QNAME = new QName("http://webservice.weixin.xxdai.com/", "goAcquireGift");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.weixin.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GoAcquireGift }
     * 
     */
    public GoAcquireGift createGoAcquireGift() {
        return new GoAcquireGift();
    }

    /**
     * Create an instance of {@link UpdateWeixinUserResponse }
     * 
     */
    public UpdateWeixinUserResponse createUpdateWeixinUserResponse() {
        return new UpdateWeixinUserResponse();
    }

    /**
     * Create an instance of {@link IsActivityEndResponse }
     * 
     */
    public IsActivityEndResponse createIsActivityEndResponse() {
        return new IsActivityEndResponse();
    }

    /**
     * Create an instance of {@link IsFollPacks }
     * 
     */
    public IsFollPacks createIsFollPacks() {
        return new IsFollPacks();
    }

    /**
     * Create an instance of {@link GetWeixinAccoiunt }
     * 
     */
    public GetWeixinAccoiunt createGetWeixinAccoiunt() {
        return new GetWeixinAccoiunt();
    }

    /**
     * Create an instance of {@link IsFollPacksResponse }
     * 
     */
    public IsFollPacksResponse createIsFollPacksResponse() {
        return new IsFollPacksResponse();
    }

    /**
     * Create an instance of {@link IsActivityEnd }
     * 
     */
    public IsActivityEnd createIsActivityEnd() {
        return new IsActivityEnd();
    }

    /**
     * Create an instance of {@link RefreshAccessToken }
     * 
     */
    public RefreshAccessToken createRefreshAccessToken() {
        return new RefreshAccessToken();
    }

    /**
     * Create an instance of {@link WeixinUserBindResponse }
     * 
     */
    public WeixinUserBindResponse createWeixinUserBindResponse() {
        return new WeixinUserBindResponse();
    }

    /**
     * Create an instance of {@link SaveWeixinUser }
     * 
     */
    public SaveWeixinUser createSaveWeixinUser() {
        return new SaveWeixinUser();
    }

    /**
     * Create an instance of {@link GetWeixinUser }
     * 
     */
    public GetWeixinUser createGetWeixinUser() {
        return new GetWeixinUser();
    }

    /**
     * Create an instance of {@link GetWeixinAccoiuntResponse }
     * 
     */
    public GetWeixinAccoiuntResponse createGetWeixinAccoiuntResponse() {
        return new GetWeixinAccoiuntResponse();
    }

    /**
     * Create an instance of {@link UnSubscribeWeixinServiceNo }
     * 
     */
    public UnSubscribeWeixinServiceNo createUnSubscribeWeixinServiceNo() {
        return new UnSubscribeWeixinServiceNo();
    }

    /**
     * Create an instance of {@link UnSubscribeWeixinServiceNoResponse }
     * 
     */
    public UnSubscribeWeixinServiceNoResponse createUnSubscribeWeixinServiceNoResponse() {
        return new UnSubscribeWeixinServiceNoResponse();
    }

    /**
     * Create an instance of {@link GetAccessTokenResponse }
     * 
     */
    public GetAccessTokenResponse createGetAccessTokenResponse() {
        return new GetAccessTokenResponse();
    }

    /**
     * Create an instance of {@link SubscribeWeixinServiceNoResponse }
     * 
     */
    public SubscribeWeixinServiceNoResponse createSubscribeWeixinServiceNoResponse() {
        return new SubscribeWeixinServiceNoResponse();
    }

    /**
     * Create an instance of {@link GetAccessToken }
     * 
     */
    public GetAccessToken createGetAccessToken() {
        return new GetAccessToken();
    }

    /**
     * Create an instance of {@link CheckMobile }
     * 
     */
    public CheckMobile createCheckMobile() {
        return new CheckMobile();
    }

    /**
     * Create an instance of {@link WeixinUserBind }
     * 
     */
    public WeixinUserBind createWeixinUserBind() {
        return new WeixinUserBind();
    }

    /**
     * Create an instance of {@link GoAcquireGiftResponse }
     * 
     */
    public GoAcquireGiftResponse createGoAcquireGiftResponse() {
        return new GoAcquireGiftResponse();
    }

    /**
     * Create an instance of {@link UpdateWeixinUser }
     * 
     */
    public UpdateWeixinUser createUpdateWeixinUser() {
        return new UpdateWeixinUser();
    }

    /**
     * Create an instance of {@link SaveWeixinUserResponse }
     * 
     */
    public SaveWeixinUserResponse createSaveWeixinUserResponse() {
        return new SaveWeixinUserResponse();
    }

    /**
     * Create an instance of {@link SubscribeWeixinServiceNo }
     * 
     */
    public SubscribeWeixinServiceNo createSubscribeWeixinServiceNo() {
        return new SubscribeWeixinServiceNo();
    }

    /**
     * Create an instance of {@link RefreshAccessTokenResponse }
     * 
     */
    public RefreshAccessTokenResponse createRefreshAccessTokenResponse() {
        return new RefreshAccessTokenResponse();
    }

    /**
     * Create an instance of {@link GetWeixinUserBindResponse }
     * 
     */
    public GetWeixinUserBindResponse createGetWeixinUserBindResponse() {
        return new GetWeixinUserBindResponse();
    }

    /**
     * Create an instance of {@link GetWeixinUserResponse }
     * 
     */
    public GetWeixinUserResponse createGetWeixinUserResponse() {
        return new GetWeixinUserResponse();
    }

    /**
     * Create an instance of {@link GetWeixinUserBind }
     * 
     */
    public GetWeixinUserBind createGetWeixinUserBind() {
        return new GetWeixinUserBind();
    }

    /**
     * Create an instance of {@link CheckMobileResponse }
     * 
     */
    public CheckMobileResponse createCheckMobileResponse() {
        return new CheckMobileResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckMobileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "checkMobileResponse")
    public JAXBElement<CheckMobileResponse> createCheckMobileResponse(CheckMobileResponse value) {
        return new JAXBElement<CheckMobileResponse>(_CheckMobileResponse_QNAME, CheckMobileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeixinUserBind }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "getWeixinUserBind")
    public JAXBElement<GetWeixinUserBind> createGetWeixinUserBind(GetWeixinUserBind value) {
        return new JAXBElement<GetWeixinUserBind>(_GetWeixinUserBind_QNAME, GetWeixinUserBind.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeixinUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "getWeixinUserResponse")
    public JAXBElement<GetWeixinUserResponse> createGetWeixinUserResponse(GetWeixinUserResponse value) {
        return new JAXBElement<GetWeixinUserResponse>(_GetWeixinUserResponse_QNAME, GetWeixinUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeixinUserBindResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "getWeixinUserBindResponse")
    public JAXBElement<GetWeixinUserBindResponse> createGetWeixinUserBindResponse(GetWeixinUserBindResponse value) {
        return new JAXBElement<GetWeixinUserBindResponse>(_GetWeixinUserBindResponse_QNAME, GetWeixinUserBindResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshAccessTokenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "refreshAccessTokenResponse")
    public JAXBElement<RefreshAccessTokenResponse> createRefreshAccessTokenResponse(RefreshAccessTokenResponse value) {
        return new JAXBElement<RefreshAccessTokenResponse>(_RefreshAccessTokenResponse_QNAME, RefreshAccessTokenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeWeixinServiceNo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "subscribeWeixinServiceNo")
    public JAXBElement<SubscribeWeixinServiceNo> createSubscribeWeixinServiceNo(SubscribeWeixinServiceNo value) {
        return new JAXBElement<SubscribeWeixinServiceNo>(_SubscribeWeixinServiceNo_QNAME, SubscribeWeixinServiceNo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveWeixinUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "saveWeixinUserResponse")
    public JAXBElement<SaveWeixinUserResponse> createSaveWeixinUserResponse(SaveWeixinUserResponse value) {
        return new JAXBElement<SaveWeixinUserResponse>(_SaveWeixinUserResponse_QNAME, SaveWeixinUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GoAcquireGiftResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "goAcquireGiftResponse")
    public JAXBElement<GoAcquireGiftResponse> createGoAcquireGiftResponse(GoAcquireGiftResponse value) {
        return new JAXBElement<GoAcquireGiftResponse>(_GoAcquireGiftResponse_QNAME, GoAcquireGiftResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateWeixinUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "updateWeixinUser")
    public JAXBElement<UpdateWeixinUser> createUpdateWeixinUser(UpdateWeixinUser value) {
        return new JAXBElement<UpdateWeixinUser>(_UpdateWeixinUser_QNAME, UpdateWeixinUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WeixinUserBind }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "weixinUserBind")
    public JAXBElement<WeixinUserBind> createWeixinUserBind(WeixinUserBind value) {
        return new JAXBElement<WeixinUserBind>(_WeixinUserBind_QNAME, WeixinUserBind.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckMobile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "checkMobile")
    public JAXBElement<CheckMobile> createCheckMobile(CheckMobile value) {
        return new JAXBElement<CheckMobile>(_CheckMobile_QNAME, CheckMobile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccessToken }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "getAccessToken")
    public JAXBElement<GetAccessToken> createGetAccessToken(GetAccessToken value) {
        return new JAXBElement<GetAccessToken>(_GetAccessToken_QNAME, GetAccessToken.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccessTokenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "getAccessTokenResponse")
    public JAXBElement<GetAccessTokenResponse> createGetAccessTokenResponse(GetAccessTokenResponse value) {
        return new JAXBElement<GetAccessTokenResponse>(_GetAccessTokenResponse_QNAME, GetAccessTokenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeWeixinServiceNoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "subscribeWeixinServiceNoResponse")
    public JAXBElement<SubscribeWeixinServiceNoResponse> createSubscribeWeixinServiceNoResponse(SubscribeWeixinServiceNoResponse value) {
        return new JAXBElement<SubscribeWeixinServiceNoResponse>(_SubscribeWeixinServiceNoResponse_QNAME, SubscribeWeixinServiceNoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnSubscribeWeixinServiceNo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "unSubscribeWeixinServiceNo")
    public JAXBElement<UnSubscribeWeixinServiceNo> createUnSubscribeWeixinServiceNo(UnSubscribeWeixinServiceNo value) {
        return new JAXBElement<UnSubscribeWeixinServiceNo>(_UnSubscribeWeixinServiceNo_QNAME, UnSubscribeWeixinServiceNo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnSubscribeWeixinServiceNoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "unSubscribeWeixinServiceNoResponse")
    public JAXBElement<UnSubscribeWeixinServiceNoResponse> createUnSubscribeWeixinServiceNoResponse(UnSubscribeWeixinServiceNoResponse value) {
        return new JAXBElement<UnSubscribeWeixinServiceNoResponse>(_UnSubscribeWeixinServiceNoResponse_QNAME, UnSubscribeWeixinServiceNoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeixinAccoiuntResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "getWeixinAccoiuntResponse")
    public JAXBElement<GetWeixinAccoiuntResponse> createGetWeixinAccoiuntResponse(GetWeixinAccoiuntResponse value) {
        return new JAXBElement<GetWeixinAccoiuntResponse>(_GetWeixinAccoiuntResponse_QNAME, GetWeixinAccoiuntResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeixinUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "getWeixinUser")
    public JAXBElement<GetWeixinUser> createGetWeixinUser(GetWeixinUser value) {
        return new JAXBElement<GetWeixinUser>(_GetWeixinUser_QNAME, GetWeixinUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveWeixinUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "saveWeixinUser")
    public JAXBElement<SaveWeixinUser> createSaveWeixinUser(SaveWeixinUser value) {
        return new JAXBElement<SaveWeixinUser>(_SaveWeixinUser_QNAME, SaveWeixinUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WeixinUserBindResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "weixinUserBindResponse")
    public JAXBElement<WeixinUserBindResponse> createWeixinUserBindResponse(WeixinUserBindResponse value) {
        return new JAXBElement<WeixinUserBindResponse>(_WeixinUserBindResponse_QNAME, WeixinUserBindResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshAccessToken }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "refreshAccessToken")
    public JAXBElement<RefreshAccessToken> createRefreshAccessToken(RefreshAccessToken value) {
        return new JAXBElement<RefreshAccessToken>(_RefreshAccessToken_QNAME, RefreshAccessToken.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsActivityEnd }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "isActivityEnd")
    public JAXBElement<IsActivityEnd> createIsActivityEnd(IsActivityEnd value) {
        return new JAXBElement<IsActivityEnd>(_IsActivityEnd_QNAME, IsActivityEnd.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsFollPacksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "isFollPacksResponse")
    public JAXBElement<IsFollPacksResponse> createIsFollPacksResponse(IsFollPacksResponse value) {
        return new JAXBElement<IsFollPacksResponse>(_IsFollPacksResponse_QNAME, IsFollPacksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeixinAccoiunt }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "getWeixinAccoiunt")
    public JAXBElement<GetWeixinAccoiunt> createGetWeixinAccoiunt(GetWeixinAccoiunt value) {
        return new JAXBElement<GetWeixinAccoiunt>(_GetWeixinAccoiunt_QNAME, GetWeixinAccoiunt.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsFollPacks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "isFollPacks")
    public JAXBElement<IsFollPacks> createIsFollPacks(IsFollPacks value) {
        return new JAXBElement<IsFollPacks>(_IsFollPacks_QNAME, IsFollPacks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsActivityEndResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "isActivityEndResponse")
    public JAXBElement<IsActivityEndResponse> createIsActivityEndResponse(IsActivityEndResponse value) {
        return new JAXBElement<IsActivityEndResponse>(_IsActivityEndResponse_QNAME, IsActivityEndResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateWeixinUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "updateWeixinUserResponse")
    public JAXBElement<UpdateWeixinUserResponse> createUpdateWeixinUserResponse(UpdateWeixinUserResponse value) {
        return new JAXBElement<UpdateWeixinUserResponse>(_UpdateWeixinUserResponse_QNAME, UpdateWeixinUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GoAcquireGift }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "goAcquireGift")
    public JAXBElement<GoAcquireGift> createGoAcquireGift(GoAcquireGift value) {
        return new JAXBElement<GoAcquireGift>(_GoAcquireGift_QNAME, GoAcquireGift.class, null, value);
    }

}
