package com.chengjia.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	// 用于生成验证码的字符,去掉了易混淆的1,i,0,o,l,I,O
	private static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz";

	/**
	 * 使用指定字符补齐字符串
	 * 
	 * @param length
	 * @param chara
	 * @return
	 */
	public static String polishingZeroBegin(int length, long num) {
		return String.format("%0" + length + "d", num);
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperFirstLetter(String str) {
		// return str.substring(0, 1).toUpperCase() + str.substring(1);

		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	/**
	 * 判断是否是字母和数字的结合
	 * 
	 * @Description
	 * @param str
	 * @return
	 * @return boolean
	 */
	public static boolean isAsciiOrDigit(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!isAscii(ch))
				return false;
		}
		return true;
	}

	/**
	 * 判断是否是数字
	 * 
	 * @Description
	 * @param str
	 * @return
	 * @return boolean
	 */
	public static boolean isDigit(String str) {
		if (str == null)
			return false;
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!isDigit(ch))
				return false;
		}
		return true;
	}

	public static boolean isAscii(char ch) {
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9');
	}

	public static boolean isDigit(char ch) {
		return (ch >= '0' && ch <= '9');
	}

	/**
	 * 是否手机号码
	 * 
	 * @Description
	 * @param mobiles
	 * @return
	 * @return boolean
	 */

	public static boolean isMobileNO(String mobiles) {
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(mobiles);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断字符串是否是一个IP地址
	 * 
	 * @param addr
	 * @return
	 */
	public static boolean isIPAddr(String addr) {
		if (isEmpty(addr))
			return false;
		String[] ips = split(addr, '.');
		if (ips.length != 4)
			return false;
		try {
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0 && ipc <= 255 && ipd >= 0 && ipd <= 255;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 生成用户密码,多次加密
	 * 
	 * @Description
	 * @param loginPwd
	 * @param secret
	 * @param needSha1
	 * @return
	 * @return String
	 */
	public static String genPwd(String loginPwd, String secret, boolean needSha1) {
		if (loginPwd == null) {
			return "";
		}
		if (needSha1) {
			loginPwd = EncryptUtils.sha1(loginPwd);
		}
		secret = EncryptUtils.md5(secret.toUpperCase());
		return EncryptUtils.md5(loginPwd.toUpperCase() + "@" + secret);
	}

	/**
	 * 生成经过MD5算法加密的字符串
	 * 
	 * @Description
	 * @param neededEncrypedString
	 * @return
	 * @return String
	 */

	public final static String encrypeMD5(String neededEncrypedString) {

		String encrypeString = null;
		String encrypeType = "MD5";
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			if (neededEncrypedString == null)
				neededEncrypedString = "";
			byte[] neededEncrypedByteTemp = neededEncrypedString.getBytes();

			// 得到MD5的加密算法对象
			MessageDigest md = MessageDigest.getInstance(encrypeType);

			// 更新算法使用的摘要
			md.update(neededEncrypedByteTemp);

			// 完成算法加密过程
			byte[] middleResult = md.digest();

			// 把加密后的字节数组转化为字符串
			int length = middleResult.length;
			char[] neededEncrypedByte = new char[length * 2];
			int k = 0;
			for (int i = 0; i < length; i++) {
				byte byte0 = middleResult[i];
				neededEncrypedByte[k++] = hexDigits[byte0 >>> 4 & 0xf];
				neededEncrypedByte[k++] = hexDigits[byte0 & 0xf];
			}
			encrypeString = new String(neededEncrypedByte);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return encrypeString;
	}

	/**
	 * URL encoding.
	 * 
	 * @param s
	 *            a string to be URL-encoded
	 * @return URL encoding of s using character encoding UTF-8; null if s is
	 *         null.
	 */
	public static final String encode(String s) {
		try {
			if (s != null)
				return URLEncoder.encode(s, "UTF-8");
			else
				return s;
		} catch (UnsupportedEncodingException e) {
			// Java Spec requires UTF-8 be in all Java environments, so this
			// should not happen
			return s;
		}
	}

	/**
	 * URL decoding.
	 * 
	 * @param s
	 *            a URL-encoded string to be URL-decoded
	 * @return URL decoded value of s using character encoding UTF-8; null if s
	 *         is null.
	 */
	public static final String decode(String s) {
		try {
			if (s != null)
				return URLDecoder.decode(s, "UTF-8");
			else
				return s;
		} catch (UnsupportedEncodingException e) {
			// Java Spec requires UTF-8 be in all Java environments, so this
			// should not happen
			return s;
		}
	}

	// 加密
	public static String encodeBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	// 解密
	public static String decodeBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 使用系统默认字符源生成验证码
	 * 
	 * @param verifySize
	 *            验证码长度
	 * @return
	 */
	public static String generateVerifyCode(int verifySize) {
		return generateVerifyCode(verifySize, VERIFY_CODES);
	}

	/**
	 * 使用指定源生成验证码
	 * 
	 * @param verifySize
	 *            验证码长度
	 * @param sources
	 *            验证码字符源
	 * @return
	 */
	public static String generateVerifyCode(int verifySize, String sources) {
		if (sources == null || sources.length() == 0) {
			sources = VERIFY_CODES;
		}
		int codesLen = sources.length();
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(verifySize);
		for (int i = 0; i < verifySize; i++) {
			verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
		}
		return verifyCode.toString();
	}

	/**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成简单id
	 * 
	 * @param u
	 * @return
	 */
	public static String generateSingleID(String u) {
		java.util.Date now = new java.util.Date();
		if (u == null)
			u = "";
		long nowLong = now.getTime();
		String strUIID = u + String.valueOf(nowLong) + (int) (Math.random() * 1000);
		return strUIID;
	}

}
