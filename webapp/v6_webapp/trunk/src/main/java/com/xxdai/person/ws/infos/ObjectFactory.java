
package com.xxdai.person.ws.infos;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.person.ws.infos package. 
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

    private final static QName _UpdateInfosResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "updateInfosResponse");
    private final static QName _AddInfos_QNAME = new QName("http://interfaces.webService.xxdai.com/", "addInfos");
    private final static QName _UpdateInfos_QNAME = new QName("http://interfaces.webService.xxdai.com/", "updateInfos");
    private final static QName _AddInfosResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "addInfosResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.person.ws.infos
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddInfosResponse }
     * 
     */
    public AddInfosResponse createAddInfosResponse() {
        return new AddInfosResponse();
    }

    /**
     * Create an instance of {@link UpdateInfos }
     * 
     */
    public UpdateInfos createUpdateInfos() {
        return new UpdateInfos();
    }

    /**
     * Create an instance of {@link AddInfos }
     * 
     */
    public AddInfos createAddInfos() {
        return new AddInfos();
    }

    /**
     * Create an instance of {@link UpdateInfosResponse }
     * 
     */
    public UpdateInfosResponse createUpdateInfosResponse() {
        return new UpdateInfosResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateInfosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "updateInfosResponse")
    public JAXBElement<UpdateInfosResponse> createUpdateInfosResponse(UpdateInfosResponse value) {
        return new JAXBElement<UpdateInfosResponse>(_UpdateInfosResponse_QNAME, UpdateInfosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInfos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "addInfos")
    public JAXBElement<AddInfos> createAddInfos(AddInfos value) {
        return new JAXBElement<AddInfos>(_AddInfos_QNAME, AddInfos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateInfos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "updateInfos")
    public JAXBElement<UpdateInfos> createUpdateInfos(UpdateInfos value) {
        return new JAXBElement<UpdateInfos>(_UpdateInfos_QNAME, UpdateInfos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddInfosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "addInfosResponse")
    public JAXBElement<AddInfosResponse> createAddInfosResponse(AddInfosResponse value) {
        return new JAXBElement<AddInfosResponse>(_AddInfosResponse_QNAME, AddInfosResponse.class, null, value);
    }

}
