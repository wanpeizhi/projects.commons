package com.chengjia.commons.wechat;

import java.util.Map;

import com.chengjia.commons.utils.JsonUtils;

/**
 * 消息模板对象
 */
public class WechatNoteTpl {

	// 接收用户openID
	private String touser;

	// 消息模板ID
	private String template_id;

	// 点击消息跳转url
	private String url;

	// 消息顶部的颜色
	private String topColor;

	// 参数列表
	private Map<String, WechatNoteTplParam> data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopColor() {
		return topColor;
	}

	public void setTopColor(String topColor) {
		this.topColor = topColor;
	}

	public Map<String, WechatNoteTplParam> getData() {
		return data;
	}

	public void setData(Map<String, WechatNoteTplParam> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}

}
