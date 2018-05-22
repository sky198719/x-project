
package com.xxdai.external.question.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.question.ws package. 
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

    private final static QName _QueryNaireByCode_QNAME = new QName("http://webservice.question.xxdai.com/", "queryNaireByCode");
    private final static QName _QueryQuestionResponse_QNAME = new QName("http://webservice.question.xxdai.com/", "queryQuestionResponse");
    private final static QName _QueryNaireByCodeResponse_QNAME = new QName("http://webservice.question.xxdai.com/", "queryNaireByCodeResponse");
    private final static QName _QueryQuestionNaire_QNAME = new QName("http://webservice.question.xxdai.com/", "queryQuestionNaire");
    private final static QName _SaveQuestionUser_QNAME = new QName("http://webservice.question.xxdai.com/", "saveQuestionUser");
    private final static QName _SaveUseQuestionAnswerResponse_QNAME = new QName("http://webservice.question.xxdai.com/", "saveUseQuestionAnswerResponse");
    private final static QName _SaveUseQuestionAnswer_QNAME = new QName("http://webservice.question.xxdai.com/", "saveUseQuestionAnswer");
    private final static QName _QueryQuestion_QNAME = new QName("http://webservice.question.xxdai.com/", "queryQuestion");
    private final static QName _QueryQuestionNaireResponse_QNAME = new QName("http://webservice.question.xxdai.com/", "queryQuestionNaireResponse");
    private final static QName _SaveQuestionUserResponse_QNAME = new QName("http://webservice.question.xxdai.com/", "saveQuestionUserResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.question.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryQuestion }
     * 
     */
    public QueryQuestion createQueryQuestion() {
        return new QueryQuestion();
    }

    /**
     * Create an instance of {@link SaveUseQuestionAnswer }
     * 
     */
    public SaveUseQuestionAnswer createSaveUseQuestionAnswer() {
        return new SaveUseQuestionAnswer();
    }

    /**
     * Create an instance of {@link SaveUseQuestionAnswerResponse }
     * 
     */
    public SaveUseQuestionAnswerResponse createSaveUseQuestionAnswerResponse() {
        return new SaveUseQuestionAnswerResponse();
    }

    /**
     * Create an instance of {@link SaveQuestionUser }
     * 
     */
    public SaveQuestionUser createSaveQuestionUser() {
        return new SaveQuestionUser();
    }

    /**
     * Create an instance of {@link QueryQuestionNaire }
     * 
     */
    public QueryQuestionNaire createQueryQuestionNaire() {
        return new QueryQuestionNaire();
    }

    /**
     * Create an instance of {@link SaveQuestionUserResponse }
     * 
     */
    public SaveQuestionUserResponse createSaveQuestionUserResponse() {
        return new SaveQuestionUserResponse();
    }

    /**
     * Create an instance of {@link QueryQuestionNaireResponse }
     * 
     */
    public QueryQuestionNaireResponse createQueryQuestionNaireResponse() {
        return new QueryQuestionNaireResponse();
    }

    /**
     * Create an instance of {@link QueryQuestionResponse }
     * 
     */
    public QueryQuestionResponse createQueryQuestionResponse() {
        return new QueryQuestionResponse();
    }

    /**
     * Create an instance of {@link QueryNaireByCode }
     * 
     */
    public QueryNaireByCode createQueryNaireByCode() {
        return new QueryNaireByCode();
    }

    /**
     * Create an instance of {@link QueryNaireByCodeResponse }
     * 
     */
    public QueryNaireByCodeResponse createQueryNaireByCodeResponse() {
        return new QueryNaireByCodeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryNaireByCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "queryNaireByCode")
    public JAXBElement<QueryNaireByCode> createQueryNaireByCode(QueryNaireByCode value) {
        return new JAXBElement<QueryNaireByCode>(_QueryNaireByCode_QNAME, QueryNaireByCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryQuestionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "queryQuestionResponse")
    public JAXBElement<QueryQuestionResponse> createQueryQuestionResponse(QueryQuestionResponse value) {
        return new JAXBElement<QueryQuestionResponse>(_QueryQuestionResponse_QNAME, QueryQuestionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryNaireByCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "queryNaireByCodeResponse")
    public JAXBElement<QueryNaireByCodeResponse> createQueryNaireByCodeResponse(QueryNaireByCodeResponse value) {
        return new JAXBElement<QueryNaireByCodeResponse>(_QueryNaireByCodeResponse_QNAME, QueryNaireByCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryQuestionNaire }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "queryQuestionNaire")
    public JAXBElement<QueryQuestionNaire> createQueryQuestionNaire(QueryQuestionNaire value) {
        return new JAXBElement<QueryQuestionNaire>(_QueryQuestionNaire_QNAME, QueryQuestionNaire.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveQuestionUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "saveQuestionUser")
    public JAXBElement<SaveQuestionUser> createSaveQuestionUser(SaveQuestionUser value) {
        return new JAXBElement<SaveQuestionUser>(_SaveQuestionUser_QNAME, SaveQuestionUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUseQuestionAnswerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "saveUseQuestionAnswerResponse")
    public JAXBElement<SaveUseQuestionAnswerResponse> createSaveUseQuestionAnswerResponse(SaveUseQuestionAnswerResponse value) {
        return new JAXBElement<SaveUseQuestionAnswerResponse>(_SaveUseQuestionAnswerResponse_QNAME, SaveUseQuestionAnswerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveUseQuestionAnswer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "saveUseQuestionAnswer")
    public JAXBElement<SaveUseQuestionAnswer> createSaveUseQuestionAnswer(SaveUseQuestionAnswer value) {
        return new JAXBElement<SaveUseQuestionAnswer>(_SaveUseQuestionAnswer_QNAME, SaveUseQuestionAnswer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryQuestion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "queryQuestion")
    public JAXBElement<QueryQuestion> createQueryQuestion(QueryQuestion value) {
        return new JAXBElement<QueryQuestion>(_QueryQuestion_QNAME, QueryQuestion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryQuestionNaireResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "queryQuestionNaireResponse")
    public JAXBElement<QueryQuestionNaireResponse> createQueryQuestionNaireResponse(QueryQuestionNaireResponse value) {
        return new JAXBElement<QueryQuestionNaireResponse>(_QueryQuestionNaireResponse_QNAME, QueryQuestionNaireResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveQuestionUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.question.xxdai.com/", name = "saveQuestionUserResponse")
    public JAXBElement<SaveQuestionUserResponse> createSaveQuestionUserResponse(SaveQuestionUserResponse value) {
        return new JAXBElement<SaveQuestionUserResponse>(_SaveQuestionUserResponse_QNAME, SaveQuestionUserResponse.class, null, value);
    }

}
