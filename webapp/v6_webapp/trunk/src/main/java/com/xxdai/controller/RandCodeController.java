/**
 * Copyright (c) 2015, www.xinxindai.com All Rights Reserved. 
 *
 */
package com.xxdai.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxdai.util.Configuration;
import com.xxdai.util.RandCodeUtils;
import com.xxdai.util.TokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;


@Controller
@RequestMapping(value = "/randCode")
public class RandCodeController {

    private static final Log log = LogFactory.getLog(RandCodeController.class);

    @RequestMapping(value = "/createVerifyCode", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    void createVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        JSONObject obj = RandCodeUtils.getVerifyCode();
        if (null != obj) {
            String randCode = obj.getString("randCode");
            request.getSession().setAttribute("imgCode", randCode);

            byte[] imageCode = obj.getBytes("imageCode");
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(imageCode);
                BufferedImage image = ImageIO.read(in);
                OutputStream output = response.getOutputStream();
                //输出图象到页面
                ImageIO.write(image, "JPEG", response.getOutputStream());
                output.flush();
            } catch (Exception e) {
                log.error("生成图片验证码失败", e);
            }
        }
    }

    //获取验证码链接（跨域）   @author aiden at 2016-10-27 15:44:20
    @RequestMapping(value = "/getImageCodeUrl", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String getImageCodeUrl(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", 0 );
        jsonObject.put("msg", "获取验证码链接成功！");
        jsonObject.put("verifcodeUrl", Configuration.getInstance().getValue("webapp_url") + "/randCode/createVerifyCode.do");
        return TokenUtil.jsonpCallback(request, jsonObject.toJSONString());
    }

    //校验图片验证码（跨域+万能验证码） @author aiden at 2016-10-27 16:55:32
    @RequestMapping(value = "/checkVerifyCode4xyd", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    String checkVerifyCode4xyd(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String sessionCode = String.valueOf(request.getSession().getAttribute("imgCode"));
            String paramCode = request.getParameter("verifyCode");
            if (RandCodeUtils.checkVerifyCode(paramCode, sessionCode)) {
                result.put("resultCode", 0);
                result.put("msg", "图片验证码正确！");
            } else {
                result.put("resultCode", 1);
                result.put("msg", "图片验证码错误！");
            }
        } catch (Exception e) {
            log.error("验证图片验证码失败", e);
        }
        return  TokenUtil.jsonpCallback(request, result.toJSONString());
    }

    @RequestMapping(value = "/checkVerifyCode", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String checkVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            Object imgCodeObj = request.getSession().getAttribute("imgCode");
            String imgCode = request.getParameter("imgCode");
            if (imgCodeObj != null && imgCode != null && imgCode.equalsIgnoreCase(String.valueOf(imgCodeObj))) {
                result.put("resultCode", 0);
            } else {
                result.put("resultCode", 1);
            }
        } catch (Exception e) {
            log.error("验证图片验证码失败", e);
        } finally {
            return TokenUtil.jsonpCallback(request, result.toJSONString());
        }
    }
}
