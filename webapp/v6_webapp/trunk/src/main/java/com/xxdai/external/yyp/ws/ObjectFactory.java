
package com.xxdai.external.yyp.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.yyp.ws package. 
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

    private final static QName _QuitYypResponse_QNAME = new QName("http://webservice.yyp.xxdai.com/", "quitYypResponse");
    private final static QName _BuyYyp_QNAME = new QName("http://webservice.yyp.xxdai.com/", "buyYyp");
    private final static QName _BuyYypResponse_QNAME = new QName("http://webservice.yyp.xxdai.com/", "buyYypResponse");
    private final static QName _QuitYyp_QNAME = new QName("http://webservice.yyp.xxdai.com/", "quitYyp");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.yyp.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QuitYyp }
     * 
     */
    public QuitYyp createQuitYyp() {
        return new QuitYyp();
    }

    /**
     * Create an instance of {@link BuyYypResponse }
     * 
     */
    public BuyYypResponse createBuyYypResponse() {
        return new BuyYypResponse();
    }

    /**
     * Create an instance of {@link BuyYyp }
     * 
     */
    public BuyYyp createBuyYyp() {
        return new BuyYyp();
    }

    /**
     * Create an instance of {@link QuitYypResponse }
     * 
     */
    public QuitYypResponse createQuitYypResponse() {
        return new QuitYypResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuitYypResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.yyp.xxdai.com/", name = "quitYypResponse")
    public JAXBElement<QuitYypResponse> createQuitYypResponse(QuitYypResponse value) {
        return new JAXBElement<QuitYypResponse>(_QuitYypResponse_QNAME, QuitYypResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyYyp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.yyp.xxdai.com/", name = "buyYyp")
    public JAXBElement<BuyYyp> createBuyYyp(BuyYyp value) {
        return new JAXBElement<BuyYyp>(_BuyYyp_QNAME, BuyYyp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyYypResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.yyp.xxdai.com/", name = "buyYypResponse")
    public JAXBElement<BuyYypResponse> createBuyYypResponse(BuyYypResponse value) {
        return new JAXBElement<BuyYypResponse>(_BuyYypResponse_QNAME, BuyYypResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuitYyp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.yyp.xxdai.com/", name = "quitYyp")
    public JAXBElement<QuitYyp> createQuitYyp(QuitYyp value) {
        return new JAXBElement<QuitYyp>(_QuitYyp_QNAME, QuitYyp.class, null, value);
    }

}
