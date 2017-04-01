package com.chengjia.commons.common;

/**
 * 常量类
 * 
 * @author Administrator
 *
 */
public final class Constants {

	/**
	 * 返回结果
	 */
	public static final String RESULT_STATUS_SUCCESS = "success";
	public static final String RESULT_STATUS_FAIL = "fail";

	/**
	 * 返回结果码
	 */
	public static final int RESULT_CODE_SUCCESS = 101;// 成功
	public static final int RESULT_CODE_FAIL = 102;// 失败，程序继续
	public static final int RESULT_CODE_ERROR = 103;// 错误，程序中止
	public static final int RESULT_CODE_INVALID_SESSION = 201;// session已失效
	public static final int RESULT_CODE_OVERTIME = 301;// 请求超时
	public static final int RESULT_CODE_NO_TOKEN = 401;// 未授权
	public static final int RESULT_CODE_INVALID_TOKEN = 402;// token无效
	public static final int RESULT_CODE_ERROR_CODE = 501;// 错误验证码
	public static final int RESULT_CODE_INVALID_CODE = 502;// 无效验证码
	public static final int RESULT_CODE_ERROR_CLIENT = 503;// 无效客户端

	/**
	 * http请求返回码
	 */
	public static final int HTTP_CODE_OK = 200;// 成功
	public static final int HTTP_CODE_UNCHANGED = 304;// 未修改
	public static final int HTTP_CODE_UNAUTHORIZED = 401;// 未授权
	public static final int HTTP_CODE_NOTDOUND = 404;// 未找到
	public static final int HTTP_CODE_OVERTIME = 408;// 请求超时
	public static final int HTTP_CODE_SERVICE_ERROR = 500;// 服务端错误
	public static final int HTTP_CODE_GATEWAY_ERROR = 502;// 网关错误

	/**
	 * http请求方式
	 */
	public static final String HTTP_REQUEST_METHOD_GET = "get";
	public static final String HTTP_REQUEST_METHOD_POST = "post";

	/**
	 * 用户默认密码
	 */
	public static final String DEFAULT_PASSWORD = "123456";

	/**
	 * 当前用户key
	 */
	public static final String CURRENT_USER = "currentUser";
	public static final String CURRENT_USER_ID = "currentUserId";

	/**
	 * 微信相关
	 */
	public static final String OPEN_ID = "openid";
	public static final String ACCESS_TOKEN = "access_token";

	/**
	 * 用户类型
	 */
	public static final String USER_TYPE_COMMON = "common";// 普通用户
	public static final String USER_TYPE_AGENT = "agent";// 经纪人
	public static final String USER_TYPE_CONSOLE = "system";// 系统用户

	/**
	 * 文件上传根路径
	 */
	public static final String FILE_UPLOAD_ROOT_PATH = "/upload";

	/**
	 * 验证码类型
	 */
	public static final String IMG_VERIF_CODE = "imgVerifCode";
	public static final String SMS_VERIF_CODE = "smsVerifCode";

	/**
	 * 接口访问url
	 */
	public static final String SERVICE_URL = "service.url";

	/**
	 * 网站根域名
	 */
	public static final String WEBSITE_DOMAIN = "website.domain";

	/**
	 * 授权类型
	 */
	public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
	public static final String GRANT_TYPE_IMPLICIT = "token";
	public static final String GRANT_TYPE_PASSWORD_CREDENTIALS = "password";
	public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
	public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

	/**
	 * 授权范围
	 */
	public static final String SCOPE_READ = "read";
	public static final String SCOPE_WRITE = "write";
	public static final String SCOPE_READ_WRITE = "read write";

	/**
	 * 查询参数相关
	 */
	public static final int PAGE_NO = 1;
	public static final int PAGE_ROWS = 15;
	public static final String ORDER_DESC = "desc";
	public static final String ORDER_ASC = "asc";

	/**
	 * ehcache缓存相关默认参数
	 */
	// 默認緩存名称
	public static final String CACHE_DEFAULT_NAME = "defaultCache";
	// 默认缓存策略
	public static final String CACHE_DEFAULT_POLICY = "LRU";
	// 是否永不过期
	public static final boolean CACHE_DEFAULT_ETERNAL = false;
	// 缓存内存最大值
	public static final int CACHE_DEFAULT_MAXELEMENTSINMEMORY = 10000;
	// 缓存达到最大值时是否溢出到磁盘
	public static final boolean CACHE_DEFAULT_OVERFLOWTODISK = false;
	// 磁盘缓存在VM重新启动时是否保持(默认为false)
	public static final boolean CACHE_DEFAULT_DISKPERSISTENT = false;
	// 元素在缓存里存在的时间(秒为单位). 0 表示永远存在不过期
	public static final int CACHE_DEFAULT_TIMETOLIVESECONDS = 3600;
	// 导致元素过期的访问间隔(秒为单位). 0表示可以永远空闲,默认为0
	public static final int CACHE_DEFAULT_TIMETOIDLESECONDS = 3600;

}
