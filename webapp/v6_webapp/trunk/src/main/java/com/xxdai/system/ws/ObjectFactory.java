
package com.xxdai.system.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.system.ws package. 
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

    private final static QName _UpdateSConfig_QNAME = new QName("http://webservice.system.xxdai.com/", "updateSConfig");
    private final static QName _QuerySysConfigByKey_QNAME = new QName("http://webservice.system.xxdai.com/", "querySysConfigByKey");
    private final static QName _AddSConfig_QNAME = new QName("http://webservice.system.xxdai.com/", "addSConfig");
    private final static QName _AddSConfigResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "addSConfigResponse");
    private final static QName _UpdateSysConfigResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "UpdateSysConfigResponse");
    private final static QName _QueryObjectById_QNAME = new QName("http://webservice.system.xxdai.com/", "queryObjectById");
    private final static QName _ClearSysConfigAllCacheResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "clearSysConfigAllCacheResponse");
    private final static QName _UpdateSConfigResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "updateSConfigResponse");
    private final static QName _ClearSysConfigAllCache_QNAME = new QName("http://webservice.system.xxdai.com/", "clearSysConfigAllCache");
    private final static QName _QueryObjectByIdResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "queryObjectByIdResponse");
    private final static QName _ClearSysConfigCacheResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "clearSysConfigCacheResponse");
    private final static QName _QuerySysConfigByKeyResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "querySysConfigByKeyResponse");
    private final static QName _QueryPageSysConfigResponse_QNAME = new QName("http://webservice.system.xxdai.com/", "queryPageSysConfigResponse");
    private final static QName _ClearSysConfigCache_QNAME = new QName("http://webservice.system.xxdai.com/", "clearSysConfigCache");
    private final static QName _UpdateSysConfig_QNAME = new QName("http://webservice.system.xxdai.com/", "UpdateSysConfig");
    private final static QName _QueryPageSysConfig_QNAME = new QName("http://webservice.system.xxdai.com/", "queryPageSysConfig");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.system.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateSysConfig }
     * 
     */
    public UpdateSysConfig createUpdateSysConfig() {
        return new UpdateSysConfig();
    }

    /**
     * Create an instance of {@link ClearSysConfigCache }
     * 
     */
    public ClearSysConfigCache createClearSysConfigCache() {
        return new ClearSysConfigCache();
    }

    /**
     * Create an instance of {@link QueryPageSysConfigResponse }
     * 
     */
    public QueryPageSysConfigResponse createQueryPageSysConfigResponse() {
        return new QueryPageSysConfigResponse();
    }

    /**
     * Create an instance of {@link QueryPageSysConfig }
     * 
     */
    public QueryPageSysConfig createQueryPageSysConfig() {
        return new QueryPageSysConfig();
    }

    /**
     * Create an instance of {@link AddSConfig }
     * 
     */
    public AddSConfig createAddSConfig() {
        return new AddSConfig();
    }

    /**
     * Create an instance of {@link AddSConfigResponse }
     * 
     */
    public AddSConfigResponse createAddSConfigResponse() {
        return new AddSConfigResponse();
    }

    /**
     * Create an instance of {@link UpdateSysConfigResponse }
     * 
     */
    public UpdateSysConfigResponse createUpdateSysConfigResponse() {
        return new UpdateSysConfigResponse();
    }

    /**
     * Create an instance of {@link QuerySysConfigByKey }
     * 
     */
    public QuerySysConfigByKey createQuerySysConfigByKey() {
        return new QuerySysConfigByKey();
    }

    /**
     * Create an instance of {@link UpdateSConfig }
     * 
     */
    public UpdateSConfig createUpdateSConfig() {
        return new UpdateSConfig();
    }

    /**
     * Create an instance of {@link ClearSysConfigCacheResponse }
     * 
     */
    public ClearSysConfigCacheResponse createClearSysConfigCacheResponse() {
        return new ClearSysConfigCacheResponse();
    }

    /**
     * Create an instance of {@link QuerySysConfigByKeyResponse }
     * 
     */
    public QuerySysConfigByKeyResponse createQuerySysConfigByKeyResponse() {
        return new QuerySysConfigByKeyResponse();
    }

    /**
     * Create an instance of {@link UpdateSConfigResponse }
     * 
     */
    public UpdateSConfigResponse createUpdateSConfigResponse() {
        return new UpdateSConfigResponse();
    }

    /**
     * Create an instance of {@link ClearSysConfigAllCache }
     * 
     */
    public ClearSysConfigAllCache createClearSysConfigAllCache() {
        return new ClearSysConfigAllCache();
    }

    /**
     * Create an instance of {@link ClearSysConfigAllCacheResponse }
     * 
     */
    public ClearSysConfigAllCacheResponse createClearSysConfigAllCacheResponse() {
        return new ClearSysConfigAllCacheResponse();
    }

    /**
     * Create an instance of {@link QueryObjectById }
     * 
     */
    public QueryObjectById createQueryObjectById() {
        return new QueryObjectById();
    }

    /**
     * Create an instance of {@link QueryObjectByIdResponse }
     * 
     */
    public QueryObjectByIdResponse createQueryObjectByIdResponse() {
        return new QueryObjectByIdResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "updateSConfig")
    public JAXBElement<UpdateSConfig> createUpdateSConfig(UpdateSConfig value) {
        return new JAXBElement<UpdateSConfig>(_UpdateSConfig_QNAME, UpdateSConfig.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuerySysConfigByKey }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "querySysConfigByKey")
    public JAXBElement<QuerySysConfigByKey> createQuerySysConfigByKey(QuerySysConfigByKey value) {
        return new JAXBElement<QuerySysConfigByKey>(_QuerySysConfigByKey_QNAME, QuerySysConfigByKey.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "addSConfig")
    public JAXBElement<AddSConfig> createAddSConfig(AddSConfig value) {
        return new JAXBElement<AddSConfig>(_AddSConfig_QNAME, AddSConfig.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "addSConfigResponse")
    public JAXBElement<AddSConfigResponse> createAddSConfigResponse(AddSConfigResponse value) {
        return new JAXBElement<AddSConfigResponse>(_AddSConfigResponse_QNAME, AddSConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSysConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "UpdateSysConfigResponse")
    public JAXBElement<UpdateSysConfigResponse> createUpdateSysConfigResponse(UpdateSysConfigResponse value) {
        return new JAXBElement<UpdateSysConfigResponse>(_UpdateSysConfigResponse_QNAME, UpdateSysConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryObjectById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryObjectById")
    public JAXBElement<QueryObjectById> createQueryObjectById(QueryObjectById value) {
        return new JAXBElement<QueryObjectById>(_QueryObjectById_QNAME, QueryObjectById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearSysConfigAllCacheResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "clearSysConfigAllCacheResponse")
    public JAXBElement<ClearSysConfigAllCacheResponse> createClearSysConfigAllCacheResponse(ClearSysConfigAllCacheResponse value) {
        return new JAXBElement<ClearSysConfigAllCacheResponse>(_ClearSysConfigAllCacheResponse_QNAME, ClearSysConfigAllCacheResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "updateSConfigResponse")
    public JAXBElement<UpdateSConfigResponse> createUpdateSConfigResponse(UpdateSConfigResponse value) {
        return new JAXBElement<UpdateSConfigResponse>(_UpdateSConfigResponse_QNAME, UpdateSConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearSysConfigAllCache }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "clearSysConfigAllCache")
    public JAXBElement<ClearSysConfigAllCache> createClearSysConfigAllCache(ClearSysConfigAllCache value) {
        return new JAXBElement<ClearSysConfigAllCache>(_ClearSysConfigAllCache_QNAME, ClearSysConfigAllCache.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryObjectByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryObjectByIdResponse")
    public JAXBElement<QueryObjectByIdResponse> createQueryObjectByIdResponse(QueryObjectByIdResponse value) {
        return new JAXBElement<QueryObjectByIdResponse>(_QueryObjectByIdResponse_QNAME, QueryObjectByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearSysConfigCacheResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "clearSysConfigCacheResponse")
    public JAXBElement<ClearSysConfigCacheResponse> createClearSysConfigCacheResponse(ClearSysConfigCacheResponse value) {
        return new JAXBElement<ClearSysConfigCacheResponse>(_ClearSysConfigCacheResponse_QNAME, ClearSysConfigCacheResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuerySysConfigByKeyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "querySysConfigByKeyResponse")
    public JAXBElement<QuerySysConfigByKeyResponse> createQuerySysConfigByKeyResponse(QuerySysConfigByKeyResponse value) {
        return new JAXBElement<QuerySysConfigByKeyResponse>(_QuerySysConfigByKeyResponse_QNAME, QuerySysConfigByKeyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPageSysConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryPageSysConfigResponse")
    public JAXBElement<QueryPageSysConfigResponse> createQueryPageSysConfigResponse(QueryPageSysConfigResponse value) {
        return new JAXBElement<QueryPageSysConfigResponse>(_QueryPageSysConfigResponse_QNAME, QueryPageSysConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearSysConfigCache }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "clearSysConfigCache")
    public JAXBElement<ClearSysConfigCache> createClearSysConfigCache(ClearSysConfigCache value) {
        return new JAXBElement<ClearSysConfigCache>(_ClearSysConfigCache_QNAME, ClearSysConfigCache.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSysConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "UpdateSysConfig")
    public JAXBElement<UpdateSysConfig> createUpdateSysConfig(UpdateSysConfig value) {
        return new JAXBElement<UpdateSysConfig>(_UpdateSysConfig_QNAME, UpdateSysConfig.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPageSysConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.system.xxdai.com/", name = "queryPageSysConfig")
    public JAXBElement<QueryPageSysConfig> createQueryPageSysConfig(QueryPageSysConfig value) {
        return new JAXBElement<QueryPageSysConfig>(_QueryPageSysConfig_QNAME, QueryPageSysConfig.class, null, value);
    }

}
