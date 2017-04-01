package com.chengjia.commons.http;

import java.io.Serializable;
import java.util.Map;

/**
 * json请求对象
 * 
 * @author Administrator
 *
 */
public class JsonRequest implements Serializable {

	private static final long serialVersionUID = -391261182288770510L;
	private HttpHeader requestHeader;// 响应头
	private HttpCookies requestCookies;// 响应cookie
	private String requestUrl;// 请求url
	private String method;// 请求方法,post或者get
	private Map params;// 请求参数
	private String version;// 版本
	private int client;// 客户端,1表示安卓，2表示iPhone，3表示pc
	private String time;// 请求时间，格式yyyyMMddHHmmss
	private String token;// 令牌
	private String device;// 终端设备

	public HttpHeader getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(HttpHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	public HttpCookies getRequestCookies() {
		return requestCookies;
	}

	public void setRequestCookies(HttpCookies requestCookies) {
		this.requestCookies = requestCookies;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

}
