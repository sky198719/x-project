
package com.xxdai.external.popularize.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>saveUserSuperiorMapping complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="saveUserSuperiorMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userSuperiorMappingJson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveUserSuperiorMapping", propOrder = {
    "userSuperiorMappingJson"
})
public class SaveUserSuperiorMapping {

    protected String userSuperiorMappingJson;

    /**
     * 获取userSuperiorMappingJson属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserSuperiorMappingJson() {
        return userSuperiorMappingJson;
    }

    /**
     * 设置userSuperiorMappingJson属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserSuperiorMappingJson(String value) {
        this.userSuperiorMappingJson = value;
    }

}
