package com.chengjia.commons.wechat;

/**
 * 微信消息基本配置
 * 
 * @author Administrator
 *
 */
public class WechatConfig {

	// 微信appID
	public static final String WECHAT_APP_ID = "wx516ca3560e4d6bdf";

	// 微信app secret
	public static final String WECHAT_APP_SECRET = "394f47d166d08348bf3e8f9c16ec28c4";

	// 微信获取token地址
	public static final String WECHAT_COMMON_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

	// 微信通过模板发送消息地址
	public static final String WECHAT_SEND_TPL_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";

	// 微信获取code地址
	// 由于授权操作安全等级较高，所以在发起授权请求时，微信会对授权链接做正则强匹配校验，如果链接的参数顺序不对，授权页面将无法正常访问,https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	public static final String WECHAT_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

	// 通过code获取token的地址
	public static final String WECHAT_CODE_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	// 示例：https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code

	// 刷新token地址
	public static final String WECHAT_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	// 示例：https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN

	// 获取用户信息，示例：https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	public static final String WECHAT_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

	// 检验微信授权是否正确,示例：https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
	public static final String WECHAT_TOKEN_VALID_URL = "https://api.weixin.qq.com/sns/auth";

}
