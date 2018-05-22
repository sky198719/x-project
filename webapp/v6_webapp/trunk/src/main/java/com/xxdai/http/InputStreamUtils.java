package com.xxdai.http;

import java.io.*;

/**
 * String、Stream转换工具类.
 */
public class InputStreamUtils {

    private final static int BUFFER_SIZE = 1024;

    private final static String DEFAULT_ENCODING = "UTF-8";

    /**
     * @param in
     * @return String
     * @throws Exception
     */
    public static String inputStream2String(InputStream in) throws IOException {
       return inputStream2String(in, DEFAULT_ENCODING);
    }

    /**
     * @param in
     * @param encoding
     * @return String
     * @throws Exception
     */
    public static String inputStream2String(InputStream in, String encoding) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), encoding);
    }

    /**
     * @param in
     * @param encoding
     * @return InputStream
     * @throws Exception
     */
    public static InputStream string2InputStream(String in, String encoding) throws UnsupportedEncodingException {
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes(encoding));
        return is;
    }

    /**
     * @param in
     * @return InputStream
     * @throws Exception
     */
    public static InputStream string2InputStream(String in) {
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes());
        return is;
    }
}
