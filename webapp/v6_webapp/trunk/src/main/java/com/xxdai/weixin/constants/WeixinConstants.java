package com.xxdai.weixin.constants;

public final class WeixinConstants {
    /**
     * TOKEN 是你在微信平台开发模式中设置
     */
    public final static String TOKEN = "xinxindai";

    public static final String SIGNATURE = "signature";

    public static final String TIMESTAMP = "timestamp";

    public static final String NONCE = "nonce";

    public static final String ECHOSTR = "echostr";

    /**
     * 字符编码，UTF-8
     */
    public static final String CHARACTERENCODING_UTF8 = "UTF-8";

    public static final String CONTENTTYPE_TEXT_HTML = "text/html";
    /**
     * 微信用户关注公众号状态。1，关注
     */
    public static final int WX_USER_STATUS_SUBSCRIBE = 1;
    /**
     * 微信用户关注公众号状态。2，取消关注
     */
    public static final int WX_USER_STATUS_UNSUBSCRIBE = 2;
    /**
     * 微信用户关注公众号状态。3，未关注(游客身份访问时，获取的用户信息)
     */
    public static final int WX_USER_STATUS_NOTCONCERN = 3;

    /**
     * 微信用户openid标识字符串
     */
    public static final String WEIXIN_USER_SESSION_OPENID = "openid";

    /**
     * 微信服务号
     */
    public static final String WEIXIN_SERVICE_CODE = "WEIXIN_SERVICE_CODE";
    /**
     * 微信开发者服务器令牌
     */
    public static final String WEIXIN_TOKEN = "WEIXIN_TOKEN";

    /**
     * 微信应用ID
     */
    public static final String WEIXIN_APPID = "WEIXIN_APPID";

    /**
     * 微信应用密钥
     */
    public static final String WEIXIN_APPSECRET = "WEIXIN_APPSECRET";

    /**
     * 微信全局唯一票据
     */
    public static final String WEIXIN_ACCESS_TOKEN = "WEIXIN_ACCESS_TOKEN";

    /**
     * 微信鉴权页面重定向URI
     */
    public static final String REDIRECT_URI = "REDIRECT_URI";

    /**
     * 微信专用的front模块地址(子域名)
     */
    public static final String WEIXIN_FRONT_URL = "WEIXIN_FRONT_URL";

    /**
     * 微信专用JS、CSS版本
     */
    public static final String WEIXIN_CSSJS_VERSION = "WEIXIN_CSSJS_VERSION";

    /**
     * 微信下载页面===IOS版本下载地址
     */
    public static final String WEIXIN_IOS_DOWNLOAD = "WEIXIN_IOS_DOWNLOAD";

    /**
     * 微信下载页面===ANDROID版本下载地址
     */
    public static final String WEIXIN_ANDROID_DOWNLOAD = "WEIXIN_ANDROID_URL";

    /**
     * 每个红包的使用金额
     */
    public static final String COUPONTENDERMONEY = "COUPONTENDERMONEY";

    /**
     * 微信红包分享链接的图标
     */
    public static final String WEIXIN_COUPON_SHARE_IMG = "COUPON_SHARE_IMG";


    public static final String JSAPI_TICKET = "jsapi_ticket";
    public static final long JSAPI_EXPIRES_IN = 7200;
}
