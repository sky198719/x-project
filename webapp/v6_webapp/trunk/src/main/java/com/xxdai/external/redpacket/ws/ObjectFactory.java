
package com.xxdai.external.redpacket.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xxdai.external.redpacket.ws package. 
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

    private final static QName _GetRedpacketTotal_QNAME = new QName("http://interfaces.webservice.redpacket.xxdai.com/", "getRedpacketTotal");
    private final static QName _GetRedpacketTotalResponse_QNAME = new QName("http://interfaces.webservice.redpacket.xxdai.com/", "getRedpacketTotalResponse");
    private final static QName _GetRedpacketListResponse_QNAME = new QName("http://interfaces.webservice.redpacket.xxdai.com/", "getRedpacketListResponse");
    private final static QName _GetRedpacketList_QNAME = new QName("http://interfaces.webservice.redpacket.xxdai.com/", "getRedpacketList");
    private final static QName _GetRedpacketListByUseCoditionResponse_QNAME = new QName("http://interfaces.webservice.redpacket.xxdai.com/", "getRedpacketListByUseCoditionResponse");
    private final static QName _GetRedpacketListByUseCodition_QNAME = new QName("http://interfaces.webservice.redpacket.xxdai.com/", "getRedpacketListByUseCodition");
    private final static QName _QueryRedPacketRecord_QNAME = new QName("http://interfaces.webservice.redpacket.xxdai.com/", "queryRedPacketRecord");
    private final static QName _QueryRedPacketRecordResponse_QNAME = new QName("http://interfaces.webservice.redpacket.xxdai.com/", "queryRedPacketRecordResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xxdai.external.redpacket.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetRedpacketListByUseCoditionResponse }
     * 
     */
    public GetRedpacketListByUseCoditionResponse createGetRedpacketListByUseCoditionResponse() {
        return new GetRedpacketListByUseCoditionResponse();
    }

    /**
     * Create an instance of {@link GetRedpacketListByUseCodition }
     * 
     */
    public GetRedpacketListByUseCodition createGetRedpacketListByUseCodition() {
        return new GetRedpacketListByUseCodition();
    }

    /**
     * Create an instance of {@link QueryRedPacketRecordResponse }
     * 
     */
    public QueryRedPacketRecordResponse createQueryRedPacketRecordResponse() {
        return new QueryRedPacketRecordResponse();
    }

    /**
     * Create an instance of {@link QueryRedPacketRecord }
     * 
     */
    public QueryRedPacketRecord createQueryRedPacketRecord() {
        return new QueryRedPacketRecord();
    }

    /**
     * Create an instance of {@link GetRedpacketList }
     * 
     */
    public GetRedpacketList createGetRedpacketList() {
        return new GetRedpacketList();
    }

    /**
     * Create an instance of {@link GetRedpacketTotalResponse }
     * 
     */
    public GetRedpacketTotalResponse createGetRedpacketTotalResponse() {
        return new GetRedpacketTotalResponse();
    }

    /**
     * Create an instance of {@link GetRedpacketListResponse }
     * 
     */
    public GetRedpacketListResponse createGetRedpacketListResponse() {
        return new GetRedpacketListResponse();
    }

    /**
     * Create an instance of {@link GetRedpacketTotal }
     * 
     */
    public GetRedpacketTotal createGetRedpacketTotal() {
        return new GetRedpacketTotal();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRedpacketTotal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redpacket.xxdai.com/", name = "getRedpacketTotal")
    public JAXBElement<GetRedpacketTotal> createGetRedpacketTotal(GetRedpacketTotal value) {
        return new JAXBElement<GetRedpacketTotal>(_GetRedpacketTotal_QNAME, GetRedpacketTotal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRedpacketTotalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redpacket.xxdai.com/", name = "getRedpacketTotalResponse")
    public JAXBElement<GetRedpacketTotalResponse> createGetRedpacketTotalResponse(GetRedpacketTotalResponse value) {
        return new JAXBElement<GetRedpacketTotalResponse>(_GetRedpacketTotalResponse_QNAME, GetRedpacketTotalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRedpacketListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redpacket.xxdai.com/", name = "getRedpacketListResponse")
    public JAXBElement<GetRedpacketListResponse> createGetRedpacketListResponse(GetRedpacketListResponse value) {
        return new JAXBElement<GetRedpacketListResponse>(_GetRedpacketListResponse_QNAME, GetRedpacketListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRedpacketList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redpacket.xxdai.com/", name = "getRedpacketList")
    public JAXBElement<GetRedpacketList> createGetRedpacketList(GetRedpacketList value) {
        return new JAXBElement<GetRedpacketList>(_GetRedpacketList_QNAME, GetRedpacketList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRedpacketListByUseCoditionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redpacket.xxdai.com/", name = "getRedpacketListByUseCoditionResponse")
    public JAXBElement<GetRedpacketListByUseCoditionResponse> createGetRedpacketListByUseCoditionResponse(GetRedpacketListByUseCoditionResponse value) {
        return new JAXBElement<GetRedpacketListByUseCoditionResponse>(_GetRedpacketListByUseCoditionResponse_QNAME, GetRedpacketListByUseCoditionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRedpacketListByUseCodition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redpacket.xxdai.com/", name = "getRedpacketListByUseCodition")
    public JAXBElement<GetRedpacketListByUseCodition> createGetRedpacketListByUseCodition(GetRedpacketListByUseCodition value) {
        return new JAXBElement<GetRedpacketListByUseCodition>(_GetRedpacketListByUseCodition_QNAME, GetRedpacketListByUseCodition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRedPacketRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redpacket.xxdai.com/", name = "queryRedPacketRecord")
    public JAXBElement<QueryRedPacketRecord> createQueryRedPacketRecord(QueryRedPacketRecord value) {
        return new JAXBElement<QueryRedPacketRecord>(_QueryRedPacketRecord_QNAME, QueryRedPacketRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRedPacketRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://interfaces.webservice.redpacket.xxdai.com/", name = "queryRedPacketRecordResponse")
    public JAXBElement<QueryRedPacketRecordResponse> createQueryRedPacketRecordResponse(QueryRedPacketRecordResponse value) {
        return new JAXBElement<QueryRedPacketRecordResponse>(_QueryRedPacketRecordResponse_QNAME, QueryRedPacketRecordResponse.class, null, value);
    }

}
