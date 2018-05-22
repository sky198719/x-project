
package com.xxdai.external.redis.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>saveAccountTradeMsgResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="saveAccountTradeMsgResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://interfaces.webservice.redis.xxdai.com/}redisMsg" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveAccountTradeMsgResponse", propOrder = {
    "_return"
})
public class SaveAccountTradeMsgResponse {

    @XmlElement(name = "return")
    protected RedisMsg _return;

    /**
     * 获取return属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RedisMsg }
     *     
     */
    public RedisMsg getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RedisMsg }
     *     
     */
    public void setReturn(RedisMsg value) {
        this._return = value;
    }

}
