package com.chengjia.commons.call;

/**
 * 呼叫中心配置
 * 
 * @author Administrator
 *
 */
public class CallConfig {

	/**
	 * 版本
	 */
	public static final String APPVER = "1";
	/**
	 * 密碼
	 */
	public static final String PWD = "b22a45af18ff42afac9fedfacb7f51bb";

	/**
	 * 账号
	 */
	public static final String ACCOUNT = "4006110858";

	/**
	 * 测试账号
	 */
	public static final String ACCOUNT_TEST = "4006659949";

	/**
	 * 测试账号
	 */
	public static final String USER_TEST = "4006659949_dev";

	/**
	 * TOKEN
	 */
	public static String token = "b22a45af18ff42afac9fedfacb7f51bb";

	/**
	 * 登录接口
	 */
	public static final String LOGIN_API = "http://xtapi.union400.com/api/union400Login.action";

	/**
	 * 注销接口
	 */
	public static final String LOGOUT_API = "http://api.union400.com/union400logout";

	/**
	 * 监听接口
	 */
	public static final String EVENT_API = "http://api.union400.com/union400event";

	/**
	 * 添加虚拟分机
	 */
	public static final String EXTENSION_ADD_API = "http://xtapi.union400.com/api/call/addExtension.action";

	/**
	 * 查询
	 */
	public static final String EXTENSION_QUERY_API = "http://xtapi.union400.com/api/call/queryExtension.action";

	/**
	 * 批量查询
	 */
	public static final String EXTENSION_BATCH_QUERY_API = "http://xtapi.union400.com/api/call/batchQueryExtension.action";

	/**
	 * 分机删除
	 */
	public static final String EXTENSION_DEL_API = "http://xtapi.union400.com/api/call/deleteExtension.action";

}
