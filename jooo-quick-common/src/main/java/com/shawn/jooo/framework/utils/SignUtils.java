package com.shawn.jooo.framework.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名工具类
 *
 * @author shawn
 */
public class SignUtils {

    private static final String SIGN_PARAM = "sign";
    public static final String SIGN_METHOD_MD5 = "md5";
    public static final String SIGN_METHOD_HMAC = "hmac";
    public static final String CHARSET_UTF8 = "UTF-8";

    public static boolean checkSign(HttpServletRequest request, String secret) {
        String sign = request.getParameter(SIGN_PARAM);
        Map<String, String> params = new HashMap<>();
        Enumeration<String> iter = request.getParameterNames();
        while (iter.hasMoreElements()) {
            String paramName = iter.nextElement().trim();
            if (!paramName.equals(SIGN_PARAM)) {
                params.put(paramName, request.getParameter(paramName));
            }
        }

        String md5 = md5Sign(params, secret);
        if (!md5.equalsIgnoreCase(sign)) {
            return false;
        }
        return true;
    }

    public static String md5Sign(Map<String, String> params, String secret) {

        //排序
        String[] keys = params.keySet().toArray(new String[params.size()]);
        Arrays.sort(keys);

        //拼接
        StringBuilder paramString = new StringBuilder();
        for (String key : keys) {
            paramString.append(key + "=" + params.get(key));
        }
        paramString.append(secret);

        //编码
        try {
            byte[] bytes = encryptMD5(paramString.toString());
            return byte2hex(bytes);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("md5Sign error", e);
        }
    }


    private static byte[] encryptMD5(String data) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(data.getBytes(CHARSET_UTF8));
        return digest.digest();
    }

    private static String byte2hex(byte[] bytes) {
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = ((int) bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


}
