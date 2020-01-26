package com.techwells.wumei.util;
/**
 * @author 徐跃辉  E-mail: xuyuehui158@163.com
 * @version 创建时间：2016年12月21日 下午3:15:51
 * 类说明
 */


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String encrypt(String source) throws NoSuchAlgorithmException {
        return encrypt(source, null);
    }

    public static String encrypt(String source, String encode) throws NoSuchAlgorithmException {
        java.security.MessageDigest md5;
        md5 = java.security.MessageDigest.getInstance("MD5");
        if (encode != null) {
            try {
                md5.update(source.getBytes(encode));
            } catch (UnsupportedEncodingException ignored) {
            }
        } else {
            md5.update(source.getBytes());
        }

        byte[] byteDigest = md5.digest();
        return toHexChar(byteDigest);
    }

    /**
     * 二行制转字符串
     *
     * @param b 字节
     * @return String
     */
    private static String toHexChar(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }

    public static String md5(String value) {
        String result;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update((value).getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException error) {
            error.printStackTrace();
        }
        assert md5 != null;
        byte[] b = md5.digest();
        int i;
        StringBuffer buf = new StringBuffer("");

        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }

        result = buf.toString();
        return result;
    }
}

 
