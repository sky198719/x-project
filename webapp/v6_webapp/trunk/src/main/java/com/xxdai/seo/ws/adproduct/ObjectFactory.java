
package com.xxdai.seo.ws.adproduct;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.seo.ws.adproduct package. 
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

    private final static QName _GetSeoAdProductById_QNAME = new QName("http://webservice.seo.xxdai.com/", "getSeoAdProductById");
    private final static QName _SelectAdProductResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "selectAdProductResponse");
    private final static QName _DeleteAdProductResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "deleteAdProductResponse");
    private final static QName _SaveOrUpdateAdProductResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "saveOrUpdateAdProductResponse");
    private final static QName _SelectPageAdProduct_QNAME = new QName("http://webservice.seo.xxdai.com/", "selectPageAdProduct");
    private final static QName _SelectPageAdProductResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "selectPageAdProductResponse");
    private final static QName _SaveOrUpdateAdProduct_QNAME = new QName("http://webservice.seo.xxdai.com/", "saveOrUpdateAdProduct");
    private final static QName _GetSeoAdProductByIdResponse_QNAME = new QName("http://webservice.seo.xxdai.com/", "getSeoAdProductByIdResponse");
    private final static QName _SelectAdProduct_QNAME = new QName("http://webservice.seo.xxdai.com/", "selectAdProduct");
    private final static QName _DeleteAdProduct_QNAME = new QName("http://webservice.seo.xxdai.com/", "deleteAdProduct");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.seo.ws.adproduct
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteAdProduct }
     * 
     */
    public DeleteAdProduct createDeleteAdProduct() {
        return new DeleteAdProduct();
    }

    /**
     * Create an instance of {@link SelectAdProduct }
     * 
     */
    public SelectAdProduct createSelectAdProduct() {
        return new SelectAdProduct();
    }

    /**
     * Create an instance of {@link GetSeoAdProductByIdResponse }
     * 
     */
    public GetSeoAdProductByIdResponse createGetSeoAdProductByIdResponse() {
        return new GetSeoAdProductByIdResponse();
    }

    /**
     * Create an instance of {@link SaveOrUpdateAdProduct }
     * 
     */
    public SaveOrUpdateAdProduct createSaveOrUpdateAdProduct() {
        return new SaveOrUpdateAdProduct();
    }

    /**
     * Create an instance of {@link SelectPageAdProductResponse }
     * 
     */
    public SelectPageAdProductResponse createSelectPageAdProductResponse() {
        return new SelectPageAdProductResponse();
    }

    /**
     * Create an instance of {@link SelectPageAdProduct }
     * 
     */
    public SelectPageAdProduct createSelectPageAdProduct() {
        return new SelectPageAdProduct();
    }

    /**
     * Create an instance of {@link SaveOrUpdateAdProductResponse }
     * 
     */
    public SaveOrUpdateAdProductResponse createSaveOrUpdateAdProductResponse() {
        return new SaveOrUpdateAdProductResponse();
    }

    /**
     * Create an instance of {@link DeleteAdProductResponse }
     * 
     */
    public DeleteAdProductResponse createDeleteAdProductResponse() {
        return new DeleteAdProductResponse();
    }

    /**
     * Create an instance of {@link SelectAdProductResponse }
     * 
     */
    public SelectAdProductResponse createSelectAdProductResponse() {
        return new SelectAdProductResponse();
    }

    /**
     * Create an instance of {@link GetSeoAdProductById }
     * 
     */
    public GetSeoAdProductById createGetSeoAdProductById() {
        return new GetSeoAdProductById();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSeoAdProductById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "getSeoAdProductById")
    public JAXBElement<GetSeoAdProductById> createGetSeoAdProductById(GetSeoAdProductById value) {
        return new JAXBElement<GetSeoAdProductById>(_GetSeoAdProductById_QNAME, GetSeoAdProductById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAdProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "selectAdProductResponse")
    public JAXBElement<SelectAdProductResponse> createSelectAdProductResponse(SelectAdProductResponse value) {
        return new JAXBElement<SelectAdProductResponse>(_SelectAdProductResponse_QNAME, SelectAdProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAdProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "deleteAdProductResponse")
    public JAXBElement<DeleteAdProductResponse> createDeleteAdProductResponse(DeleteAdProductResponse value) {
        return new JAXBElement<DeleteAdProductResponse>(_DeleteAdProductResponse_QNAME, DeleteAdProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveOrUpdateAdProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "saveOrUpdateAdProductResponse")
    public JAXBElement<SaveOrUpdateAdProductResponse> createSaveOrUpdateAdProductResponse(SaveOrUpdateAdProductResponse value) {
        return new JAXBElement<SaveOrUpdateAdProductResponse>(_SaveOrUpdateAdProductResponse_QNAME, SaveOrUpdateAdProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectPageAdProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "selectPageAdProduct")
    public JAXBElement<SelectPageAdProduct> createSelectPageAdProduct(SelectPageAdProduct value) {
        return new JAXBElement<SelectPageAdProduct>(_SelectPageAdProduct_QNAME, SelectPageAdProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectPageAdProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "selectPageAdProductResponse")
    public JAXBElement<SelectPageAdProductResponse> createSelectPageAdProductResponse(SelectPageAdProductResponse value) {
        return new JAXBElement<SelectPageAdProductResponse>(_SelectPageAdProductResponse_QNAME, SelectPageAdProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveOrUpdateAdProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "saveOrUpdateAdProduct")
    public JAXBElement<SaveOrUpdateAdProduct> createSaveOrUpdateAdProduct(SaveOrUpdateAdProduct value) {
        return new JAXBElement<SaveOrUpdateAdProduct>(_SaveOrUpdateAdProduct_QNAME, SaveOrUpdateAdProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSeoAdProductByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "getSeoAdProductByIdResponse")
    public JAXBElement<GetSeoAdProductByIdResponse> createGetSeoAdProductByIdResponse(GetSeoAdProductByIdResponse value) {
        return new JAXBElement<GetSeoAdProductByIdResponse>(_GetSeoAdProductByIdResponse_QNAME, GetSeoAdProductByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectAdProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "selectAdProduct")
    public JAXBElement<SelectAdProduct> createSelectAdProduct(SelectAdProduct value) {
        return new JAXBElement<SelectAdProduct>(_SelectAdProduct_QNAME, SelectAdProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAdProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.seo.xxdai.com/", name = "deleteAdProduct")
    public JAXBElement<DeleteAdProduct> createDeleteAdProduct(DeleteAdProduct value) {
        return new JAXBElement<DeleteAdProduct>(_DeleteAdProduct_QNAME, DeleteAdProduct.class, null, value);
    }

}
