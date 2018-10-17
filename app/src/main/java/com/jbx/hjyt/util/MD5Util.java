package com.jbx.hjyt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

//    public static String md5(String txt) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            // 问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
//            md.update(txt.getBytes("GBK"));
//            StringBuffer buf = new StringBuffer();
//            for (byte b : md.digest()) {
//                buf.append(String.format("%02x", b & 0xff));
//            }
//            return buf.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String md5Password(String password){
        StringBuffer sb = new StringBuffer();
        // 得到一个信息摘要器
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            // 把每一个byte做一个与运算 0xff
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
