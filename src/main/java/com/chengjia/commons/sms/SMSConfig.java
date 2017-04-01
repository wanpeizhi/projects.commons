package com.chengjia.commons.sms;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

/**
 * 短信配置
 * 
 * @author Administrator
 *
 */
public class SMSConfig {

	private static final String ACCOUNT_ID = "8a48b5514db9e13d014dbc35e88a00b8";
	private static final String ACCOUNT_TOKEN = "f76a3c12b545455fa9a51d27751fbb43";
	private static final String SEND_URL = "app.cloopen.com";
	private static final String SEND_PORT = "8883";
	private static final String APP_ID = "8a216da858ce0b3c0158d26badd102d7";

	private static CCPRestSmsSDK restAPI;

	static {
		restAPI = new CCPRestSmsSDK();
		restAPI.init(SEND_URL, SEND_PORT);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(ACCOUNT_ID, ACCOUNT_TOKEN);// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(APP_ID);// 初始化应用ID
	}

	public static final CCPRestSmsSDK getRestSms() {
		if (restAPI == null) {
			restAPI = new CCPRestSmsSDK();
		}
		return restAPI;
	}
}
