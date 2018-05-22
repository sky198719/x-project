
package com.xxdai.system.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>clearSysConfigCache complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="clearSysConfigCache">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="syskey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clearSysConfigCache", propOrder = {
    "syskey"
})
public class ClearSysConfigCache {

    protected String syskey;

    /**
     * 获取syskey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSyskey() {
        return syskey;
    }

    /**
     * 设置syskey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSyskey(String value) {
        this.syskey = value;
    }

}
