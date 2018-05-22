
package com.xxdai.external.activity.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.activity.ws package. 
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

    private final static QName _ReceiveBack_QNAME = new QName("http://webservice.activity.xxdai.com/", "receiveBack");
    private final static QName _AccountBackResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "accountBackResponse");
    private final static QName _CountCouponByid_QNAME = new QName("http://webservice.activity.xxdai.com/", "countCouponByid");
    private final static QName _ReceiveBackResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "receiveBackResponse");
    private final static QName _SelectActivityResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "selectActivityResponse");
    private final static QName _SelectActivity_QNAME = new QName("http://webservice.activity.xxdai.com/", "selectActivity");
    private final static QName _Count_QNAME = new QName("http://webservice.activity.xxdai.com/", "count");
    private final static QName _QueryTenderInfoByActTimeResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "queryTenderInfoByActTimeResponse");
    private final static QName _Receive_QNAME = new QName("http://webservice.activity.xxdai.com/", "receive");
    private final static QName _SaveCodeGroup_QNAME = new QName("http://webservice.activity.xxdai.com/", "saveCodeGroup");
    private final static QName _SaveActivity_QNAME = new QName("http://webservice.activity.xxdai.com/", "saveActivity");
    private final static QName _AccountBack_QNAME = new QName("http://webservice.activity.xxdai.com/", "accountBack");
    private final static QName _SelectByuserid_QNAME = new QName("http://webservice.activity.xxdai.com/", "selectByuserid");
    private final static QName _QueryTenderInfoByActTime_QNAME = new QName("http://webservice.activity.xxdai.com/", "queryTenderInfoByActTime");
    private final static QName _ReceiveResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "receiveResponse");
    private final static QName _SaveCodeGroupResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "saveCodeGroupResponse");
    private final static QName _Datebegin_QNAME = new QName("http://webservice.activity.xxdai.com/", "datebegin");
    private final static QName _SaveActivityResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "saveActivityResponse");
    private final static QName _UpdateCode_QNAME = new QName("http://webservice.activity.xxdai.com/", "updateCode");
    private final static QName _GetActivitysResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "getActivitysResponse");
    private final static QName _IsInActivityTime_QNAME = new QName("http://webservice.activity.xxdai.com/", "isInActivityTime");
    private final static QName _IsInActivityTimeResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "isInActivityTimeResponse");
    private final static QName _CountResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "countResponse");
    private final static QName _GetActivitys_QNAME = new QName("http://webservice.activity.xxdai.com/", "getActivitys");
    private final static QName _SelectByuseridResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "selectByuseridResponse");
    private final static QName _UpdateCodeResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "updateCodeResponse");
    private final static QName _CountCouponByidResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "countCouponByidResponse");
    private final static QName _GetActivityInfoByCode_QNAME = new QName("http://webservice.activity.xxdai.com/", "getActivityInfoByCode");
    private final static QName _GetActivityInfoByCodeResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "getActivityInfoByCodeResponse");
    private final static QName _DatebeginResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "datebeginResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.activity.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectActivityResponse }
     * 
     */
    public SelectActivityResponse createSelectActivityResponse() {
        return new SelectActivityResponse();
    }

    /**
     * Create an instance of {@link Count }
     * 
     */
    public Count createCount() {
        return new Count();
    }

    /**
     * Create an instance of {@link SelectActivity }
     * 
     */
    public SelectActivity createSelectActivity() {
        return new SelectActivity();
    }

    /**
     * Create an instance of {@link CountCouponByid }
     * 
     */
    public CountCouponByid createCountCouponByid() {
        return new CountCouponByid();
    }

    /**
     * Create an instance of {@link AccountBackResponse }
     * 
     */
    public AccountBackResponse createAccountBackResponse() {
        return new AccountBackResponse();
    }

    /**
     * Create an instance of {@link ReceiveBack }
     * 
     */
    public ReceiveBack createReceiveBack() {
        return new ReceiveBack();
    }

    /**
     * Create an instance of {@link ReceiveBackResponse }
     * 
     */
    public ReceiveBackResponse createReceiveBackResponse() {
        return new ReceiveBackResponse();
    }

    /**
     * Create an instance of {@link ReceiveResponse }
     * 
     */
    public ReceiveResponse createReceiveResponse() {
        return new ReceiveResponse();
    }

    /**
     * Create an instance of {@link QueryTenderInfoByActTime }
     * 
     */
    public QueryTenderInfoByActTime createQueryTenderInfoByActTime() {
        return new QueryTenderInfoByActTime();
    }

    /**
     * Create an instance of {@link SelectByuserid }
     * 
     */
    public SelectByuserid createSelectByuserid() {
        return new SelectByuserid();
    }

    /**
     * Create an instance of {@link AccountBack }
     * 
     */
    public AccountBack createAccountBack() {
        return new AccountBack();
    }

    /**
     * Create an instance of {@link SaveActivity }
     * 
     */
    public SaveActivity createSaveActivity() {
        return new SaveActivity();
    }

    /**
     * Create an instance of {@link Datebegin }
     * 
     */
    public Datebegin createDatebegin() {
        return new Datebegin();
    }

    /**
     * Create an instance of {@link SaveActivityResponse }
     * 
     */
    public SaveActivityResponse createSaveActivityResponse() {
        return new SaveActivityResponse();
    }

    /**
     * Create an instance of {@link SaveCodeGroupResponse }
     * 
     */
    public SaveCodeGroupResponse createSaveCodeGroupResponse() {
        return new SaveCodeGroupResponse();
    }

    /**
     * Create an instance of {@link Receive }
     * 
     */
    public Receive createReceive() {
        return new Receive();
    }

    /**
     * Create an instance of {@link SaveCodeGroup }
     * 
     */
    public SaveCodeGroup createSaveCodeGroup() {
        return new SaveCodeGroup();
    }

    /**
     * Create an instance of {@link QueryTenderInfoByActTimeResponse }
     * 
     */
    public QueryTenderInfoByActTimeResponse createQueryTenderInfoByActTimeResponse() {
        return new QueryTenderInfoByActTimeResponse();
    }

    /**
     * Create an instance of {@link CountResponse }
     * 
     */
    public CountResponse createCountResponse() {
        return new CountResponse();
    }

    /**
     * Create an instance of {@link IsInActivityTimeResponse }
     * 
     */
    public IsInActivityTimeResponse createIsInActivityTimeResponse() {
        return new IsInActivityTimeResponse();
    }

    /**
     * Create an instance of {@link GetActivitys }
     * 
     */
    public GetActivitys createGetActivitys() {
        return new GetActivitys();
    }

    /**
     * Create an instance of {@link GetActivitysResponse }
     * 
     */
    public GetActivitysResponse createGetActivitysResponse() {
        return new GetActivitysResponse();
    }

    /**
     * Create an instance of {@link UpdateCode }
     * 
     */
    public UpdateCode createUpdateCode() {
        return new UpdateCode();
    }

    /**
     * Create an instance of {@link IsInActivityTime }
     * 
     */
    public IsInActivityTime createIsInActivityTime() {
        return new IsInActivityTime();
    }

    /**
     * Create an instance of {@link GetActivityInfoByCode }
     * 
     */
    public GetActivityInfoByCode createGetActivityInfoByCode() {
        return new GetActivityInfoByCode();
    }

    /**
     * Create an instance of {@link CountCouponByidResponse }
     * 
     */
    public CountCouponByidResponse createCountCouponByidResponse() {
        return new CountCouponByidResponse();
    }

    /**
     * Create an instance of {@link UpdateCodeResponse }
     * 
     */
    public UpdateCodeResponse createUpdateCodeResponse() {
        return new UpdateCodeResponse();
    }

    /**
     * Create an instance of {@link SelectByuseridResponse }
     * 
     */
    public SelectByuseridResponse createSelectByuseridResponse() {
        return new SelectByuseridResponse();
    }

    /**
     * Create an instance of {@link DatebeginResponse }
     * 
     */
    public DatebeginResponse createDatebeginResponse() {
        return new DatebeginResponse();
    }

    /**
     * Create an instance of {@link GetActivityInfoByCodeResponse }
     * 
     */
    public GetActivityInfoByCodeResponse createGetActivityInfoByCodeResponse() {
        return new GetActivityInfoByCodeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveBack }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "receiveBack")
    public JAXBElement<ReceiveBack> createReceiveBack(ReceiveBack value) {
        return new JAXBElement<ReceiveBack>(_ReceiveBack_QNAME, ReceiveBack.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountBackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "accountBackResponse")
    public JAXBElement<AccountBackResponse> createAccountBackResponse(AccountBackResponse value) {
        return new JAXBElement<AccountBackResponse>(_AccountBackResponse_QNAME, AccountBackResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountCouponByid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "countCouponByid")
    public JAXBElement<CountCouponByid> createCountCouponByid(CountCouponByid value) {
        return new JAXBElement<CountCouponByid>(_CountCouponByid_QNAME, CountCouponByid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveBackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "receiveBackResponse")
    public JAXBElement<ReceiveBackResponse> createReceiveBackResponse(ReceiveBackResponse value) {
        return new JAXBElement<ReceiveBackResponse>(_ReceiveBackResponse_QNAME, ReceiveBackResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "selectActivityResponse")
    public JAXBElement<SelectActivityResponse> createSelectActivityResponse(SelectActivityResponse value) {
        return new JAXBElement<SelectActivityResponse>(_SelectActivityResponse_QNAME, SelectActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "selectActivity")
    public JAXBElement<SelectActivity> createSelectActivity(SelectActivity value) {
        return new JAXBElement<SelectActivity>(_SelectActivity_QNAME, SelectActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Count }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "count")
    public JAXBElement<Count> createCount(Count value) {
        return new JAXBElement<Count>(_Count_QNAME, Count.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTenderInfoByActTimeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "queryTenderInfoByActTimeResponse")
    public JAXBElement<QueryTenderInfoByActTimeResponse> createQueryTenderInfoByActTimeResponse(QueryTenderInfoByActTimeResponse value) {
        return new JAXBElement<QueryTenderInfoByActTimeResponse>(_QueryTenderInfoByActTimeResponse_QNAME, QueryTenderInfoByActTimeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "receive")
    public JAXBElement<Receive> createReceive(Receive value) {
        return new JAXBElement<Receive>(_Receive_QNAME, Receive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveCodeGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "saveCodeGroup")
    public JAXBElement<SaveCodeGroup> createSaveCodeGroup(SaveCodeGroup value) {
        return new JAXBElement<SaveCodeGroup>(_SaveCodeGroup_QNAME, SaveCodeGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "saveActivity")
    public JAXBElement<SaveActivity> createSaveActivity(SaveActivity value) {
        return new JAXBElement<SaveActivity>(_SaveActivity_QNAME, SaveActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountBack }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "accountBack")
    public JAXBElement<AccountBack> createAccountBack(AccountBack value) {
        return new JAXBElement<AccountBack>(_AccountBack_QNAME, AccountBack.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectByuserid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "selectByuserid")
    public JAXBElement<SelectByuserid> createSelectByuserid(SelectByuserid value) {
        return new JAXBElement<SelectByuserid>(_SelectByuserid_QNAME, SelectByuserid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTenderInfoByActTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "queryTenderInfoByActTime")
    public JAXBElement<QueryTenderInfoByActTime> createQueryTenderInfoByActTime(QueryTenderInfoByActTime value) {
        return new JAXBElement<QueryTenderInfoByActTime>(_QueryTenderInfoByActTime_QNAME, QueryTenderInfoByActTime.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "receiveResponse")
    public JAXBElement<ReceiveResponse> createReceiveResponse(ReceiveResponse value) {
        return new JAXBElement<ReceiveResponse>(_ReceiveResponse_QNAME, ReceiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveCodeGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "saveCodeGroupResponse")
    public JAXBElement<SaveCodeGroupResponse> createSaveCodeGroupResponse(SaveCodeGroupResponse value) {
        return new JAXBElement<SaveCodeGroupResponse>(_SaveCodeGroupResponse_QNAME, SaveCodeGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Datebegin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "datebegin")
    public JAXBElement<Datebegin> createDatebegin(Datebegin value) {
        return new JAXBElement<Datebegin>(_Datebegin_QNAME, Datebegin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "saveActivityResponse")
    public JAXBElement<SaveActivityResponse> createSaveActivityResponse(SaveActivityResponse value) {
        return new JAXBElement<SaveActivityResponse>(_SaveActivityResponse_QNAME, SaveActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "updateCode")
    public JAXBElement<UpdateCode> createUpdateCode(UpdateCode value) {
        return new JAXBElement<UpdateCode>(_UpdateCode_QNAME, UpdateCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivitysResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "getActivitysResponse")
    public JAXBElement<GetActivitysResponse> createGetActivitysResponse(GetActivitysResponse value) {
        return new JAXBElement<GetActivitysResponse>(_GetActivitysResponse_QNAME, GetActivitysResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsInActivityTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "isInActivityTime")
    public JAXBElement<IsInActivityTime> createIsInActivityTime(IsInActivityTime value) {
        return new JAXBElement<IsInActivityTime>(_IsInActivityTime_QNAME, IsInActivityTime.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsInActivityTimeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "isInActivityTimeResponse")
    public JAXBElement<IsInActivityTimeResponse> createIsInActivityTimeResponse(IsInActivityTimeResponse value) {
        return new JAXBElement<IsInActivityTimeResponse>(_IsInActivityTimeResponse_QNAME, IsInActivityTimeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "countResponse")
    public JAXBElement<CountResponse> createCountResponse(CountResponse value) {
        return new JAXBElement<CountResponse>(_CountResponse_QNAME, CountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivitys }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "getActivitys")
    public JAXBElement<GetActivitys> createGetActivitys(GetActivitys value) {
        return new JAXBElement<GetActivitys>(_GetActivitys_QNAME, GetActivitys.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectByuseridResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "selectByuseridResponse")
    public JAXBElement<SelectByuseridResponse> createSelectByuseridResponse(SelectByuseridResponse value) {
        return new JAXBElement<SelectByuseridResponse>(_SelectByuseridResponse_QNAME, SelectByuseridResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "updateCodeResponse")
    public JAXBElement<UpdateCodeResponse> createUpdateCodeResponse(UpdateCodeResponse value) {
        return new JAXBElement<UpdateCodeResponse>(_UpdateCodeResponse_QNAME, UpdateCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountCouponByidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "countCouponByidResponse")
    public JAXBElement<CountCouponByidResponse> createCountCouponByidResponse(CountCouponByidResponse value) {
        return new JAXBElement<CountCouponByidResponse>(_CountCouponByidResponse_QNAME, CountCouponByidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityInfoByCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "getActivityInfoByCode")
    public JAXBElement<GetActivityInfoByCode> createGetActivityInfoByCode(GetActivityInfoByCode value) {
        return new JAXBElement<GetActivityInfoByCode>(_GetActivityInfoByCode_QNAME, GetActivityInfoByCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityInfoByCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "getActivityInfoByCodeResponse")
    public JAXBElement<GetActivityInfoByCodeResponse> createGetActivityInfoByCodeResponse(GetActivityInfoByCodeResponse value) {
        return new JAXBElement<GetActivityInfoByCodeResponse>(_GetActivityInfoByCodeResponse_QNAME, GetActivityInfoByCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatebeginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "datebeginResponse")
    public JAXBElement<DatebeginResponse> createDatebeginResponse(DatebeginResponse value) {
        return new JAXBElement<DatebeginResponse>(_DatebeginResponse_QNAME, DatebeginResponse.class, null, value);
    }

}
