package com.chengjia.commons.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chengjia.commons.common.Constants;
import com.chengjia.commons.http.JsonResponse;
import com.chengjia.commons.wechat.WechatAccessToken;
import com.chengjia.commons.wechat.WechatConfig;
import com.chengjia.commons.wechat.WechatNoteTpl;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 微信工具类
 */
public class WechatUtils {

	public static final Logger logger = LogManager.getLogger(WechatUtils.class.getName());

	public static WechatAccessToken wechatAccessToken;

	/**
	 * 通过接口从数据库获取微信访问的access_token
	 * 
	 * @return
	 * @throws Exception
	 */
	public static final WechatAccessToken getAccessTokenFromLocal(String url, Map params) throws Exception {
		String result = HttpUtils.invorkHttpClientGetRequest(url, params);
		if (StringUtils.isNotBlank(result)) {
			JsonResponse jsonResponse = JsonUtils.toObject(result, JsonResponse.class);
			if (jsonResponse.getData() != null) {
				Map dataMap = JsonUtils.toCollection(JsonUtils.toJson(jsonResponse.getData()),
						new TypeReference<Map>() {
						});
				if (dataMap.get("pValue") != null) {
					wechatAccessToken = new WechatAccessToken();
					wechatAccessToken.setAccess_token(dataMap.get("pValue").toString());
					wechatAccessToken.setExpiresIn(7200);
					wechatAccessToken.setUpdateTime(dataMap.get("updateTime").toString());
				}
			}
		}
		return wechatAccessToken;
	}

	/**
	 * 从微信获取access_token
	 * 
	 * @return
	 * @throws Exception
	 */
	public static final WechatAccessToken getAccessTokenFromWechat() throws Exception {
		Map paramsMap = new HashMap();
		paramsMap.put("appid", WechatConfig.WECHAT_APP_ID);
		paramsMap.put("secret", WechatConfig.WECHAT_APP_SECRET);
		String result = HttpUtils.invorkHttpClientGetRequest(WechatConfig.WECHAT_COMMON_TOKEN_URL, paramsMap);
		Map dataMap = JsonUtils.toCollection(result, new TypeReference<Map>() {
		});
		logger.debug("获取微信token返回结果" + result);
		if (dataMap.containsKey("access_token")) {
			wechatAccessToken = new WechatAccessToken();
			wechatAccessToken.setAccess_token(dataMap.get("access_token").toString());
			wechatAccessToken.setExpiresIn(Long.valueOf(dataMap.get("expires_in").toString()));
			wechatAccessToken.setUpdateTime(DateUtils.getCurDatetimeString());

		}
		return wechatAccessToken;
	}

	/**
	 * 刷新access_token,更新到数据库
	 * 
	 * @return
	 * @throws Exception
	 */
	public static final WechatAccessToken refreshWechatAccessToken(String url, Map params) throws Exception {
		wechatAccessToken = getAccessTokenFromWechat();
		params.put("pValue", wechatAccessToken.getAccess_token());
		params.put("updateTime", wechatAccessToken.getUpdateTime());
		HttpUtils.invorkHttpClientPostRequest(url, params);
		return wechatAccessToken;
	}

	/**
	 * 发送模板消息
	 * 
	 * @param wechatNoteTpl
	 * @return
	 */
	public static final String sendTplNote(String accessToken, WechatNoteTpl wechatNoteTpl) {
		Map paramsMap = new HashMap();
		paramsMap.put(Constants.ACCESS_TOKEN, accessToken);
		paramsMap.putAll(JsonUtils.toCollection(wechatNoteTpl.toString(), new TypeReference<HashMap>() {
		}));
		return HttpUtils.invorkHttpClientPostRequest(WechatConfig.WECHAT_SEND_TPL_MSG_URL, paramsMap);
	}

	/**
	 * 用户同意微信网页授权之后通过code获得token
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static final WechatAccessToken getWechatUserAccessTokenWithCode(String code) throws Exception {
		WechatAccessToken wechatUserAccessToken = null;
		Map paramsMap = new LinkedHashMap();
		paramsMap.put("appid", WechatConfig.WECHAT_APP_ID);
		paramsMap.put("secret", WechatConfig.WECHAT_APP_SECRET);
		paramsMap.put("code", code);
		paramsMap.put("grant_type", "authorization_code");
		// 示例：https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		String result = HttpUtils.invorkHttpClientGetRequest(WechatConfig.WECHAT_CODE_TOKEN_URL, paramsMap);
		Map dataMap = JsonUtils.toCollection(result, new TypeReference<Map>() {
		});
		logger.debug("用户同意授权，获取微信token返回结果" + result);
		if (dataMap.containsKey("access_token")) {
			wechatUserAccessToken = new WechatAccessToken();
			wechatUserAccessToken.setAccess_token(dataMap.get("access_token").toString());
			wechatUserAccessToken.setExpiresIn(Long.valueOf(dataMap.get("expires_in").toString()));
			wechatUserAccessToken.setUpdateTime(DateUtils.getCurDatetimeString());
			wechatUserAccessToken.setOpenid(dataMap.get("openid").toString());
			wechatUserAccessToken.setRefresh_token(dataMap.get("refresh_token").toString());
			wechatUserAccessToken.setScope(dataMap.get("scope").toString());
		}
		return wechatUserAccessToken;
	}

	/**
	 * 验证用户token
	 * 
	 * @throws Exception
	 */
	public static final String validWechatUserToken(String access_token, String openid) throws Exception {
		// 示例：示例：https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
		Map paramsMap = new LinkedHashMap();
		paramsMap.put("access_token", access_token);
		paramsMap.put("openid", openid);
		return HttpUtils.invorkHttpClientGetRequest(WechatConfig.WECHAT_TOKEN_VALID_URL, paramsMap);
	}

}
