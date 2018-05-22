
package com.xxdai.external.gamebwnh.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.gamebwnh.ws package. 
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

    private final static QName _QueryGoalDetailsResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "queryGoalDetailsResponse");
    private final static QName _AddGameChancesResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "addGameChancesResponse");
    private final static QName _ShareThenGetChangeResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "shareThenGetChangeResponse");
    private final static QName _SubstractGameChancesResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "substractGameChancesResponse");
    private final static QName _SubstractGameChances_QNAME = new QName("http://webservice.weixin.xxdai.com/", "substractGameChances");
    private final static QName _IfCanPlayDfjGanmeResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "ifCanPlayDfjGanmeResponse");
    private final static QName _RecordGameGoals_QNAME = new QName("http://webservice.weixin.xxdai.com/", "recordGameGoals");
    private final static QName _GoalExchangeCoin_QNAME = new QName("http://webservice.weixin.xxdai.com/", "goalExchangeCoin");
    private final static QName _GoalExchangeCoinResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "goalExchangeCoinResponse");
    private final static QName _QueryGoalDetails_QNAME = new QName("http://webservice.weixin.xxdai.com/", "queryGoalDetails");
    private final static QName _RecordGameGoalsResponse_QNAME = new QName("http://webservice.weixin.xxdai.com/", "recordGameGoalsResponse");
    private final static QName _ShareThenGetChange_QNAME = new QName("http://webservice.weixin.xxdai.com/", "shareThenGetChange");
    private final static QName _IfCanPlayDfjGanme_QNAME = new QName("http://webservice.weixin.xxdai.com/", "ifCanPlayDfjGanme");
    private final static QName _AddGameChances_QNAME = new QName("http://webservice.weixin.xxdai.com/", "addGameChances");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.gamebwnh.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddGameChances }
     * 
     */
    public AddGameChances createAddGameChances() {
        return new AddGameChances();
    }

    /**
     * Create an instance of {@link IfCanPlayDfjGanme }
     * 
     */
    public IfCanPlayDfjGanme createIfCanPlayDfjGanme() {
        return new IfCanPlayDfjGanme();
    }

    /**
     * Create an instance of {@link GoalExchangeCoinResponse }
     * 
     */
    public GoalExchangeCoinResponse createGoalExchangeCoinResponse() {
        return new GoalExchangeCoinResponse();
    }

    /**
     * Create an instance of {@link QueryGoalDetails }
     * 
     */
    public QueryGoalDetails createQueryGoalDetails() {
        return new QueryGoalDetails();
    }

    /**
     * Create an instance of {@link RecordGameGoalsResponse }
     * 
     */
    public RecordGameGoalsResponse createRecordGameGoalsResponse() {
        return new RecordGameGoalsResponse();
    }

    /**
     * Create an instance of {@link ShareThenGetChange }
     * 
     */
    public ShareThenGetChange createShareThenGetChange() {
        return new ShareThenGetChange();
    }

    /**
     * Create an instance of {@link GoalExchangeCoin }
     * 
     */
    public GoalExchangeCoin createGoalExchangeCoin() {
        return new GoalExchangeCoin();
    }

    /**
     * Create an instance of {@link IfCanPlayDfjGanmeResponse }
     * 
     */
    public IfCanPlayDfjGanmeResponse createIfCanPlayDfjGanmeResponse() {
        return new IfCanPlayDfjGanmeResponse();
    }

    /**
     * Create an instance of {@link RecordGameGoals }
     * 
     */
    public RecordGameGoals createRecordGameGoals() {
        return new RecordGameGoals();
    }

    /**
     * Create an instance of {@link SubstractGameChancesResponse }
     * 
     */
    public SubstractGameChancesResponse createSubstractGameChancesResponse() {
        return new SubstractGameChancesResponse();
    }

    /**
     * Create an instance of {@link SubstractGameChances }
     * 
     */
    public SubstractGameChances createSubstractGameChances() {
        return new SubstractGameChances();
    }

    /**
     * Create an instance of {@link ShareThenGetChangeResponse }
     * 
     */
    public ShareThenGetChangeResponse createShareThenGetChangeResponse() {
        return new ShareThenGetChangeResponse();
    }

    /**
     * Create an instance of {@link AddGameChancesResponse }
     * 
     */
    public AddGameChancesResponse createAddGameChancesResponse() {
        return new AddGameChancesResponse();
    }

    /**
     * Create an instance of {@link QueryGoalDetailsResponse }
     * 
     */
    public QueryGoalDetailsResponse createQueryGoalDetailsResponse() {
        return new QueryGoalDetailsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryGoalDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "queryGoalDetailsResponse")
    public JAXBElement<QueryGoalDetailsResponse> createQueryGoalDetailsResponse(QueryGoalDetailsResponse value) {
        return new JAXBElement<QueryGoalDetailsResponse>(_QueryGoalDetailsResponse_QNAME, QueryGoalDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGameChancesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "addGameChancesResponse")
    public JAXBElement<AddGameChancesResponse> createAddGameChancesResponse(AddGameChancesResponse value) {
        return new JAXBElement<AddGameChancesResponse>(_AddGameChancesResponse_QNAME, AddGameChancesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShareThenGetChangeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "shareThenGetChangeResponse")
    public JAXBElement<ShareThenGetChangeResponse> createShareThenGetChangeResponse(ShareThenGetChangeResponse value) {
        return new JAXBElement<ShareThenGetChangeResponse>(_ShareThenGetChangeResponse_QNAME, ShareThenGetChangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubstractGameChancesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "substractGameChancesResponse")
    public JAXBElement<SubstractGameChancesResponse> createSubstractGameChancesResponse(SubstractGameChancesResponse value) {
        return new JAXBElement<SubstractGameChancesResponse>(_SubstractGameChancesResponse_QNAME, SubstractGameChancesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubstractGameChances }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "substractGameChances")
    public JAXBElement<SubstractGameChances> createSubstractGameChances(SubstractGameChances value) {
        return new JAXBElement<SubstractGameChances>(_SubstractGameChances_QNAME, SubstractGameChances.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IfCanPlayDfjGanmeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "ifCanPlayDfjGanmeResponse")
    public JAXBElement<IfCanPlayDfjGanmeResponse> createIfCanPlayDfjGanmeResponse(IfCanPlayDfjGanmeResponse value) {
        return new JAXBElement<IfCanPlayDfjGanmeResponse>(_IfCanPlayDfjGanmeResponse_QNAME, IfCanPlayDfjGanmeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecordGameGoals }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "recordGameGoals")
    public JAXBElement<RecordGameGoals> createRecordGameGoals(RecordGameGoals value) {
        return new JAXBElement<RecordGameGoals>(_RecordGameGoals_QNAME, RecordGameGoals.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GoalExchangeCoin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "goalExchangeCoin")
    public JAXBElement<GoalExchangeCoin> createGoalExchangeCoin(GoalExchangeCoin value) {
        return new JAXBElement<GoalExchangeCoin>(_GoalExchangeCoin_QNAME, GoalExchangeCoin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GoalExchangeCoinResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "goalExchangeCoinResponse")
    public JAXBElement<GoalExchangeCoinResponse> createGoalExchangeCoinResponse(GoalExchangeCoinResponse value) {
        return new JAXBElement<GoalExchangeCoinResponse>(_GoalExchangeCoinResponse_QNAME, GoalExchangeCoinResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryGoalDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "queryGoalDetails")
    public JAXBElement<QueryGoalDetails> createQueryGoalDetails(QueryGoalDetails value) {
        return new JAXBElement<QueryGoalDetails>(_QueryGoalDetails_QNAME, QueryGoalDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecordGameGoalsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "recordGameGoalsResponse")
    public JAXBElement<RecordGameGoalsResponse> createRecordGameGoalsResponse(RecordGameGoalsResponse value) {
        return new JAXBElement<RecordGameGoalsResponse>(_RecordGameGoalsResponse_QNAME, RecordGameGoalsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShareThenGetChange }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "shareThenGetChange")
    public JAXBElement<ShareThenGetChange> createShareThenGetChange(ShareThenGetChange value) {
        return new JAXBElement<ShareThenGetChange>(_ShareThenGetChange_QNAME, ShareThenGetChange.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IfCanPlayDfjGanme }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "ifCanPlayDfjGanme")
    public JAXBElement<IfCanPlayDfjGanme> createIfCanPlayDfjGanme(IfCanPlayDfjGanme value) {
        return new JAXBElement<IfCanPlayDfjGanme>(_IfCanPlayDfjGanme_QNAME, IfCanPlayDfjGanme.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGameChances }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.weixin.xxdai.com/", name = "addGameChances")
    public JAXBElement<AddGameChances> createAddGameChances(AddGameChances value) {
        return new JAXBElement<AddGameChances>(_AddGameChances_QNAME, AddGameChances.class, null, value);
    }

}
