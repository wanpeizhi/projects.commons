package com.chengjia.commons.http;

import java.io.Serializable;

/**
 * json响应对象
 * 
 * @author Administrator
 *
 */
public class JsonResponse implements Serializable {

	private static final long serialVersionUID = -6040776449688171459L;
	private String requestUrl;// 原请求url
	private String redirectUrl;// 重定向url
	private int code;// 结果状态码
	private String status;// 结果状态
	private String msg;// 返回消息
	private Object data;// 返回数据
	private String time;// 响应时间
	private String token;// 访问token
	private HttpHeader responseHeader;// 响应头
	private HttpCookies responseCookies;// 响应cookie

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
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

	public HttpHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(HttpHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public HttpCookies getResponseCookies() {
		return responseCookies;
	}

	public void setResponseCookies(HttpCookies responseCookies) {
		this.responseCookies = responseCookies;
	}

}
