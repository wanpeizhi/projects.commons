package com.chengjia.commons.wechat;

/**
 * 微信访问凭证
 * 
 * @author Administrator
 *
 */
public class WechatAccessToken {

	// 凭证
	private String access_token;

	// 有效期，单位秒
	private long expiresIn;

	// 创建时间
	private String updateTime;

	// openid
	private String openid;

	// refresh_token
	private String refresh_token;

	// 范围
	private String scope;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
