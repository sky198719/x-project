
package com.xxdai.person.ws.baseinfo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.person.ws.baseinfo package. 
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

    private final static QName _AlterBaseInfo_QNAME = new QName("http://interfaces.webService.xxdai.com/", "alterBaseInfo");
    private final static QName _AlterBaseInfoResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "alterBaseInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.person.ws.baseinfo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AlterBaseInfoResponse }
     * 
     */
    public AlterBaseInfoResponse createAlterBaseInfoResponse() {
        return new AlterBaseInfoResponse();
    }

    /**
     * Create an instance of {@link AlterBaseInfo }
     * 
     */
    public AlterBaseInfo createAlterBaseInfo() {
        return new AlterBaseInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlterBaseInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "alterBaseInfo")
    public JAXBElement<AlterBaseInfo> createAlterBaseInfo(AlterBaseInfo value) {
        return new JAXBElement<AlterBaseInfo>(_AlterBaseInfo_QNAME, AlterBaseInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlterBaseInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "alterBaseInfoResponse")
    public JAXBElement<AlterBaseInfoResponse> createAlterBaseInfoResponse(AlterBaseInfoResponse value) {
        return new JAXBElement<AlterBaseInfoResponse>(_AlterBaseInfoResponse_QNAME, AlterBaseInfoResponse.class, null, value);
    }

}
