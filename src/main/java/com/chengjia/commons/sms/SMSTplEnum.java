package com.chengjia.commons.sms;

public enum SMSTplEnum {
	verifCode("1", "【诚家好房】你的短信验证码为{1}，2分钟内输入有效！");

	private final String id;
	private final String text;

	private SMSTplEnum(String id, String text) {
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
