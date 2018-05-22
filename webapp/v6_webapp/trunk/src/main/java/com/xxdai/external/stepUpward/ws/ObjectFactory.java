
package com.xxdai.external.stepUpward.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.stepUpward.ws package. 
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

    private final static QName _AddStep_QNAME = new QName("http://webservice.step.xxdai.com/", "addStep");
    private final static QName _GetStepGAData_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepGAData");
    private final static QName _GetQuitDescResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "getQuitDescResponse");
    private final static QName _GetJoinRecordResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "getJoinRecordResponse");
    private final static QName _FindStepJoinById_QNAME = new QName("http://webservice.step.xxdai.com/", "findStepJoinById");
    private final static QName _QueryStepQuitListResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "queryStepQuitListResponse");
    private final static QName _StatisticStepJoinResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "statisticStepJoinResponse");
    private final static QName _QueryEndStepJoinList_QNAME = new QName("http://webservice.step.xxdai.com/", "queryEndStepJoinList");
    private final static QName _AddStepResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "addStepResponse");
    private final static QName _UpdateStepStatus_QNAME = new QName("http://webservice.step.xxdai.com/", "updateStepStatus");
    private final static QName _GetStepListResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepListResponse");
    private final static QName _GetStepForIndexResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepForIndexResponse");
    private final static QName _QueryEndStepJoinListResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "queryEndStepJoinListResponse");
    private final static QName _StatisticStepJoin_QNAME = new QName("http://webservice.step.xxdai.com/", "statisticStepJoin");
    private final static QName _GetStepForIndex_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepForIndex");
    private final static QName _UpdateStepResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "updateStepResponse");
    private final static QName _GetQuitDesc_QNAME = new QName("http://webservice.step.xxdai.com/", "getQuitDesc");
    private final static QName _GetStepByIdResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepByIdResponse");
    private final static QName _GetStepById_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepById");
    private final static QName _PreQuitCheckResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "preQuitCheckResponse");
    private final static QName _QueryStepQuitList_QNAME = new QName("http://webservice.step.xxdai.com/", "queryStepQuitList");
    private final static QName _QuitResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "quitResponse");
    private final static QName _QueryTendingStepJoinListResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "queryTendingStepJoinListResponse");
    private final static QName _BuyStep_QNAME = new QName("http://webservice.step.xxdai.com/", "buyStep");
    private final static QName _GetStepList_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepList");
    private final static QName _GetStepAccount_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepAccount");
    private final static QName _Quit_QNAME = new QName("http://webservice.step.xxdai.com/", "quit");
    private final static QName _UpdateStepStatusResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "updateStepStatusResponse");
    private final static QName _GetStepAccountResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepAccountResponse");
    private final static QName _GetJoinRecord_QNAME = new QName("http://webservice.step.xxdai.com/", "getJoinRecord");
    private final static QName _UpdateStep_QNAME = new QName("http://webservice.step.xxdai.com/", "updateStep");
    private final static QName _BuyStepResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "buyStepResponse");
    private final static QName _QueryTendingStepJoinList_QNAME = new QName("http://webservice.step.xxdai.com/", "queryTendingStepJoinList");
    private final static QName _GetStepGADataResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "getStepGADataResponse");
    private final static QName _FindStepJoinByIdResponse_QNAME = new QName("http://webservice.step.xxdai.com/", "findStepJoinByIdResponse");
    private final static QName _PreQuitCheck_QNAME = new QName("http://webservice.step.xxdai.com/", "preQuitCheck");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.stepUpward.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PreQuitCheck }
     * 
     */
    public PreQuitCheck createPreQuitCheck() {
        return new PreQuitCheck();
    }

    /**
     * Create an instance of {@link FindStepJoinByIdResponse }
     * 
     */
    public FindStepJoinByIdResponse createFindStepJoinByIdResponse() {
        return new FindStepJoinByIdResponse();
    }

    /**
     * Create an instance of {@link GetStepGADataResponse }
     * 
     */
    public GetStepGADataResponse createGetStepGADataResponse() {
        return new GetStepGADataResponse();
    }

    /**
     * Create an instance of {@link GetJoinRecord }
     * 
     */
    public GetJoinRecord createGetJoinRecord() {
        return new GetJoinRecord();
    }

    /**
     * Create an instance of {@link QueryTendingStepJoinList }
     * 
     */
    public QueryTendingStepJoinList createQueryTendingStepJoinList() {
        return new QueryTendingStepJoinList();
    }

    /**
     * Create an instance of {@link BuyStepResponse }
     * 
     */
    public BuyStepResponse createBuyStepResponse() {
        return new BuyStepResponse();
    }

    /**
     * Create an instance of {@link UpdateStep }
     * 
     */
    public UpdateStep createUpdateStep() {
        return new UpdateStep();
    }

    /**
     * Create an instance of {@link Quit }
     * 
     */
    public Quit createQuit() {
        return new Quit();
    }

    /**
     * Create an instance of {@link GetStepAccount }
     * 
     */
    public GetStepAccount createGetStepAccount() {
        return new GetStepAccount();
    }

    /**
     * Create an instance of {@link GetStepAccountResponse }
     * 
     */
    public GetStepAccountResponse createGetStepAccountResponse() {
        return new GetStepAccountResponse();
    }

    /**
     * Create an instance of {@link UpdateStepStatusResponse }
     * 
     */
    public UpdateStepStatusResponse createUpdateStepStatusResponse() {
        return new UpdateStepStatusResponse();
    }

    /**
     * Create an instance of {@link QueryTendingStepJoinListResponse }
     * 
     */
    public QueryTendingStepJoinListResponse createQueryTendingStepJoinListResponse() {
        return new QueryTendingStepJoinListResponse();
    }

    /**
     * Create an instance of {@link BuyStep }
     * 
     */
    public BuyStep createBuyStep() {
        return new BuyStep();
    }

    /**
     * Create an instance of {@link GetStepList }
     * 
     */
    public GetStepList createGetStepList() {
        return new GetStepList();
    }

    /**
     * Create an instance of {@link QuitResponse }
     * 
     */
    public QuitResponse createQuitResponse() {
        return new QuitResponse();
    }

    /**
     * Create an instance of {@link GetQuitDesc }
     * 
     */
    public GetQuitDesc createGetQuitDesc() {
        return new GetQuitDesc();
    }

    /**
     * Create an instance of {@link GetStepByIdResponse }
     * 
     */
    public GetStepByIdResponse createGetStepByIdResponse() {
        return new GetStepByIdResponse();
    }

    /**
     * Create an instance of {@link QueryStepQuitList }
     * 
     */
    public QueryStepQuitList createQueryStepQuitList() {
        return new QueryStepQuitList();
    }

    /**
     * Create an instance of {@link GetStepById }
     * 
     */
    public GetStepById createGetStepById() {
        return new GetStepById();
    }

    /**
     * Create an instance of {@link PreQuitCheckResponse }
     * 
     */
    public PreQuitCheckResponse createPreQuitCheckResponse() {
        return new PreQuitCheckResponse();
    }

    /**
     * Create an instance of {@link StatisticStepJoin }
     * 
     */
    public StatisticStepJoin createStatisticStepJoin() {
        return new StatisticStepJoin();
    }

    /**
     * Create an instance of {@link GetStepForIndex }
     * 
     */
    public GetStepForIndex createGetStepForIndex() {
        return new GetStepForIndex();
    }

    /**
     * Create an instance of {@link UpdateStepResponse }
     * 
     */
    public UpdateStepResponse createUpdateStepResponse() {
        return new UpdateStepResponse();
    }

    /**
     * Create an instance of {@link QueryEndStepJoinListResponse }
     * 
     */
    public QueryEndStepJoinListResponse createQueryEndStepJoinListResponse() {
        return new QueryEndStepJoinListResponse();
    }

    /**
     * Create an instance of {@link GetStepForIndexResponse }
     * 
     */
    public GetStepForIndexResponse createGetStepForIndexResponse() {
        return new GetStepForIndexResponse();
    }

    /**
     * Create an instance of {@link GetStepListResponse }
     * 
     */
    public GetStepListResponse createGetStepListResponse() {
        return new GetStepListResponse();
    }

    /**
     * Create an instance of {@link UpdateStepStatus }
     * 
     */
    public UpdateStepStatus createUpdateStepStatus() {
        return new UpdateStepStatus();
    }

    /**
     * Create an instance of {@link AddStepResponse }
     * 
     */
    public AddStepResponse createAddStepResponse() {
        return new AddStepResponse();
    }

    /**
     * Create an instance of {@link QueryEndStepJoinList }
     * 
     */
    public QueryEndStepJoinList createQueryEndStepJoinList() {
        return new QueryEndStepJoinList();
    }

    /**
     * Create an instance of {@link StatisticStepJoinResponse }
     * 
     */
    public StatisticStepJoinResponse createStatisticStepJoinResponse() {
        return new StatisticStepJoinResponse();
    }

    /**
     * Create an instance of {@link QueryStepQuitListResponse }
     * 
     */
    public QueryStepQuitListResponse createQueryStepQuitListResponse() {
        return new QueryStepQuitListResponse();
    }

    /**
     * Create an instance of {@link FindStepJoinById }
     * 
     */
    public FindStepJoinById createFindStepJoinById() {
        return new FindStepJoinById();
    }

    /**
     * Create an instance of {@link GetJoinRecordResponse }
     * 
     */
    public GetJoinRecordResponse createGetJoinRecordResponse() {
        return new GetJoinRecordResponse();
    }

    /**
     * Create an instance of {@link GetQuitDescResponse }
     * 
     */
    public GetQuitDescResponse createGetQuitDescResponse() {
        return new GetQuitDescResponse();
    }

    /**
     * Create an instance of {@link AddStep }
     * 
     */
    public AddStep createAddStep() {
        return new AddStep();
    }

    /**
     * Create an instance of {@link GetStepGAData }
     * 
     */
    public GetStepGAData createGetStepGAData() {
        return new GetStepGAData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddStep }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "addStep")
    public JAXBElement<AddStep> createAddStep(AddStep value) {
        return new JAXBElement<AddStep>(_AddStep_QNAME, AddStep.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepGAData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepGAData")
    public JAXBElement<GetStepGAData> createGetStepGAData(GetStepGAData value) {
        return new JAXBElement<GetStepGAData>(_GetStepGAData_QNAME, GetStepGAData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQuitDescResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getQuitDescResponse")
    public JAXBElement<GetQuitDescResponse> createGetQuitDescResponse(GetQuitDescResponse value) {
        return new JAXBElement<GetQuitDescResponse>(_GetQuitDescResponse_QNAME, GetQuitDescResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetJoinRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getJoinRecordResponse")
    public JAXBElement<GetJoinRecordResponse> createGetJoinRecordResponse(GetJoinRecordResponse value) {
        return new JAXBElement<GetJoinRecordResponse>(_GetJoinRecordResponse_QNAME, GetJoinRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindStepJoinById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "findStepJoinById")
    public JAXBElement<FindStepJoinById> createFindStepJoinById(FindStepJoinById value) {
        return new JAXBElement<FindStepJoinById>(_FindStepJoinById_QNAME, FindStepJoinById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryStepQuitListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "queryStepQuitListResponse")
    public JAXBElement<QueryStepQuitListResponse> createQueryStepQuitListResponse(QueryStepQuitListResponse value) {
        return new JAXBElement<QueryStepQuitListResponse>(_QueryStepQuitListResponse_QNAME, QueryStepQuitListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatisticStepJoinResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "statisticStepJoinResponse")
    public JAXBElement<StatisticStepJoinResponse> createStatisticStepJoinResponse(StatisticStepJoinResponse value) {
        return new JAXBElement<StatisticStepJoinResponse>(_StatisticStepJoinResponse_QNAME, StatisticStepJoinResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryEndStepJoinList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "queryEndStepJoinList")
    public JAXBElement<QueryEndStepJoinList> createQueryEndStepJoinList(QueryEndStepJoinList value) {
        return new JAXBElement<QueryEndStepJoinList>(_QueryEndStepJoinList_QNAME, QueryEndStepJoinList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddStepResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "addStepResponse")
    public JAXBElement<AddStepResponse> createAddStepResponse(AddStepResponse value) {
        return new JAXBElement<AddStepResponse>(_AddStepResponse_QNAME, AddStepResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStepStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "updateStepStatus")
    public JAXBElement<UpdateStepStatus> createUpdateStepStatus(UpdateStepStatus value) {
        return new JAXBElement<UpdateStepStatus>(_UpdateStepStatus_QNAME, UpdateStepStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepListResponse")
    public JAXBElement<GetStepListResponse> createGetStepListResponse(GetStepListResponse value) {
        return new JAXBElement<GetStepListResponse>(_GetStepListResponse_QNAME, GetStepListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepForIndexResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepForIndexResponse")
    public JAXBElement<GetStepForIndexResponse> createGetStepForIndexResponse(GetStepForIndexResponse value) {
        return new JAXBElement<GetStepForIndexResponse>(_GetStepForIndexResponse_QNAME, GetStepForIndexResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryEndStepJoinListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "queryEndStepJoinListResponse")
    public JAXBElement<QueryEndStepJoinListResponse> createQueryEndStepJoinListResponse(QueryEndStepJoinListResponse value) {
        return new JAXBElement<QueryEndStepJoinListResponse>(_QueryEndStepJoinListResponse_QNAME, QueryEndStepJoinListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatisticStepJoin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "statisticStepJoin")
    public JAXBElement<StatisticStepJoin> createStatisticStepJoin(StatisticStepJoin value) {
        return new JAXBElement<StatisticStepJoin>(_StatisticStepJoin_QNAME, StatisticStepJoin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepForIndex }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepForIndex")
    public JAXBElement<GetStepForIndex> createGetStepForIndex(GetStepForIndex value) {
        return new JAXBElement<GetStepForIndex>(_GetStepForIndex_QNAME, GetStepForIndex.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStepResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "updateStepResponse")
    public JAXBElement<UpdateStepResponse> createUpdateStepResponse(UpdateStepResponse value) {
        return new JAXBElement<UpdateStepResponse>(_UpdateStepResponse_QNAME, UpdateStepResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQuitDesc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getQuitDesc")
    public JAXBElement<GetQuitDesc> createGetQuitDesc(GetQuitDesc value) {
        return new JAXBElement<GetQuitDesc>(_GetQuitDesc_QNAME, GetQuitDesc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepByIdResponse")
    public JAXBElement<GetStepByIdResponse> createGetStepByIdResponse(GetStepByIdResponse value) {
        return new JAXBElement<GetStepByIdResponse>(_GetStepByIdResponse_QNAME, GetStepByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepById")
    public JAXBElement<GetStepById> createGetStepById(GetStepById value) {
        return new JAXBElement<GetStepById>(_GetStepById_QNAME, GetStepById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PreQuitCheckResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "preQuitCheckResponse")
    public JAXBElement<PreQuitCheckResponse> createPreQuitCheckResponse(PreQuitCheckResponse value) {
        return new JAXBElement<PreQuitCheckResponse>(_PreQuitCheckResponse_QNAME, PreQuitCheckResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryStepQuitList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "queryStepQuitList")
    public JAXBElement<QueryStepQuitList> createQueryStepQuitList(QueryStepQuitList value) {
        return new JAXBElement<QueryStepQuitList>(_QueryStepQuitList_QNAME, QueryStepQuitList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "quitResponse")
    public JAXBElement<QuitResponse> createQuitResponse(QuitResponse value) {
        return new JAXBElement<QuitResponse>(_QuitResponse_QNAME, QuitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTendingStepJoinListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "queryTendingStepJoinListResponse")
    public JAXBElement<QueryTendingStepJoinListResponse> createQueryTendingStepJoinListResponse(QueryTendingStepJoinListResponse value) {
        return new JAXBElement<QueryTendingStepJoinListResponse>(_QueryTendingStepJoinListResponse_QNAME, QueryTendingStepJoinListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyStep }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "buyStep")
    public JAXBElement<BuyStep> createBuyStep(BuyStep value) {
        return new JAXBElement<BuyStep>(_BuyStep_QNAME, BuyStep.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepList")
    public JAXBElement<GetStepList> createGetStepList(GetStepList value) {
        return new JAXBElement<GetStepList>(_GetStepList_QNAME, GetStepList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepAccount")
    public JAXBElement<GetStepAccount> createGetStepAccount(GetStepAccount value) {
        return new JAXBElement<GetStepAccount>(_GetStepAccount_QNAME, GetStepAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Quit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "quit")
    public JAXBElement<Quit> createQuit(Quit value) {
        return new JAXBElement<Quit>(_Quit_QNAME, Quit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStepStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "updateStepStatusResponse")
    public JAXBElement<UpdateStepStatusResponse> createUpdateStepStatusResponse(UpdateStepStatusResponse value) {
        return new JAXBElement<UpdateStepStatusResponse>(_UpdateStepStatusResponse_QNAME, UpdateStepStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepAccountResponse")
    public JAXBElement<GetStepAccountResponse> createGetStepAccountResponse(GetStepAccountResponse value) {
        return new JAXBElement<GetStepAccountResponse>(_GetStepAccountResponse_QNAME, GetStepAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetJoinRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getJoinRecord")
    public JAXBElement<GetJoinRecord> createGetJoinRecord(GetJoinRecord value) {
        return new JAXBElement<GetJoinRecord>(_GetJoinRecord_QNAME, GetJoinRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStep }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "updateStep")
    public JAXBElement<UpdateStep> createUpdateStep(UpdateStep value) {
        return new JAXBElement<UpdateStep>(_UpdateStep_QNAME, UpdateStep.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyStepResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "buyStepResponse")
    public JAXBElement<BuyStepResponse> createBuyStepResponse(BuyStepResponse value) {
        return new JAXBElement<BuyStepResponse>(_BuyStepResponse_QNAME, BuyStepResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTendingStepJoinList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "queryTendingStepJoinList")
    public JAXBElement<QueryTendingStepJoinList> createQueryTendingStepJoinList(QueryTendingStepJoinList value) {
        return new JAXBElement<QueryTendingStepJoinList>(_QueryTendingStepJoinList_QNAME, QueryTendingStepJoinList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStepGADataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "getStepGADataResponse")
    public JAXBElement<GetStepGADataResponse> createGetStepGADataResponse(GetStepGADataResponse value) {
        return new JAXBElement<GetStepGADataResponse>(_GetStepGADataResponse_QNAME, GetStepGADataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindStepJoinByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "findStepJoinByIdResponse")
    public JAXBElement<FindStepJoinByIdResponse> createFindStepJoinByIdResponse(FindStepJoinByIdResponse value) {
        return new JAXBElement<FindStepJoinByIdResponse>(_FindStepJoinByIdResponse_QNAME, FindStepJoinByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PreQuitCheck }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.step.xxdai.com/", name = "preQuitCheck")
    public JAXBElement<PreQuitCheck> createPreQuitCheck(PreQuitCheck value) {
        return new JAXBElement<PreQuitCheck>(_PreQuitCheck_QNAME, PreQuitCheck.class, null, value);
    }

}
