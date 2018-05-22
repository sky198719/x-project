
package com.xxdai.seo.ws.category;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>queryAllManagement complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="queryAllManagement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paramJson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryAllManagement", propOrder = {
    "paramJson"
})
public class QueryAllManagement {

    protected String paramJson;

    /**
     * 获取paramJson属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamJson() {
        return paramJson;
    }

    /**
     * 设置paramJson属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamJson(String value) {
        this.paramJson = value;
    }

}
