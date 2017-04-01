package com.chengjia.commons.wechat;

/**
 * 消息模板参数对象
 */
public class WechatNoteTplParam {

	// 参数值
	private String value;

	// 颜色
	private String color = "#000000";

	public WechatNoteTplParam(String value, String color) {
		this.value = value;
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
