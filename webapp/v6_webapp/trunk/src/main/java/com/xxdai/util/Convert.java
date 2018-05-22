package com.xxdai.util;

public class Convert {

    public static String getType(int type) {
        if (type == -1) {
            return "信用标";
        } else if (type == 2) {
            return "推荐标";
        } else if (type == 3) {
            return "净值标";
        } else if (type == 4) {
            return "秒还标";
        } else if (type == 5) {
            return "调剂标";
        } else {
            return "抵押标";
        }
    }

    public static String getBankLogo(String bankName) {
        StringBuilder bankLogo = new StringBuilder();
        bankLogo.append(Configuration.getInstance().getValue("mobile_prefix_url") + "/images/bankLogo/");
        if ("华夏银行".equals(bankName)) {
            bankLogo.append("huaxia");
        } else if ("中国工商银行".equals(bankName)) {
            bankLogo.append("gongshang");
        } else if ("招商银行".equals(bankName)) {
            bankLogo.append("zhaoshang");
        } else if ("中国农业银行".equals(bankName)) {
            bankLogo.append("nongye");
        } else if ("中国建设银行".equals(bankName)) {
            bankLogo.append("jianshe");
        } else if ("北京银行".equals(bankName)) {
            bankLogo.append("beijing");
        } else if ("交通银行".equals(bankName)) {
            bankLogo.append("jiaotong");
        } else if ("兴业银行".equals(bankName)) {
            bankLogo.append("xingye");
        } else if ("南京银行".equals(bankName)) {
            bankLogo.append("nanjing");
        } else if ("中国民生银行".equals(bankName)) {
            bankLogo.append("minsheng");
        } else if ("中国光大银行".equals(bankName)) {
            bankLogo.append("guangda");
        } else if ("中国银行".equals(bankName)) {
            bankLogo.append("zhongguo");
        } else if ("平安银行".equals(bankName)) {
            bankLogo.append("pingan");
        } else if ("东亚银行".equals(bankName)) {
            bankLogo.append("dongya");
        } else if ("宁波银行".equals(bankName)) {
            bankLogo.append("ningbo");
        } else if ("中信银行".equals(bankName)) {
            bankLogo.append("zhongxin");
        } else if ("广发银行".equals(bankName)) {
            bankLogo.append("guangfa");
        } else if ("上海银行".equals(bankName)) {
            bankLogo.append("shanghai");
        } else if ("上海浦东发展银行".equals(bankName)) {
            bankLogo.append("pufa");
        } else if ("中国邮政".equals(bankName)) {
            bankLogo.append("youzheng");
        } else if ("杭州银行".equals(bankName)) {
            bankLogo.append("hangzhou");
        } else if ("上海农业商业银行".equals(bankName)) {
            bankLogo.append("shanghainongshang");
        } else {
            bankLogo.append("default");
        }
        bankLogo.append(".png");
        return bankLogo.toString();
    }

    /**
     * 连连支付的银行编码转为V6系统的银行编码
     */
    public static String lianlianToV6BankCode(String bankCode) {
        String rtBankCode = "";
        if ("01020000".equals(bankCode)) {
            rtBankCode = "icbc";
        } else if ("01030000".equals(bankCode)) {
            rtBankCode = "abc";
        } else if ("01040000".equals(bankCode)) {
            rtBankCode = "boc";
        } else if ("01050000".equals(bankCode)) {
            rtBankCode = "ccb";
        } else if ("03080000".equals(bankCode)) {
            rtBankCode = "cmb";
        } else if ("03100000".equals(bankCode)) {
            rtBankCode = "spdb";
        } else if ("03030000".equals(bankCode)) {
            rtBankCode = "ceb";
        } else if ("03070000".equals(bankCode)) {
            rtBankCode = "pingan";
        } else if ("03040000".equals(bankCode)) {
            rtBankCode = "hxb";
        } else if ("03090000".equals(bankCode)) {
            rtBankCode = "cib";
        } else if ("03020000".equals(bankCode)) {
            rtBankCode = "citic";
        }
        return rtBankCode;
    }

    /**
     * 富友支付的银行编码转为V6系统的银行编码
     */
    public static String fuiouToV6BankCode(String bankCode) {
        String rtBankCode = "";
        if ("0801020000".equals(bankCode)) {
            rtBankCode = "icbc"; // 工行
        } else if ("0801030000".equals(bankCode)) {
            rtBankCode = "abc"; // 农行
        } else if ("0801040000".equals(bankCode)) {
            rtBankCode = "boc"; // 中行
        } else if ("0801050000".equals(bankCode) || "0801050001".equals(bankCode)) {
            rtBankCode = "ccb"; // 建行
        } else if ("0803020000".equals(bankCode)) {
            rtBankCode = "citic"; // 中信
        } else if ("0803090000".equals(bankCode) || "0803090002".equals(bankCode)) {
            rtBankCode = "cib"; // 兴业
        } else if ("0803100000".equals(bankCode)) {
            rtBankCode = "spdb"; // 浦发
        } else if ("0803050000".equals(bankCode)) {
            rtBankCode = "cmbc"; // 民生
        } else if ("0803060000".equals(bankCode)) {
            rtBankCode = "cgb"; // 广发
        } else if ("0803180000".equals(bankCode) || "0804105840".equals(bankCode)) {
            rtBankCode = "pingan"; // 平安
        } else if ("0801000000".equals(bankCode) || "0801009999".equals(bankCode)) {
            rtBankCode = "psbc"; // 邮储
        } else if ("0803030000".equals(bankCode)) {
            rtBankCode = "ceb"; // 光大
        } else if ("0803080000".equals(bankCode)) {
            rtBankCode = "cmb"; // 招行
        } else if ("0803040000".equals(bankCode)) {
            rtBankCode = "hxb"; // 华夏
        }
        return rtBankCode;
    }

}


