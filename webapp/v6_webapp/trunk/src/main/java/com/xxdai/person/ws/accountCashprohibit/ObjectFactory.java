
package com.xxdai.person.ws.accountCashprohibit;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.person.ws.accountCashprohibit package. 
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

    private final static QName _SelectByUserIdIsFS_QNAME = new QName("http://interfaces.webService.xxdai.com/", "selectByUserIdIsFS");
    private final static QName _SelectByUserIdIsFSResponse_QNAME = new QName("http://interfaces.webService.xxdai.com/", "selectByUserIdIsFSResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.person.ws.accountCashprohibit
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectByUserIdIsFSResponse }
     * 
     */
    public SelectByUserIdIsFSResponse createSelectByUserIdIsFSResponse() {
        return new SelectByUserIdIsFSResponse();
    }

    /**
     * Create an instance of {@link SelectByUserIdIsFS }
     * 
     */
    public SelectByUserIdIsFS createSelectByUserIdIsFS() {
        return new SelectByUserIdIsFS();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectByUserIdIsFS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "selectByUserIdIsFS")
    public JAXBElement<SelectByUserIdIsFS> createSelectByUserIdIsFS(SelectByUserIdIsFS value) {
        return new JAXBElement<SelectByUserIdIsFS>(_SelectByUserIdIsFS_QNAME, SelectByUserIdIsFS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectByUserIdIsFSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webService.xxdai.com/", name = "selectByUserIdIsFSResponse")
    public JAXBElement<SelectByUserIdIsFSResponse> createSelectByUserIdIsFSResponse(SelectByUserIdIsFSResponse value) {
        return new JAXBElement<SelectByUserIdIsFSResponse>(_SelectByUserIdIsFSResponse_QNAME, SelectByUserIdIsFSResponse.class, null, value);
    }

}
