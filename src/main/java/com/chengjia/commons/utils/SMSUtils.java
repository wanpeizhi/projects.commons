package com.chengjia.commons.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.chengjia.commons.sms.SMS253Config;
import com.chengjia.commons.sms.SMSConfig;

/**
 * 短信服务类
 * 
 * @author Administrator
 *
 */
public class SMSUtils {

	/**
	 * 短信发送(融云服务商)
	 * 
	 * @param mobile
	 * @param tplId
	 * @param tplValue
	 * @param dtype
	 * @return
	 */
	public static Map send(String mobile, String tplId, String[] tplValue) {
		Map result = null;
		try {
			result = SMSConfig.getRestSms().sendTemplateSMS(mobile, tplId, tplValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 253云通讯短信发送
	 * 
	 * @param phone
	 * @param message
	 * @param extend
	 * @return
	 * @throws Exception
	 */
	public static Map send253(String phone, String message, String extend) throws Exception {
		Map map = new HashMap();
		map.put("un", SMS253Config.ACCOUNT_ID);
		map.put("pw", SMS253Config.ACCOUNT_PW);
		map.put("phone", phone);
		map.put("msg", message);
		map.put("rd", "1");
		map.put("ex", extend);

		HttpUtils httpUtils = new HttpUtils();
		String result = httpUtils.invorkHttpClientGetRequest(SMS253Config.SEND_URL, map);

		Map resultMap = new HashMap();
		String[] resultArr = StringUtils.split(result, "/\n");
		String responseTime = resultArr[0].substring(0, resultArr[0].indexOf(","));
		String responseCode = resultArr[0].substring(resultArr[0].indexOf(",") + 1);
		resultMap.put("responseTime", responseTime);
		resultMap.put("responseCode", responseCode);
		if (responseCode.equals("0")) {
			String responseMessageId = resultArr[1];
			resultMap.put("messageId", responseMessageId);
			resultMap.put("status", "success");
		} else {
			resultMap.put("status", "fail");
		}

		return resultMap;
	}

	/**
	 * 随机数字作为短信验证码
	 * 
	 * @return
	 */
	public static String getRandom() {
		return RandomStringUtils.randomNumeric(6).toUpperCase();
	}

	public static void main(String[] args) {
		/*
		 * try { Map result = null; result = SMSUtils.send("13762368336",
		 * SMSTplEnum.verifCode.getId(), new String[] { getRandom() });
		 * System.out.println("SDKTestSendTemplateSMS result=" + result); if
		 * ("000000".equals(result.get("statusCode"))) { // 正常返回输出data包体信息（map）
		 * HashMap<String, Object> data = (HashMap<String, Object>)
		 * result.get("data"); Set<String> keySet = data.keySet(); for (String
		 * key : keySet) { Object object = data.get(key); System.out.println(key
		 * + " = " + object); } } else { // 异常返回输出错误码和错误信息
		 * System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " +
		 * result.get("statusMsg")); } } catch (Exception e) { // TODO: handle
		 * exception }
		 */

		try {
			Map result = null;
			SMSUtils smsUtils = new SMSUtils();
			result = smsUtils.send253("13762368336", "【253云通讯】您好，你的验证码是123456", null);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
