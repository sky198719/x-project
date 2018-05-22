
package com.xxdai.external.hotActivities.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.hotActivities.ws package. 
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

    private final static QName _UpdateHotActivities_QNAME = new QName("http://webservice.activity.xxdai.com/", "updateHotActivities");
    private final static QName _UpdateHotActivitiesResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "updateHotActivitiesResponse");
    private final static QName _GetHotActivitiesListResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "getHotActivitiesListResponse");
    private final static QName _AddHotActivities_QNAME = new QName("http://webservice.activity.xxdai.com/", "addHotActivities");
    private final static QName _GetHotActivitiesList_QNAME = new QName("http://webservice.activity.xxdai.com/", "getHotActivitiesList");
    private final static QName _GetHotActivities_QNAME = new QName("http://webservice.activity.xxdai.com/", "getHotActivities");
    private final static QName _DeleteHotActivities_QNAME = new QName("http://webservice.activity.xxdai.com/", "deleteHotActivities");
    private final static QName _DeleteHotActivitiesResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "deleteHotActivitiesResponse");
    private final static QName _GetHotActivitiesResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "getHotActivitiesResponse");
    private final static QName _AddHotActivitiesResponse_QNAME = new QName("http://webservice.activity.xxdai.com/", "addHotActivitiesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.hotActivities.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteHotActivitiesResponse }
     * 
     */
    public DeleteHotActivitiesResponse createDeleteHotActivitiesResponse() {
        return new DeleteHotActivitiesResponse();
    }

    /**
     * Create an instance of {@link DeleteHotActivities }
     * 
     */
    public DeleteHotActivities createDeleteHotActivities() {
        return new DeleteHotActivities();
    }

    /**
     * Create an instance of {@link AddHotActivitiesResponse }
     * 
     */
    public AddHotActivitiesResponse createAddHotActivitiesResponse() {
        return new AddHotActivitiesResponse();
    }

    /**
     * Create an instance of {@link GetHotActivitiesResponse }
     * 
     */
    public GetHotActivitiesResponse createGetHotActivitiesResponse() {
        return new GetHotActivitiesResponse();
    }

    /**
     * Create an instance of {@link GetHotActivitiesListResponse }
     * 
     */
    public GetHotActivitiesListResponse createGetHotActivitiesListResponse() {
        return new GetHotActivitiesListResponse();
    }

    /**
     * Create an instance of {@link UpdateHotActivitiesResponse }
     * 
     */
    public UpdateHotActivitiesResponse createUpdateHotActivitiesResponse() {
        return new UpdateHotActivitiesResponse();
    }

    /**
     * Create an instance of {@link UpdateHotActivities }
     * 
     */
    public UpdateHotActivities createUpdateHotActivities() {
        return new UpdateHotActivities();
    }

    /**
     * Create an instance of {@link GetHotActivities }
     * 
     */
    public GetHotActivities createGetHotActivities() {
        return new GetHotActivities();
    }

    /**
     * Create an instance of {@link GetHotActivitiesList }
     * 
     */
    public GetHotActivitiesList createGetHotActivitiesList() {
        return new GetHotActivitiesList();
    }

    /**
     * Create an instance of {@link AddHotActivities }
     * 
     */
    public AddHotActivities createAddHotActivities() {
        return new AddHotActivities();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateHotActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "updateHotActivities")
    public JAXBElement<UpdateHotActivities> createUpdateHotActivities(UpdateHotActivities value) {
        return new JAXBElement<UpdateHotActivities>(_UpdateHotActivities_QNAME, UpdateHotActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateHotActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "updateHotActivitiesResponse")
    public JAXBElement<UpdateHotActivitiesResponse> createUpdateHotActivitiesResponse(UpdateHotActivitiesResponse value) {
        return new JAXBElement<UpdateHotActivitiesResponse>(_UpdateHotActivitiesResponse_QNAME, UpdateHotActivitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotActivitiesListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "getHotActivitiesListResponse")
    public JAXBElement<GetHotActivitiesListResponse> createGetHotActivitiesListResponse(GetHotActivitiesListResponse value) {
        return new JAXBElement<GetHotActivitiesListResponse>(_GetHotActivitiesListResponse_QNAME, GetHotActivitiesListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddHotActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "addHotActivities")
    public JAXBElement<AddHotActivities> createAddHotActivities(AddHotActivities value) {
        return new JAXBElement<AddHotActivities>(_AddHotActivities_QNAME, AddHotActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotActivitiesList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "getHotActivitiesList")
    public JAXBElement<GetHotActivitiesList> createGetHotActivitiesList(GetHotActivitiesList value) {
        return new JAXBElement<GetHotActivitiesList>(_GetHotActivitiesList_QNAME, GetHotActivitiesList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "getHotActivities")
    public JAXBElement<GetHotActivities> createGetHotActivities(GetHotActivities value) {
        return new JAXBElement<GetHotActivities>(_GetHotActivities_QNAME, GetHotActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteHotActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "deleteHotActivities")
    public JAXBElement<DeleteHotActivities> createDeleteHotActivities(DeleteHotActivities value) {
        return new JAXBElement<DeleteHotActivities>(_DeleteHotActivities_QNAME, DeleteHotActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteHotActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "deleteHotActivitiesResponse")
    public JAXBElement<DeleteHotActivitiesResponse> createDeleteHotActivitiesResponse(DeleteHotActivitiesResponse value) {
        return new JAXBElement<DeleteHotActivitiesResponse>(_DeleteHotActivitiesResponse_QNAME, DeleteHotActivitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "getHotActivitiesResponse")
    public JAXBElement<GetHotActivitiesResponse> createGetHotActivitiesResponse(GetHotActivitiesResponse value) {
        return new JAXBElement<GetHotActivitiesResponse>(_GetHotActivitiesResponse_QNAME, GetHotActivitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddHotActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.activity.xxdai.com/", name = "addHotActivitiesResponse")
    public JAXBElement<AddHotActivitiesResponse> createAddHotActivitiesResponse(AddHotActivitiesResponse value) {
        return new JAXBElement<AddHotActivitiesResponse>(_AddHotActivitiesResponse_QNAME, AddHotActivitiesResponse.class, null, value);
    }

}
