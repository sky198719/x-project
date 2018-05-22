
package com.xxdai.external.activityUser.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>joinThanksGivingDayActivity complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="joinThanksGivingDayActivity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="joinParams" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "joinThanksGivingDayActivity", propOrder = {
    "joinParams"
})
public class JoinThanksGivingDayActivity {

    protected String joinParams;

    /**
     * 获取joinParams属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoinParams() {
        return joinParams;
    }

    /**
     * 设置joinParams属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoinParams(String value) {
        this.joinParams = value;
    }

}
