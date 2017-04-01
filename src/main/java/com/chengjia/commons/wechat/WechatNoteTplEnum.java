package com.chengjia.commons.wechat;

/**
 * 定义模板id和内容，内容仅展示
 * 
 * @author Administrator
 *
 */
public enum WechatNoteTplEnum {
	houseAccessInvite("", "【诚家网】您好,{{first}},{{second}}向您发送了看房邀请,请点击查看详情！");

	private final String id;
	private final String text;

	private WechatNoteTplEnum(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

}
