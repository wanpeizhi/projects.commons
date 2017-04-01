package com.chengjia.commons.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.chengjia.commons.encrypt.EncryDES;
import com.chengjia.commons.encrypt.EncryMD5;
import com.chengjia.commons.encrypt.EncrySHA1;

public class EncryptUtils {
	/**
	 * 获取MD5加密后的字符，算法不可以逆
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return new EncryMD5().getMD5ofStr(str);
	}

	/**
	 * 获取SHA1加密后的字符，算法不可以逆
	 * 
	 * @param str
	 * @return
	 */
	public static String sha1(String str) {
		return new EncrySHA1().getDigestOfString(str.getBytes());
	}

	/**
	 * 获取DES加密后的字符，此加密方法可逆
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encodeDES(String str, String secret) {
		try {
			return new EncryDES(secret).encrypt(str).toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取DES解密后的字符，此加密方法可逆
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String decodeDES(String str, String secret) {
		try {
			return new EncryDES(secret).decrypt(str).toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串md5加密
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encryptMD5(String data) throws IOException, NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		return md5.digest(data.getBytes("utf-8"));
	}

	/**
	 * 字节数组转16进制字符串
	 * @param bytes
	 * @return
	 */
	public static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

}
