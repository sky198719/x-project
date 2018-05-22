/**
* <p>Title: DicCommonUtils.java</p>
* <p>Description: </p>
* <p>Copyright (c) 2014, www.xinxindai.com All Rights Reserved. </p>
* <p>Company: www.xinxindai.com</p>
* @author huna
* @date 2014年10月23日
* @version 1.0
*/
package com.xxdai.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xxdai.client.CXF_Factory;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.system.bo.DicCommonVo;
import com.xxdai.system.ws.DicCXFService;
import com.xxdai.ws.util.WSModelResponse;

/**
 * @author huna
 * @since jdk1.6
 * @version 上午11:00:49
 */
public class DicCommonUtils {
	
	private static DicCXFService dicCxfService = (DicCXFService)CXF_Factory.getFactory(DicCXFService.class, Configuration.getInstance().getValue("webService_url") + "/dicWebService").create();
	
	/**
     * 根据typeCode、pkey获取pvalue的值
     * @param typeCode
     * @param pkey
     * @return pvalue
     */
	public static String getDicValue(String typeCode, Object pkey){
    	Map map = new HashMap();
        map.put("typeCode", typeCode);
        map.put("pkey", pkey);
    	String str = dicCxfService.queryDicCommonByTypeCodeAndPkey(JsonUtil.beanToJson(map));
        WSModelResponse res = JsonUtil.jsonToBean(str, WSModelResponse.class);
        if(res.getData() != null){
        	DicCommonVo vo = JsonUtil.jsonToBean(res.getData().toString(), DicCommonVo.class);
            if(vo != null){
            	return vo.getPvalue();
            }
        }
        return "";
    }
	
	/**
	 * 根据typeCode获取详细的字典数据列表
	 * @param typeCode
	 * @return commonList
	 */
	public static List<DicCommonVo> getCommonList(String typeCode){
		String code="{'code':'"+typeCode+"'}";
        String str= dicCxfService.queryCommonValue(code);
		WSModelResponse modelResponse=JsonUtil.jsonToBean(str, WSModelResponse.class);
		List<DicCommonVo> commonList = JsonUtil.jsonToList(modelResponse.getData().toString(), DicCommonVo.class);
		return commonList;
	}
}
