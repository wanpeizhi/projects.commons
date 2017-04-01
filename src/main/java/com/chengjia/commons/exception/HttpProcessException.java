package com.chengjia.commons.exception;

/**
 * http请求处理异常
 * 
 * @author Administrator
 *
 */
public class HttpProcessException extends CustomException {

	private static final long serialVersionUID = -5617278590507780150L;

	public HttpProcessException() {
		super();
	}

	public HttpProcessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HttpProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpProcessException(String message) {
		super(message);
	}

	public HttpProcessException(Throwable cause) {
		super(cause);
	}

}
