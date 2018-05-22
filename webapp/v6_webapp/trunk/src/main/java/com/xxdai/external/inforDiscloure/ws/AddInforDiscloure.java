
package com.xxdai.external.inforDiscloure.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>addInforDiscloure complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="addInforDiscloure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addInforDiscloureStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addInforDiscloure", propOrder = {
    "addInforDiscloureStr"
})
public class AddInforDiscloure {

    protected String addInforDiscloureStr;

    /**
     * 获取addInforDiscloureStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddInforDiscloureStr() {
        return addInforDiscloureStr;
    }

    /**
     * 设置addInforDiscloureStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddInforDiscloureStr(String value) {
        this.addInforDiscloureStr = value;
    }

}
