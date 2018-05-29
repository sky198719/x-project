package com.xxd.h5.web.redpackage;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * created by xiguoding on 2018/4/27 下午3:02
 */
@Slf4j
public class RewardRestTest {
    private Object js = "{\n" +
            "    \"couponList\": [\n" +
            "      {\n" +
            "        \"amount\": 20,\n" +
            "        \"code\": \"dCauuWWmRMlMJeqfUU\",\n" +
            "        \"couponId\": 404,\n" +
            "        \"effectiveEndTime\": 1527868799000,\n" +
            "        \"effectiveStartTime\": 1517155200000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"Y\",\n" +
            "        \"isUsed\": \"Y\",\n" +
            "        \"name\": \"站内信测试红包\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              1,\n" +
            "              3,\n" +
            "              6,\n" +
            "              18\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 300,\n" +
            "        \"statusDescribe\": \"剩余34天过期\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 15,\n" +
            "        \"code\": \"jpSKg4RGa1vV7KmEoe\",\n" +
            "        \"couponId\": 382,\n" +
            "        \"effectiveEndTime\": 1546358399000,\n" +
            "        \"effectiveStartTime\": 1516204800000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"Y\",\n" +
            "        \"isUsed\": \"Y\",\n" +
            "        \"name\": \"_三月--红包加息标除外\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              3,\n" +
            "              6,\n" +
            "              12\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 200,\n" +
            "        \"statusDescribe\": \"剩余248天过期\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"pageIndex\": 1,\n" +
            "    \"pageSize\": 20,\n" +
            "    \"totalPages\": 1,\n" +
            "    \"totalSize\": 10\n" +
            "  }";

    private String str = "{\"amount\":20,\"code\":\"dCauuWWmRMlMJeqfUU\",\"couponId\":404,\"effectiveEndTime\":1527868799000,\"effectiveStartTime\":1517155200000,\"isEffective\":\"Y\",\"isEvent\":\"Y\",\"isUsed\":\"Y\",\"name\":\"站内信测试红包\",\"platform\":[\"WAP\",\"APP\",\"PC\"],\"productScope\":[{\"productType\":5,\"termsList\":[1,3,6,18]}],\"quota\":300,\"statusDescribe\":\"剩余34天过期\",\"type\":\"RED_ENVELOPE\"}";

