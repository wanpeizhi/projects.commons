package com.chengjia.commons.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chengjia.commons.exception.HttpProcessException;
import com.chengjia.commons.wechat.WechatConfig;

public class HttpUtils {

	private static final Logger logger = LogManager.getLogger(HttpUtils.class);
	public static PoolingHttpClientConnectionManager poolConnManager;

	static {
		try {
			// 需要通过以下代码声明对https连接支
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
			// 初始化连接管理器
			if (poolConnManager == null) {
				poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
				// Increase max total connection to 200
				poolConnManager.setMaxTotal(200);
				// Increase default max connection per route to 20
				poolConnManager.setDefaultMaxPerRoute(20);
			}
		} catch (KeyManagementException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (KeyStoreException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获得客户端连接
	 * 
	 * @return
	 */
	public static CloseableHttpClient getHttpclientConnection() throws HttpProcessException {
		try {
			CookieStore cookieStore = new BasicCookieStore();
			CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager)
					.setDefaultCookieStore(cookieStore).build();
			return httpClient;
		} catch (Exception e) {
			throw new HttpProcessException("获取httpclient connection 失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 关闭客户端连接
	 * 
	 * @param closeableHttpClient
	 */
	public static void closeHttpclientConnection(CloseableHttpClient closeableHttpClient) throws HttpProcessException {
		try {
			if (closeableHttpClient != null)
				closeableHttpClient.close();
		} catch (IOException e) {
			throw new HttpProcessException("关闭httpclient connection 失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 关闭响应
	 * 
	 * @param closeableHttpResponse
	 */
	public static void closeResponseConnection(CloseableHttpResponse closeableHttpResponse)
			throws HttpProcessException {
		try {
			if (closeableHttpResponse != null)
				closeableHttpResponse.close();
		} catch (IOException e) {
			throw new HttpProcessException("关闭response connection 失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 模拟get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String invorkHttpClientGetRequest(String url, Map params) throws Exception {
		String responseContent = null;
		CloseableHttpClient httpClient = getHttpclientConnection();
		CloseableHttpResponse response = null;
		HttpGet httpGet = new HttpGet();
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000)
				.setSocketTimeout(10000).build();
		try {
			StringBuffer paramStr = new StringBuffer();
			if (params != null) {
				for (Object key : params.keySet()) {
					paramStr.append(key.toString()).append("=")
							.append(URLEncoder.encode(params.get(key)!=null?params.get(key).toString():"", "utf-8")).append("&");
				}
			}
			HttpContext httpContext = new BasicHttpContext();
			if (url.indexOf("?") > -1) {
				if (StringUtils.isNotBlank(paramStr)) {
					url = url + "&" + paramStr;
				}
			} else {
				if (StringUtils.isNotBlank(paramStr)) {
					url = url + "?" + paramStr;
				}
			}
			URI uri = new URI(url);
			httpGet.setURI(uri);
			httpGet.setConfig(config);
			response = httpClient.execute(httpGet, httpContext);
			HttpEntity entity = response.getEntity();
			responseContent = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			return responseContent;
		} catch (ClientProtocolException e) {
			throw new Exception("http get请求失败!" + e.getMessage(), e);
		} catch (IOException e) {
			throw new Exception("http get请求失败!" + e.getMessage(), e);
		} finally {
			httpGet.releaseConnection();
		}
	}

	/**
	 * 模拟post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String invorkHttpClientPostRequest(String url, Map params) throws HttpProcessException {
		String responseContent = null;
		CloseableHttpClient httpClient = getHttpclientConnection();
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000)
				.setSocketTimeout(10000).build();

		try {
			List formparams = new ArrayList();
			if (params != null) {
				for (Object key : params.keySet()) {
					formparams.add(new BasicNameValuePair(key.toString(), params.get(key)!=null?params.get(key).toString():""));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
			httpPost.setConfig(config);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			responseContent = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			return responseContent;
		} catch (ClientProtocolException e) {
			throw new HttpProcessException("http post请求失败!" + e.getMessage(), e);
		} catch (IOException e) {
			throw new HttpProcessException("http post请求失败!" + e.getMessage(), e);
		} finally {
			httpPost.releaseConnection();
		}
	}

	public static void main(String[] args) throws Exception {
		HttpUtils h = new HttpUtils();
		String url = "https://desc.alicdn.com/i7/430/980/43798770630/TB1H4VEKFXXXXXLXXXX8qtpFXlX.desc%7Cvar%5Edesc%3Bsign%5E73924a9a15bf37d8e794a88a4db9d5b1%3Blang%5Egbk%3Bt%5E1447834495";
		String html = h.invorkHttpClientGetRequest(url, null);
		System.out.println(html);

		String domain = "http://localhost:8080/services";
		Map params = new HashMap();
		String result = null;
		url = "/v1/systemUser/login";
		params.put("mobile", "13762368336");
		params.put("passWord", "123456");
		try {
			result = h.invorkHttpClientGetRequest(url, params);
			System.out.println(JsonUtils.toJson("=="));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map paramsMap = new HashMap();
		paramsMap.put("appid", WechatConfig.WECHAT_APP_ID);
		paramsMap.put("secret", WechatConfig.WECHAT_APP_SECRET);
		result = HttpUtils.invorkHttpClientGetRequest(WechatConfig.WECHAT_COMMON_TOKEN_URL, paramsMap);
		System.out.println(result);
	}

}
