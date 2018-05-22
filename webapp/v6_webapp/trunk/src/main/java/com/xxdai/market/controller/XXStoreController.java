package com.xxdai.market.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.account.model.MarketWsResponse;
import com.xxdai.client.ClientUtil;
import com.xxdai.core.util.http.HttpUtil;
import com.xxdai.core.util.json.JsonUtil;
import com.xxdai.core.util.lang.StringUtil;
import com.xxdai.external.market.ws.MarketCXFService;
import com.xxdai.external.user.ws.User;
import com.xxdai.market.bo.Levels;
import com.xxdai.market.bo.MarketConstant;
import com.xxdai.market.bo.MarketGoodsOV;
import com.xxdai.market.bo.MarketOrder;
import com.xxdai.util.Configuration;


@Controller
@RequestMapping(value = "/xxStore")
public class XXStoreController {
	private static final Logger log = Logger.getLogger(XXStoreController.class);
	
	private final MarketCXFService marketCXFService = (MarketCXFService) ClientUtil.getWebService(MarketCXFService.class,"marketWebService");
	
    @RequestMapping(value = "/getMarketGoods", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getMarketGoods(HttpServletRequest request,HttpServletResponse response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
	    	String pageSize = request.getParameter("pageSize");
	    	String currentPage = request.getParameter("currentPage");
    		paramJson.put("pageNum", currentPage);
    		paramJson.put("pageSize", pageSize);
    		
    		//log.info("XXStoreController getMarketGoods ----> params:" + paramJson.toJSONString());
			String resultStr = marketCXFService.queryMarketGoods(paramJson.toJSONString());
			//log.info("XXStoreController getMarketGoods ----> return:" + resultStr);
			
    		MarketWsResponse res =  JsonUtil.jsonToBean(resultStr, MarketWsResponse.class);
    		
    		if (res.getResultCode() == 200) {
    			String dataStr = String.valueOf(res.getData());
        		
        		List<MarketGoodsOV> list = JsonUtil.jsonToList(dataStr, MarketGoodsOV.class);
        		
        		for (MarketGoodsOV ov : list) {
        			ov.setExpressfee(ov.getExpressfee().setScale(2, BigDecimal.ROUND_HALF_UP));
        			ov.setPrice(ov.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
        			//ov.setXinxinCoinPayPrice(ov.getXinxinCoinPayPrice().setScale(2));
        			ov.setAccountPayPrice(ov.getAccountPayPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
        			ov.setAccountTotalPay(ov.getAccountTotalPay().setScale(2, BigDecimal.ROUND_HALF_UP));
        			ov.setUseMoney(ov.getUseMoney().setScale(2, BigDecimal.ROUND_HALF_UP));
        			//ov.setXinxinCoinTotalPay(ov.getXinxinCoinTotalPay().setScale(2));
        		}
        		
        		int totalPages;
        		int iPageSize = Integer.valueOf(pageSize);
        		if(res.getTotalCount() % iPageSize == 0) {
        			totalPages = res.getTotalCount() / iPageSize;
        		} else {
        			totalPages = res.getTotalCount() / iPageSize + 1;
        		}
        		
        		resultJson.put("resultCode", res.getResultCode());
    			resultJson.put("listData", list);
    			resultJson.put("totalPages", totalPages);
    			resultJson.put("imagePreUrl", Configuration.getInstance().getValue("image_back_url"));
			}else{
				resultJson = JSONObject.parseObject(resultStr);
			}

    	}catch(Exception e){
    		log.error("XXStoreController getMarketGoods ----> arise exception:" , e);
    		//返回页面
	    	resultJson.put("resultCode", -1);
	    	resultJson.put("resultDesc", "操作失败，请稍后重试...");
    	}
    	//log.info("XXStoreController getMarketGoods ----> return to page:" + resultJson.toJSONString());
		return resultJson.toJSONString();
	}
    
    @RequestMapping(value = "/getGoodsDetail", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getGoodsDetail(HttpServletRequest request,HttpServletResponse response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
    		String goodsId = request.getParameter("goodsId");
    		//log.info("XXStoreController getGoodsDetail ----> params:" + JSONObject.parse(goodsId).toString());
    		String resultStr = marketCXFService.queryMarketGoodsById(JSONObject.parse(goodsId).toString());
    		//log.info("XXStoreController getGoodsDetail ----> return:" + resultStr);
    		if (StringUtil.isNotBlank(resultStr)) {
    			resultJson = JSON.parseObject(resultStr);
    		}else{
    			resultJson.put("resultCode", "-1");
    			resultJson.put("resultDesc", "获取商城商品列表失败，请稍后重试...");
    		}
    	}catch(Exception e){
    		log.error("XXStoreController getGoodsDetail ----> arise exception:" , e);
    		//返回页面
    		resultJson.put("resultCode", -1);
    		resultJson.put("resultDesc", "操作失败，请稍后重试...");
    	}
    	//log.info("XXStoreController getGoodsDetail ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
    
    @RequestMapping(value = "/getMyCoins", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getMyCoins(HttpServletRequest request,HttpServletResponse response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
    		
    		User user = (User)request.getSession().getAttribute("loginUser");
    		if(null == user) {
    			resultJson.put("resultCode", -1);
        		resultJson.put("resultDesc", "用户未登录");
        		return resultJson.toJSONString();
    		}
    		
    		Levels levels = null;
    		//log.info("XXStoreController getMyCoins ----> params:" + user.getUserId());
    		String resultStr = marketCXFService.selectLevelsByUserId(JsonUtil.beanToJson(user.getUserId()));
    		//log.info("XXStoreController getMyCoins ----> return:" + resultStr);
    		MarketWsResponse mres = JsonUtil.jsonToBean(resultStr, MarketWsResponse.class);
    		if(mres.getData() != null){
    			levels = JsonUtil.jsonToBean(mres.getData().toString(), Levels.class);				
    		}
    		
    		int xxCoin = 0;     //新新币
			if(levels!=null && levels.getCoins() != null){
				xxCoin = levels.getCoins().intValue();
			}
    		
			resultJson.put("resultCode", "0");
			resultJson.put("resultDesc", "获取新新币成功");
			resultJson.put("myCoins", xxCoin);
    	}catch(Exception e){
    		log.error("XXStoreController getMyCoins ----> arise exception:" , e);
    		//返回页面
    		resultJson.put("resultCode", -1);
    		resultJson.put("resultDesc", "操作失败，请稍后重试...");
    	}
    	//log.info("XXStoreController getMyCoins ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
    
    @RequestMapping(value = "/realGoodsOrder", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String realGoodsOrder(HttpServletRequest request,HttpServletResponse response) {
    	JSONObject paramJson = new JSONObject();
    	JSONObject resultJson = new JSONObject();
    	try{
    		log.info("------------实物还订单处理开始------------");
    		User user = (User)request.getSession().getAttribute("loginUser");
    		if(null == user) {
    			resultJson.put("resultCode", -1);
        		resultJson.put("resultDesc", "用户未登录");
        		return resultJson.toJSONString();
    		}
    		
    		int resultCode = 0;
    		String resultDesc = "下单成功";
    		  
    		int payType = Integer.parseInt(request.getParameter("payType"));
    		String username = "保卫南海游戏用户";
    		String mobile = "4000169521";
    		String address = "保卫南海游戏营销方案虚拟地址";
    		String postCode = "000000";
    		String buyCount = request.getParameter("buyAmount");
    		String id = request.getParameter("goodsId");
    		String str = marketCXFService.queryMarketGoodsById(JsonUtil.beanToJson(id));
    		MarketWsResponse marketRes =  JsonUtil.jsonToBean(str, MarketWsResponse.class);
    		String dataStr = String.valueOf(marketRes.getData());
    		MarketGoodsOV marketGoods = JsonUtil.jsonToBean(dataStr, MarketGoodsOV.class);
    		
    		MarketOrder order = new MarketOrder();
    		//生成订单 
    		//order.setOrderNum("xxc_" + id + "_" + DateUtil.format(new Date(), "yyMMddHHmmss"));
    		log.info("--------------生成订单开始---------------------------");
    		
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("tableName", "xxd_market_order");
    		params.put("columnName", "ordernum");
    		str = marketCXFService.generateMarketNum(JsonUtil.beanToJson(params));
    		marketRes =  JsonUtil.jsonToBean(str, MarketWsResponse.class);
    		String orderNum = String.valueOf(marketRes.getData());
    		order.setOrderNum(orderNum);
    		
    		order.setOrderType(MarketConstant.XXD_MARKET_MATERIAL_ORDER_TYPE);
    		order.setBuyCount(Integer.valueOf(buyCount));
    		order.setAddressAccount(address);
    		order.setUserName(username);
    		order.setPayType(payType);
    		order.setAddTime(new Date());
    		log.info("--------------下单状态---------------------------");
    		order.setStatus(MarketConstant.XXD_MARKET_ALREADLY_PAY_ORDER_STATUS);//下单状态
    		order.setUserId(user.getUserId());
    		order.setPostCode(postCode);
    		order.setNumber(mobile);
    		order.setAddIp(HttpUtil.getRealIpAddr(request));
    			 
    		order.setGoodsNum(marketGoods.getGoodsNum());
     		order.setCostPrice(marketGoods.getPrice());//商品原价
     		order.setExpressFee(marketGoods.getExpressfee());
     		log.info("--------------商品原价---------------------------");
    		//validateToken(request, "goods_token_list");
    		
    		params = new HashMap<String, Object>();
    		params.put("marketOrder", order);
    		params.put("marketGoods", marketGoods);
    		params.put("userId", user.getUserId());
    		params.put("ip", HttpUtil.getRealIpAddr(request));
    		String marketJson = JsonUtil.beanToJson(params);

    		//生成订单
    		log.info("XXStoreController realGoodsOrder ----> params:" + params);
			str = marketCXFService.createMarketOrder(marketJson);
			log.info("XXStoreController realGoodsOrder ----> return:" + str);
			marketRes = JsonUtil.jsonToBean(str, MarketWsResponse.class);
			
			//新新币不足
			if(marketRes.getResultCode() == MarketConstant.XXD_NOT_ENOUGH_XINXIN_COINS_ERROR) {
				resultCode = -2;
				resultDesc = MarketConstant.XXD_NOT_ENOUGH_XINXIN_COINS_ERROR_MSG;
			}
			
			//余额不足错误
			if(marketRes.getResultCode() == MarketConstant.XXD_NOT_ENOUGH_ACCOUNT_ERROR) {
				resultCode = -1;
				resultDesc = MarketConstant.XXD_NOT_ENOUGH_ACCOUNT_ERROR_MSG;
			}
	
			//非法支付方式
			if(marketRes.getResultCode() == MarketConstant.XXD_MARKET_ILLEGAL_OPERATION_ERROR) {
				resultCode = -1;
				resultDesc = MarketConstant.XXD_MARKET_ILLEGAL_OPERATION_ERROR_MSG;
			}
			
			//账户冻结
			if(marketRes.getResultCode() == MarketConstant.XXD_ACCOUNT_FREEZE_ERROR) {
				resultCode = -1;
				resultDesc = MarketConstant.XXD_ACCOUNT_FREEZE_ERROR_MSG;
			}
			 
			//禁止提现
			if(marketRes.getResultCode() == MarketConstant.XXD_COULD_NOT_WITHDRAWALS_ERROR) {
				resultCode = -1;
				resultDesc = MarketConstant.XXD_COULD_NOT_WITHDRAWALS_ERROR_MSG;
			}
			
			//黑名单
			if(marketRes.getResultCode() == MarketConstant.XXD_BLACK_LIST_ERROR) {
				resultCode = -1;
				resultDesc = MarketConstant.XXD_BLACK_LIST_ERROR_MSG;
			}
			
			//价格计算异常错误
			if(marketRes.getResultCode() == MarketConstant.XXD_ILLEGA_PRICE_ERROR) {
				resultCode = -1;
				resultDesc = MarketConstant.XXD_ILLEGA_PRICE_ERROR_MSG;
			}
			log.info("-----------------------订单生成结果："+resultDesc);
			resultJson.put("resultCode", resultCode);
    		resultJson.put("resultDesc", resultDesc);
    		   
    	}catch(Exception e){
    		log.error("XXStoreController realGoodsOrder ----> arise exception:" , e);
    		//返回页面
    		resultJson.put("resultCode", -1);
    		resultJson.put("resultDesc", "操作失败，请稍后重试...");
    	}
    	log.info("XXStoreController realGoodsOrder ----> return to page:" + resultJson.toJSONString());
    	return resultJson.toJSONString();
    }
}
