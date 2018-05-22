
package com.xxdai.external.redis.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.redis.ws package. 
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

    private final static QName _SaveAccountTradeMsg_QNAME = new QName("http://interfaces.webservice.redis.xxdai.com/", "saveAccountTradeMsg");
    private final static QName _SelectRedisMsgSeq_QNAME = new QName("http://interfaces.webservice.redis.xxdai.com/", "selectRedisMsgSeq");
    private final static QName _SaveAccountTradeMsgResponse_QNAME = new QName("http://interfaces.webservice.redis.xxdai.com/", "saveAccountTradeMsgResponse");
    private final static QName _SendMessage_QNAME = new QName("http://interfaces.webservice.redis.xxdai.com/", "sendMessage");
    private final static QName _SendMessageResponse_QNAME = new QName("http://interfaces.webservice.redis.xxdai.com/", "sendMessageResponse");
    private final static QName _SelectRedisMsgSeqResponse_QNAME = new QName("http://interfaces.webservice.redis.xxdai.com/", "selectRedisMsgSeqResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.redis.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendMessage }
     * 
     */
    public SendMessage createSendMessage() {
        return new SendMessage();
    }

    /**
     * Create an instance of {@link SendMessageResponse }
     * 
     */
    public SendMessageResponse createSendMessageResponse() {
        return new SendMessageResponse();
    }

    /**
     * Create an instance of {@link SelectRedisMsgSeqResponse }
     * 
     */
    public SelectRedisMsgSeqResponse createSelectRedisMsgSeqResponse() {
        return new SelectRedisMsgSeqResponse();
    }

    /**
     * Create an instance of {@link SelectRedisMsgSeq }
     * 
     */
    public SelectRedisMsgSeq createSelectRedisMsgSeq() {
        return new SelectRedisMsgSeq();
    }

    /**
     * Create an instance of {@link SaveAccountTradeMsg }
     * 
     */
    public SaveAccountTradeMsg createSaveAccountTradeMsg() {
        return new SaveAccountTradeMsg();
    }

    /**
     * Create an instance of {@link SaveAccountTradeMsgResponse }
     * 
     */
    public SaveAccountTradeMsgResponse createSaveAccountTradeMsgResponse() {
        return new SaveAccountTradeMsgResponse();
    }

    /**
     * Create an instance of {@link RedisMsg }
     * 
     */
    public RedisMsg createRedisMsg() {
        return new RedisMsg();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveAccountTradeMsg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redis.xxdai.com/", name = "saveAccountTradeMsg")
    public JAXBElement<SaveAccountTradeMsg> createSaveAccountTradeMsg(SaveAccountTradeMsg value) {
        return new JAXBElement<SaveAccountTradeMsg>(_SaveAccountTradeMsg_QNAME, SaveAccountTradeMsg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectRedisMsgSeq }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redis.xxdai.com/", name = "selectRedisMsgSeq")
    public JAXBElement<SelectRedisMsgSeq> createSelectRedisMsgSeq(SelectRedisMsgSeq value) {
        return new JAXBElement<SelectRedisMsgSeq>(_SelectRedisMsgSeq_QNAME, SelectRedisMsgSeq.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveAccountTradeMsgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redis.xxdai.com/", name = "saveAccountTradeMsgResponse")
    public JAXBElement<SaveAccountTradeMsgResponse> createSaveAccountTradeMsgResponse(SaveAccountTradeMsgResponse value) {
        return new JAXBElement<SaveAccountTradeMsgResponse>(_SaveAccountTradeMsgResponse_QNAME, SaveAccountTradeMsgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redis.xxdai.com/", name = "sendMessage")
    public JAXBElement<SendMessage> createSendMessage(SendMessage value) {
        return new JAXBElement<SendMessage>(_SendMessage_QNAME, SendMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redis.xxdai.com/", name = "sendMessageResponse")
    public JAXBElement<SendMessageResponse> createSendMessageResponse(SendMessageResponse value) {
        return new JAXBElement<SendMessageResponse>(_SendMessageResponse_QNAME, SendMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectRedisMsgSeqResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redis.xxdai.com/", name = "selectRedisMsgSeqResponse")
    public JAXBElement<SelectRedisMsgSeqResponse> createSelectRedisMsgSeqResponse(SelectRedisMsgSeqResponse value) {
        return new JAXBElement<SelectRedisMsgSeqResponse>(_SelectRedisMsgSeqResponse_QNAME, SelectRedisMsgSeqResponse.class, null, value);
    }

}
