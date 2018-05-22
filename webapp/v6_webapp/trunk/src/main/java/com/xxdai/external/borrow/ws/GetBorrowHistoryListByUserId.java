
package com.xxdai.external.borrow.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getBorrowHistoryListByUserId complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getBorrowHistoryListByUserId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jSon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getBorrowHistoryListByUserId", propOrder = {
    "jSon"
})
public class GetBorrowHistoryListByUserId {

    protected String jSon;

    /**
     * 获取jSon属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJSon() {
        return jSon;
    }

    /**
     * 设置jSon属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJSon(String value) {
        this.jSon = value;
    }

}