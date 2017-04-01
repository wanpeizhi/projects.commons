package com.chengjia.commons.utils;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.chengjia.commons.call.CallConfig;
import com.fasterxml.jackson.core.type.TypeReference;

public class CallCenterUtils {

	private HttpUtils httpUtils = new HttpUtils();

	/**
	 * 登录
	 * 
	 * @param appver
	 *            应用版本值：1
	 * @param timestamp
	 *            当前时间，服务端会验证时间有效 性，如果与服务端时间差大于 10 分钟则请求无效。
	 * @param user
	 *            登录用户名
	 * @param pwd
	 *            密 码 , 参 与 计 算 secret, 当 做 token 使用。参数传递中不需要该 参数。
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String login(String appver, String timestamp, String user, String pwd, String token) throws Exception {
		Map params = new HashMap();
		params.put("appver", appver);
		params.put("user", user);
		params.put("timestamp", timestamp);

		Object[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		StringBuilder query = new StringBuilder();
		for (Object tempKey : keys) {
			String key = tempKey.toString();
			String value = params.get(key).toString();
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				query.append(key).append(URLEncoder.encode(value, "utf8"));
			}
		}

		query.append(token);
		byte[] bytes = EncryptUtils.encryptMD5(query.toString());
		String secret = EncryptUtils.byte2hex(bytes);
		params.put("secret", secret);

		String result = httpUtils.invorkHttpClientPostRequest(CallConfig.LOGIN_API, params);
		Map map = JsonUtils.toCollection(result, new TypeReference<HashMap>() {
		});
		if (map.containsKey("data")) {
			HashMap data = (HashMap) map.get("data");
			if (data.containsKey("token")) {
				CallConfig.token = data.get("token").toString();
			}
		}
		return result;
	}

	/**
	 * 注销
	 * 
	 * @param user
	 * @param account
	 * @param appver
	 * @param secret
	 * @return
	 */
	public String logout(String user, String account, String appver, String secret) {
		Map params = new HashMap();
		params.put("user", user);
		params.put("account", account);
		params.put("appver", appver);
		params.put("secret", secret);
		return httpUtils.invorkHttpClientPostRequest(CallConfig.LOGOUT_API, params);
	}

	/**
	 * 添加分机
	 * 
	 * @param appver
	 *            应用版本值：1
	 * @param user
	 *            用户名
	 * @param timestamp
	 *            当前时间，服务端会验证时间有效 性，如果与服务端时间差大于 10 分钟则请求无效。
	 * @param account
	 *            企业 400 号码
	 * @param data
	 *            传入的分机编号和电话号码， JSONArray 格式，数据必须使用 URL(utf-8)进行编码，详细信息参
	 *            考样例。分机号码上限 1000 个
	 * @param dn
	 *            分机编号
	 * @param tel
	 *            手机号码或者固话号码，手机号码 格式:18611334885, 不需要添加 0 或 者 区 号 。 固 话 格 式 ：
	 *            01053358846，不需要添加任何其 它非数字内容。
	 * @return
	 * @throws Exception
	 */
	public String addExtensionDn(String appver, String timestamp, String user, String account, String data, String dn,
			String tel) throws Exception {
		Map params = new HashMap();
		params.put("user", user);
		params.put("account", account);
		params.put("appver", appver);
		params.put("data", data);
		params.put("dn", dn);
		params.put("tel", tel);
		params.put("timestamp", timestamp);

		Object[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		StringBuilder query = new StringBuilder();
		for (Object tempKey : keys) {
			String key = tempKey.toString();
			String value = params.get(key).toString();
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				query.append(key).append(URLEncoder.encode(value, "utf8"));
			}
		}

		query.append(CallConfig.token);
		byte[] bytes = EncryptUtils.encryptMD5(query.toString());
		String secret = EncryptUtils.byte2hex(bytes);
		params.put("secret", secret);
		return httpUtils.invorkHttpClientPostRequest(CallConfig.EXTENSION_ADD_API, params);
	}

