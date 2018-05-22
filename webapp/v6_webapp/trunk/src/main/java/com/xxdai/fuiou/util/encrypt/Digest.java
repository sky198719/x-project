package com.xxdai.fuiou.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {

    public static final String ENCODE = "UTF-8";

    public static String signMD5(String aValue) {
        try {
            byte[] input = aValue.getBytes(Digest.ENCODE);
            MessageDigest md = MessageDigest.getInstance("MD5");
            return ConvertUtils.toHex(md.digest(input));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
