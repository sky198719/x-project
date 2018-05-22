
package com.xxdai.external.borrowFee.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.borrowFee.ws package. 
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

    private final static QName _SelectBorrowFeesMapByBorrowIdResponse_QNAME = new QName("http://webservice.borrow.xxdai.com/", "selectBorrowFeesMapByBorrowIdResponse");
    private final static QName _DaoException_QNAME = new QName("http://webservice.borrow.xxdai.com/", "DaoException");
    private final static QName _SelectBorrowFeesMapByBorrowId_QNAME = new QName("http://webservice.borrow.xxdai.com/", "selectBorrowFeesMapByBorrowId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.borrowFee.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectBorrowFeesMapByBorrowId }
     * 
     */
    public SelectBorrowFeesMapByBorrowId createSelectBorrowFeesMapByBorrowId() {
        return new SelectBorrowFeesMapByBorrowId();
    }

    /**
     * Create an instance of {@link DaoException }
     * 
     */
    public DaoException createDaoException() {
        return new DaoException();
    }

    /**
     * Create an instance of {@link SelectBorrowFeesMapByBorrowIdResponse }
     * 
     */
    public SelectBorrowFeesMapByBorrowIdResponse createSelectBorrowFeesMapByBorrowIdResponse() {
        return new SelectBorrowFeesMapByBorrowIdResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectBorrowFeesMapByBorrowIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "selectBorrowFeesMapByBorrowIdResponse")
    public JAXBElement<SelectBorrowFeesMapByBorrowIdResponse> createSelectBorrowFeesMapByBorrowIdResponse(SelectBorrowFeesMapByBorrowIdResponse value) {
        return new JAXBElement<SelectBorrowFeesMapByBorrowIdResponse>(_SelectBorrowFeesMapByBorrowIdResponse_QNAME, SelectBorrowFeesMapByBorrowIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DaoException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "DaoException")
    public JAXBElement<DaoException> createDaoException(DaoException value) {
        return new JAXBElement<DaoException>(_DaoException_QNAME, DaoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectBorrowFeesMapByBorrowId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.borrow.xxdai.com/", name = "selectBorrowFeesMapByBorrowId")
    public JAXBElement<SelectBorrowFeesMapByBorrowId> createSelectBorrowFeesMapByBorrowId(SelectBorrowFeesMapByBorrowId value) {
        return new JAXBElement<SelectBorrowFeesMapByBorrowId>(_SelectBorrowFeesMapByBorrowId_QNAME, SelectBorrowFeesMapByBorrowId.class, null, value);
    }

}
