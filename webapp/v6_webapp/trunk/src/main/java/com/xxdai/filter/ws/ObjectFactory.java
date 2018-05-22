
package com.xxdai.filter.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.filter.ws package. 
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

    private final static QName _PutUserIdToRedis_QNAME = new QName("http://webservice.batch.xxdai.com/", "putUserIdToRedis");
    private final static QName _PutUserDimensionToIO_QNAME = new QName("http://webservice.batch.xxdai.com/", "putUserDimensionToIO");
    private final static QName _PutUserDimensionToIOResponse_QNAME = new QName("http://webservice.batch.xxdai.com/", "putUserDimensionToIOResponse");
    private final static QName _PutUserIdToRedisResponse_QNAME = new QName("http://webservice.batch.xxdai.com/", "putUserIdToRedisResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.filter.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PutUserIdToRedis }
     * 
     */
    public PutUserIdToRedis createPutUserIdToRedis() {
        return new PutUserIdToRedis();
    }

    /**
     * Create an instance of {@link PutUserIdToRedisResponse }
     * 
     */
    public PutUserIdToRedisResponse createPutUserIdToRedisResponse() {
        return new PutUserIdToRedisResponse();
    }

    /**
     * Create an instance of {@link PutUserDimensionToIOResponse }
     * 
     */
    public PutUserDimensionToIOResponse createPutUserDimensionToIOResponse() {
        return new PutUserDimensionToIOResponse();
    }

    /**
     * Create an instance of {@link PutUserDimensionToIO }
     * 
     */
    public PutUserDimensionToIO createPutUserDimensionToIO() {
        return new PutUserDimensionToIO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutUserIdToRedis }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.batch.xxdai.com/", name = "putUserIdToRedis")
    public JAXBElement<PutUserIdToRedis> createPutUserIdToRedis(PutUserIdToRedis value) {
        return new JAXBElement<PutUserIdToRedis>(_PutUserIdToRedis_QNAME, PutUserIdToRedis.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutUserDimensionToIO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.batch.xxdai.com/", name = "putUserDimensionToIO")
    public JAXBElement<PutUserDimensionToIO> createPutUserDimensionToIO(PutUserDimensionToIO value) {
        return new JAXBElement<PutUserDimensionToIO>(_PutUserDimensionToIO_QNAME, PutUserDimensionToIO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutUserDimensionToIOResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.batch.xxdai.com/", name = "putUserDimensionToIOResponse")
    public JAXBElement<PutUserDimensionToIOResponse> createPutUserDimensionToIOResponse(PutUserDimensionToIOResponse value) {
        return new JAXBElement<PutUserDimensionToIOResponse>(_PutUserDimensionToIOResponse_QNAME, PutUserDimensionToIOResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutUserIdToRedisResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.batch.xxdai.com/", name = "putUserIdToRedisResponse")
    public JAXBElement<PutUserIdToRedisResponse> createPutUserIdToRedisResponse(PutUserIdToRedisResponse value) {
        return new JAXBElement<PutUserIdToRedisResponse>(_PutUserIdToRedisResponse_QNAME, PutUserIdToRedisResponse.class, null, value);
    }

}
