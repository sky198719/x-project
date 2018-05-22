
package com.xxdai.external.activity.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>selectByuserid complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="selectByuserid">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "selectByuserid", propOrder = {
    "userid"
})
public class SelectByuserid {

    protected long userid;

    /**
     * 获取userid属性的值。
     * 
     */
    public long getUserid() {
        return userid;
    }

    /**
     * 设置userid属性的值。
     * 
     */
    public void setUserid(long value) {
        this.userid = value;
    }

}
