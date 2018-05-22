package com.xxdai.randcode.controller;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.*;

@Controller
@RequestMapping(value = "/verify")
public class NewRandCodeController {
    private static final Log log = LogFactory.getLog(NewRandCodeController.class);
    private static ResourceBundle resb = ResourceBundle.getBundle("kaptcha");
    private static Properties props = new Properties();
    private static Producer kaptchaProducer = null;
    private static String sessionKeyValue = null;
    private static String sessionKeyDateValue = null;

    static {
        ImageIO.setUseCache(false);

        Set<String> keys = resb.keySet();
        Iterator ite = keys.iterator();
        while (ite.hasNext()) {
            Object key = ite.next();
            String value = resb.getString(key.toString());
            props.put(key,value);
            log.info(key+"="+value);
        }

        Config config = new Config(props);
        kaptchaProducer = config.getProducerImpl();
        sessionKeyValue = config.getSessionKey();
        sessionKeyDateValue = config.getSessionDate();

    }

    @RequestMapping(value = "/code", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    void code(HttpServletRequest request, HttpServletResponse response) {
        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        try {
            response.setContentType("image/jpeg");
            String capText = this.kaptchaProducer.createText();
            request.getSession().setAttribute(this.sessionKeyValue, capText);
            request.getSession().setAttribute(this.sessionKeyDateValue, new Date());

            BufferedImage bi = this.kaptchaProducer.createImage(capText);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            log.info("capText="+capText);

        } catch (Exception e) {
            log.error("生成图片验证码失败", e);
        }
    }
    @RequestMapping(value = "/getCode", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String getCode(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = true;
        if(flag){
            return "";
        }
        Object sessionImgCodeObj = request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        return sessionImgCodeObj == null ? "null" : sessionImgCodeObj.toString();
    }

}
