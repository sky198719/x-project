
package com.xxdai.external.sms.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.sms.ws package. 
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

    private final static QName _SendVoiceSMS_QNAME = new QName("http://webservice.sendmsg.xxdai.com/", "sendVoiceSMS");
    private final static QName _SendSMSResponse_QNAME = new QName("http://webservice.sendmsg.xxdai.com/", "sendSMSResponse");
    private final static QName _SendSMS_QNAME = new QName("http://webservice.sendmsg.xxdai.com/", "sendSMS");
    private final static QName _SendVoiceSMSResponse_QNAME = new QName("http://webservice.sendmsg.xxdai.com/", "sendVoiceSMSResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.sms.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendSMSResponse }
     * 
     */
    public SendSMSResponse createSendSMSResponse() {
        return new SendSMSResponse();
    }

    /**
     * Create an instance of {@link SendVoiceSMSResponse }
     * 
     */
    public SendVoiceSMSResponse createSendVoiceSMSResponse() {
        return new SendVoiceSMSResponse();
    }

    /**
     * Create an instance of {@link SendSMS }
     * 
     */
    public SendSMS createSendSMS() {
        return new SendSMS();
    }

    /**
     * Create an instance of {@link SendVoiceSMS }
     * 
     */
    public SendVoiceSMS createSendVoiceSMS() {
        return new SendVoiceSMS();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendVoiceSMS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.sendmsg.xxdai.com/", name = "sendVoiceSMS")
    public JAXBElement<SendVoiceSMS> createSendVoiceSMS(SendVoiceSMS value) {
        return new JAXBElement<SendVoiceSMS>(_SendVoiceSMS_QNAME, SendVoiceSMS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSMSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.sendmsg.xxdai.com/", name = "sendSMSResponse")
    public JAXBElement<SendSMSResponse> createSendSMSResponse(SendSMSResponse value) {
        return new JAXBElement<SendSMSResponse>(_SendSMSResponse_QNAME, SendSMSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSMS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.sendmsg.xxdai.com/", name = "sendSMS")
    public JAXBElement<SendSMS> createSendSMS(SendSMS value) {
        return new JAXBElement<SendSMS>(_SendSMS_QNAME, SendSMS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendVoiceSMSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.sendmsg.xxdai.com/", name = "sendVoiceSMSResponse")
    public JAXBElement<SendVoiceSMSResponse> createSendVoiceSMSResponse(SendVoiceSMSResponse value) {
        return new JAXBElement<SendVoiceSMSResponse>(_SendVoiceSMSResponse_QNAME, SendVoiceSMSResponse.class, null, value);
    }

}
