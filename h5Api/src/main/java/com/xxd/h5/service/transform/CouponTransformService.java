package com.xxd.h5.service.transform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.enums.CouponStatusEnum;
import com.xxd.h5.common.enums.CouponTypeEnum;
import com.xxd.h5.common.enums.ProductTypeEnum;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.vo.coupon.CouponVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author zhangshengwen
 * @date 2017/11/22
 */
@Service
public class CouponTransformService extends H5BaseService {


    public CouponVO transformHasCanUse(JSONObject jsonObject) {
        return setHasCanUse(transform(jsonObject), jsonObject.getJSONArray("items"));
    }

    /**
     * 判断是否有可用红包,并设定相关字段.
     *
     * @param vo
     * @param jsonArray
     * @return
     */
    private  CouponVO setHasCanUse(CouponVO vo, JSONArray jsonArray) {
        boolean hasCanUser = false;
        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
            JSONObject j = (JSONObject) iterator.next();
            CouponVO.Item item = JsonUtil.toObject(j, CouponVO.Item.class);
            if (item.getCanUse() == 1) {
                hasCanUser = true;
            }
        }
        if (hasCanUser) {
            vo.setHasCanUse("1");
        }
        return vo;
    }

    public  CouponVO transformCoupon(JSONObject jsonObject) {
        if (jsonObject == null) {
            return new CouponVO();
        }
        JSONArray jsonArray = jsonObject.getJSONArray("couponList");
        CouponVO vo = new CouponVO();
        vo.setTotalCount(jsonArray.size());
        traversalTransformCoupon(vo, jsonArray);
        return vo;
    }

    private  void traversalTransformCoupon(CouponVO vo, JSONArray jsonArray) {
        List<CouponVO.Item> items = vo.getItems();
        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
            JSONObject j = (JSONObject) iterator.next();
            CouponVO.Item item = new CouponVO.Item();
            item.setType(CouponTypeEnum.COUPON.getType());

            if(j.containsKey("couponId")){
                item.setId(j.getString("couponId"));
            }

            if(j.containsKey("code")){
                item.setCode(j.getString("code"));
            }

            if(j.containsKey("name")){
                item.setTitle(j.getString("name"));
            }

            if(j.containsKey("isEffective") && j.containsKey("isUsed")){
                item.setStatus(getCouponStatus(j.getString("isEffective"), j.getString("isUsed")));
            }

            if(j.containsKey("productScope")){
                item.setProductRange(getProductRange(j.getJSONArray("productScope")));
            }

            if(j.containsKey("effectiveStartTime")){
                item.setStartDate(j.getLong("effectiveStartTime"));
            }

            if(j.containsKey("effectiveEndTime")){
                item.setValidDate(j.getLong("effectiveEndTime"));
            }

            if(j.containsKey("amount")){
                item.setAmount(j.getDouble("amount"));
            }


            if(j.containsKey("quota")){
                item.setAmountLimit(j.getDouble("quota"));
            }


            if(j.containsKey("platform")){
                item.setPlatform(getPlatform(j.getJSONArray("platform")));
            }


            items.add(item);
        }
    }


    /**
     * 判断优惠券状态. 先判断是否使用  再判断是否过期
     */
    private  int getCouponStatus(String isEffective, String isUsed) {
        if ("N".equals(isUsed)) {
            return CouponStatusEnum.USED.getStatus();
        }
        //  是否过期
        if ("N".equals(isEffective)) {
            return CouponStatusEnum.OVERDUE.getStatus();
        }
        return CouponStatusEnum.CAN_USE.getStatus();

    }


    /**
     * 使用范围
     * <p>
     * before
     * <p>
     * "productScope": [
     * {
     * "productType": 5,
     * "termsList": [
     * 1,
     * 3,
     * 6,
     * 12,
     * 24,
     * 36
     * ]
     * }
     * ]
     * after
     * </p>
     * <p>
     * _
     * </p>
     *
     * @param jsonArray
     * @return
     */
    private  String getProductRange(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return StringUtils.EMPTY;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            sb.append(ProductTypeEnum.getNameByType(jsonObject.getInteger("productType")));
            JSONArray termsList = jsonObject.getJSONArray("termsList");
            for (int j = 0; j < termsList.size(); j++) {
                Integer term = termsList.getInteger(j);
                if (j == termsList.size() - 1) {
                    sb.append(term);
                } else {
                    sb.append(term + "/");
                }
            }
            sb.append("个月");
            sb.append("、");
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 可使用平台
     * before
     * <p>
     * "platform": [
     * "WAP",
     * "APP",
     * "PC"
     * ]
     * </p>
     * after
     * <p>
     * "WAP"、"APP"、"PC"
     * </p>
     *
     * @return
     */
    private  String getPlatform(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return StringUtils.EMPTY;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < jsonArray.size(); i++) {
            sb.append(jsonArray.getString(i));
            sb.append("、");
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }



    /**
     * 优惠券的转换 将优惠券数据库格式转换成前面的红包数据格式一致
     * @param jsonObject 优惠券数据json对象
     * @return 返回转换后的数据格式
     */
    public CouponVO transform(JSONObject jsonObject) {
        if (jsonObject == null) {
            return new CouponVO();
        }
        CouponVO vo = new CouponVO();
        vo.setTotalCount(jsonObject.getInteger("totalCount"));
        traversalTransform(vo, jsonObject.getJSONArray("items"));
        return vo;
    }



    public CouponVO transform(JSONArray jsonArray) {
        if (jsonArray == null) {
            return new CouponVO();
        }
        CouponVO vo = new CouponVO();
        vo.setTotalCount(jsonArray.size());
        traversalTransform(vo, jsonArray);
        return vo;
    }

    /**
     * canUse字段赋值
     *
     * @param jsonObject
     * @return
     */
    public  CouponVO transform2CanUseCoupon(JSONObject jsonObject) {
        CouponVO vo = transformCoupon(jsonObject);
        for (CouponVO.Item item : vo.getItems()) {
            item.setCanUse(1);
        }
        return vo;
    }


    /**
     * 遍历做字段转换.
     * 做红包 优惠券融合的时候部分字段的转换
     * @param vo 接受的红包对象
     * @param jsonArray 红包的json数据
     */
    private void traversalTransform(CouponVO vo, JSONArray jsonArray) {
        List<CouponVO.Item> items = vo.getItems();
        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
            JSONObject j = (JSONObject) iterator.next();
            CouponVO.Item item = JsonUtil.toObject(j, CouponVO.Item.class);
            item.setType(CouponTypeEnum.REDPACKAGE.getType());
            item.setId(j.getString("redEnvelopId"));
            item.setCode(j.getString("redCode"));
            item.setPlatform("WAP、APP、PC");
            items.add(item);
        }
    }

}