	/**
	 * 查询
	 * 
	 * @param appver
	 * @param timestamp
	 * @param user
	 * @param account
	 * @param dn
	 * @return
	 * @throws Exception
	 */
	public String queryExtensionDn(String appver, String timestamp, String user, String account, String dn)
			throws Exception {
		Map params = new HashMap();
		params.put("user", user);
		params.put("account", account);
		params.put("appver", appver);
		params.put("dn", dn);
		params.put("timestamp", timestamp);

		Object[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		StringBuilder query = new StringBuilder();
		for (Object tempKey : keys) {
			String key = tempKey.toString();
			String value = params.get(key).toString();
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				query.append(key).append(URLEncoder.encode(value, "utf8"));
			}
		}

		query.append(CallConfig.token);
		System.out.println("query=" + query);
		byte[] bytes = EncryptUtils.encryptMD5(query.toString());
		String secret = EncryptUtils.byte2hex(bytes);
		params.put("secret", secret);
		return httpUtils.invorkHttpClientPostRequest(CallConfig.EXTENSION_QUERY_API, params);
	}

	/**
	 * 批量查询
	 * 
	 * @param appver
	 * @param timestamp
	 * @param user
	 * @param account
	 * @param pageSize
	 * @param pageNow
	 * @return
	 * @throws Exception
	 */
	public String batchQueryExtensionDn(String appver, String timestamp, String user, String account, String pageSize,
			String pageNow) throws Exception {
		Map params = new HashMap();
		params.put("user", user);
		params.put("account", account);
		params.put("appver", appver);
		params.put("pageSize", pageSize);
		params.put("pageNow", pageNow);
		params.put("timestamp", timestamp);

		Object[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		StringBuilder query = new StringBuilder();
		for (Object tempKey : keys) {
			String key = tempKey.toString();
			String value = params.get(key).toString();
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				query.append(key).append(URLEncoder.encode(value, "utf8"));
			}
		}

		query.append(CallConfig.token);
		System.out.println("query=" + query);
		byte[] bytes = EncryptUtils.encryptMD5(query.toString());
		String secret = EncryptUtils.byte2hex(bytes);
		params.put("secret", secret);
		return httpUtils.invorkHttpClientPostRequest(CallConfig.EXTENSION_BATCH_QUERY_API, params);
	}
	
	/**
	 * 查询总的分机数
	 * @param appver
	 * @param timestamp
	 * @param user
	 * @param account
	 * @param pageSize
	 * @param pageNow
	 * @return
	 * @throws Exception
	 */
	public long getExtensionDnCount(String appver, String timestamp, String user, String account, String pageSize,
			String pageNow) throws Exception {
		String response =  this.batchQueryExtensionDn(appver, timestamp, user, account, pageSize, pageNow);
		HashMap map = JsonUtils.toCollection(response, new TypeReference<HashMap>() {
		});
		long count = 0;
		if(map.containsKey("data")){
			HashMap data = (HashMap) map.get("data");
			if (data.containsKey("total")) {
				count = Long.valueOf(data.get("total").toString());
			}
		}
		return count;
	}

	/**
	 * 删除分机
	 * 
	 * @param user
	 * @param account
	 * @param appver
	 * @param secret
	 * @param dn
	 * @return
	 * @throws Exception
	 */
	public String delExtensionDn(String appver, String timestamp, String user, String account, String dn)
			throws Exception {
		Map params = new HashMap();
		params.put("user", user);
		params.put("account", account);
		params.put("appver", appver);
		params.put("dn", dn);
		params.put("timestamp", timestamp);

		Map params1 = new HashMap();
		Object[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		StringBuilder query = new StringBuilder();
		for (Object tempKey : keys) {
			String key = tempKey.toString();
			String value = params.get(key).toString();
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				query.append(key).append(URLEncoder.encode(value, "utf8"));
				params1.put(key, URLEncoder.encode(value, "utf8"));
			}
		}

		System.out.println("token" + CallConfig.token);
		query.append(CallConfig.token);
		System.out.println("query=" + query);
		byte[] bytes = EncryptUtils.encryptMD5(query.toString());
		String secret = EncryptUtils.byte2hex(bytes);
		System.out.println("secret[" + secret + "]");
		params1.put("secret", secret);

		return httpUtils.invorkHttpClientPostRequest(CallConfig.EXTENSION_DEL_API, params1);
	}

	/**
	 * 事件监听
	 * 
	 * @param user
	 * @param account
	 * @param appver
	 * @param secret
	 * @param telnum
	 * @return
	 */
	public String eventListen(String user, String account, String appver, String secret, String telnum) {
		Map params = new HashMap();
		params.put("user", user);
		params.put("account", account);
		params.put("appver", appver);
		params.put("secret", secret);
		params.put("telnum", telnum);
		return httpUtils.invorkHttpClientPostRequest(CallConfig.EVENT_API, params);
	}

}
