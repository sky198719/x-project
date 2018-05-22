package com.xxdai.fuiou.util.common;

import com.xxdai.fuiou.util.httpclient.HttpClient4Util;
import com.xxdai.fuiou.util.httpclient.HttpParameter;
import com.xxdai.fuiou.util.httpclient.HttpResp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class YJPayUtil {

    private static Log log = LogFactory.getLog(YJPayUtil.class);

    /**
     * 请求一键支付接口
     */
    public String payAPIRequest(String requestURL, String data, boolean post) throws IOException {
        log.info("requestURL=" + requestURL);

        HttpClient4Util util;
        if (requestURL.contains("https")) {
            util = new HttpClient4Util(30000, true);
        } else {
            util = new HttpClient4Util(30000, false);
        }

        HttpParameter httpParameter = new HttpParameter();
        if (data != null) {
            httpParameter.add("FM", data);
        }
        log.info("FM=" + data);

        HttpResp httpResp;
        if (post) {
            httpResp = util.doPost(requestURL, httpParameter, "utf-8");
        } else {
            httpResp = util.doGet(requestURL, httpParameter, "utf-8");
        }
        return httpResp.getText("utf-8");
    }

    /**
     * 将一键支付返回的结果进行解析
     */
    public Map<String, String> checkResult(String result) throws Exception {
        log.info("响应结果：" + result);

        Map<String, String> hashMap = new HashMap<String, String>();
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new InputSource(new StringReader(result)));
        Element root = document.getRootElement();
        for (Iterator iter = root.elementIterator(); iter.hasNext(); ) {
            Element element = (Element) iter.next();
            hashMap.put(element.getName(), element.getText());
        }
        return hashMap;
    }

}