    private String str2 = "        \n" +
            "{\n" +
            "    \"couponList\": [\n" +
            "      {\n" +
            "        \"amount\": 32,\n" +
            "        \"code\": \"CbqUggcGE1PxCm8caR\",\n" +
            "        \"couponId\": 386,\n" +
            "        \"effectiveEndTime\": 1546358399000,\n" +
            "        \"effectiveStartTime\": 1516204800000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"N\",\n" +
            "        \"isUsed\": \"N\",\n" +
            "        \"name\": \"只使用优惠券\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              3,\n" +
            "              6,\n" +
            "              12,\n" +
            "              18\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 200,\n" +
            "        \"statusDescribe\": \"已使用\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 25,\n" +
            "        \"code\": \"ZfiKrcBg48Db2PyFZb\",\n" +
            "        \"couponId\": 387,\n" +
            "        \"effectiveEndTime\": 1546358399000,\n" +
            "        \"effectiveStartTime\": 1516204800000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"N\",\n" +
            "        \"isUsed\": \"N\",\n" +
            "        \"name\": \"测试使用优惠券\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              3,\n" +
            "              6,\n" +
            "              12,\n" +
            "              18\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 300,\n" +
            "        \"statusDescribe\": \"已使用\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 12,\n" +
            "        \"code\": \"bTTeF95Yy2H7LFBud3\",\n" +
            "        \"couponId\": 401,\n" +
            "        \"effectiveEndTime\": 1535817599000,\n" +
            "        \"effectiveStartTime\": 1517155200000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"N\",\n" +
            "        \"isUsed\": \"Y\",\n" +
            "        \"name\": \"新发标测试红包\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              1,\n" +
            "              3,\n" +
            "              6,\n" +
            "              12\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 300,\n" +
            "        \"statusDescribe\": \"剩余126天过期\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 15,\n" +
            "        \"code\": \"jgZVltw0MFd8gNdd1s\",\n" +
            "        \"couponId\": 403,\n" +
            "        \"effectiveEndTime\": 1530460799000,\n" +
            "        \"effectiveStartTime\": 1517155200000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"N\",\n" +
            "        \"isUsed\": \"N\",\n" +
            "        \"name\": \"新发标红包\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              1,\n" +
            "              3,\n" +
            "              6,\n" +
            "              12,\n" +
            "              18\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 200,\n" +
            "        \"statusDescribe\": \"已使用\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 66,\n" +
            "        \"code\": \"JxpnfjIpd5SHvWixLa\",\n" +
            "        \"couponId\": 411,\n" +
            "        \"effectiveEndTime\": 2720966399000,\n" +
            "        \"effectiveStartTime\": 1521388800000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"N\",\n" +
            "        \"isUsed\": \"Y\",\n" +
            "        \"name\": \"邀请好友奖励红包\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              1,\n" +
            "              3,\n" +
            "              6\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 6600,\n" +
            "        \"statusDescribe\": \"剩余13843天过期\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 20,\n" +
            "        \"code\": \"d4WZcSWK7IbufaKuCW\",\n" +
            "        \"couponId\": 404,\n" +
            "        \"effectiveEndTime\": 1527868799000,\n" +
            "        \"effectiveStartTime\": 1517155200000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"Y\",\n" +
            "        \"isUsed\": \"Y\",\n" +
            "        \"name\": \"站内信测试红包\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              1,\n" +
            "              3,\n" +
            "              6,\n" +
            "              18\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 300,\n" +
            "        \"statusDescribe\": \"剩余34天过期\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 20,\n" +
            "        \"code\": \"n9EiyhMmn0TDNxCjpq\",\n" +
            "        \"couponId\": 404,\n" +
            "        \"effectiveEndTime\": 1527868799000,\n" +
            "        \"effectiveStartTime\": 1517155200000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"Y\",\n" +
            "        \"isUsed\": \"Y\",\n" +
            "        \"name\": \"站内信测试红包\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              1,\n" +
            "              3,\n" +
            "              6,\n" +
            "              18\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 300,\n" +
            "        \"statusDescribe\": \"剩余34天过期\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 20,\n" +
            "        \"code\": \"zGic1uGIZ593vElxO1\",\n" +
            "        \"couponId\": 404,\n" +
            "        \"effectiveEndTime\": 1527868799000,\n" +
            "        \"effectiveStartTime\": 1517155200000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"Y\",\n" +
            "        \"isUsed\": \"Y\",\n" +
            "        \"name\": \"站内信测试红包\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              1,\n" +
            "              3,\n" +
            "              6,\n" +
            "              18\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 300,\n" +
            "        \"statusDescribe\": \"剩余34天过期\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 20,\n" +
            "        \"code\": \"dCauuWWmRMlMJeqfUU\",\n" +
            "        \"couponId\": 404,\n" +
            "        \"effectiveEndTime\": 1527868799000,\n" +
            "        \"effectiveStartTime\": 1517155200000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"Y\",\n" +
            "        \"isUsed\": \"Y\",\n" +
            "        \"name\": \"站内信测试红包\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              1,\n" +
            "              3,\n" +
            "              6,\n" +
            "              18\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 300,\n" +
            "        \"statusDescribe\": \"剩余34天过期\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"amount\": 15,\n" +
            "        \"code\": \"jpSKg4RGa1vV7KmEoe\",\n" +
            "        \"couponId\": 382,\n" +
            "        \"effectiveEndTime\": 1546358399000,\n" +
            "        \"effectiveStartTime\": 1516204800000,\n" +
            "        \"isEffective\": \"Y\",\n" +
            "        \"isEvent\": \"Y\",\n" +
            "        \"isUsed\": \"Y\",\n" +
            "        \"name\": \"_三月--红包加息标除外\",\n" +
            "        \"platform\": [\n" +
            "          \"WAP\",\n" +
            "          \"APP\",\n" +
            "          \"PC\"\n" +
            "        ],\n" +
            "        \"productScope\": [\n" +
            "          {\n" +
            "            \"productType\": 5,\n" +
            "            \"termsList\": [\n" +
            "              3,\n" +
            "              6,\n" +
            "              12\n" +
            "            ]\n" +
            "          }\n" +
            "        ],\n" +
            "        \"quota\": 200,\n" +
            "        \"statusDescribe\": \"剩余248天过期\",\n" +
            "        \"type\": \"RED_ENVELOPE\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"pageIndex\": 1,\n" +
            "    \"pageSize\": 20,\n" +
            "    \"totalPages\": 1,\n" +
            "    \"totalSize\": 10\n" +
            "  }";

    @Test
    public void test() {
        //Coupons coupons = new Coupons();
        /*String string = js.toString();

        Coupons coupons = JSONObject.parseObject(string, Coupons.class);
        System.out.println(coupons);*/

        /*Integer size = JsonUtil.toJSONObject(str2).getInteger("totalSize");
        System.out.println(size);*/

        /*JSONObject jsonObject = JSONObject.parseObject(string);
        coupons.setPageIndex(jsonObject.getInteger("pageIndex"));
        coupons.setPageSize(jsonObject.getInteger("pageSize"));
        coupons.setTotalPages(jsonObject.getInteger("totalPages"));
        coupons.setTotalSize(jsonObject.getInteger("totalSize"));

        JSONArray couponList = jsonObject.getJSONArray("couponList");
        Iterator<Object> iterator = couponList.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);

            //Coupons.Coupon coupon = JSONObject.parseObject(next.toString(), Coupons.Coupon.class);
            //System.out.println(coupon);

            //coupons.getCouponList().add(coupon);
        }

        System.out.println(coupons);*/

    }

    @Test
    public void test1() {
        /*Coupons.Coupon coupon = JSONObject.parseObject(str, Coupons.Coupon.class);
        System.out.println(coupon);*/
    }
}
