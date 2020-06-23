package com.zhrenjie04.alex.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类（封装jdk自带的md5加密方法）
 *
 * @author 张人杰
 * @date 2016年12月2日 下午4:14:22
 */
public class Md5Util {

    public static String encrypt(String source) {
        return encodeMd5(source.getBytes());
    }

    public static byte[] encrypt(byte[] source) {
        try {
            return MessageDigest.getInstance("MD5").digest(source);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static String encodeMd5(byte[] source) {
        try {
            return encodeHex(MessageDigest.getInstance("MD5").digest(source));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static String encodeHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                sb.append("0");
            }
            sb.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
    }
}
