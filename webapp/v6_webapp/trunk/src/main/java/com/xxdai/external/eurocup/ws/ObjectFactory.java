
package com.xxdai.external.eurocup.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.eurocup.ws package. 
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

    private final static QName _FindGuessRecordByUserId_QNAME = new QName("http://webservice.activity.xxdai.com/", "findGuessRecordByUserId");
    private final static QName _FindGuessRecordByUserIdResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "findGuessRecordByUserIdResponse");
    private final static QName _QueryAllTeamsResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "queryAllTeamsResponse");
    private final static QName _SaveGuessResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "saveGuessResponse");
    private final static QName _Exception_QNAME = new QName("http://webservice.activity.xxdai.com/", "Exception");
    private final static QName _IsInActivity_QNAME = new QName("http://webservice.activity.xxdai.com/", "isInActivity");
    private final static QName _IsInActivityResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "isInActivityResponse");
    private final static QName _QueryAllTeams_QNAME = new QName("http://webservice.activity.xxdai.com/", "queryAllTeams");
    private final static QName _SaveGuess_QNAME = new QName("http://webservice.activity.xxdai.com/", "saveGuess");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.eurocup.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IsInActivity }
     * 
     */
    public IsInActivity createIsInActivity() {
        return new IsInActivity();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link SaveGuess }
     * 
     */
    public SaveGuess createSaveGuess() {
        return new SaveGuess();
    }

    /**
     * Create an instance of {@link QueryAllTeams }
     * 
     */
    public QueryAllTeams createQueryAllTeams() {
        return new QueryAllTeams();
    }

    /**
     * Create an instance of {@link IsInActivityResponse }
     * 
     */
    public IsInActivityResponse createIsInActivityResponse() {
        return new IsInActivityResponse();
    }

    /**
     * Create an instance of {@link FindGuessRecordByUserId }
     * 
     */
    public FindGuessRecordByUserId createFindGuessRecordByUserId() {
        return new FindGuessRecordByUserId();
    }

    /**
     * Create an instance of {@link SaveGuessResponse }
     * 
     */
    public SaveGuessResponse createSaveGuessResponse() {
        return new SaveGuessResponse();
    }

    /**
     * Create an instance of {@link QueryAllTeamsResponse }
     * 
     */
    public QueryAllTeamsResponse createQueryAllTeamsResponse() {
        return new QueryAllTeamsResponse();
    }

    /**
     * Create an instance of {@link FindGuessRecordByUserIdResponse }
     * 
     */
    public FindGuessRecordByUserIdResponse createFindGuessRecordByUserIdResponse() {
        return new FindGuessRecordByUserIdResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindGuessRecordByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "findGuessRecordByUserId")
    public JAXBElement<FindGuessRecordByUserId> createFindGuessRecordByUserId(FindGuessRecordByUserId value) {
        return new JAXBElement<FindGuessRecordByUserId>(_FindGuessRecordByUserId_QNAME, FindGuessRecordByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindGuessRecordByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "findGuessRecordByUserIdResponse")
    public JAXBElement<FindGuessRecordByUserIdResponse> createFindGuessRecordByUserIdResponse(FindGuessRecordByUserIdResponse value) {
        return new JAXBElement<FindGuessRecordByUserIdResponse>(_FindGuessRecordByUserIdResponse_QNAME, FindGuessRecordByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryAllTeamsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "queryAllTeamsResponse")
    public JAXBElement<QueryAllTeamsResponse> createQueryAllTeamsResponse(QueryAllTeamsResponse value) {
        return new JAXBElement<QueryAllTeamsResponse>(_QueryAllTeamsResponse_QNAME, QueryAllTeamsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveGuessResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "saveGuessResponse")
    public JAXBElement<SaveGuessResponse> createSaveGuessResponse(SaveGuessResponse value) {
        return new JAXBElement<SaveGuessResponse>(_SaveGuessResponse_QNAME, SaveGuessResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsInActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "isInActivity")
    public JAXBElement<IsInActivity> createIsInActivity(IsInActivity value) {
        return new JAXBElement<IsInActivity>(_IsInActivity_QNAME, IsInActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsInActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "isInActivityResponse")
    public JAXBElement<IsInActivityResponse> createIsInActivityResponse(IsInActivityResponse value) {
        return new JAXBElement<IsInActivityResponse>(_IsInActivityResponse_QNAME, IsInActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryAllTeams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "queryAllTeams")
    public JAXBElement<QueryAllTeams> createQueryAllTeams(QueryAllTeams value) {
        return new JAXBElement<QueryAllTeams>(_QueryAllTeams_QNAME, QueryAllTeams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveGuess }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "saveGuess")
    public JAXBElement<SaveGuess> createSaveGuess(SaveGuess value) {
        return new JAXBElement<SaveGuess>(_SaveGuess_QNAME, SaveGuess.class, null, value);
    }

}
