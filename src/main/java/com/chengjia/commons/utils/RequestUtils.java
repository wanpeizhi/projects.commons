package com.chengjia.commons.utils;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestUtils {

	/**
	 * 获取请求上下文
	 * 
	 * @param request
	 * @return
	 */
	public String getContext(HttpServletRequest request) {
		return request.getContextPath();
	}

	/**
	 * 获取请求绝对路径
	 * 
	 * @param request
	 * @return
	 */
	public String getRequestUrl(HttpServletRequest request) {
		return request.getRequestURL().toString();
	}

	/**
	 * 获取COOKIE
	 * 
	 * @param name
	 */
	public Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; i++) {
			System.out.println("cookie name:" + cookies[i].getName());
			if (name.equals(cookies[i].getName())) {
				return cookies[i];
			}
		}
		return null;
	}

	/**
	 * 设置COOKIE
	 * 
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);

		String serverName = request.getServerName();
		if (!StringUtils.isIPAddr(serverName)) {
			String domain = getDomainOfServerName(serverName);
			if (domain != null && domain.indexOf('.') != -1) {
				cookie.setDomain('.' + domain);
			}
		}

		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 获取用户访问URL中的根域名 例如: www.pktuan.com -> pktuan.com
	 * 
	 * @param req
	 * @return
	 */
	public String getDomainOfServerName(String host) {
		if (StringUtils.isIPAddr(host))
			return null;
		String[] names = StringUtils.split(host, '.');
		int len = names.length;
		if (len >= 2)
			return names[len - 2] + '.' + names[len - 1];
		return host;
	}

	/**
	 * 从URL地址中解析出URL前缀，例如 http://wap.mo168.com:8081/index.jsp ->
	 * http://wap.mo168.com:8081
	 * 
	 * @param req
	 * @return
	 */
	public String getUrlPrefix(HttpServletRequest req) {
		StringBuffer url = new StringBuffer(req.getScheme());
		url.append("://");
		url.append(req.getServerName());
		int port = req.getServerPort();
		if (port != 80) {
			url.append(":");
			url.append(port);
		}
		return url.toString();
	}

	/**
	 * 获取访问的URL全路径
	 * 
	 * @param req
	 * @return
	 */
	public String getRequestURL(HttpServletRequest req) {
		StringBuffer url = new StringBuffer(req.getRequestURI());
		String param = req.getQueryString();
		if (param != null) {
			url.append('?');
			url.append(param);
		}
		String path = url.toString();
		return path.substring(req.getContextPath().length());
	}

	/**
	 * 获取header信息，名字大小写无关
	 * 
	 * @param req
	 * @param name
	 * @return
	 */
	public String getHeader(HttpServletRequest req, String name) {
		String value = req.getHeader(name);
		if (value != null)
			return value;
		Enumeration names = req.getHeaderNames();
		while (names.hasMoreElements()) {
			String n = (String) names.nextElement();
			if (n.equalsIgnoreCase(name)) {
				return req.getHeader(n);
			}
		}
		return null;
	}

	/**
	 * 打印所有的头信息
	 * 
	 * @param out
	 * @param req
	 */
	public void dumpHeaders(PrintStream out, HttpServletRequest req) {
		Enumeration names = req.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			out.println(name + "=" + req.getHeader(name));
		}
	}

	/**
	 * 打印所有头信息
	 * 
	 * @param req
	 * @param out
	 */
	public void dumpHeaders(HttpServletRequest req, PrintStream out) {
		Enumeration hds = req.getHeaderNames();
		out.println("=============== HEADERS ===============");
		while (hds.hasMoreElements()) {
			String name = (String) hds.nextElement();
			out.println(name + "=" + req.getHeader(name));
		}
	}

	/**
	 * 判断手机是否支持某种类型的格式
	 * 
	 * @param req
	 * @param contentType
	 * @return
	 */
	public boolean support(HttpServletRequest req, String contentType) {
		String accept = getHeader(req, "accept");
		if (accept != null) {
			accept = accept.toLowerCase();
			return accept.indexOf(contentType.toLowerCase()) != -1;
		}
		return false;
	}

	/**
	 * 將request params转map
	 * 
	 * @param request
	 * @return
	 */
	public Map getRequestParams(HttpServletRequest request) {
		Map requestParams = new HashMap();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String paramName = e.nextElement().toString();
			requestParams.put(paramName, request.getParameter(paramName));
		}
		return requestParams;
	}

}
