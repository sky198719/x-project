package com.xxdai.lianlian.wap.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxdai.lianlian.share.security.Md5Algorithm;
import com.xxdai.lianlian.share.security.RSAUtil;
import com.xxdai.lianlian.wap.enums.SignTypeEnum;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* 常用工具函数
* @author guoyx e-mail:guoyx@lianlian.com
* @date:2013-6-25 下午05:23:05
* @version :1.0
*
*/
public class YinTongUtil{

    /**
     * str空判断
     * @param str
     * @return
     * @author guoyx
     */
    public static boolean isnull(String str)
    {
        if (null == str || str.equalsIgnoreCase("null") || str.equals(""))
        {
            return true;
        } else
            return false;
    }
    /**
     * 签名验证
     *
     * @param reqStr
     * @return
     */
    public static boolean checkSign(String reqStr, String rsa_public, String md5_key)
    {
        JSONObject reqObj = JSON.parseObject(reqStr);
        if (reqObj == null)
        {
            return false;
        }
        String sign_type = reqObj.getString("sign_type");
        if (SignTypeEnum.MD5.getCode().equals(sign_type))
        {
            return checkSignMD5(reqObj, md5_key);
        } else
        {
            return checkSignRSA(reqObj, rsa_public);
        }
    }
    /**
     * RSA签名验证
     *
     * @param reqObj
     * @return
     * @author guoyx
     */
    private static boolean checkSignRSA(JSONObject reqObj, String rsa_public)
    {

        System.out.println("进入商户[" + reqObj.getString("oid_partner")
                + "]RSA签名验证");
        if (reqObj == null)
        {
            return false;
        }
        String sign = reqObj.getString("sign");
        // 生成待签名串
        String sign_src = genSignData(reqObj);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]待签名原串"
                + sign_src);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]签名串"
                + sign);
        try
        {
            if (RSAUtil.checksign(rsa_public, sign_src, sign))
            {
                System.out.println("商户[" + reqObj.getString("oid_partner")
                        + "]RSA签名验证通过");
                return true;
            } else
            {
                System.out.println("商户[" + reqObj.getString("oid_partner")
                        + "]RSA签名验证未通过");
                return false;
            }
        } catch (Exception e)
        {
            System.out.println("商户[" + reqObj.getString("oid_partner")
                    + "]RSA签名验证异常" + e.getMessage());
            return false;
        }
    }
    /**
     * MD5签名验证
     *
     * @return
     * @author guoyx
     */
    private static boolean checkSignMD5(JSONObject reqObj, String md5_key)
    {
        System.out.println("进入商户[" + reqObj.getString("oid_partner")
                + "]MD5签名验证");
        if (reqObj == null)
        {
            return false;
        }
        String sign = reqObj.getString("sign");
        // 生成待签名串
        String sign_src = genSignData(reqObj);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]待签名原串"
                + sign_src);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]签名串"
                + sign);
        sign_src += "&key=" + md5_key;
        try
        {
            if (sign.equals(Md5Algorithm.getInstance().md5Digest(
                    sign_src.getBytes("utf-8"))))
            {
                System.out.println("商户[" + reqObj.getString("oid_partner")
                        + "]MD5签名验证通过");
                return true;
            } else
            {
                System.out.println("商户[" + reqObj.getString("oid_partner")
                        + "]MD5签名验证未通过");
                return false;
            }
        } catch (UnsupportedEncodingException e)
        {
            System.out.println("商户[" + reqObj.getString("oid_partner")
                    + "]MD5签名验证异常" + e.getMessage());
            return false;
        }
    }
    /**
     * 生成待签名串
     * @return
     * @author guoyx
     */
    public static String genSignData(JSONObject jsonObject)
    {
        StringBuffer content = new StringBuffer();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(jsonObject.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++)
        {
            String key = (String) keys.get(i);
            if ("sign".equals(key))
            {
                continue;
            }
            String value = jsonObject.getString(key);
            // 空串不参与签名
            if (isnull(value))
            {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&"))
        {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }
}
