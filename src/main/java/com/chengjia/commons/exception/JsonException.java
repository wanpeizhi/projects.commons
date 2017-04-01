package com.chengjia.commons.exception;

public class JsonException extends CustomException {

	private static final long serialVersionUID = 348475180014962829L;

	public JsonException() {
		super();
	}

	public JsonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonException(String message) {
		super(message);
	}

	public JsonException(Throwable cause) {
		super(cause);
	}

}
